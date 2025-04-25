package ru.nsu.ccfit.lisitsin.view.table.impl;

import com.vaadin.flow.router.Route;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.nsu.ccfit.lisitsin.dao.GenericRepository;
import ru.nsu.ccfit.lisitsin.entity.Job;
import ru.nsu.ccfit.lisitsin.view.table.DefaultTableView;

@Route("Должности сотрудников")
public class JobTableView extends DefaultTableView<Job> {

    public JobTableView(JdbcTemplate jdbcTemplate) {
        super(Job.class, new GenericRepository<>(jdbcTemplate, Job.class) {});
    }

}
