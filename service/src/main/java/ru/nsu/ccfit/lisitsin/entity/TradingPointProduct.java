package ru.nsu.ccfit.lisitsin.entity;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.ccfit.lisitsin.utils.ColumnView;
import ru.nsu.ccfit.lisitsin.utils.IdColumn;
import ru.nsu.ccfit.lisitsin.utils.LinkTableView;
import ru.nsu.ccfit.lisitsin.utils.TableView;

@TableView(viewName = "Товары торговых точек", tableName = "trading_point_product", order = 9)
@Getter
@Setter
public class TradingPointProduct {

    @ColumnView(viewName = "ID", columnName = "id", isEditable = false, isCreationRequired = false)
    @IdColumn
    private Long id;

    @LinkTableView(linkClass = TradingPoint.class)
    @ColumnView(viewName = "ID Торговой точки", columnName = "tp_id")
    private Integer tpId;

    @LinkTableView(linkClass = ProductInfo.class)
    @ColumnView(viewName = "ID информации о товаре", columnName = "product_info_id")
    private Long productInfoId;

}