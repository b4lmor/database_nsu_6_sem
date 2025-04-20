package ru.nsu.ccfit.lisitsin.forms;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import ru.nsu.ccfit.lisitsin.annotations.ColumnView;
import ru.nsu.ccfit.lisitsin.entity.JobTitle;
import ru.nsu.ccfit.lisitsin.entity.OrderStatus;
import ru.nsu.ccfit.lisitsin.entity.TradingPointBuildingType;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.Map;
import java.util.function.Function;

public class DefaultForm extends Dialog {

    protected static final Map<Class<?>, Function<String, AbstractSinglePropertyField<?, ?>>> INPUT_FIELD_MAPPER = Map.of(
            String.class, TextField::new,
            Integer.class, IntegerField::new,
            Long.class, IntegerField::new,
            Double.class, NumberField::new,
            LocalDate.class, DatePicker::new
    );

    public DefaultForm(FormBuilder formBuilder) {
        setModal(true);
        setCloseOnEsc(true);
        setCloseOnOutsideClick(false);

        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));

        formBuilder.build(formLayout, this);

        Button closeButton = new Button("Закрыть", e -> close());

        VerticalLayout dialogContent = new VerticalLayout(formLayout, closeButton);
        dialogContent.setPadding(false);

        add(dialogContent);
    }

    protected static <T> T createInstanceFromArray(Class<T> clazz, Object[] values)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        T instance = clazz.getDeclaredConstructor().newInstance();
        Field[] fields = clazz.getDeclaredFields();

        int j = 0;
        for (int i = j; i < fields.length; i++) {
            if (!fields[i].isAnnotationPresent(ColumnView.class) || !fields[i].getAnnotation(ColumnView.class).isVisible()) {
                continue;
            }

            fields[i].setAccessible(true);
            fields[i].set(instance, values[j]);
            j++;
        }

        return instance;
    }

    protected static Select<?> getSelectorForEnum(Class<?> enumClass) {
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
