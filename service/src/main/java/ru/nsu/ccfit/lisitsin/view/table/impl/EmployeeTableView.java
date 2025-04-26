package ru.nsu.ccfit.lisitsin.view.table.impl;

import com.vaadin.flow.router.Route;
import ru.nsu.ccfit.lisitsin.dao.GenericRepository;
import ru.nsu.ccfit.lisitsin.dao.JdbcTemplateWrapper;
import ru.nsu.ccfit.lisitsin.entity.Employee;
import ru.nsu.ccfit.lisitsin.view.table.DefaultTableView;

@Route("Сотрудники")
public class EmployeeTableView extends DefaultTableView<Employee> {

    public EmployeeTableView(JdbcTemplateWrapper jdbcTemplateWrapper) {
        super(Employee.class, new GenericRepository<>(jdbcTemplateWrapper, Employee.class) {});
    }

}
