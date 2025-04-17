package ru.nsu.ccfit.lisitsin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.ccfit.lisitsin.utils.TableViewName;

@TableViewName("Товары")
@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {
    @Id
    @Size(max = 60)
    @Column(name = "article", nullable = false, length = 60)
    private String article;

    @Size(max = 60)
    @Column(name = "name", length = 60)
    private String name;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "photo_url", length = Integer.MAX_VALUE)
    private String photoUrl;

}