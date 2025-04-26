package ru.nsu.ccfit.lisitsin.view.table.impl;

import com.vaadin.flow.router.Route;
import ru.nsu.ccfit.lisitsin.dao.GenericRepository;
import ru.nsu.ccfit.lisitsin.dao.JdbcTemplateWrapper;
import ru.nsu.ccfit.lisitsin.entity.ClientInfo;
import ru.nsu.ccfit.lisitsin.view.table.DefaultTableView;

@Route("Информация о клиентах")
public class ClientInfoTableView extends DefaultTableView<ClientInfo> {

    public ClientInfoTableView(JdbcTemplateWrapper jdbcTemplateWrapper) {
        super(ClientInfo.class, new GenericRepository<>(jdbcTemplateWrapper, ClientInfo.class) {});
    }

}
