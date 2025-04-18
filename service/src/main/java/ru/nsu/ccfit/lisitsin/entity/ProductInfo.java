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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@TableViewName("Информация о товарах")
@Getter
@Setter
@Entity
@Table(name = "product_info")
public class ProductInfo implements Identical {

    @ColumnViewName(value = "ID", isEditable = false)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @LinkTableView(linkClass = Product.class, joinColumn = "article")
    @ColumnViewName("Артикль товара")
    @NotNull
    @Column(name = "product_article", nullable = false)
    private String productArticle;

    @ColumnViewName("Дата доставки")
    @NotNull
    @Column(name = "delivery_date", nullable = false)
    private LocalDate deliveryDate;

    @ColumnViewName("Дата продажи")
    @Column(name = "sell_date")
    private LocalDate sellDate;

    @ColumnViewName("Цена")
    @NotNull
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Override
    public List<Object> getIds() {
        return List.of(id);
    }
}