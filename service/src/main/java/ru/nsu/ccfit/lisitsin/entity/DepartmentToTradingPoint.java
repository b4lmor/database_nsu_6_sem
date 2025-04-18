package ru.nsu.ccfit.lisitsin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.ccfit.lisitsin.utils.ColumnViewName;
import ru.nsu.ccfit.lisitsin.utils.LinkTableView;
import ru.nsu.ccfit.lisitsin.utils.TableViewName;

import java.util.List;

@TableViewName("Секции торговых точек")
@Getter
@Setter
@Entity
@Table(name = "department_to_trading_point")
public class DepartmentToTradingPoint implements Identical {

    @LinkTableView(linkClass = TradingPoint.class)
    @ColumnViewName("ID Торговой точки")
    @Id
    @Column(name = "tp_id", nullable = false)
    private Integer tpId;

    @LinkTableView(linkClass = Department.class)
    @ColumnViewName("ID Секции")
    @Id
    @Column(name = "department_id", nullable = false)
    private Integer departmentId;

    @Override
    public List<Object> getIds() {
        return List.of(tpId, departmentId);
    }

    @Override
    public List<String> getIdColumns() {
        return List.of("tp_id", "department_id");
    }
}