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

import java.time.Instant;
import java.util.List;

@TableViewName("Заказы товаров")
@Getter
@Setter
@Entity
@Table(name = "product_order")
public class ProductOrder implements Identical {

    @ColumnViewName(value = "ID", isEditable = false)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @LinkTableView(linkClass = Employee.class)
    @ColumnViewName("ID Менеджера")
    @Column(name = "manager_id")
    private Integer managerId;

    @LinkTableView(linkClass = Vendor.class)
    @ColumnViewName("ID Поставщика")
    @Column(name = "vendor_id")
    private Integer vendorId;

    @LinkTableView(linkClass = TradingPoint.class)
    @ColumnViewName("ID Тороговой точки")
    @NotNull
    @Column(name = "tp_id", nullable = false)
    private Integer tpId;

    @ColumnViewName("Дата создания")
    @NotNull
    @Column(name = "create_date", nullable = false)
    private Instant createDate;

    @ColumnViewName("Дата подтверждения")
    @Column(name = "confirm_date")
    private Instant confirmDate;

    @ColumnViewName("Дата доставки")
    @Column(name = "delivery_date")
    private Instant deliveryDate;

    @ColumnViewName("Статус заказа")
    @Column(name = "order_status")
    private String orderStatus;

    @Override
    public List<Object> getIds() {
        return List.of(id);
    }
}