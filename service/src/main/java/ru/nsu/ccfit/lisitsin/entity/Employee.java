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

import java.time.LocalDate;

@TableViewName("Сотрудники")
@Getter
@Setter
@Entity
@Table(name = "employee")
public class Employee implements Identical {

    @ColumnViewName(value = "ID", isEditable = false)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ColumnViewName("ФИО")
    @Size(max = 100)
    @NotNull
    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @ColumnViewName("Дата рождения")
    @NotNull
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @ColumnViewName("Дата приема")
    @NotNull
    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    @ColumnViewName("Дата увольнения")
    @Column(name = "resignation_date")
    private LocalDate resignationDate;

    public Long getId() {
        return (long) id;
    }

}