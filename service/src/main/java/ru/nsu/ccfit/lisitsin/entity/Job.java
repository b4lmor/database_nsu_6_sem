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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@TableViewName("Должности сотрудников")
@Getter
@Setter
@Entity
@Table(name = "job")
public class Job implements Identical {

    @ColumnViewName(value = "ID", isEditable = false)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @LinkTableView(linkClass = Employee.class)
    @ColumnViewName("ID сотрудника")
    @Column(name = "employee_id")
    private Integer employeeId;

    @LinkTableView(linkClass = TradingPoint.class)
    @ColumnViewName("ID Торговой точки")
    @Column(name = "tp_id")
    private Integer tpId;

    @ColumnViewName("Дата начала работы")
    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @ColumnViewName("Дата окончания работы")
    @Column(name = "end_date")
    private LocalDate endDate;

    @ColumnViewName("Зарплата")
    @NotNull
    @Column(name = "salary", nullable = false, precision = 10, scale = 2)
    private BigDecimal salary;

    @ColumnViewName("Должность")
    @Column(name = "job_title")
    private String jobTitle;

    public List<Object> getIds() {
        return List.of(id);
    }

}