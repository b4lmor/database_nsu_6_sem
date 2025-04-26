package ru.nsu.ccfit.lisitsin.view.table.impl;

import com.vaadin.flow.router.Route;
import ru.nsu.ccfit.lisitsin.dao.GenericRepository;
import ru.nsu.ccfit.lisitsin.dao.JdbcTemplateWrapper;
import ru.nsu.ccfit.lisitsin.entity.Sale;
import ru.nsu.ccfit.lisitsin.view.table.DefaultTableView;

@Route("Продажи")
public class SaleTableView extends DefaultTableView<Sale> {

    public SaleTableView(JdbcTemplateWrapper jdbcTemplateWrapper) {
        super(Sale.class, new GenericRepository<>(jdbcTemplateWrapper, Sale.class) {});
    }

}
