package ru.nsu.ccfit.lisitsin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.ccfit.lisitsin.utils.TableViewName;

@TableViewName("Здания торговой точки")
@Getter
@Setter
@Entity
@Table(name = "trading_point_building")
public class TradingPointBuilding {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 120)
    @NotNull
    @Column(name = "address", nullable = false, length = 120)
    private String address;

    @Size(max = 50)
    @NotNull
    @Column(name = "name", nullable = false, length = 50)
    private String name;

/*
 TODO [Reverse Engineering] create field to map the 'tp_type' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @Column(name = "tp_type", columnDefinition = "trading_point_type not null")
    private Object tpType;
*/
}