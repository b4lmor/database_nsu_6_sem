package ru.nsu.ccfit.lisitsin.entity;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.ccfit.lisitsin.annotations.ColumnView;
import ru.nsu.ccfit.lisitsin.annotations.IdColumn;
import ru.nsu.ccfit.lisitsin.annotations.TableView;

import java.time.LocalDate;

@TableView(viewName = "Сотрудники", tableName = "employee", order = 0)
@Getter
@Setter
public class Employee {

    @ColumnView(viewName = "ID", columnName = "id", isEditable = false, isCreationRequired = false)
    @IdColumn
    private Integer id;

    @ColumnView(viewName = "ФИО", columnName = "full_name")
    private String fullName;

    @ColumnView(viewName = "Дата рождения", columnName = "birth_date")
    private LocalDate birthDate;

    @ColumnView(viewName = "Дата приема", columnName = "hire_date", isCreationRequired = false)
    private LocalDate hireDate;

    @ColumnView(viewName = "Дата увольнения", columnName = "resignation_date", isCreationRequired = false)
    private LocalDate resignationDate;

}