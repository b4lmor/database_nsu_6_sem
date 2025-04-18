package ru.nsu.ccfit.lisitsin.tableview.impl;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import ru.nsu.ccfit.lisitsin.dao.ClientInfoRepository;
import ru.nsu.ccfit.lisitsin.entity.ClientInfo;
import ru.nsu.ccfit.lisitsin.forms.FormBuilder;
import ru.nsu.ccfit.lisitsin.tableview.TableView;

@Route("Информация о клиентах")
public class ClientInfoTableView extends TableView<ClientInfo> {

    private final ClientInfoRepository clientInfoRepository;

    public ClientInfoTableView(ClientInfoRepository clientInfoRepository) {
        super(ClientInfo.class, clientInfoRepository);
        this.clientInfoRepository = clientInfoRepository;

        registerForm("Добавить информацию о клиенте", registerForm());
    }

    private FormBuilder registerForm() {
        return (form, dialog) -> {

            TextField nameField = new TextField("ФИО");
            DatePicker birthDate = new DatePicker("Дата рождения");
            NumberField heightField = new NumberField("Рост");
            NumberField weightField = new NumberField("Вес");
            TextField specificityField = new TextField("Доп. информация");
            TextField phoneField = new TextField("Номер телефона");
            EmailField emailField = new EmailField("Email");

            Button saveButton = new Button(
                    "Сохранить",
                    e -> {
                        clientInfoRepository.create(
                                nameField.getValue(),
                                birthDate.getValue(),
                                heightField.getValue(),
                                weightField.getValue(),
                                specificityField.getValue(),
                                phoneField.getValue(),
                                emailField.getValue()
                        );

                        dialog.close();
                        refreshData();
                    }
            );

            form.add(nameField, birthDate, heightField, weightField, specificityField, phoneField, emailField, saveButton);
        };
    }

}
