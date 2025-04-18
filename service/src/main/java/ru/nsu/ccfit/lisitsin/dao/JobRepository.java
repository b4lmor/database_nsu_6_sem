package ru.nsu.ccfit.lisitsin.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.nsu.ccfit.lisitsin.entity.Job;

import java.time.LocalDate;
import java.util.List;

@Repository
public class JobRepository extends GenericRepository<Job> {

    public JobRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, Job.class);
    }

    public void create(int employeeId, int tpId, LocalDate startDate, double salary, String jobTitle) {
        jdbcTemplate.update(
                "INSERT INTO job (employee_id, tp_id, start_date, salary, job_title) VALUES (?, ?, ?, ?, ?::job_title_type)",
                employeeId, tpId, startDate, salary, jobTitle
        );
    }

    @Override
    public void update(List<Object> params) {
        jdbcTemplate.update(
                "UPDATE job SET employee_id = ?, tp_id = ?, start_date = ?, end_date = ?, " +
                        "salary = ?, job_title = ?::job_title_type WHERE id = ?",
                    Long.valueOf(params.get(1).toString()),
                    Long.valueOf(params.get(2).toString()),
                    params.get(3),
                    params.get(4),
                    Double.valueOf(params.get(5).toString()),
                    params.get(6),
                    Long.valueOf(params.get(0).toString())
                );
    }
}
