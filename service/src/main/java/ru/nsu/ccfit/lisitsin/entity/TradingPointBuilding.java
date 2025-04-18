package ru.nsu.ccfit.lisitsin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.ccfit.lisitsin.utils.ColumnViewName;
import ru.nsu.ccfit.lisitsin.utils.TableViewName;

import java.util.List;

@TableViewName("Здания торговых точек")
@Getter
@Setter
@Entity
@Table(name = "trading_point_building")
public class TradingPointBuilding implements Identical {

    @ColumnViewName("ID")
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ColumnViewName("Адрес")
    @Size(max = 120)
    @NotNull
    @Column(name = "address", nullable = false, length = 120)
    private String address;

    @ColumnViewName("Название")
    @Size(max = 50)
    @NotNull
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @ColumnViewName("Тип")
    @Column(name = "tp_type")
    private String tpType;

    public List<Object> getIds() {
        return List.of(id);
    }

}