package ru.nsu.ccfit.lisitsin.entity;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.ccfit.lisitsin.utils.ColumnView;
import ru.nsu.ccfit.lisitsin.utils.IdColumn;
import ru.nsu.ccfit.lisitsin.utils.LinkTableView;
import ru.nsu.ccfit.lisitsin.utils.TableView;

import java.time.LocalDate;

@TableView(viewName = "Информация о товарах", tableName = "product_info", order = 9)
@Getter
@Setter
public class ProductInfo {

    @ColumnView(viewName = "ID", columnName = "id", isEditable = false, isCreationRequired = false)
    @IdColumn
    private Long id;

    @LinkTableView(linkClass = Product.class, joinColumn = "article")
    @ColumnView(viewName = "Артикль товара", columnName = "product_article")
    private String productArticle;

    @ColumnView(viewName = "Дата доставки", columnName = "delivery_date")
    private LocalDate deliveryDate;

    @ColumnView(viewName = "Дата продажи", columnName = "sell_date", isCreationRequired = false)
    private LocalDate sellDate;

    @ColumnView(viewName = "Цена", columnName = "price")
    private Double price;

}