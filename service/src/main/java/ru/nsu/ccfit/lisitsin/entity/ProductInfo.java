package ru.nsu.ccfit.lisitsin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.ccfit.lisitsin.utils.TableViewName;

import java.math.BigDecimal;
import java.time.Instant;

@TableViewName("Информация о товарах")
@Getter
@Setter
@Entity
@Table(name = "product_info")
public class ProductInfo {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_article", nullable = false)
    private Product productArticle;

    @NotNull
    @Column(name = "delivery_date", nullable = false)
    private Instant deliveryDate;

    @Column(name = "sell_date")
    private Instant sellDate;

    @NotNull
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

}