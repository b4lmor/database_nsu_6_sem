package ru.nsu.ccfit.lisitsin.tableview.impl;

import com.vaadin.flow.router.Route;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.nsu.ccfit.lisitsin.dao.GenericRepository;
import ru.nsu.ccfit.lisitsin.entity.TradingPointProduct;
import ru.nsu.ccfit.lisitsin.tableview.DefaultTableView;

@Route("Товары торговых точек")
public class TradingPointProductTableView extends DefaultTableView<TradingPointProduct> {

    public TradingPointProductTableView(JdbcTemplate jdbcTemplate) {
        super(TradingPointProduct.class, new GenericRepository<>(jdbcTemplate, TradingPointProduct.class) {});
    }

}
