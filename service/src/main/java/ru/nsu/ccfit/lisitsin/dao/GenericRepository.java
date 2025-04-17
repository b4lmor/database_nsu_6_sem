package ru.nsu.ccfit.lisitsin.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@RequiredArgsConstructor
public abstract class GenericRepository<T> {

    protected final JdbcTemplate jdbcTemplate;

    protected final Class<T> entityClass;

    public List<T> findAll() {
        String tableName = entityClass.getSimpleName().toLowerCase();
        return jdbcTemplate.query("SELECT * FROM " + tableName, new BeanPropertyRowMapper<>(entityClass));
    }

    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE id = ?", id);
    }

    protected String getTableName() {
        return entityClass.getSimpleName().toLowerCase();
    }
}
