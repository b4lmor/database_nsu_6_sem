package ru.nsu.ccfit.lisitsin.tableview.impl;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import ru.nsu.ccfit.lisitsin.dao.VendorRepository;
import ru.nsu.ccfit.lisitsin.entity.Vendor;
import ru.nsu.ccfit.lisitsin.forms.FormBuilder;
import ru.nsu.ccfit.lisitsin.tableview.TableView;

@Route("Поставщики")
public class VendorTableView extends TableView<Vendor> {

    private final VendorRepository vendorRepository;

    public VendorTableView(VendorRepository vendorRepository) {
        super(Vendor.class, vendorRepository);
        this.vendorRepository = vendorRepository;

        registerForm("Добавить поставщика", registerForm());
    }

    private FormBuilder registerForm() {
        return (form, dialog) -> {

            TextField nameField = new TextField("Название");
            TextField addressField = new TextField("Адрес");

            Button saveButton = new Button(
                    "Сохранить",
                    e -> {
                        vendorRepository.create(nameField.getValue(), addressField.getValue());

                        dialog.close();
                        refreshData();
                    }
            );

            form.add(nameField, addressField, saveButton);
        };
    }

}
