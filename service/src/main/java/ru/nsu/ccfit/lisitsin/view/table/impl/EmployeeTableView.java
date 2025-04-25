package ru.nsu.ccfit.lisitsin.view.table.impl;

import com.vaadin.flow.router.Route;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.nsu.ccfit.lisitsin.dao.GenericRepository;
import ru.nsu.ccfit.lisitsin.entity.Employee;
import ru.nsu.ccfit.lisitsin.view.table.DefaultTableView;

@Route("Сотрудники")
public class EmployeeTableView extends DefaultTableView<Employee> {

    public EmployeeTableView(JdbcTemplate jdbcTemplate) {
        super(Employee.class, new GenericRepository<>(jdbcTemplate, Employee.class) {});
    }

}
