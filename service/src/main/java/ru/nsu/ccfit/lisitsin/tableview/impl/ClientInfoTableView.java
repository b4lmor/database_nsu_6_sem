package ru.nsu.ccfit.lisitsin.tableview.impl;

import com.vaadin.flow.router.Route;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.nsu.ccfit.lisitsin.dao.GenericRepository;
import ru.nsu.ccfit.lisitsin.entity.ClientInfo;
import ru.nsu.ccfit.lisitsin.tableview.DefaultTableView;

@Route("Информация о клиентах")
public class ClientInfoTableView extends DefaultTableView<ClientInfo> {

    public ClientInfoTableView(JdbcTemplate jdbcTemplate) {
        super(ClientInfo.class, new GenericRepository<>(jdbcTemplate, ClientInfo.class) {});
    }

}
