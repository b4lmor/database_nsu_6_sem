package ru.nsu.ccfit.lisitsin.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.util.ReflectionUtils;
import ru.nsu.ccfit.lisitsin.annotations.ColumnView;
import ru.nsu.ccfit.lisitsin.annotations.EnumColumn;
import ru.nsu.ccfit.lisitsin.annotations.IdColumn;
import ru.nsu.ccfit.lisitsin.annotations.TableView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public abstract class GenericRepository<T> {

    private static final Object[] EMPTY_PARAMS = new Object[]{};

    protected final JdbcTemplateWrapper jdbcTemplateWrapper;

    protected final Class<T> entityClass;

    public void create(Object... params) {
        jdbcTemplateWrapper.consume(jdbcTemplate -> jdbcTemplate.update(buildCreateQuery(), params));
    }

    public List<T> findAll(Map<Field, Object> filterItems) {
        return jdbcTemplateWrapper.produce(
                jdbcTemplate ->
                        jdbcTemplate.query(
                                buildSelectQueryWithFilters(filterItems),
                                new BeanPropertyRowMapper<>(entityClass),
                                filterItems == null ? EMPTY_PARAMS : filterItems.values().toArray()
                        )
        );
    }

    public <U> U findByField(Class<U> targetClass, String column, Object value) {
        return jdbcTemplateWrapper.produce(
                jdbcTemplate ->
                        jdbcTemplate.queryForObject(
                                "SELECT * FROM %s WHERE %s = ? LIMIT 1".formatted(getTableName(targetClass), column),
                                new BeanPropertyRowMapper<>(targetClass),
                                value
                        )
        );
    }

    public void delete(T entity) {
        LinkedHashMap<String, Object> ids = extractIdColumns(entity);
        List<String> idColumns = ids.keySet().stream().toList();

        jdbcTemplateWrapper.consume(
                jdbcTemplate ->
                jdbcTemplate.update(buildDeleteQuery(idColumns), idColumns.stream().map(ids::get).toArray())
        );
    }

    public void update(List<Object> params) {
        jdbcTemplateWrapper.consume(
                jdbcTemplate -> jdbcTemplate.update(buildUpdateQuery(), processFieldBeforeUpdate(params))
        );
    }

    protected String getTableName(Class<?> clazz) {
        return clazz.getAnnotation(TableView.class).tableName();
    }

    private String buildDeleteQuery(List<String> idColumns) {
        StringBuilder sql = new StringBuilder("DELETE FROM ").append(getTableName(entityClass)).append(" WHERE ");

        for (int i = 0; i < idColumns.size(); i++) {
            if (i > 0) {
                sql.append(" AND ");
            }

            sql.append(idColumns.get(i)).append(" = ?");
        }

        return sql.toString();
    }

    private LinkedHashMap<String, Object> extractIdColumns(T entity) {
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

    private String buildSelectQueryWithFilters(Map<Field, Object> filterItems) {
        if (filterItems == null || filterItems.isEmpty()) {
            return "SELECT * FROM " + getTableName(entityClass);
        }

        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM ").append(getTableName(entityClass));

        int paramCount = 0;

        for (Map.Entry<Field, Object> entry : filterItems.entrySet()) {
            if (paramCount == 0) {
                sqlBuilder.append(" WHERE ");
            }

            if (entry.getValue() == null || entry.getValue().toString().isBlank() ||
                    !entry.getKey().isAnnotationPresent(ColumnView.class) ||
                    !entry.getKey().getAnnotation(ColumnView.class).isVisible()) {
                continue;
            }

            if (paramCount > 0) {
                sqlBuilder.append(" AND ");
            }

            sqlBuilder.append(entry.getKey().getAnnotation(ColumnView.class).columnName());

            if (entry.getKey().isAnnotationPresent(EnumColumn.class)) {
                sqlBuilder.append("::text");
            }

            sqlBuilder.append(" = ?");

            paramCount++;
        }

        return sqlBuilder.toString();
    }

    private String buildCreateQuery() {
        List<String> columns = new ArrayList<>();
        List<String> placeholders = new ArrayList<>();

        ReflectionUtils.doWithFields(entityClass, field -> {
            ColumnView columnView = field.getAnnotation(ColumnView.class);
            if (columnView != null && columnView.isCreationRequired()) {
                columns.add(columnView.columnName());

                if (field.isAnnotationPresent(EnumColumn.class)) {
                    placeholders.add("?::" + field.getAnnotation(EnumColumn.class).enumName());

                } else {
                    placeholders.add("?");
                }
            }
        });

        return "INSERT INTO %s (%s) VALUES (%s)".formatted(
                getTableName(entityClass),
                String.join(", ", columns),
                String.join(", ", placeholders)
        );
    }

    private String buildUpdateQuery() {
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
                    String stmt = columnName + " = ?";

                    if (field.isAnnotationPresent(EnumColumn.class)) {
                        stmt += "::" + field.getAnnotation(EnumColumn.class).enumName();
                    }

                    settableColumns.add(stmt);
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

    private Object[] processFieldBeforeUpdate(List<Object> originalParams) {
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
