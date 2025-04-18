package ru.nsu.ccfit.lisitsin.forms;

import com.vaadin.flow.component.textfield.TextField;
import org.springframework.util.ReflectionUtils;
import ru.nsu.ccfit.lisitsin.utils.ColumnViewName;

import java.lang.reflect.Field;

public class ObjectViewForm<T> extends DefaultForm {

    public ObjectViewForm(Class<T> targetClass, Object item) {
        super(getForm(targetClass, item));
    }

    private static <T> FormBuilder getForm(Class<T> targetClass, Object item) {
        return (form, dialog) ->
                ReflectionUtils.doWithFields(
                        targetClass,
                        field -> {
                            ColumnViewName annotation = field.getAnnotation(ColumnViewName.class);
                            if (annotation != null && annotation.isVisible()) {
                                form.add(getComponent(field, annotation.value(), item));
                            }
                        }
                );
    }

    private static <T> TextField getComponent(Field field, String columnName, T item) {
        field.setAccessible(true);

        var component = new TextField(columnName);
        Object value = ReflectionUtils.getField(field, item);
        component.setValue(value == null ? "---" : value.toString());

        component.setReadOnly(true);

        return component;
    }
}



