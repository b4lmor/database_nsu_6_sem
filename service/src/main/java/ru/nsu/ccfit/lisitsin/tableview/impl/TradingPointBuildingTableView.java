package ru.nsu.ccfit.lisitsin.tableview.impl;

import com.vaadin.flow.router.Route;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.nsu.ccfit.lisitsin.dao.GenericRepository;
import ru.nsu.ccfit.lisitsin.entity.TradingPointBuilding;
import ru.nsu.ccfit.lisitsin.tableview.DefaultTableView;

@Route("Здания торговых точек")
public class TradingPointBuildingTableView extends DefaultTableView<TradingPointBuilding> {

    public TradingPointBuildingTableView(JdbcTemplate jdbcTemplate) {
        super(TradingPointBuilding.class, new GenericRepository<>(jdbcTemplate, TradingPointBuilding.class) {});
    }

}
