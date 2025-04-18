package ru.nsu.ccfit.lisitsin.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.nsu.ccfit.lisitsin.entity.TradingPoint;

import java.util.List;

@Repository
public class TradingPointRepository extends GenericRepository<TradingPoint> {

    public TradingPointRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, TradingPoint.class);
    }

    public void create(long tpdId, long managerId, String name, double rentPayment, double tpSize) {
        jdbcTemplate.update(
                "INSERT INTO trading_point (tpb_id, manager_id, name, rent_payment, tp_size) VALUES (?, ?, ?, ?, ?)",
                tpdId,
                managerId,
                name,
                rentPayment,
                tpSize
        );
    }

    @Override
    public void update(List<Object> params) {
        jdbcTemplate.update(
                "UPDATE trading_point SET tpb_id = ?, manager_id = ?, name = ?, rent_payment = ?, tp_size = ?  WHERE id = ?",
                Long.valueOf(params.get(1).toString()),
                Long.valueOf(params.get(2).toString()),
                params.get(3),
                Double.valueOf(params.get(4).toString()),
                Double.valueOf(params.get(5).toString()),
                Long.valueOf(params.get(0).toString())
        );
    }

    public void updateDepartment(int departmentId, int tradingPointId) {
        jdbcTemplate.update(
                "CALL add_department_to_trading_point(?, ?)",
                departmentId,
                tradingPointId
        );
    }
}
