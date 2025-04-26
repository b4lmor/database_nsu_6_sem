package ru.nsu.ccfit.lisitsin.view.table.impl;

import com.vaadin.flow.router.Route;
import ru.nsu.ccfit.lisitsin.dao.GenericRepository;
import ru.nsu.ccfit.lisitsin.dao.JdbcTemplateWrapper;
import ru.nsu.ccfit.lisitsin.entity.Department;
import ru.nsu.ccfit.lisitsin.view.table.DefaultTableView;

@Route("Секции")
public class DepartmentTableView extends DefaultTableView<Department> {

    public DepartmentTableView(JdbcTemplateWrapper jdbcTemplateWrapper) {
        super(Department.class, new GenericRepository<>(jdbcTemplateWrapper, Department.class) {});
    }

}
