package ru.nsu.ccfit.lisitsin.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.ReflectionUtils;
import ru.nsu.ccfit.lisitsin.utils.ColumnView;
import ru.nsu.ccfit.lisitsin.utils.IdColumn;
import ru.nsu.ccfit.lisitsin.utils.TableView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

@RequiredArgsConstructor
public abstract class GenericRepository<T> {

    protected final JdbcTemplate jdbcTemplate;

    protected final Class<T> entityClass;

    public void create(Object... params) {
        jdbcTemplate.update(buildCreateQuery(), params);
    }

    public List<T> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM " + getTableName(entityClass),
                new BeanPropertyRowMapper<>(entityClass)
        );
    }

    public <U> U findByField(Class<U> targetClass, String column, Object value) {

        return jdbcTemplate.queryForObject(
                "SELECT * FROM %s WHERE %s = ? LIMIT 1".formatted(getTableName(targetClass), column),
                new BeanPropertyRowMapper<>(targetClass),
                value
        );
    }

    public void delete(T entity) {
        LinkedHashMap<String, Object> ids = extractIdColumns(entity);
        List<String> idColumns = ids.keySet().stream().toList();

        jdbcTemplate.update(buildDeleteQuery(idColumns), idColumns.stream().map(ids::get).toArray());
    }

    public void update(List<Object> params) {
        jdbcTemplate.update(buildUpdateQuery(), processFieldBeforeUpdate(params));
    }

    protected String getTableName(Class<?> clazz) {
        return clazz.getAnnotation(TableView.class).tableName();
    }

    protected String buildDeleteQuery(List<String> idColumns) {
        StringBuilder sql = new StringBuilder("DELETE FROM ").append(getTableName(entityClass)).append(" WHERE ");

        for (int i = 0; i < idColumns.size(); i++) {
            if (i > 0) {
                sql.append(" AND ");
            }

            sql.append(idColumns.get(i)).append(" = ?");
        }

        return sql.toString();
    }

    protected LinkedHashMap<String, Object> extractIdColumns(T entity) {
        LinkedHashMap<String, Object> idMap = new LinkedHashMap<>();

        ReflectionUtils.doWithFields(entity.getClass(), field -> {
            if (field.isAnnotationPresent(IdColumn.class)) {
                field.setAccessible(true);
                ColumnView columnView = field.getAnnotation(ColumnView.class);
                String columnName = columnView != null ? columnView.columnName() : field.getName();
                Object value = ReflectionUtils.getField(field, entity);
                idMap.put(columnName, value);
            }
        });

        return idMap;
    }

    protected String buildCreateQuery() {
        List<String> columns = new ArrayList<>();
        List<String> placeholders = new ArrayList<>();

        ReflectionUtils.doWithFields(entityClass, field -> {
            ColumnView columnView = field.getAnnotation(ColumnView.class);
            if (columnView != null && columnView.isCreationRequired()) {
                columns.add(columnView.columnName());
                placeholders.add("?");
            }
        });

        return "INSERT INTO %s (%s) VALUES (%s)".formatted(
                getTableName(entityClass),
                String.join(", ", columns),
                String.join(", ", placeholders)
        );
    }

    protected String buildUpdateQuery() {
        String tableName = getTableName(entityClass);

        List<String> settableColumns = new ArrayList<>();
        List<String> idColumns = new ArrayList<>();

        ReflectionUtils.doWithFields(entityClass, field -> {
            ColumnView columnView = field.getAnnotation(ColumnView.class);
            if (columnView != null) {
                String columnName = columnView.columnName();

                if (field.isAnnotationPresent(IdColumn.class)) {
                    idColumns.add(columnName + " = ?");
                } else if (columnView.isEditable()) {
                    settableColumns.add(columnName + " = ?");
                }
            }
        });

        if (idColumns.isEmpty()) {
            throw new IllegalStateException("No ID fields found in " + entityClass.getName());

        } else if (settableColumns.isEmpty()) {
            throw new RuntimeException("Недоступная операция");
        }

        return "UPDATE %s SET %s WHERE %s".formatted(
                tableName,
                String.join(", ", settableColumns),
                String.join(" AND ", idColumns)
        );
    }

    protected Object[] processFieldBeforeUpdate(List<Object> originalParams) {
        List<Integer> idFieldIndices = getIdFieldIndices(entityClass);
        List<Field> fields = Arrays.stream(entityClass.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(IdColumn.class) ||
                        (f.isAnnotationPresent(ColumnView.class) && f.getAnnotation(ColumnView.class).isEditable()))
                .toList();

        List<Object> nonIdParams = new ArrayList<>();
        List<Object> idParams = new ArrayList<>();

        for (int i = 0; i < originalParams.size(); i++) {
            if (idFieldIndices.contains(i)) {
                idParams.add(
                        processField(originalParams.get(i), fields.get(i).getType())
                );

            } else {
                nonIdParams.add(
                        processField(originalParams.get(i), fields.get(i).getType())
                );
            }
        }

        nonIdParams.addAll(idParams);

        return nonIdParams.toArray();
    }

    private Object processField(Object field, Class<?> type) {
        if (type == Integer.class) {
            return Integer.valueOf(field.toString());

        } else if (type == Long.class) {
            return Long.valueOf(field.toString());

        } else if (type == Double.class) {
            return Double.valueOf(field.toString());

        } else {
            return field;
        }
    }

    private List<Integer> getIdFieldIndices(Class<?> entityClass) {
        List<Integer> indices = new ArrayList<>();
        Field[] fields = entityClass.getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            if (fields[i].isAnnotationPresent(IdColumn.class)) {
                indices.add(i);
            }
        }

        return indices;
    }
}
