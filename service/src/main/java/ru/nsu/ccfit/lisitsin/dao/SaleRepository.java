package ru.nsu.ccfit.lisitsin.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.nsu.ccfit.lisitsin.entity.Sale;

import java.util.List;

@Repository
public class SaleRepository extends GenericRepository<Sale> {

    public SaleRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, Sale.class);
    }

    public void create(long tppId, long clientInfoId, int saleCount) {
        jdbcTemplate.update(
                "INSERT INTO sale (tpp_id, client_info_id, sale_count, created_at) VALUES (?, ?, ?, NOW())",
                tppId, clientInfoId, saleCount
        );
    }

    @Override
    public void update(List<Object> params) {
        jdbcTemplate.update(
                "UPDATE sale SET tpp_id = ?, client_info_id = ?, sale_count = ? WHERE id = ?",
                    Long.valueOf(params.get(1).toString()),
                    Long.valueOf(params.get(2).toString()),
                    Integer.valueOf(params.get(3).toString()),
                    Long.valueOf(params.get(0).toString())
                );
    }
}
