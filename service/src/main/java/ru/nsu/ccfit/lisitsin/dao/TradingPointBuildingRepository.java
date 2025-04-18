package ru.nsu.ccfit.lisitsin.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.nsu.ccfit.lisitsin.entity.TradingPointBuilding;

import java.util.List;

@Repository
public class TradingPointBuildingRepository extends GenericRepository<TradingPointBuilding> {

    public TradingPointBuildingRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, TradingPointBuilding.class);
    }

    public void create(String address, String name, String type) {
        jdbcTemplate.update(
                "INSERT INTO trading_point_building (address, name, tp_type) VALUES (?, ?, ?::trading_point_type)",
                address, name, type
        );
    }

    @Override
    public void update(List<Object> params) {
        jdbcTemplate.update(
                "UPDATE trading_point_building SET address = ?, name = ?, tp_type = ?::trading_point_type WHERE id = ?",
                params.get(1),
                params.get(2),
                params.get(3),
                Long.valueOf(params.get(0).toString())
        );
    }

}
