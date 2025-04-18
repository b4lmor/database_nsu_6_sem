package ru.nsu.ccfit.lisitsin.tableview.impl;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import ru.nsu.ccfit.lisitsin.dao.ProductRepository;
import ru.nsu.ccfit.lisitsin.entity.Product;
import ru.nsu.ccfit.lisitsin.forms.FormBuilder;
import ru.nsu.ccfit.lisitsin.tableview.TableView;

@Route("Товары")
public class ProductTableView extends TableView<Product> {

    private final ProductRepository productRepository;

    public ProductTableView(ProductRepository productRepository) {
        super(Product.class, productRepository);
        this.productRepository = productRepository;

        registerForm("Добавить товар", registerForm());

        grid.addColumn(
                new ComponentRenderer<>(
                        item -> {
                            if (item.getPhotoUrl() == null || item.getPhotoUrl().isBlank()) {
                                return new Span("No photo");
                            }

                            Image image = new Image(item.getPhotoUrl(), "Photo");
                            image.setHeight("100px");
                            image.setWidth("100px");

                            return image;
                        }
                )
        ).setHeader("Фото");
    }

    private FormBuilder registerForm() {
        return (form, dialog) -> {

            TextField articleField = new TextField("Артикль");
            TextField nameField = new TextField("Название");
            TextField descriptionField = new TextField("Описание");
            TextField photoUrlField = new TextField("URL фото");

            Button saveButton = new Button(
                    "Сохранить",
                    e -> {
                        productRepository.create(
                                articleField.getValue(),
                                nameField.getValue(),
                                descriptionField.getValue(),
                                photoUrlField.getValue()
                        );

                        dialog.close();
                        refreshData();
                    }
            );

            form.add(articleField, nameField, descriptionField, photoUrlField, saveButton);
        };
    }

}
