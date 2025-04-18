package ru.nsu.ccfit.lisitsin.tableview.impl;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import ru.nsu.ccfit.lisitsin.dao.ProductOrderDetailRepository;
import ru.nsu.ccfit.lisitsin.entity.ProductOrderDetail;
import ru.nsu.ccfit.lisitsin.forms.FormBuilder;
import ru.nsu.ccfit.lisitsin.tableview.TableView;

@Route("Детали заказов")
public class ProductOrderDetailTableView extends TableView<ProductOrderDetail> {

    private final ProductOrderDetailRepository repository;

    public ProductOrderDetailTableView(ProductOrderDetailRepository repository) {
        super(ProductOrderDetail.class, repository);
        this.repository = repository;

        registerForm("Добавить товар в заказ", registerForm());
    }

    private FormBuilder registerForm() {
        return (form, dialog) -> {

            IntegerField orderIdField = new IntegerField("ID Заказа");
            TextField productArticleField = new TextField("Артикль товара");
            IntegerField countField = new IntegerField("Кол-во товара");

            Button saveButton = new Button(
                    "Сохранить",
                    e -> {
                        repository.create(
                                orderIdField.getValue(),
                                productArticleField.getValue(),
                                countField.getValue()
                        );

                        dialog.close();
                        refreshData();
                    }
            );

            form.add(orderIdField, productArticleField, countField, saveButton);
        };
    }

}
