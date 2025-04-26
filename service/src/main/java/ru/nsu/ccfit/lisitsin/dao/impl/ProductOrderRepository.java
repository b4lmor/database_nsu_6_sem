package ru.nsu.ccfit.lisitsin.dao.impl;

import org.springframework.stereotype.Repository;
import ru.nsu.ccfit.lisitsin.dao.GenericRepository;
import ru.nsu.ccfit.lisitsin.dao.JdbcTemplateWrapper;
import ru.nsu.ccfit.lisitsin.entity.ProductOrder;

import java.util.List;

@Repository
public class ProductOrderRepository extends GenericRepository<ProductOrder> {

    public ProductOrderRepository(JdbcTemplateWrapper jdbcTemplateWrapper) {
        super(jdbcTemplateWrapper, ProductOrder.class);
    }

    @Override
    public void update(List<Object> params) {
        throw new RuntimeException("Недоступная операция");
    }

    public void confirmOrder(long id) {
        jdbcTemplateWrapper.consume(jdbcTemplate -> jdbcTemplate.update("CALL confirm_order(?)", id));
    }

    public void acceptOrder(long id) {
        jdbcTemplateWrapper.consume(jdbcTemplate -> jdbcTemplate.update("CALL accept_order(?)", id));
    }
}
