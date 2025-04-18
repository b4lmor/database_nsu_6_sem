package ru.nsu.ccfit.lisitsin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.ccfit.lisitsin.utils.ColumnViewName;
import ru.nsu.ccfit.lisitsin.utils.LinkTableView;
import ru.nsu.ccfit.lisitsin.utils.TableViewName;

import java.util.List;

@TableViewName("Детали заказов")
@Getter
@Setter
@Entity
@Table(name = "product_order_details")
public class ProductOrderDetail implements Identical {

    @Id
    @LinkTableView(linkClass = ProductOrder.class)
    @ColumnViewName(value = "ID Заказа товара", isEditable = false)
    @Column(name = "product_order_id")
    private Long productOrderId;

    @Id
    @LinkTableView(linkClass = Product.class)
    @ColumnViewName(value = "Артикль товара", isEditable = false)
    @Column(name = "product_article")
    private String productArticle;

    @NotNull
    @Column(name = "product_count", nullable = false)
    private Integer productCount;

    @Override
    public List<Object> getIds() {
        return List.of(productOrderId, productArticle);
    }

    @Override
    public List<String> getIdColumns() {
        return List.of("product_order_id", "product_article");
    }
}