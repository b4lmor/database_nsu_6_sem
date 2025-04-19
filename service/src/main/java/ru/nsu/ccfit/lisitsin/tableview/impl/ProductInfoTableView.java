package ru.nsu.ccfit.lisitsin.tableview.impl;

import com.vaadin.flow.router.Route;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.nsu.ccfit.lisitsin.dao.GenericRepository;
import ru.nsu.ccfit.lisitsin.entity.ProductInfo;
import ru.nsu.ccfit.lisitsin.tableview.DefaultTableView;

@Route("Информация о товарах")
public class ProductInfoTableView extends DefaultTableView<ProductInfo> {

    public ProductInfoTableView(JdbcTemplate jdbcTemplate) {
        super(ProductInfo.class, new GenericRepository<>(jdbcTemplate, ProductInfo.class) {});
    }

}
