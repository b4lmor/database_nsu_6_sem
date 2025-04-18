package ru.nsu.ccfit.lisitsin.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.nsu.ccfit.lisitsin.entity.Employee;

import java.time.LocalDate;
import java.util.List;

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

    @Override
    public void update(List<Object> params) {
        jdbcTemplate.update(
                "UPDATE employee SET full_name = ?, birth_date = ?, hire_date = ?, resignation_date = ? WHERE id = ?",
                    params.get(1),
                    params.get(2),
                    params.get(3),
                    params.get(4),
                    Long.valueOf(params.get(0).toString())
                );
    }
}
