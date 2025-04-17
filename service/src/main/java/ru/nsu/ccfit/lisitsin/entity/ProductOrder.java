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

import java.time.Instant;

@TableViewName("Заказы товаров")
@Getter
@Setter
@Entity
@Table(name = "product_order")
public class ProductOrder {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tp_id", nullable = false)
    private TradingPoint tp;

    @NotNull
    @Column(name = "create_date", nullable = false)
    private Instant createDate;

    @Column(name = "confirm_date")
    private Instant confirmDate;
    @Column(name = "delivery_date")
    private Instant deliveryDate;

/*
 TODO [Reverse Engineering] create field to map the 'order_status' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @ColumnDefault("'ORDERED'")
    @Column(name = "order_status", columnDefinition = "product_order_status_type not null")
    private Object orderStatus;
*/
}