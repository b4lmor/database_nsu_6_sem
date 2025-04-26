package ru.nsu.ccfit.lisitsin.view.main;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.nsu.ccfit.lisitsin.dao.JdbcTemplateWrapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class SqlQuerySection extends VerticalLayout {

    private final JdbcTemplateWrapper jdbcTemplateWrapper;

    @PostConstruct
    private void init() {
        setSizeFull();
        setSpacing(true);

        TextArea sqlQueryField = new TextArea("SQL запрос");
        sqlQueryField.setWidthFull();
        sqlQueryField.setPlaceholder("Введите SELECT запрос...");

        Grid<Map<String, Object>> resultGrid = new Grid<>();
        resultGrid.setSizeFull();
        resultGrid.setSelectionMode(Grid.SelectionMode.NONE);

        Button executeButton = new Button(
                "Выполнить",
                e -> executeSqlQuery(sqlQueryField.getValue(), resultGrid, jdbcTemplateWrapper)
        );

        add(sqlQueryField, executeButton, resultGrid);

        ContextMenu contextMenu = new ContextMenu();
        contextMenu.setTarget(sqlQueryField);

        try {
            Path sqlDir = Paths.get("./src/main/resources/sql");
            if (!Files.exists(sqlDir)) {
                Files.createDirectories(sqlDir);
            }

            try (Stream<Path> paths = Files.list(sqlDir)) {
                paths.filter(Files::isRegularFile)
                        .filter(p -> p.toString().toLowerCase().endsWith(".sql"))
                        .sorted(Comparator.comparingInt(path -> {
                            String fileName = path.getFileName().toString();
                            String numberPart = fileName.replaceAll("^query_(\\d+)(?:_\\d+)?\\.sql$", "$1");
                            return Integer.parseInt(numberPart);
                        }))
                        .forEach(file -> {
                            try {
                                String content = Files.readString(file);

                                int beginIndex = content.indexOf('\n');

                                String header = content.substring(3, beginIndex);
                                String query = content.substring(beginIndex + 1);

                                contextMenu.addItem(header, e -> sqlQueryField.setValue(query));

                            } catch (IOException ex) {
                                log.error("Can't load sql file to context menu: {}", ex.getMessage());
                            }
                        });
            }

        } catch (IOException e) {
            log.error("Ошибка доступа к папке ./sql: {}", e.getMessage());
        }
    }

    private void executeSqlQuery(String query, Grid<Map<String, Object>> resultGrid, JdbcTemplateWrapper jdbcTemplateWrapper) {
        if (query == null || query.trim().isEmpty()) {
            return;
        }

        List<Map<String, Object>> results = jdbcTemplateWrapper.produce(
                jdbcTemplate -> jdbcTemplate.queryForList(query)
        );

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

}
