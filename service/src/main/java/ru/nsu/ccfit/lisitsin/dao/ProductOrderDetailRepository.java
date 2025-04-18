package ru.nsu.ccfit.lisitsin.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.nsu.ccfit.lisitsin.entity.ProductOrderDetail;

import java.util.List;

@Repository
public class ProductOrderDetailRepository extends GenericRepository<ProductOrderDetail> {

    public ProductOrderDetailRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, ProductOrderDetail.class);
    }

    public void create(long productOrderId, String productArticle, int productCount) {
        jdbcTemplate.update(
                "INSERT INTO product_order_details (product_order_id, product_article, product_count) VALUES (?, ?, ?)",
                productOrderId,
                productArticle,
                productCount
        );
    }

    @Override
    public void update(List<Object> params) {
        jdbcTemplate.update(
                "UPDATE product_order_details SET product_count = ? WHERE product_order_id = ? AND product_article = ?",
                Integer.valueOf(params.get(2).toString()),
                params.get(1),
                Long.valueOf(params.get(0).toString())
        );
    }
}
