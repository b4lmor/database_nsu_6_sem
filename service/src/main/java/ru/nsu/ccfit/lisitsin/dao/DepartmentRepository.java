package ru.nsu.ccfit.lisitsin.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.nsu.ccfit.lisitsin.entity.Department;

import java.util.List;

@Repository
public class DepartmentRepository extends GenericRepository<Department> {

    public DepartmentRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, Department.class);
    }

    public void create(Integer tpbId, Integer managerId, String name, Integer floor) {
        jdbcTemplate.update(
                "INSERT INTO department (tpb_id, manager_id, name, floor) VALUES (?, ?, ?, ?)",
                tpbId,
                managerId,
                name,
                floor
        );
    }

    @Override
    public void update(List<Object> params) {
        jdbcTemplate.update(
                "UPDATE department SET tpb_id = ?, manager_id = ?, name = ?, floor = ? WHERE id = ?",
                Integer.valueOf(params.get(1).toString()),
                Integer.valueOf(params.get(2).toString()),
                params.get(2),
                Integer.valueOf(params.get(3).toString()),
                Long.valueOf(params.get(0).toString())
        );
    }
}
