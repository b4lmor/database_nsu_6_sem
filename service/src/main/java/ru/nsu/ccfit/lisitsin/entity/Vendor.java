package ru.nsu.ccfit.lisitsin.entity;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.ccfit.lisitsin.annotations.ColumnView;
import ru.nsu.ccfit.lisitsin.annotations.IdColumn;
import ru.nsu.ccfit.lisitsin.annotations.TableView;

@TableView(viewName = "Поставщики", tableName = "vendor", order = 6)
@Getter
@Setter
public class Vendor {

    @ColumnView(viewName = "ID", columnName = "id", isEditable = false, isCreationRequired = false)
    @IdColumn
    private Integer id;

    @ColumnView(viewName = "Название", columnName = "name")
    private String name;

    @ColumnView(viewName = "Адрес", columnName = "address")
    private String address;

}