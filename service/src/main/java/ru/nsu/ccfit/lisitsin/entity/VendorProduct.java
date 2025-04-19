package ru.nsu.ccfit.lisitsin.entity;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.ccfit.lisitsin.utils.ColumnView;
import ru.nsu.ccfit.lisitsin.utils.IdColumn;
import ru.nsu.ccfit.lisitsin.utils.LinkTableView;
import ru.nsu.ccfit.lisitsin.utils.TableView;

@TableView(viewName = "Товары поставщиков", tableName = "vendor_product", order = 9)
@Getter
@Setter
public class VendorProduct {

    @ColumnView(viewName = "ID", columnName = "id", isEditable = false, isCreationRequired = false)
    @IdColumn
    private Long id;

    @LinkTableView(linkClass = TradingPoint.class)
    @ColumnView(viewName = "ID Поставщика", columnName = "vendor_id")
    private Integer vendorId;

    @LinkTableView(linkClass = ProductInfo.class)
    @ColumnView(viewName = "ID Информации о товаре", columnName = "product_info_id")
    private Long productInfoId;

}