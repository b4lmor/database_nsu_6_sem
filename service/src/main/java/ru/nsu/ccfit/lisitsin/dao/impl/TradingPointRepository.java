package ru.nsu.ccfit.lisitsin.dao.impl;

import org.springframework.stereotype.Repository;
import ru.nsu.ccfit.lisitsin.dao.GenericRepository;
import ru.nsu.ccfit.lisitsin.dao.JdbcTemplateWrapper;
import ru.nsu.ccfit.lisitsin.entity.TradingPoint;

@Repository
public class TradingPointRepository extends GenericRepository<TradingPoint> {

    public TradingPointRepository(JdbcTemplateWrapper jdbcTemplateWrapper) {
        super(jdbcTemplateWrapper, TradingPoint.class);
    }

    public void updateDepartment(int departmentId, int tradingPointId) {
        jdbcTemplateWrapper.consume(
                jdbcTemplate ->
                        jdbcTemplate.update(
                                "CALL add_department_to_trading_point(?, ?)",
                                departmentId,
                                tradingPointId
                        )
        );
    }
}
