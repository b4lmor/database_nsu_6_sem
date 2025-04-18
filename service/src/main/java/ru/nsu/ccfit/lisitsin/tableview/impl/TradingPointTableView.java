package ru.nsu.ccfit.lisitsin.tableview.impl;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import ru.nsu.ccfit.lisitsin.dao.TradingPointRepository;
import ru.nsu.ccfit.lisitsin.entity.TradingPoint;
import ru.nsu.ccfit.lisitsin.forms.FormBuilder;
import ru.nsu.ccfit.lisitsin.tableview.TableView;

@Route("Торговые точки")
public class TradingPointTableView extends TableView<TradingPoint> {

    private final TradingPointRepository tradingPointRepository;

    public TradingPointTableView(TradingPointRepository tradingPointRepository) {
        super(TradingPoint.class, tradingPointRepository);
        this.tradingPointRepository = tradingPointRepository;

        registerForm("Добавить торговую точку", createForm());
        registerForm("Прикрепить к секции", updateDepartmentForm());
    }

    private FormBuilder createForm() {
        return (form, dialog) -> {

            IntegerField tpdIdField = new IntegerField("ID Здания");
            IntegerField managerIdField = new IntegerField("ID Менеджера");
            TextField nameField = new TextField("Название");
            NumberField rentPaymentField = new NumberField("Стоимость аренды");
            NumberField sizeField = new NumberField("Площадь помещения");

            Button saveButton = new Button(
                    "Сохранить",
                    e -> {
                        tradingPointRepository.create(
                                tpdIdField.getValue(),
                                managerIdField.getValue(),
                                nameField.getValue(),
                                rentPaymentField.getValue(),
                                sizeField.getValue()
                        );

                        dialog.close();
                        refreshData();
                    }
            );

            form.add(tpdIdField, managerIdField, nameField, rentPaymentField, sizeField, saveButton);
        };
    }

    private FormBuilder updateDepartmentForm() {
        return (form, dialog) -> {

            IntegerField departmentIdField = new IntegerField("ID Секции");
            IntegerField tpIdField = new IntegerField("ID Торговой точки");

            Button saveButton = new Button(
                    "Сохранить",
                    e -> {
                        tradingPointRepository.updateDepartment(
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
