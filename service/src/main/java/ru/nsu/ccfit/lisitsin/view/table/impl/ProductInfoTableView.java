package ru.nsu.ccfit.lisitsin.view.table.impl;

import com.vaadin.flow.router.Route;
import ru.nsu.ccfit.lisitsin.dao.GenericRepository;
import ru.nsu.ccfit.lisitsin.dao.JdbcTemplateWrapper;
import ru.nsu.ccfit.lisitsin.entity.ProductInfo;
import ru.nsu.ccfit.lisitsin.view.table.DefaultTableView;

@Route("Информация о товарах")
public class ProductInfoTableView extends DefaultTableView<ProductInfo> {

    public ProductInfoTableView(JdbcTemplateWrapper jdbcTemplateWrapper) {
        super(ProductInfo.class, new GenericRepository<>(jdbcTemplateWrapper, ProductInfo.class) {});
    }

}
