package ru.nsu.ccfit.lisitsin.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.ccfit.lisitsin.utils.TableViewName;

@TableViewName(isVisible = false)
@Getter
@Setter
@Entity
@Table(name = "department_to_trading_point")
public class DepartmentToTradingPoint {
    @EmbeddedId
    private DepartmentToTradingPointId id;

    @MapsId("tpId")
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tp_id", nullable = false)
    private TradingPoint tp;

    @MapsId("departmentId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

}