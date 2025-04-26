package ru.nsu.ccfit.lisitsin.view.table.impl;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import ru.nsu.ccfit.lisitsin.dao.GenericRepository;
import ru.nsu.ccfit.lisitsin.dao.JdbcTemplateWrapper;
import ru.nsu.ccfit.lisitsin.entity.Product;
import ru.nsu.ccfit.lisitsin.view.table.DefaultTableView;

@Route("Товары")
public class ProductTableView extends DefaultTableView<Product> {

    public ProductTableView(JdbcTemplateWrapper jdbcTemplateWrapper) {
        super(Product.class, new GenericRepository<>(jdbcTemplateWrapper, Product.class) {});

        grid.addColumn(
                new ComponentRenderer<>(
                        item -> {
                            if (item.getPhotoUrl() == null || item.getPhotoUrl().isBlank()) {
                                return new Span("---");
                            }

                            Image image = new Image(item.getPhotoUrl(), "Photo");
                            image.setHeight("100px");
                            image.setWidth("100px");

                            return image;
                        }
                )
        ).setHeader("Фото");
    }

}
