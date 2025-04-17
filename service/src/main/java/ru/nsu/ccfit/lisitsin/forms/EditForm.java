package ru.nsu.ccfit.lisitsin.forms;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.util.ReflectionUtils;
import ru.nsu.ccfit.lisitsin.tableview.FormBuilder;
import ru.nsu.ccfit.lisitsin.utils.ColumnViewName;

import java.lang.reflect.Field;
import java.time.LocalDate;

public class EditForm<T> extends DefaultForm {

    public EditForm(Class<T> targetClass, T item) {
        super(getForm(targetClass, item));
    }

    private static <T> FormBuilder getForm(Class<T> targetClass, T item) {
        return (form, dialog) -> {
            ReflectionUtils.doWithFields(
                    targetClass,
                    field -> {
                        ColumnViewName annotation = field.getAnnotation(ColumnViewName.class);
                        if (annotation != null && annotation.isVisible()) {
                            var component = getComponent(field, annotation.value(), item);

                            if (!annotation.isEditable()) {
                                component.setReadOnly(true);
                            }

                            form.add(component);
                        }
                    }
            );

            Button saveButton = new Button(
                    "Сохранить",
                    e -> {
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



