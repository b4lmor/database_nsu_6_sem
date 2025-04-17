package ru.nsu.ccfit.lisitsin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.ccfit.lisitsin.utils.TableViewName;

@TableViewName("Детали заказов")
@Getter
@Setter
@Entity
@Table(name = "product_order_details")
public class ProductOrderDetail {
    @EmbeddedId
    private ProductOrderDetailId id;

    @MapsId("productOrderId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_order_id", nullable = false)
    private ProductOrder productOrder;

    @MapsId("productArticle")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_article", nullable = false)
    private Product productArticle;

    @NotNull
    @Column(name = "product_count", nullable = false)
    private Integer productCount;

}