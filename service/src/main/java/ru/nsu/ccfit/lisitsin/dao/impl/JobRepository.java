package ru.nsu.ccfit.lisitsin.dao.impl;

import org.springframework.stereotype.Repository;
import ru.nsu.ccfit.lisitsin.dao.GenericRepository;
import ru.nsu.ccfit.lisitsin.dao.JdbcTemplateWrapper;
import ru.nsu.ccfit.lisitsin.entity.Job;

import java.util.List;

@Repository
public class JobRepository extends GenericRepository<Job> {

    public JobRepository(JdbcTemplateWrapper jdbcTemplateWrapper) {
        super(jdbcTemplateWrapper, Job.class);
    }

    @Override
    public void update(List<Object> params) {
        throw new RuntimeException("Недоступная операция");
    }

}
