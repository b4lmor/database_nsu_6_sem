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

import java.time.LocalDate;
import java.util.List;

@TableViewName("Продажи")
@Getter
@Setter
@Entity
@Table(name = "sale")
public class Sale implements Identical {

    @ColumnViewName(value = "ID", isEditable = false)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @LinkTableView(linkClass = TradingPointProduct.class)
    @ColumnViewName("ID Товара торговой точки")
    @NotNull
    @Column(name = "tpp_id", nullable = false)
    private Long tppId;

    @LinkTableView(linkClass = ClientInfo.class)
    @ColumnViewName("ID Информации о клиенте")
    @Column(name = "client_info_id")
    private Long clientInfoId;

    @ColumnViewName("Кол-во продаж (за раз)")
    @Column(name = "sale_count", nullable = false)
    private Integer saleCount;

    @ColumnViewName(value = "Дата продажи", isEditable = false)
    @Column(name = "created_at")
    private LocalDate createdAt;

    @Override
    public List<Object> getIds() {
        return List.of(id);
    }
}