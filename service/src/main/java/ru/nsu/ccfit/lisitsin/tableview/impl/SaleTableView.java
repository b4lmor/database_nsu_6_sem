package ru.nsu.ccfit.lisitsin.tableview.impl;

import com.vaadin.flow.router.Route;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.nsu.ccfit.lisitsin.dao.GenericRepository;
import ru.nsu.ccfit.lisitsin.entity.Sale;
import ru.nsu.ccfit.lisitsin.tableview.DefaultTableView;

@Route("Продажи")
public class SaleTableView extends DefaultTableView<Sale> {

    public SaleTableView(JdbcTemplate jdbcTemplate) {
        super(Sale.class, new GenericRepository<>(jdbcTemplate, Sale.class) {});
    }

}
