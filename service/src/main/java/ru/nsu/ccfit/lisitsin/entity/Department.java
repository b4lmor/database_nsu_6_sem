package ru.nsu.ccfit.lisitsin.entity;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.ccfit.lisitsin.utils.ColumnView;
import ru.nsu.ccfit.lisitsin.utils.IdColumn;
import ru.nsu.ccfit.lisitsin.utils.LinkTableView;
import ru.nsu.ccfit.lisitsin.utils.TableView;

@TableView(viewName = "Секции", tableName = "department", order = 4)
@Getter
@Setter
public class Department {

    @ColumnView(viewName = "ID", columnName = "id", isEditable = false, isCreationRequired = false)
    @IdColumn
    private Integer id;

    @LinkTableView(linkClass = TradingPointBuilding.class)
    @ColumnView(viewName = "ID Здания", columnName = "tpb_id")
    private Integer tpbId;

    @LinkTableView(linkClass = Employee.class)
    @ColumnView(viewName = "ID менеджера", columnName = "manager_id")
    private Integer managerId;

    @ColumnView(viewName = "Название", columnName = "name")
    private String name;

    @ColumnView(viewName = "Этаж", columnName = "floor")
    private Integer floor;

}