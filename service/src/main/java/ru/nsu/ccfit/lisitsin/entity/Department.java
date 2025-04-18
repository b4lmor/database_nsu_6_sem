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
import ru.nsu.ccfit.lisitsin.utils.LinkTableView;
import ru.nsu.ccfit.lisitsin.utils.TableViewName;

import java.util.List;

@TableViewName("Секции")
@Getter
@Setter
@Entity
@Table(name = "department")
public class Department implements Identical {

    @ColumnViewName(value = "ID", isEditable = false)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @LinkTableView(linkClass = TradingPointBuilding.class)
    @ColumnViewName("ID Здания")
    @NotNull
    @Column(name = "tpb_id", nullable = false)
    private Integer tpbId;

    @LinkTableView(linkClass = Employee.class)
    @ColumnViewName("ID менеджера")
    @Column(name = "manager_id")
    private Integer managerId;

    @ColumnViewName("Название")
    @Size(max = 50)
    @NotNull
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @ColumnViewName("Этаж")
    @NotNull
    @Column(name = "floor", nullable = false)
    private Integer floor;

    @Override
    public List<Object> getIds() {
        return List.of(id);
    }
}