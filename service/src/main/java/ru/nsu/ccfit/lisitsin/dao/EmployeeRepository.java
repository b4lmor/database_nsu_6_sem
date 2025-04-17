package ru.nsu.ccfit.lisitsin.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.nsu.ccfit.lisitsin.entity.Employee;

import java.time.LocalDate;

@Repository
public class EmployeeRepository extends GenericRepository<Employee> {

    public EmployeeRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, Employee.class);
    }

    public void create(String fullName, LocalDate birthDate) {
        jdbcTemplate.update(
                "INSERT INTO employee (full_name, birth_date) VALUES (?, ?)",
                fullName,
                birthDate
        );
    }

}
