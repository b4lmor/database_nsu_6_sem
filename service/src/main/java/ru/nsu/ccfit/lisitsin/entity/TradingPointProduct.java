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

@TableViewName("Товары торговых точек")
@Getter
@Setter
@Entity
@Table(name = "trading_point_product")
public class TradingPointProduct implements Identical {

    @ColumnViewName(value = "ID", isEditable = false)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @LinkTableView(linkClass = TradingPoint.class)
    @ColumnViewName("ID Торговой точки")
    @NotNull
    @Column(name = "tp_id", nullable = false)
    private Integer tpId;

    @LinkTableView(linkClass = ProductInfo.class)
    @ColumnViewName("ID информации о товаре")
    @NotNull
    @Column(name = "product_info_id", nullable = false)
    private Long productInfoId;

    @Override
    public List<Object> getIds() {
        return List.of(id);
    }
}