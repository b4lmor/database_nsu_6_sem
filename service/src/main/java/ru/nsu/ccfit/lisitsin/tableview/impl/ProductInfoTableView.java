package ru.nsu.ccfit.lisitsin.tableview.impl;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import ru.nsu.ccfit.lisitsin.dao.ProductInfoRepository;
import ru.nsu.ccfit.lisitsin.entity.ProductInfo;
import ru.nsu.ccfit.lisitsin.forms.FormBuilder;
import ru.nsu.ccfit.lisitsin.tableview.TableView;

@Route("Информация о товарах")
public class ProductInfoTableView extends TableView<ProductInfo> {

    private final ProductInfoRepository productInfoRepository;

    public ProductInfoTableView(ProductInfoRepository productInfoRepository) {
        super(ProductInfo.class, productInfoRepository);
        this.productInfoRepository = productInfoRepository;

        registerForm("Добавить информацию о товаре", registerForm());
    }

    private FormBuilder registerForm() {
        return (form, dialog) -> {

            TextField nameField = new TextField("Артикль товара");
            DatePicker deliveryDate = new DatePicker("Дата доставки");
            NumberField priceField = new NumberField("Цена");

            Button saveButton = new Button(
                    "Сохранить",
                    e -> {
                        productInfoRepository.create(
                                nameField.getValue(),
                                deliveryDate.getValue(),
                                priceField.getValue()
                        );

                        dialog.close();
                        refreshData();
                    }
            );

            form.add(nameField, deliveryDate, priceField, saveButton);
        };
    }

}
