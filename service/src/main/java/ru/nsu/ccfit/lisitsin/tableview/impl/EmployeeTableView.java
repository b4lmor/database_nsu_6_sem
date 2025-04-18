package ru.nsu.ccfit.lisitsin.tableview.impl;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import ru.nsu.ccfit.lisitsin.dao.EmployeeRepository;
import ru.nsu.ccfit.lisitsin.entity.Employee;
import ru.nsu.ccfit.lisitsin.forms.FormBuilder;
import ru.nsu.ccfit.lisitsin.tableview.TableView;

@Route("Сотрудники")
public class EmployeeTableView extends TableView<Employee> {

    private final EmployeeRepository employeeRepository;

    public EmployeeTableView(EmployeeRepository employeeRepository) {
        super(Employee.class, employeeRepository);
        this.employeeRepository = employeeRepository;

        registerForm("Добавить сотрудника", registerForm());
    }

    private FormBuilder registerForm() {
        return (form, dialog) -> {

            TextField nameField = new TextField("ФИО");
            DatePicker birthDate = new DatePicker("Дата рождения");

            Button saveButton = new Button(
                    "Сохранить",
                    e -> {
                        employeeRepository.create(nameField.getValue(), birthDate.getValue());

                        dialog.close();
                        refreshData();
                    }
            );

            form.add(nameField, birthDate, saveButton);
        };
    }

}
