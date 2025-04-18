package ru.nsu.ccfit.lisitsin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.ccfit.lisitsin.utils.ColumnViewName;
import ru.nsu.ccfit.lisitsin.utils.TableViewName;

import java.util.List;

@TableViewName("Товары")
@Getter
@Setter
@Entity
@Table(name = "product")
public class Product implements Identical {

    @ColumnViewName(value = "Артикль", isEditable = false)
    @Id
    @Size(max = 60)
    @Column(name = "article", nullable = false, length = 60)
    private String article;

    @ColumnViewName("Название")
    @Size(max = 60)
    @Column(name = "name", length = 60)
    private String name;

    @ColumnViewName("Описание")
    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @ColumnViewName("URL фото")
    @Column(name = "photo_url", length = Integer.MAX_VALUE)
    private String photoUrl;

    @Override
    public List<Object> getIds() {
        return List.of(article);
    }

    @Override
    public List<String> getIdColumns() {
        return List.of("article");
    }
}