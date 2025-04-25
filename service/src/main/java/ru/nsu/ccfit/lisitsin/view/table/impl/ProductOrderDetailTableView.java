package ru.nsu.ccfit.lisitsin.view.table.impl;

import com.vaadin.flow.router.Route;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.nsu.ccfit.lisitsin.dao.GenericRepository;
import ru.nsu.ccfit.lisitsin.entity.ProductOrderDetail;
import ru.nsu.ccfit.lisitsin.view.table.DefaultTableView;

@Route("Детали заказов")
public class ProductOrderDetailTableView extends DefaultTableView<ProductOrderDetail> {

    public ProductOrderDetailTableView(JdbcTemplate jdbcTemplate) {
        super(ProductOrderDetail.class, new GenericRepository<>(jdbcTemplate, ProductOrderDetail.class) {});
    }

}
