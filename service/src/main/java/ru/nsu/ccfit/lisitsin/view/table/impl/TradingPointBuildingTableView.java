package ru.nsu.ccfit.lisitsin.view.table.impl;

import com.vaadin.flow.router.Route;
import ru.nsu.ccfit.lisitsin.dao.GenericRepository;
import ru.nsu.ccfit.lisitsin.dao.JdbcTemplateWrapper;
import ru.nsu.ccfit.lisitsin.entity.TradingPointBuilding;
import ru.nsu.ccfit.lisitsin.view.table.DefaultTableView;

@Route("Здания торговых точек")
public class TradingPointBuildingTableView extends DefaultTableView<TradingPointBuilding> {

    public TradingPointBuildingTableView(JdbcTemplateWrapper jdbcTemplateWrapper) {
        super(TradingPointBuilding.class, new GenericRepository<>(jdbcTemplateWrapper, TradingPointBuilding.class) {});
    }

}
