package ru.nsu.ccfit.lisitsin.forms;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.util.ReflectionUtils;
import ru.nsu.ccfit.lisitsin.annotations.ColumnView;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class EditForm<T> extends DefaultForm {

    public EditForm(Class<T> targetClass, T item, Consumer<List<Object>> updateConsumer) {
        super(getForm(targetClass, item, updateConsumer));
    }

    private static <T> FormBuilder getForm(Class<T> targetClass, T item, Consumer<List<Object>> updateConsumer) {
        return (form, dialog) -> {
            Map<String, Supplier<Object>> fields = new LinkedHashMap<>();

            ReflectionUtils.doWithFields(
                    targetClass,
                    field -> {
                        ColumnView annotation = field.getAnnotation(ColumnView.class);
                        if (annotation != null && annotation.isVisible()) {
                            var component = getComponent(field, annotation.viewName(), item);

                            if (!annotation.isEditable()) {
                                component.setReadOnly(true);
                            }

                            fields.put(annotation.columnName(), component::getValue);
                            form.add(component);
                        }
                    }
            );

            Button saveButton = new Button(
                    "Сохранить",
                    e -> {
                        updateConsumer.accept(fields.values().stream().map(Supplier::get).toList());
                        dialog.close();
                    }
            );

            form.add(saveButton);
        };
    }

    private static <T> AbstractSinglePropertyField<?, ?> getComponent(Field field, String columnName, T item) {
        field.setAccessible(true);

        if (field.getType().equals(LocalDate.class)) {
            var component = new DatePicker(columnName);
            Object value = ReflectionUtils.getField(field, item);
            if (value != null) {
                component.setValue((LocalDate) ReflectionUtils.getField(field, item));
            }
            return component;

        } else {
            var component = new TextField(columnName);
            Object value = ReflectionUtils.getField(field, item);
            component.setValue(value == null ? "---" : value.toString());
            return component;
        }

    }
}



