package ru.nsu.ccfit.lisitsin.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.nsu.ccfit.lisitsin.dao.GenericRepository;
import ru.nsu.ccfit.lisitsin.entity.TradingPoint;

@Repository
public class TradingPointRepository extends GenericRepository<TradingPoint> {

    public TradingPointRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, TradingPoint.class);
    }

    public void updateDepartment(int departmentId, int tradingPointId) {
        jdbcTemplate.update(
                "CALL add_department_to_trading_point(?, ?)",
                departmentId,
                tradingPointId
        );
    }
}
