package ru.nsu.ccfit.lisitsin.entity;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.ccfit.lisitsin.annotations.ColumnView;
import ru.nsu.ccfit.lisitsin.annotations.EnumColumn;
import ru.nsu.ccfit.lisitsin.annotations.TableView;

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

    @EnumColumn(value = TradingPointBuildingType.class, enumName = "trading_point_type")
    @ColumnView(viewName = "Тип", columnName = "tp_type")
    private String tpType;

}