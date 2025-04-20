package ru.nsu.ccfit.lisitsin.entity;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.ccfit.lisitsin.annotations.ColumnView;
import ru.nsu.ccfit.lisitsin.annotations.IdColumn;
import ru.nsu.ccfit.lisitsin.annotations.LinkTableView;
import ru.nsu.ccfit.lisitsin.annotations.TableView;

@TableView(viewName = "Секции торговых точек", tableName = "department_to_trading_point", order = 5)
@Getter
@Setter
public class DepartmentToTradingPoint {

    @LinkTableView(linkClass = Department.class)
    @ColumnView(viewName = "ID Секции", columnName = "department_id", isEditable = false)
    @IdColumn
    private Integer departmentId;

    @LinkTableView(linkClass = TradingPoint.class)
    @ColumnView(viewName = "ID Торговой точки", columnName = "tp_id", isEditable = false)
    @IdColumn
    private Integer tpId;

}