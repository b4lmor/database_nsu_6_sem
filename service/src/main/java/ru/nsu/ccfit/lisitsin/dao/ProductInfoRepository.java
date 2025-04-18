package ru.nsu.ccfit.lisitsin.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.nsu.ccfit.lisitsin.entity.ProductInfo;

import java.time.LocalDate;
import java.util.List;

@Repository
public class ProductInfoRepository extends GenericRepository<ProductInfo> {

    public ProductInfoRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, ProductInfo.class);
    }

    public void create(String article, LocalDate deliveryDate, double price) {
        jdbcTemplate.update(
                "INSERT INTO product_info (product_article, delivery_date, price) VALUES (?, ?, ?)",
                article, deliveryDate, price
        );
    }

    @Override
    public void update(List<Object> params) {
        jdbcTemplate.update(
                "UPDATE product_info SET product_article = ?, delivery_date = ?, sell_date = ?, price = ? WHERE id = ?",
                    params.get(1),
                    params.get(2),
                    params.get(3),
                    Double.valueOf(params.get(4).toString()),
                    Long.valueOf(params.get(0).toString())
                );
    }
}
