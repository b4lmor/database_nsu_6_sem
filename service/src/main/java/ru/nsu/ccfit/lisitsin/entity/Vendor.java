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

import java.util.List;

@TableViewName("Поставщики")
@Getter
@Setter
@Entity
@Table(name = "vendor")
public class Vendor implements Identical {

    @ColumnViewName(value = "ID", isEditable = false)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ColumnViewName("Название")
    @Size(max = 50)
    @NotNull
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @ColumnViewName("Адрес")
    @Size(max = 120)
    @NotNull
    @Column(name = "address", nullable = false, length = 120)
    private String address;

    @Override
    public List<Object> getIds() {
        return List.of(id);
    }
}