package ru.nsu.ccfit.lisitsin.view.table.impl;

import com.vaadin.flow.router.Route;
import ru.nsu.ccfit.lisitsin.dao.JdbcTemplateWrapper;
import ru.nsu.ccfit.lisitsin.dao.impl.DepartmentToTradingPointRepository;
import ru.nsu.ccfit.lisitsin.entity.DepartmentToTradingPoint;
import ru.nsu.ccfit.lisitsin.view.table.DefaultTableView;

@Route("Секции торговых точек")
public class DepartmentToTradingPointTableView extends DefaultTableView<DepartmentToTradingPoint> {

    public DepartmentToTradingPointTableView(JdbcTemplateWrapper jdbcTemplateWrapper) {
        super(DepartmentToTradingPoint.class, new DepartmentToTradingPointRepository(jdbcTemplateWrapper));
    }

}
