package ru.nsu.ccfit.lisitsin.tableview.impl;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.Route;
import ru.nsu.ccfit.lisitsin.dao.VendorProductRepository;
import ru.nsu.ccfit.lisitsin.entity.VendorProduct;
import ru.nsu.ccfit.lisitsin.forms.FormBuilder;
import ru.nsu.ccfit.lisitsin.tableview.TableView;

@Route("Товары поставщиков")
public class VendorProductTableView extends TableView<VendorProduct> {

    private final VendorProductRepository repository;

    public VendorProductTableView(VendorProductRepository repository) {
        super(VendorProduct.class, repository);
        this.repository = repository;

        registerForm("Добавить торговую точку", registerForm());
    }

    private FormBuilder registerForm() {
        return (form, dialog) -> {

            IntegerField vendorIdField = new IntegerField("ID Поставщика");
            IntegerField productIdField = new IntegerField("ID Товара");

            Button saveButton = new Button(
                    "Сохранить",
                    e -> {
                        repository.create(
                                vendorIdField.getValue(),
                                productIdField.getValue()
                        );

                        dialog.close();
                        refreshData();
                    }
            );

            form.add(vendorIdField, vendorIdField, productIdField, saveButton);
        };
    }

}
