package ru.nsu.ccfit.lisitsin.view.table.impl;

import com.vaadin.flow.router.Route;
import ru.nsu.ccfit.lisitsin.dao.GenericRepository;
import ru.nsu.ccfit.lisitsin.dao.JdbcTemplateWrapper;
import ru.nsu.ccfit.lisitsin.entity.ProductOrderDetail;
import ru.nsu.ccfit.lisitsin.view.table.DefaultTableView;

@Route("Детали заказов")
public class ProductOrderDetailTableView extends DefaultTableView<ProductOrderDetail> {

    public ProductOrderDetailTableView(JdbcTemplateWrapper jdbcTemplateWrapper) {
        super(ProductOrderDetail.class, new GenericRepository<>(jdbcTemplateWrapper, ProductOrderDetail.class) {});
    }

}
