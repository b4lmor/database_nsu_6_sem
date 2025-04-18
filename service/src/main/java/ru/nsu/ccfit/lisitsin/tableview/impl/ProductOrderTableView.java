package ru.nsu.ccfit.lisitsin.tableview.impl;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.Route;
import ru.nsu.ccfit.lisitsin.dao.ProductOrderRepository;
import ru.nsu.ccfit.lisitsin.entity.ProductOrder;
import ru.nsu.ccfit.lisitsin.forms.FormBuilder;
import ru.nsu.ccfit.lisitsin.tableview.TableView;

@Route("Заказы товаров")
public class ProductOrderTableView extends TableView<ProductOrder> {

    private final ProductOrderRepository productOrderRepository;

    public ProductOrderTableView(ProductOrderRepository productOrderRepository) {
        super(ProductOrder.class, productOrderRepository);
        this.productOrderRepository = productOrderRepository;

        registerForm("Добавить заказ", registerForm());
    }

    private FormBuilder registerForm() {
        return (form, dialog) -> {

            IntegerField managerIdField = new IntegerField("ID Менеджера");
            IntegerField vendorIdField = new IntegerField("ID Поставщика");
            IntegerField tpIdField = new IntegerField("ID Торговой точки");

            Button saveButton = new Button(
                    "Сохранить",
                    e -> {
                        productOrderRepository.create(
                                (long) managerIdField.getValue(),
                                (long) vendorIdField.getValue(),
                                (long) tpIdField.getValue()
                        );

                        dialog.close();
                        refreshData();
                    }
            );

            form.add(managerIdField, vendorIdField, tpIdField, saveButton);
        };
    }

}
