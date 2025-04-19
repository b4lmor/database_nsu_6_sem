package ru.nsu.ccfit.lisitsin.entity;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.ccfit.lisitsin.utils.ColumnView;
import ru.nsu.ccfit.lisitsin.utils.EnumColumn;
import ru.nsu.ccfit.lisitsin.utils.IdColumn;
import ru.nsu.ccfit.lisitsin.utils.LinkTableView;
import ru.nsu.ccfit.lisitsin.utils.TableView;

import java.time.LocalDate;

@TableView(viewName = "Заказы товаров", tableName = "product_order", order = 10)
@Getter
@Setter
public class ProductOrder {

    @ColumnView(viewName = "ID", columnName = "id", isEditable = false, isCreationRequired = false)
    @IdColumn
    private Long id;

    @LinkTableView(linkClass = Employee.class)
    @ColumnView(viewName = "ID Менеджера", columnName = "manager_id")
    private Integer managerId;

    @LinkTableView(linkClass = Vendor.class)
    @ColumnView(viewName = "ID Поставщика", columnName = "vendor_id")
    private Integer vendorId;

    @LinkTableView(linkClass = TradingPoint.class)
    @ColumnView(viewName = "ID Тороговой точки", columnName = "tp_id")
    private Integer tpId;

    @ColumnView(viewName = "Дата создания", columnName = "create_date", isCreationRequired = false)
    private LocalDate createDate;

    @ColumnView(viewName = "Дата подтверждения", columnName = "confirm_date", isCreationRequired = false)
    private LocalDate confirmDate;

    @ColumnView(viewName = "Дата доставки", columnName = "delivery_date", isCreationRequired = false)
    private LocalDate deliveryDate;

    @EnumColumn(OrderStatus.class)
    @ColumnView(viewName = "Статус заказа", columnName = "order_status", isCreationRequired = false)
    private String orderStatus;

}