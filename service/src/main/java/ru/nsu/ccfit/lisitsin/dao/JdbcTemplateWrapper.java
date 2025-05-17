package ru.nsu.ccfit.lisitsin.dao;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@Component
public class JdbcTemplateWrapper {

    private JdbcTemplate jdbcTemplate;

    @Value("${spring.datasource.url}")
    private String dataSourceUrl;

    @Value("${spring.datasource.driver-class-name}")
    private String dataSourceDriver;

    @Value("${spring.datasource.hikari.maximum-pool-size}")
    private Integer maximumPoolSize;

    @Value("${spring.datasource.hikari.connection-timeout}")
    private Integer connectionTimeout;

    public void connect(String username, String password) {
        close();

        HikariDataSource dataSource;

        try {
            dataSource = new HikariDataSource();
            dataSource.setDriverClassName(dataSourceDriver);
            dataSource.setJdbcUrl(dataSourceUrl);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            dataSource.setMaximumPoolSize(maximumPoolSize);
            dataSource.setConnectionTimeout(connectionTimeout);

            try (Connection ignored = dataSource.getConnection()) {
                jdbcTemplate = new JdbcTemplate(dataSource);
            }

        } catch (Exception e) {
            log.warn("Can't establish connection with database: {}", e.getMessage());

            close();

            throw new RuntimeException("Не удалось подключиться к базе данных: " + e.getMessage());
        }
    }

    public void close() {
        if (jdbcTemplate == null) {
            return;
        }

        HikariDataSource dataSource = (HikariDataSource) jdbcTemplate.getDataSource();

        if (dataSource == null) {
            return;
        }

        try {
            if (dataSource.getConnection() != null) {
                dataSource.getConnection().close();
            }

        } catch (Exception e) {
            log.error("Can't close connection with database: {}", e.getMessage());

            throw new RuntimeException("Не удалось закрыть соединение с базой данных: " + e.getMessage());
        }

        dataSource.close();

        jdbcTemplate = null;
    }

    public boolean isOpen() {
        return jdbcTemplate != null;
    }

    public <T> T produce(Function<JdbcTemplate, T> function) {
        if (jdbcTemplate != null) {
            return function.apply(jdbcTemplate);

        } else {
            throw new RuntimeException("Соединение с базой данных не установлено.");
        }
    }

    public void consume(Consumer<JdbcTemplate> consumer) {
        if (jdbcTemplate != null) {
            consumer.accept(jdbcTemplate);

        } else {
            throw new RuntimeException("Соединение с базой данных не установлено.");
        }
    }

    @PreDestroy
    private void destroy() {
        close();
    }
}
