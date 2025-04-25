package ru.nsu.ccfit.lisitsin.view.table.impl;

import com.vaadin.flow.router.Route;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.nsu.ccfit.lisitsin.dao.GenericRepository;
import ru.nsu.ccfit.lisitsin.entity.VendorProduct;
import ru.nsu.ccfit.lisitsin.view.table.DefaultTableView;

@Route("Товары поставщиков")
public class VendorProductTableView extends DefaultTableView<VendorProduct> {

    public VendorProductTableView(JdbcTemplate jdbcTemplate) {
        super(VendorProduct.class, new GenericRepository<>(jdbcTemplate, VendorProduct.class) {});
    }

}
