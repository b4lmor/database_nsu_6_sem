package ru.nsu.ccfit.lisitsin.entity;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.ccfit.lisitsin.utils.ColumnView;
import ru.nsu.ccfit.lisitsin.utils.EnumColumn;
import ru.nsu.ccfit.lisitsin.utils.IdColumn;
import ru.nsu.ccfit.lisitsin.utils.LinkTableView;
import ru.nsu.ccfit.lisitsin.utils.TableView;

import java.time.LocalDate;

@TableView(viewName = "Должности сотрудников", tableName = "job", order = 1)
@Getter
@Setter
public class Job {

    @ColumnView(viewName = "ID", columnName = "id", isEditable = false, isCreationRequired = false)
    @IdColumn
    private Integer id;

    @LinkTableView(linkClass = Employee.class)
    @ColumnView(viewName = "ID сотрудника", columnName = "employee_id")
    private Integer employeeId;

    @LinkTableView(linkClass = TradingPoint.class)
    @ColumnView(viewName = "ID Торговой точки", columnName = "tp_id")
    private Integer tpId;

    @ColumnView(viewName = "Дата начала работы", columnName = "start_date")
    private LocalDate startDate;

    @ColumnView(viewName = "Дата окончания работы", columnName = "end_date", isCreationRequired = false)
    private LocalDate endDate;

    @ColumnView(viewName = "Зарплата", columnName = "salary")
    private Double salary;

    @EnumColumn(JobTitle.class)
    @ColumnView(viewName = "Должность", columnName = "job_title")
    private String jobTitle;

}