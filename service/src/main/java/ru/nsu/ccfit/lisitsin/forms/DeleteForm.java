package ru.nsu.ccfit.lisitsin.forms;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.util.ReflectionUtils;
import ru.nsu.ccfit.lisitsin.utils.ColumnView;

import java.lang.reflect.Field;

public class DeleteForm<T> extends DefaultForm {

    public DeleteForm(Class<T> targetClass, T item, Runnable deleteConsumer) {
        super(getForm(targetClass, item, deleteConsumer));
    }

    private static <T> FormBuilder getForm(Class<T> targetClass, T item, Runnable deleteFunc) {
        return (form, dialog) -> {
            ReflectionUtils.doWithFields(
                    targetClass,
                    field -> {
                        ColumnView annotation = field.getAnnotation(ColumnView.class);
                        if (annotation != null && annotation.isVisible()) {
                            form.add(getComponent(field, annotation.viewName(), item));
                        }
                    }
            );

            Button saveButton = new Button(
                    "Удалить",
                    e -> {
                        deleteFunc.run();
                        dialog.close();
                    }
            );

            form.add(saveButton);
        };
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



