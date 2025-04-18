package ru.nsu.ccfit.lisitsin.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.nsu.ccfit.lisitsin.entity.TradingPointProduct;

import java.util.List;

@Repository
public class TradingPointProductRepository extends GenericRepository<TradingPointProduct> {

    public TradingPointProductRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, TradingPointProduct.class);
    }

    public void create(int tradingPointId, long productInfoId) {
        jdbcTemplate.update(
                "INSERT INTO trading_point_product (tp_id, product_info_id) VALUES (?, ?)",
                tradingPointId, productInfoId
        );
    }

    @Override
    public void update(List<Object> params) {
        jdbcTemplate.update(
                "UPDATE trading_point_product SET tp_id = ?, product_info_id = ? WHERE id = ?",
                Integer.valueOf(params.get(1).toString()),
                Long.valueOf(params.get(2).toString()),
                Long.valueOf(params.get(0).toString())
        );
    }
}
