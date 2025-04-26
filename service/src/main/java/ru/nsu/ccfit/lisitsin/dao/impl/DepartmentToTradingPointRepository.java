package ru.nsu.ccfit.lisitsin.dao.impl;

import org.springframework.stereotype.Repository;
import ru.nsu.ccfit.lisitsin.dao.GenericRepository;
import ru.nsu.ccfit.lisitsin.dao.JdbcTemplateWrapper;
import ru.nsu.ccfit.lisitsin.entity.DepartmentToTradingPoint;

@Repository
public class DepartmentToTradingPointRepository extends GenericRepository<DepartmentToTradingPoint> {

    public DepartmentToTradingPointRepository(JdbcTemplateWrapper jdbcTemplateWrapper) {
        super(jdbcTemplateWrapper, DepartmentToTradingPoint.class);
    }

    @Override
    public void create(Object... params) {
        jdbcTemplateWrapper.consume(
                jdbcTemplate ->
                        jdbcTemplate.update("CALL add_department_to_trading_point(?, ?)", params)
        );
    }

}
