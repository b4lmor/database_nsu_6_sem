package ru.nsu.ccfit.lisitsin.view.table.impl;

import com.vaadin.flow.router.Route;
import ru.nsu.ccfit.lisitsin.dao.GenericRepository;
import ru.nsu.ccfit.lisitsin.dao.JdbcTemplateWrapper;
import ru.nsu.ccfit.lisitsin.entity.TradingPointProduct;
import ru.nsu.ccfit.lisitsin.view.table.DefaultTableView;

@Route("Товары торговых точек")
public class TradingPointProductTableView extends DefaultTableView<TradingPointProduct> {

    public TradingPointProductTableView(JdbcTemplateWrapper jdbcTemplateWrapper) {
        super(TradingPointProduct.class, new GenericRepository<>(jdbcTemplateWrapper, TradingPointProduct.class) {});
    }

}
