package ru.nsu.ccfit.lisitsin.view.main;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;
import ru.nsu.ccfit.lisitsin.annotations.TableView;
import ru.nsu.ccfit.lisitsin.dao.JdbcTemplateWrapper;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class TableButtonsSection extends VerticalLayout {

    private final JdbcTemplateWrapper jdbcTemplateWrapper;

    @PostConstruct
    private void init() {
        setWidth("250px");
        setHeightFull();
        setSpacing(false);
        setPadding(false);
        getStyle()
                .set("border-right", "1px solid var(--lumo-contrast-10pct)")
                .set("background", "var(--lumo-contrast-5pct)");

        H2 menuHeader = new H2("Торговая организация Trado");
        menuHeader.getStyle()
                .set("margin", "0 auto")
                .set("padding", "1em");

        add(menuHeader);
        addButtons(this);
    }

    private void addButtons(VerticalLayout sideMenu) {
        Set<Class<?>> entityClasses = new Reflections("ru.nsu.ccfit.lisitsin.entity")
                .getTypesAnnotatedWith(TableView.class);

        Queue<Class<?>> buttonClasses = new PriorityQueue<>(
                (o1, o2) -> {
                    TableView tableAnnotation1 = o1.getAnnotation(TableView.class);
                    TableView tableAnnotation2 = o2.getAnnotation(TableView.class);
                    return tableAnnotation1.order() - tableAnnotation2.order();
                }
        );

        entityClasses.forEach(entityClass -> {
            TableView tableAnnotation = entityClass.getAnnotation(TableView.class);
            if (tableAnnotation != null && tableAnnotation.isVisible()) {
                buttonClasses.add(entityClass);
            }
        });

        while (!buttonClasses.isEmpty()) {
            Class<?> entityClass = buttonClasses.poll();
            TableView tableAnnotation = entityClass.getAnnotation(TableView.class);
            String tableName = tableAnnotation.viewName();

            Button button = new Button(tableName);
            button.setWidthFull();
            button.getStyle()
                    .set("text-align", "left")
                    .set("padding", "0.5em 1em")
                    .set("border-radius", "0");

            button.addClickListener(event -> {
                sideMenu.getChildren()
                        .filter(component -> component instanceof Button)
                        .forEach(component -> component.getStyle().remove("background-color"));

                button.getStyle().set("background-color", "var(--lumo-primary-color-10pct)");

                if (jdbcTemplateWrapper.isOpen()) {
                    UI.getCurrent().navigate(tableName);

                } else {
                    Notification notification = Notification.show(
                            "Ошибка: Не удалось установить соединение с базой данных.",
                            5000,
                            Notification.Position.MIDDLE
                    );
                    notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
            });

            sideMenu.add(button);
        }

    }
}
