package ru.nsu.ccfit.lisitsin.entity;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.ccfit.lisitsin.annotations.ColumnView;
import ru.nsu.ccfit.lisitsin.annotations.IdColumn;
import ru.nsu.ccfit.lisitsin.annotations.LinkTableView;
import ru.nsu.ccfit.lisitsin.annotations.TableView;

@TableView(viewName = "Торговые точки", tableName = "trading_point", order = 2)
@Getter
@Setter
public class TradingPoint {

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

    @ColumnView(viewName = "Стоимость аренды", columnName = "rent_payment")
    private Double rentPayment;

    @ColumnView(viewName = "Площадь помещения", columnName = "tp_size")
    private Double tpSize;

}