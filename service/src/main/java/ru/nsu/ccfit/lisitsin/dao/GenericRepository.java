package ru.nsu.ccfit.lisitsin.dao;

import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@RequiredArgsConstructor
public abstract class GenericRepository<T> {

    protected final JdbcTemplate jdbcTemplate;

    protected final Class<T> entityClass;

    public List<T> findAll() {
        return jdbcTemplate.query("SELECT * FROM " + getTableName(entityClass), new BeanPropertyRowMapper<>(entityClass));
    }

    public <U> U findByField(Class<U> targetClass, String column, Object value) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM %s WHERE %s = ?".formatted(getTableName(targetClass), column),
                new BeanPropertyRowMapper<>(targetClass),
                value
        );
    }

    public void delete(List<Object> idValues, List<String> idColumns) {
        StringBuilder sql = new StringBuilder("DELETE FROM ")
                .append(getTableName(entityClass))
                .append(" WHERE ");

        for (int i = 0; i < idColumns.size(); i++) {
            if (i > 0) {
                sql.append(" AND ");
            }
            sql.append(idColumns.get(i)).append(" = ?");
        }

        jdbcTemplate.update(sql.toString(), idValues.toArray());
    }

    public abstract void update(List<Object> params);

    protected String getTableName(Class<?> clazz) {
        return clazz.getAnnotation(Table.class).name();
    }
}
