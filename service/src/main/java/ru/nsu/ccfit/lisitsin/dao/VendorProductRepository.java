package ru.nsu.ccfit.lisitsin.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.nsu.ccfit.lisitsin.entity.VendorProduct;

import java.util.List;

@Repository
public class VendorProductRepository extends GenericRepository<VendorProduct> {

    public VendorProductRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, VendorProduct.class);
    }

    public void create(int vendorId, long productInfoId) {
        jdbcTemplate.update(
                "INSERT INTO vendor_product (vendor_id, product_info_id) VALUES (?, ?)",
                vendorId, productInfoId
        );
    }

    @Override
    public void update(List<Object> params) {
        jdbcTemplate.update(
                "UPDATE vendor_product SET vendor_id = ?, product_info_id = ? WHERE id = ?",
                Integer.valueOf(params.get(1).toString()),
                Long.valueOf(params.get(2).toString()),
                Long.valueOf(params.get(0).toString())
        );
    }
}
