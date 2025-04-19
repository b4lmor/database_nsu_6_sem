package ru.nsu.ccfit.lisitsin.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.nsu.ccfit.lisitsin.dao.GenericRepository;
import ru.nsu.ccfit.lisitsin.entity.DepartmentToTradingPoint;

@Repository
public class DepartmentToTradingPointRepository extends GenericRepository<DepartmentToTradingPoint> {

    public DepartmentToTradingPointRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, DepartmentToTradingPoint.class);
    }

    @Override
    public void create(Object... params) {
        jdbcTemplate.update(
                "CALL add_department_to_trading_point(?, ?)",
                params
        );
    }

}
