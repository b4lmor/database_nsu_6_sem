package ru.nsu.ccfit.lisitsin.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.nsu.ccfit.lisitsin.entity.ProductOrder;

import java.util.List;

@Repository
public class ProductOrderRepository extends GenericRepository<ProductOrder> {

    public ProductOrderRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, ProductOrder.class);
    }

    public void create(Long managerId, Long vendorId, Long tpId) {
        jdbcTemplate.update(
                "INSERT INTO product_order (manager_id, vendor_id, tp_id, create_date) VALUES (?, ?, ?, NOW())",
                managerId,
                vendorId,
                tpId
        );
    }

    @Override
    public void update(List<Object> params) {
        throw new RuntimeException("Недоступная операция");
    }
}
