package ru.nsu.ccfit.lisitsin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.ccfit.lisitsin.utils.ColumnViewName;
import ru.nsu.ccfit.lisitsin.utils.TableViewName;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@TableViewName("Информация о клиентах")
@Getter
@Setter
@Entity
@Table(name = "client_info")
public class ClientInfo implements Identical {
    @ColumnViewName(value = "ID", isEditable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_info_id_gen")
    @SequenceGenerator(name = "client_info_id_gen", sequenceName = "client_info_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @ColumnViewName("ФИО")
    @Size(max = 100)
    @Column(name = "full_name", length = 100)
    private String fullName;

    @ColumnViewName("Дата рождения")
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @ColumnViewName("Рост")
    @Column(name = "height", precision = 4, scale = 1)
    private BigDecimal height;

    @ColumnViewName("Вес")
    @Column(name = "weight", precision = 5, scale = 2)
    private BigDecimal weight;

    @ColumnViewName("Доп. информация")
    @Column(name = "specificity", length = Integer.MAX_VALUE)
    private String specificity;

    @ColumnViewName("Номер телефона")
    @Size(max = 15)
    @Column(name = "phone", length = 15)
    private String phone;

    @ColumnViewName("Email")
    @Size(max = 50)
    @Column(name = "email", length = 50)
    private String email;

    public List<Object> getIds() {
        return List.of(id);
    }
}