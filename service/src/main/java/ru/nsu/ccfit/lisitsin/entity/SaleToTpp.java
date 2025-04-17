package ru.nsu.ccfit.lisitsin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.ccfit.lisitsin.utils.TableViewName;

@TableViewName(isVisible = false)
@Getter
@Setter
@Entity
@Table(name = "sale_to_tpp")
public class SaleToTpp {
    @EmbeddedId
    private SaleToTppId id;

    @MapsId("saleId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    @MapsId("tppId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tpp_id", nullable = false)
    private TradingPointProduct tpp;

    @NotNull
    @Column(name = "sale_count", nullable = false)
    private Integer saleCount;

}