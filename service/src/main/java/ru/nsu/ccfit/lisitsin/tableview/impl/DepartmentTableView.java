package ru.nsu.ccfit.lisitsin.tableview.impl;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import ru.nsu.ccfit.lisitsin.dao.DepartmentRepository;
import ru.nsu.ccfit.lisitsin.entity.Department;
import ru.nsu.ccfit.lisitsin.forms.FormBuilder;
import ru.nsu.ccfit.lisitsin.tableview.TableView;

@Route("Секции")
public class DepartmentTableView extends TableView<Department> {

    private final DepartmentRepository departmentRepository;

    public DepartmentTableView(DepartmentRepository departmentRepository) {
        super(Department.class, departmentRepository);
        this.departmentRepository = departmentRepository;

        registerForm("Добавить секцию", registerForm());
    }

    private FormBuilder registerForm() {
        return (form, dialog) -> {

            IntegerField tpdIdField = new IntegerField("ID Здания");
            IntegerField managerIdField = new IntegerField("ID Менеджера");
            TextField nameField = new TextField("Название");
            IntegerField floorField = new IntegerField("Этаж");

            Button saveButton = new Button(
                    "Сохранить",
                    e -> {

                        departmentRepository.create(
                                tpdIdField.getValue(),
                                managerIdField.getValue(),
                                nameField.getValue(),
                                floorField.getValue()
                        );

                        dialog.close();
                        refreshData();
                    }
            );

            form.add(tpdIdField, managerIdField, nameField, floorField, saveButton);
        };
    }

}
