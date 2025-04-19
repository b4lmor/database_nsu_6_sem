package ru.nsu.ccfit.lisitsin.tableview.impl;

import com.vaadin.flow.router.Route;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.nsu.ccfit.lisitsin.dao.impl.ProductOrderRepository;
import ru.nsu.ccfit.lisitsin.entity.ProductOrder;
import ru.nsu.ccfit.lisitsin.tableview.DefaultTableView;

@Route("Заказы товаров")
public class ProductOrderTableView extends DefaultTableView<ProductOrder> {

    public ProductOrderTableView(JdbcTemplate jdbcTemplate) {
        super(ProductOrder.class, new ProductOrderRepository(jdbcTemplate) {});
    }

}
