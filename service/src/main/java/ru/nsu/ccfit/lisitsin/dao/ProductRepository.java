package ru.nsu.ccfit.lisitsin.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.nsu.ccfit.lisitsin.entity.Product;

import java.util.List;

@Repository
public class ProductRepository extends GenericRepository<Product> {

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, Product.class);
    }

    public void create(String article, String name, String description, String photoUrl) {
        jdbcTemplate.update(
                "INSERT INTO product (article, name, description, photo_url) VALUES (?, ?, ?, ?)",
                article, name, description, photoUrl
        );
    }

    @Override
    public void update(List<Object> params) {
        jdbcTemplate.update(
                "UPDATE product SET name = ?, description = ?, photo_url = ? WHERE article = ?",
                params.get(1),
                params.get(2),
                params.get(3),
                params.get(0)
        );
    }
}
