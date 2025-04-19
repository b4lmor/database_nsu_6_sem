package ru.nsu.ccfit.lisitsin.entity;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.ccfit.lisitsin.utils.ColumnView;
import ru.nsu.ccfit.lisitsin.utils.EnumColumn;
import ru.nsu.ccfit.lisitsin.utils.TableView;

@TableView(viewName = "Здания торговых точек", tableName = "trading_point_building", order = 9)
@Getter
@Setter
public class TradingPointBuilding {

    @ColumnView(viewName = "ID", columnName = "id", isEditable = false, isCreationRequired = false)
    private Integer id;

    @ColumnView(viewName = "Адрес", columnName = "address")
    private String address;

    @ColumnView(viewName = "Название", columnName = "name")
    private String name;

    @EnumColumn(TradingPointBuildingType.class)
    @ColumnView(viewName = "Тип", columnName = "tp_type")
    private String tpType;

}