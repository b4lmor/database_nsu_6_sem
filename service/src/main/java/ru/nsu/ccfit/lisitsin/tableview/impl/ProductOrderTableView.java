package ru.nsu.ccfit.lisitsin.tableview.impl;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.Route;
import ru.nsu.ccfit.lisitsin.dao.impl.ProductOrderRepository;
import ru.nsu.ccfit.lisitsin.entity.ProductOrder;
import ru.nsu.ccfit.lisitsin.forms.FormBuilder;
import ru.nsu.ccfit.lisitsin.tableview.DefaultTableView;

@Route("Заказы товаров")
public class ProductOrderTableView extends DefaultTableView<ProductOrder> {

    private final ProductOrderRepository productOrderRepository;

    public ProductOrderTableView(ProductOrderRepository productOrderRepository) {
        super(ProductOrder.class, productOrderRepository);
        this.productOrderRepository = productOrderRepository;

        registerForm("Подтвердить заказ", registerConfirmForm());
        registerForm("Принять заказ", registerAcceptForm());
    }

    private FormBuilder registerConfirmForm() {
        return (form, dialog) -> {
            IntegerField idField = new IntegerField("ID Заказа");

            Button confirmButton = new Button(
                    "Подтвердить",
                    e -> {
                        productOrderRepository.confirmOrder(idField.getValue());

                        dialog.close();
                        refreshData();
                    }
            );

            form.add(idField, confirmButton);
        };
    }

    private FormBuilder registerAcceptForm() {
        return (form, dialog) -> {
            IntegerField idField = new IntegerField("ID Заказа");

            Button confirmButton = new Button(
                    "Подтвердить",
                    e -> {
                        productOrderRepository.acceptOrder(idField.getValue());

                        dialog.close();
                        refreshData();
                    }
            );

            form.add(idField, confirmButton);
        };
    }

}
