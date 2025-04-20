package ru.nsu.ccfit.lisitsin.tableview;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;
import ru.nsu.ccfit.lisitsin.dao.GenericRepository;
import ru.nsu.ccfit.lisitsin.forms.CreateForm;
import ru.nsu.ccfit.lisitsin.forms.DefaultForm;
import ru.nsu.ccfit.lisitsin.forms.DeleteForm;
import ru.nsu.ccfit.lisitsin.forms.EditForm;
import ru.nsu.ccfit.lisitsin.forms.FilterForm;
import ru.nsu.ccfit.lisitsin.forms.FormBuilder;
import ru.nsu.ccfit.lisitsin.forms.ObjectViewForm;
import ru.nsu.ccfit.lisitsin.forms.ViewForm;
import ru.nsu.ccfit.lisitsin.annotations.ColumnView;
import ru.nsu.ccfit.lisitsin.annotations.LinkTableView;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class DefaultTableView<T> extends VerticalLayout {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    protected final Class<T> entityClass;

    protected final Grid<T> grid;

    protected final HorizontalLayout buttonLayout;

    protected final GenericRepository<T> genericRepository;

    protected ListDataProvider<T> dataProvider;

    protected GridContextMenu<T> contextMenu;

    protected T selectedItem;

    protected Map<Field, Object> filterItems;

    protected Grid.Column<T> selectedColumn;

    public DefaultTableView(Class<T> entityClass, GenericRepository<T> genericRepository) {
        this.entityClass = entityClass;
        this.grid = new Grid<>(entityClass);
        this.buttonLayout = new HorizontalLayout();
        this.genericRepository = genericRepository;
        this.contextMenu = grid.addContextMenu();

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

        add(buttonLayout, grid, bottomLayout);
        setFlexGrow(1, grid);

        buttonLayout.add(new Button("Создать", e -> showCreateForm()));
        buttonLayout.add(new Button("Изменить фильтр", e -> showFilterForm()));
        buttonLayout.add(new Button("Очистить фильтр", e -> clearFilter()));
    }

    private void addContextMenu() {
        contextMenu.addItem("Посмотреть", e -> {
            if (selectedItem != null) {
                showViewForm(selectedItem);
            }
        });

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
                showWatchLink();
            }
        });

        grid.addItemClickListener(event -> {
            grid.asSingleSelect().setValue(event.getItem());
            selectedItem = grid.asSingleSelect().getValue();
            selectedColumn = event.getColumn();
        });
    }

    private void showWatchLink() {
        Arrays.stream(entityClass.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(ColumnView.class) && f.isAnnotationPresent(LinkTableView.class))
                .filter(f -> f.getAnnotation(ColumnView.class).viewName().equals(selectedColumn.getHeaderText()))
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

    private void showViewForm(T item) {
        new ViewForm<>(entityClass, item).open();
    }

    protected void showCreateForm() {
        new CreateForm(
                entityClass,
                params -> {
                    genericRepository.create(params);
                    refreshData();
                }
        ).open();
    }

    protected void showFilterForm() {
        new FilterForm<>(
                entityClass,
                (Map<Field, Object> newFilterItems) -> {
                    filterItems = newFilterItems;
                    refreshData();
                }
        ).open();
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
                    genericRepository.delete(item);
                    refreshData();
                }
        ).open();
    }

    protected void clearFilter() {
        filterItems = null;
        refreshData();
    }

    protected void refreshData() {
        List<T> items = genericRepository.findAll(filterItems);

        dataProvider = new ListDataProvider<>(items);

        grid.setDataProvider(dataProvider);
    }

    private void configureGrid(Grid<T> grid) {
        grid.removeAllColumns();

        ReflectionUtils.doWithFields(
                entityClass,
                field -> {
                    ColumnView annotation = field.getAnnotation(ColumnView.class);
                    if (annotation != null && annotation.isVisible()) {
                        field.setAccessible(true);
                        String columnName = annotation.viewName();

                        grid.addColumn(item -> processFiled(field, item))
                                .setHeader(columnName)
                                .setComparator(createComparator(field));
                    }
                }
        );
    }

    protected void registerForm(String buttonText, FormBuilder formBuilder) {
        buttonLayout.add(new Button(buttonText, e -> new DefaultForm(formBuilder).open()));
    }

    private String processFiled(Field field, T item) {
        Object value = ReflectionUtils.getField(field, item);

        if (field.getType() == LocalDate.class) {
            return value != null ? DATE_FORMATTER.format((LocalDate) value) : "---";

        } else {
            return value != null ? value.toString() : "---";
        }
    }

    private Comparator<T> createComparator(Field field) {
        return (o1, o2) -> {
            Object value1 = ReflectionUtils.getField(field, o1);
            Object value2 = ReflectionUtils.getField(field, o2);

            if (value1 == null && value2 == null) {
                return 0;

            } else if (value1 == null) {
                return -1;

            } else if (value2 == null) {
                return 1;
            }

            if (field.getType() == LocalDate.class) {
                return ((LocalDate) value1).compareTo((LocalDate) value2);

            } else if (Comparable.class.isAssignableFrom(field.getType())) {

                return ((Comparable) value1).compareTo(value2);

            } else {
                return value1.toString().compareTo(value2.toString());
            }
        };
    }


}
