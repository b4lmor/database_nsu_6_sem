package ru.nsu.ccfit.lisitsin.tableview.impl;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.Route;
import ru.nsu.ccfit.lisitsin.dao.TradingPointProductRepository;
import ru.nsu.ccfit.lisitsin.entity.TradingPointProduct;
import ru.nsu.ccfit.lisitsin.forms.FormBuilder;
import ru.nsu.ccfit.lisitsin.tableview.TableView;

@Route("Товары торговых точек")
public class TradingPointProductTableView extends TableView<TradingPointProduct> {

    private final TradingPointProductRepository repository;

    public TradingPointProductTableView(TradingPointProductRepository repository) {
        super(TradingPointProduct.class, repository);
        this.repository = repository;

        registerForm("Добавить торговую точку", registerForm());
    }

    private FormBuilder registerForm() {
        return (form, dialog) -> {

            IntegerField tpIdField = new IntegerField("ID Торговой точки");
            IntegerField productIdField = new IntegerField("ID Товара");

            Button saveButton = new Button(
                    "Сохранить",
                    e -> {
                        repository.create(
                                tpIdField.getValue(),
                                productIdField.getValue()
                        );

                        dialog.close();
                        refreshData();
                    }
            );

            form.add(tpIdField, tpIdField, productIdField, saveButton);
        };
    }

}
