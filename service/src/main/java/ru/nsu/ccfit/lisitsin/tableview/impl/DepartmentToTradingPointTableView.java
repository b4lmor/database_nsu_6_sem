package ru.nsu.ccfit.lisitsin.tableview.impl;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.Route;
import ru.nsu.ccfit.lisitsin.dao.DepartmentToTradingPointRepository;
import ru.nsu.ccfit.lisitsin.entity.DepartmentToTradingPoint;
import ru.nsu.ccfit.lisitsin.forms.FormBuilder;
import ru.nsu.ccfit.lisitsin.tableview.TableView;

@Route("Секции торговых точек")
public class DepartmentToTradingPointTableView extends TableView<DepartmentToTradingPoint> {

    private final DepartmentToTradingPointRepository repository;

    public DepartmentToTradingPointTableView(DepartmentToTradingPointRepository repository) {
        super(DepartmentToTradingPoint.class, repository);
        this.repository = repository;

        registerForm("Добавить торговую точку", registerForm());
    }

    private FormBuilder registerForm() {
        return (form, dialog) -> {

            IntegerField departmentIdField = new IntegerField("ID Секции");
            IntegerField tpIdField = new IntegerField("ID Торговой точки");

            Button saveButton = new Button(
                    "Сохранить",
                    e -> {
                        repository.create(
                                departmentIdField.getValue(),
                                tpIdField.getValue()
                        );

                        dialog.close();
                        refreshData();
                    }
            );

            form.add(tpIdField, departmentIdField, tpIdField, saveButton);
        };
    }

}
