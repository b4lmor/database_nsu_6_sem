package ru.nsu.ccfit.lisitsin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.ccfit.lisitsin.utils.TableViewName;

import java.math.BigDecimal;

@TableViewName("Торговая точка")
@Getter
@Setter
@Entity
@Table(name = "trading_point")
public class TradingPoint {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tpb_id")
    private TradingPointBuilding tpb;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @Size(max = 50)
    @NotNull
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @NotNull
    @Column(name = "rent_payment", nullable = false, precision = 10, scale = 2)
    private BigDecimal rentPayment;

    @NotNull
    @Column(name = "tp_size", nullable = false, precision = 10, scale = 2)
    private BigDecimal tpSize;

}