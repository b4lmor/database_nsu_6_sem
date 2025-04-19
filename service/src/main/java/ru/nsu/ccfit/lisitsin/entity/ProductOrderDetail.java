package ru.nsu.ccfit.lisitsin.entity;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.ccfit.lisitsin.utils.ColumnView;
import ru.nsu.ccfit.lisitsin.utils.IdColumn;
import ru.nsu.ccfit.lisitsin.utils.LinkTableView;
import ru.nsu.ccfit.lisitsin.utils.TableView;

@TableView(viewName = "Детали заказов", tableName = "product_order_details", order = 11)
@Getter
@Setter
public class ProductOrderDetail {

    @LinkTableView(linkClass = ProductOrder.class)
    @ColumnView(viewName = "ID Заказа товара", columnName = "product_order_id", isEditable = false)
    @IdColumn
    private Long productOrderId;

    @LinkTableView(linkClass = Product.class, joinColumn = "article")
    @ColumnView(viewName = "Артикль товара", columnName = "product_article", isEditable = false)
    @IdColumn
    private String productArticle;

    @ColumnView(viewName = "Количество товара", columnName = "product_count")
    private Integer productCount;

}