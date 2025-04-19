package ru.nsu.ccfit.lisitsin.forms;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.util.ReflectionUtils;
import ru.nsu.ccfit.lisitsin.entity.JobTitle;
import ru.nsu.ccfit.lisitsin.entity.OrderStatus;
import ru.nsu.ccfit.lisitsin.entity.TradingPointBuildingType;
import ru.nsu.ccfit.lisitsin.utils.ColumnView;
import ru.nsu.ccfit.lisitsin.utils.EnumColumn;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class CreateForm extends DefaultForm {

    private static final Map<Class<?>, Function<String, AbstractSinglePropertyField<?, ?>>> INPUT_FIELD_MAPPER = Map.of(
            String.class, TextField::new,
            Integer.class, IntegerField::new,
            Long.class, IntegerField::new,
            Double.class, NumberField::new,
            LocalDate.class, DatePicker::new
    );

    public CreateForm(Class<?> entityClass, Consumer<Object[]> createConsumer) {
        super(registerForm(entityClass, createConsumer));
    }

    private static FormBuilder registerForm(Class<?> entityClass, Consumer<Object[]> createConsumer) {
        return (form, dialog) -> {

            List<Supplier<Object>> inputSuppliers = new ArrayList<>();

            ReflectionUtils.doWithFields(entityClass, field -> {

                ColumnView columnView = field.getAnnotation(ColumnView.class);

                if (columnView == null || !columnView.isCreationRequired()) {
                    return;
                }

                EnumColumn enumAnnotation = field.getAnnotation(EnumColumn.class);

                AbstractSinglePropertyField<?, ?> inputField;

                if (enumAnnotation != null) {
                    Class<?> enumClass = enumAnnotation.value();
                    inputField = getSelectorForEnum(enumClass);

                } else {
                     inputField = INPUT_FIELD_MAPPER.get(field.getType()).apply(columnView.viewName());
                }

                form.add(inputField);
                inputSuppliers.add(inputField::getValue);
            });

            Button saveButton = new Button(
                    "Сохранить",
                    e -> {
                        createConsumer.accept(inputSuppliers.stream().map(Supplier::get).toArray());
                        dialog.close();
                    }
            );

            form.add(saveButton);
        };
    }

    private static Select<?> getSelectorForEnum(Class<?> enumClass) {
        if (enumClass == JobTitle.class) {
            Select<JobTitle> jobTitleField = new Select<>();
            jobTitleField.setLabel("Должность");
            jobTitleField.setItems(JobTitle.values());
            return jobTitleField;

        } else if (enumClass == TradingPointBuildingType.class) {
            Select<TradingPointBuildingType> tradingPointBuildingTypeField = new Select<>();

            tradingPointBuildingTypeField.setLabel("Тип");
            tradingPointBuildingTypeField.setItems(TradingPointBuildingType.values());
            return tradingPointBuildingTypeField;

        } else if (enumClass == OrderStatus.class) {
            Select<OrderStatus> orderStatusSelect = new Select<>();

            orderStatusSelect.setLabel("Статус заказа");
            orderStatusSelect.setItems(OrderStatus.values());
            return orderStatusSelect;
        }

        throw new RuntimeException("Не удалось найти Enum");
    }
}
