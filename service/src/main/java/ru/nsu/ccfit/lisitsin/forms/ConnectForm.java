package ru.nsu.ccfit.lisitsin.forms;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.TextField;
import ru.nsu.ccfit.lisitsin.dao.JdbcTemplateWrapper;

public class ConnectForm extends DefaultForm {

    public ConnectForm(JdbcTemplateWrapper jdbcTemplateWrapper) {
        super(registerForm(jdbcTemplateWrapper));
    }

    private static FormBuilder registerForm(JdbcTemplateWrapper jdbcTemplateWrapper) {
        return (form, dialog) -> {

            TextField userNameField = new TextField("Username");
            TextField passwordField = new TextField("Password");

            Button saveButton = new Button(
                    "Подключиться",
                    e -> {
                        jdbcTemplateWrapper.connect(userNameField.getValue(), passwordField.getValue());
                        dialog.close();
                    }
            );

            form.add(userNameField, passwordField, saveButton);
        };
    }
}
