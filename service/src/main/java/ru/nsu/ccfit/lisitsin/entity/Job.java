package ru.nsu.ccfit.lisitsin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.ccfit.lisitsin.utils.TableViewName;

import java.math.BigDecimal;
import java.time.LocalDate;

@TableViewName("Должности сотрудников")
@Getter
@Setter
@Entity
@Table(name = "job")
public class Job {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tp_id")
    private TradingPoint tp;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;
    @NotNull
    @Column(name = "salary", nullable = false, precision = 10, scale = 2)
    private BigDecimal salary;

/*
 TODO [Reverse Engineering] create field to map the 'job_title' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @Column(name = "job_title", columnDefinition = "job_title_type not null")
    private Object jobTitle;
*/
}