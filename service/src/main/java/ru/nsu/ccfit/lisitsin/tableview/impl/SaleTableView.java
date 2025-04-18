package ru.nsu.ccfit.lisitsin.tableview.impl;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.Route;
import ru.nsu.ccfit.lisitsin.dao.SaleRepository;
import ru.nsu.ccfit.lisitsin.entity.Sale;
import ru.nsu.ccfit.lisitsin.forms.FormBuilder;
import ru.nsu.ccfit.lisitsin.tableview.TableView;

@Route("Продажи")
public class SaleTableView extends TableView<Sale> {

    private final SaleRepository saleRepository;

    public SaleTableView(SaleRepository saleRepository) {
        super(Sale.class, saleRepository);
        this.saleRepository = saleRepository;

        registerForm("Добавить продажу", registerForm());
    }

    private FormBuilder registerForm() {
        return (form, dialog) -> {

            IntegerField tppIdField = new IntegerField("ID Товара торговой точки");
            IntegerField clientInfoIdField = new IntegerField("ID Информации о клиенте");
            IntegerField saleCountField = new IntegerField("Кол-во продаж (за раз)");

            Button saveButton = new Button(
                    "Сохранить",
                    e -> {

                        saleRepository.create(
                                tppIdField.getValue(),
                                clientInfoIdField.getValue(),
                                saleCountField.getValue()
                        );

                        dialog.close();
                        refreshData();
                    }
            );

            form.add(tppIdField, clientInfoIdField, saleCountField, saveButton);
        };
    }

}
