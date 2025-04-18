package ru.nsu.ccfit.lisitsin.tableview.impl;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import ru.nsu.ccfit.lisitsin.dao.TradingPointBuildingRepository;
import ru.nsu.ccfit.lisitsin.entity.TradingPointBuilding;
import ru.nsu.ccfit.lisitsin.entity.TradingPointBuildingType;
import ru.nsu.ccfit.lisitsin.forms.FormBuilder;
import ru.nsu.ccfit.lisitsin.tableview.TableView;

@Route("Здания торговых точек")
public class TradingPointBuildingTableView extends TableView<TradingPointBuilding> {

    private final TradingPointBuildingRepository tradingPointBuildingRepository;

    public TradingPointBuildingTableView(TradingPointBuildingRepository tradingPointBuildingRepository) {
        super(TradingPointBuilding.class, tradingPointBuildingRepository);
        this.tradingPointBuildingRepository = tradingPointBuildingRepository;

        registerForm("Добавить здание", registerForm());
    }

    private FormBuilder registerForm() {
        return (form, dialog) -> {

            TextField addressField = new TextField("Адрес");
            TextField nameField = new TextField("Название");
            Select<TradingPointBuildingType> tradingPointBuildingTypeField = new Select<>();

            tradingPointBuildingTypeField.setLabel("Тип");
            tradingPointBuildingTypeField.setItems(TradingPointBuildingType.values());

            Button saveButton = new Button(
                    "Сохранить",
                    e -> {

                        tradingPointBuildingRepository.create(
                                addressField.getValue(),
                                nameField.getValue(),
                                tradingPointBuildingTypeField.getValue().name()
                        );

                        dialog.close();
                        refreshData();
                    }
            );

            form.add(addressField, nameField, tradingPointBuildingTypeField, saveButton);
        };
    }

}
