package ru.nsu.ccfit.lisitsin.entity;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.ccfit.lisitsin.utils.ColumnView;
import ru.nsu.ccfit.lisitsin.utils.IdColumn;
import ru.nsu.ccfit.lisitsin.utils.TableView;

import java.time.LocalDate;

@TableView(viewName = "Информация о клиентах", tableName = "client_info", order = 13)
@Getter
@Setter
public class ClientInfo {

    @ColumnView(viewName = "ID", columnName = "id", isEditable = false, isCreationRequired = false)
    @IdColumn
    private Long id;

    @ColumnView(viewName = "ФИО", columnName = "full_name")
    private String fullName;

    @ColumnView(viewName = "Дата рождения", columnName = "birth_date")
    private LocalDate birthDate;

    @ColumnView(viewName = "Рост", columnName = "height")
    private Double height;

    @ColumnView(viewName = "Вес", columnName = "weight")
    private Double weight;

    @ColumnView(viewName = "Доп. информация", columnName = "specificity")
    private String specificity;

    @ColumnView(viewName = "Номер телефона", columnName = "phone")
    private String phone;

    @ColumnView(viewName = "Email", columnName = "email")
    private String email;

}