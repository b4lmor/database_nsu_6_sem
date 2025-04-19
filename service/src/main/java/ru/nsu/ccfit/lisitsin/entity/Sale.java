package ru.nsu.ccfit.lisitsin.entity;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.ccfit.lisitsin.utils.ColumnView;
import ru.nsu.ccfit.lisitsin.utils.IdColumn;
import ru.nsu.ccfit.lisitsin.utils.LinkTableView;
import ru.nsu.ccfit.lisitsin.utils.TableView;

import java.time.LocalDate;

@TableView(viewName = "Продажи", tableName = "sale", order = 12)
@Getter
@Setter
public class Sale {

    @ColumnView(viewName = "ID", columnName = "id", isEditable = false, isCreationRequired = false)
    @IdColumn
    private Long id;

    @LinkTableView(linkClass = TradingPointProduct.class)
    @ColumnView(viewName = "ID Товара торговой точки", columnName = "tpp_id")
    private Long tppId;

    @LinkTableView(linkClass = Employee.class)
    @ColumnView(viewName = "ID Продавца", columnName = "seller_id")
    private Integer sellerId;

    @LinkTableView(linkClass = ClientInfo.class)
    @ColumnView(viewName = "ID Информации о клиенте", columnName = "client_info_id")
    private Long clientInfoId;

    @ColumnView(viewName = "Кол-во продаж (за раз)", columnName = "sale_count")
    private Integer saleCount;

    @ColumnView(viewName = "Дата продажи", columnName = "created_at", isEditable = false, isCreationRequired = false)
    private LocalDate createdAt;

}