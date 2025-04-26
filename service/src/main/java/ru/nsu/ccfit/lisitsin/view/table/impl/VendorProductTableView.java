package ru.nsu.ccfit.lisitsin.view.table.impl;

import com.vaadin.flow.router.Route;
import ru.nsu.ccfit.lisitsin.dao.GenericRepository;
import ru.nsu.ccfit.lisitsin.dao.JdbcTemplateWrapper;
import ru.nsu.ccfit.lisitsin.entity.VendorProduct;
import ru.nsu.ccfit.lisitsin.view.table.DefaultTableView;

@Route("Товары поставщиков")
public class VendorProductTableView extends DefaultTableView<VendorProduct> {

    public VendorProductTableView(JdbcTemplateWrapper jdbcTemplateWrapper) {
        super(VendorProduct.class, new GenericRepository<>(jdbcTemplateWrapper, VendorProduct.class) {});
    }

}
