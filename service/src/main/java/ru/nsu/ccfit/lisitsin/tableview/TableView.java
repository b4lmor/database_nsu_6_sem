package ru.nsu.ccfit.lisitsin.tableview;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import org.springframework.util.ReflectionUtils;
import ru.nsu.ccfit.lisitsin.dao.GenericRepository;
import ru.nsu.ccfit.lisitsin.entity.Identical;
import ru.nsu.ccfit.lisitsin.forms.DefaultForm;
import ru.nsu.ccfit.lisitsin.forms.DeleteForm;
import ru.nsu.ccfit.lisitsin.forms.EditForm;
import ru.nsu.ccfit.lisitsin.forms.FormBuilder;
import ru.nsu.ccfit.lisitsin.forms.ObjectViewForm;
import ru.nsu.ccfit.lisitsin.utils.ColumnViewName;
import ru.nsu.ccfit.lisitsin.utils.LinkTableView;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public abstract class TableView<T extends Identical> extends VerticalLayout {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    protected final Class<T> entityClass;

    protected final Grid<T> grid;

    protected final HorizontalLayout buttonLayout;

    protected final GenericRepository<T> genericRepository;

    protected ListDataProvider<T> dataProvider;

    protected HorizontalLayout filterLayout;

    protected ContextMenu contextMenu;

    protected T selectedItem;

    protected Grid.Column<T> selectedColumn;

    public TableView(Class<T> entityClass, GenericRepository<T> genericRepository) {
        this.entityClass = entityClass;
        this.grid = new Grid<>(entityClass);
        this.buttonLayout = new HorizontalLayout();
        this.genericRepository = genericRepository;
        this.filterLayout = new HorizontalLayout();
        this.contextMenu = new ContextMenu();

        initView();
    }

    private void initView() {
        setSizeFull();
        setPadding(true);
        setSpacing(true);

        configureGrid(grid);
        addContextMenu();

        refreshData();

        buttonLayout.setSpacing(true);

        Button backButton = new Button("На главную");
        backButton.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate("")));

        HorizontalLayout bottomLayout = new HorizontalLayout(backButton);
        bottomLayout.setWidthFull();
        bottomLayout.setJustifyContentMode(JustifyContentMode.START);

        add(buttonLayout, filterLayout, grid, bottomLayout);
        setFlexGrow(1, grid);
    }

    private void addContextMenu() {
        contextMenu.setTarget(grid);

        contextMenu.addItem("Изменить", e -> {
            if (selectedItem != null) {
                showEditForm(selectedItem);
            }
        });

        contextMenu.addItem("Удалить", e -> {
            if (selectedItem != null) {
                showDeleteForm(selectedItem);
            }
        });

        contextMenu.addItem("Посмотреть ссылку", e -> {
            if (selectedColumn != null) {
                Arrays.stream(entityClass.getDeclaredFields())
                        .filter(f -> f.isAnnotationPresent(ColumnViewName.class) && f.isAnnotationPresent(LinkTableView.class))
                        .filter(f -> f.getAnnotation(ColumnViewName.class).value().equals(selectedColumn.getHeaderText()))
                        .findFirst()
                        .ifPresentOrElse(
                                field -> {
                                    field.setAccessible(true);

                                    Object value = ReflectionUtils.getField(field, selectedItem);
                                    LinkTableView linkTableView = field.getAnnotation(LinkTableView.class);

                                    Object linkedObject = genericRepository.findByField(
                                            linkTableView.linkClass(),
                                            linkTableView.joinColumn(),
                                            value
                                    );

                                    if (linkedObject != null) {
                                        new ObjectViewForm<>(linkTableView.linkClass(), linkedObject).open();

                                    } else {
                                        throw new RuntimeException("Объект не найден");
                                    }
                                },
                                () -> {
                                    throw new RuntimeException("Поле не является внешним ключом");
                                }
                        );
            }
        });

        contextMenu.setOpenOnClick(true);

        grid.addItemClickListener(event -> {
            grid.asSingleSelect().setValue(event.getItem());
            selectedItem = grid.asSingleSelect().getValue();
            selectedColumn = event.getColumn();
        });
    }

    protected void showEditForm(T item) {
        new EditForm<>(
                entityClass,
                item,
                params -> {
                    genericRepository.update(params);
                    refreshData();
                }
        ).open();
    }

    protected void showDeleteForm(T item) {
        new DeleteForm<>(
                entityClass,
                item,
                () -> {
                    genericRepository.delete(item.getIds(), item.getIdColumns());
                    refreshData();
                }
        ).open();
    }

    protected void refreshData() {
        List<T> items = genericRepository.findAll();

        dataProvider = new ListDataProvider<>(items);

        grid.setDataProvider(dataProvider);
    }

    private void configureGrid(Grid<T> grid) {
        grid.removeAllColumns();

        ReflectionUtils.doWithFields(
                entityClass,
                field -> {
                    ColumnViewName annotation = field.getAnnotation(ColumnViewName.class);
                    if (annotation != null && annotation.isVisible()) {
                        field.setAccessible(true);
                        String columnName = annotation.value();

                        grid.addColumn(item -> processFiled(field, item)).setHeader(columnName);
                    }
                }
        );
    }

    protected void registerForm(String buttonText, FormBuilder formBuilder) {
        DefaultForm form = new DefaultForm(formBuilder);

        buttonLayout.add(new Button(buttonText, e -> form.open()));
    }

    private String processFiled(Field field, T item) {
        Object value = ReflectionUtils.getField(field, item);

        if (field.getType() == LocalDate.class) {
            return value != null ? DATE_FORMATTER.format((LocalDate) value) : "---";

        } else {
            return value != null ? value.toString() : "---";
        }
    }

}
