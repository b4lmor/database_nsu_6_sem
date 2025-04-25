package ru.nsu.ccfit.lisitsin.view.table.impl;

import com.vaadin.flow.router.Route;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.nsu.ccfit.lisitsin.dao.GenericRepository;
import ru.nsu.ccfit.lisitsin.entity.Vendor;
import ru.nsu.ccfit.lisitsin.view.table.DefaultTableView;

@Route("Поставщики")
public class VendorTableView extends DefaultTableView<Vendor> {

    public VendorTableView(JdbcTemplate jdbcTemplate) {
        super(Vendor.class, new GenericRepository<>(jdbcTemplate, Vendor.class) {});
    }

}
