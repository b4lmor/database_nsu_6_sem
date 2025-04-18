package ru.nsu.ccfit.lisitsin.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.nsu.ccfit.lisitsin.entity.DepartmentToTradingPoint;

import java.util.List;

@Repository
public class DepartmentToTradingPointRepository extends GenericRepository<DepartmentToTradingPoint> {

    public DepartmentToTradingPointRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, DepartmentToTradingPoint.class);
    }

    @Override
    public void update(List<Object> params) {
        throw new RuntimeException("Недоступная операция");
    }

    public void create(int departmentId, int tradingPointId) {
        jdbcTemplate.update(
                "CALL add_department_to_trading_point(?, ?)",
                departmentId,
                tradingPointId
        );
    }
}
