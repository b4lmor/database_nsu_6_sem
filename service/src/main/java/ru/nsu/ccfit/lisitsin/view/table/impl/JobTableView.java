package ru.nsu.ccfit.lisitsin.view.table.impl;

import com.vaadin.flow.router.Route;
import ru.nsu.ccfit.lisitsin.dao.impl.JobRepository;
import ru.nsu.ccfit.lisitsin.entity.Job;
import ru.nsu.ccfit.lisitsin.view.table.DefaultTableView;

@Route("Должности сотрудников")
public class JobTableView extends DefaultTableView<Job> {

    public JobTableView(JobRepository jobRepository) {
        super(Job.class, jobRepository);
    }

}
