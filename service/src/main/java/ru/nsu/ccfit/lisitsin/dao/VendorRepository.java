package ru.nsu.ccfit.lisitsin.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.nsu.ccfit.lisitsin.entity.Vendor;

import java.util.List;

@Repository
public class VendorRepository extends GenericRepository<Vendor> {

    public VendorRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, Vendor.class);
    }

    public void create(String fullName, String address) {
        jdbcTemplate.update("INSERT INTO vendor (name, address) VALUES (?, ?)", fullName, address);
    }

    @Override
    public void update(List<Object> params) {
        jdbcTemplate.update(
                "UPDATE vendor SET name = ?, address = ? WHERE id = ?",
                    params.get(1),
                    params.get(2),
                    Long.valueOf(params.get(0).toString())
                );
    }
}
