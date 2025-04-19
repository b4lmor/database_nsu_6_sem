package ru.nsu.ccfit.lisitsin.entity;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.ccfit.lisitsin.utils.ColumnView;
import ru.nsu.ccfit.lisitsin.utils.IdColumn;
import ru.nsu.ccfit.lisitsin.utils.TableView;

@TableView(viewName = "Товары", tableName = "product", order = 8)
@Getter
@Setter
public class Product {

    @ColumnView(viewName = "Артикль", columnName = "article", isEditable = false, isCreationRequired = false)
    @IdColumn
    private String article;

    @ColumnView(viewName = "Название", columnName = "name")
    private String name;

    @ColumnView(viewName = "Описание", columnName = "description")
    private String description;

    @ColumnView(viewName = "URL фото", columnName = "photo_url")
    private String photoUrl;

}