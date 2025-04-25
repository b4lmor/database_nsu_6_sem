package ru.nsu.ccfit.lisitsin.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.reflections.Reflections;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.nsu.ccfit.lisitsin.annotations.TableView;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

@RequiredArgsConstructor
@Route("")
public class MainView extends HorizontalLayout {

    private final JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void init() {
        setSizeFull();
        setSpacing(false);
        setPadding(false);
        setMargin(false);

        VerticalLayout sideMenu = createSideMenu();
        Div content = createContent();

        add(sideMenu, content);
        setFlexGrow(1, content);
    }

    private VerticalLayout createSideMenu() {
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
        addButtons(sideMenu);

        return sideMenu;
    }

    private Div createContent() {
        Div content = new Div();
        content.setSizeFull();
        content.getStyle().set("padding", "1em");

        VerticalLayout sqlQueryLayout = createSqlQuerySection();
        content.add(sqlQueryLayout);

        return content;
    }

    private VerticalLayout createSqlQuerySection() {
        VerticalLayout sqlQueryLayout = new VerticalLayout();
        sqlQueryLayout.setSizeFull();
        sqlQueryLayout.setSpacing(true);

        TextArea sqlQueryField = new TextArea("SQL запрос");
        sqlQueryField.setWidthFull();
        sqlQueryField.setPlaceholder("Введите SELECT запрос...");

        Grid<Map<String, Object>> resultGrid = new Grid<>();
        resultGrid.setSizeFull();
        resultGrid.setSelectionMode(Grid.SelectionMode.NONE);

        Button executeButton = new Button(
                "Выполнить",
                e -> executeSqlQuery(sqlQueryField.getValue(), resultGrid)
        );

        sqlQueryLayout.add(sqlQueryField, executeButton, resultGrid);
        return sqlQueryLayout;
    }

    private void executeSqlQuery(String query, Grid<Map<String, Object>> resultGrid) {
        if (query == null || query.trim().isEmpty()) {
            return;
        }

        List<Map<String, Object>> results = jdbcTemplate.queryForList(query);
        updateResultGrid(results, resultGrid);
    }

    private void updateResultGrid(List<Map<String, Object>> results, Grid<Map<String, Object>> resultGrid) {
        resultGrid.setItems(results);
        resultGrid.removeAllColumns();

        if (!results.isEmpty()) {
            results.getFirst().keySet().forEach(column ->
                    resultGrid.addColumn(row -> row.get(column)).setHeader(column)
            );
        }
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

                UI.getCurrent().navigate(tableName);
            });

            sideMenu.add(button);
        }
    }
}