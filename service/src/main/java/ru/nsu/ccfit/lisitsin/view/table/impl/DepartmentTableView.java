package ru.nsu.ccfit.lisitsin.view.table.impl;

import com.vaadin.flow.router.Route;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.nsu.ccfit.lisitsin.dao.GenericRepository;
import ru.nsu.ccfit.lisitsin.entity.Department;
import ru.nsu.ccfit.lisitsin.view.table.DefaultTableView;

@Route("Секции")
public class DepartmentTableView extends DefaultTableView<Department> {

    public DepartmentTableView(JdbcTemplate jdbcTemplate) {
        super(Department.class, new GenericRepository<>(jdbcTemplate, Department.class) {});
    }

}
