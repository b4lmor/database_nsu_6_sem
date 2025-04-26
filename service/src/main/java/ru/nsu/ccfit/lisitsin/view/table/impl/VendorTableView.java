package ru.nsu.ccfit.lisitsin.view.table.impl;

import com.vaadin.flow.router.Route;
import ru.nsu.ccfit.lisitsin.dao.GenericRepository;
import ru.nsu.ccfit.lisitsin.dao.JdbcTemplateWrapper;
import ru.nsu.ccfit.lisitsin.entity.Vendor;
import ru.nsu.ccfit.lisitsin.view.table.DefaultTableView;

@Route("Поставщики")
public class VendorTableView extends DefaultTableView<Vendor> {

    public VendorTableView(JdbcTemplateWrapper jdbcTemplateWrapper) {
        super(Vendor.class, new GenericRepository<>(jdbcTemplateWrapper, Vendor.class) {});
    }

}
