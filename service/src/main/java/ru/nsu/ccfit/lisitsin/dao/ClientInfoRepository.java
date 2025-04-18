package ru.nsu.ccfit.lisitsin.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.nsu.ccfit.lisitsin.entity.ClientInfo;

import java.time.LocalDate;
import java.util.List;

@Repository
public class ClientInfoRepository extends GenericRepository<ClientInfo> {

    public ClientInfoRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, ClientInfo.class);
    }

    public void create(String fullName, LocalDate birthDate, double height, double weight, String specificity, String phone, String email) {
        jdbcTemplate.update(
                "INSERT INTO client_info (full_name, birth_date, height, weight, specificity, phone, email) VALUES (?, ?, ?, ?, ?, ?, ?)",
                fullName,
                birthDate,
                height,
                weight,
                specificity,
                phone,
                email
        );
    }

    @Override
    public void update(List<Object> params) {
        jdbcTemplate.update(
                "UPDATE client_info SET full_name = ?, birth_date = ?, height = ?, weight = ?, specificity = ?, phone = ?, email = ? WHERE id = ?",
                    params.get(1),
                    params.get(2),
                    Double.valueOf(params.get(3).toString()),
                    Double.valueOf(params.get(4).toString()),
                    params.get(5),
                    params.get(6),
                    params.get(7),
                    Long.valueOf(params.get(0).toString())
                );
    }
}
