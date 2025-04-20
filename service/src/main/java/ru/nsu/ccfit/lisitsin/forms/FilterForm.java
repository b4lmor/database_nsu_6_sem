package ru.nsu.ccfit.lisitsin.forms;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.button.Button;
import org.springframework.util.ReflectionUtils;
import ru.nsu.ccfit.lisitsin.annotations.ColumnView;
import ru.nsu.ccfit.lisitsin.annotations.EnumColumn;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class FilterForm<T> extends DefaultForm {

    public FilterForm(Class<T> targetClass, Consumer<Map<Field, Object>> filterItemConsumer) {
        super(getForm(targetClass, filterItemConsumer));
    }

    private static <T> FormBuilder getForm(Class<T> entityClass, Consumer<Map<Field, Object>> filterItemConsumer) {
        return (form, dialog) -> {

            Map<Field, Supplier<Object>> inputSuppliers = new HashMap<>();

            ReflectionUtils.doWithFields(entityClass, field -> {

                ColumnView columnView = field.getAnnotation(ColumnView.class);

                if (columnView == null || !columnView.isVisible()) {
                    return;
                }

                EnumColumn enumAnnotation = field.getAnnotation(EnumColumn.class);

                AbstractSinglePropertyField<?, ?> inputField;

                if (enumAnnotation != null) {
                    Class<?> enumClass = enumAnnotation.value();
                    inputField = getSelectorForEnum(enumClass);
                    inputSuppliers.put(field, () -> inputField.getValue().toString());

                } else {
                    inputField = INPUT_FIELD_MAPPER.get(field.getType()).apply(columnView.viewName());
                    inputSuppliers.put(field, inputField::getValue);
                }

                form.add(inputField);
            });

            Button saveButton = new Button(
                    "Сохранить",
                    e -> {
                        try {
                            filterItemConsumer.accept(
                                    inputSuppliers.entrySet()
                                            .stream()
                                            .filter(en -> en.getValue().get() != null &&
                                                    !en.getValue().get().toString().isBlank())
                                            .collect(
                                                    Collectors.toMap(
                                                            Map.Entry::getKey,
                                                            entry -> entry.getValue().get()
                                                    )
                                            )
                            );

                        } catch (Exception ex) {
                            throw new RuntimeException(ex.getMessage());
                        }

                        dialog.close();
                    }
            );

            form.add(saveButton);
        };
    }
}



