package ru.nsu.ccfit.lisitsin;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.reflections.Reflections;
import ru.nsu.ccfit.lisitsin.utils.TableViewName;

import java.util.Set;

@Route("")
public class MainView extends HorizontalLayout {

    private final Div content = new Div();

    public MainView() {
        setSizeFull();
        setSpacing(false);
        setPadding(false);
        setMargin(false);

        VerticalLayout sideMenu = new VerticalLayout();
        sideMenu.setWidth("250px");
        sideMenu.setHeightFull();
        sideMenu.setSpacing(false);
        sideMenu.setPadding(false);
        sideMenu.getStyle()
                .set("border-right", "1px solid var(--lumo-contrast-10pct)")
                .set("background", "var(--lumo-contrast-5pct)");

        H2 menuHeader = new H2("Торговая организация Trado");
        menuHeader.getStyle()
                .set("margin", "0 auto")
                .set("padding", "1em");
        sideMenu.add(menuHeader);

        Set<Class<?>> entityClasses = new Reflections("ru.nsu.ccfit.lisitsin.entity")
                .getTypesAnnotatedWith(TableViewName.class);

        entityClasses.forEach(entityClass -> {
            TableViewName tableAnnotation = entityClass.getAnnotation(TableViewName.class);
            if (tableAnnotation != null && tableAnnotation.isVisible()) {
                String tableName = tableAnnotation.value();
                Button button = new Button(tableName);
                button.setWidthFull();
                button.getStyle()
                        .set("text-align", "left")
                        .set("padding", "0.5em 1em")
                        .set("border-radius", "0");

                button.addClickListener(event -> {
                    sideMenu.getChildren()
                            .filter(component -> component instanceof Button)
                            .forEach(component -> component.getStyle()
                                    .remove("background-color"));

                    button.getStyle().set("background-color", "var(--lumo-primary-color-10pct)");

                    UI.getCurrent().navigate(tableName);
                });

                sideMenu.add(button);
            }
        });

        content.setSizeFull();
        content.getStyle()
                .set("padding", "1em");

        add(sideMenu, content);
        setFlexGrow(1, content);
    }
}