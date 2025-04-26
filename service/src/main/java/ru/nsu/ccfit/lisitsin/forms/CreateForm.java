package ru.nsu.ccfit.lisitsin.forms;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.button.Button;
import org.springframework.util.ReflectionUtils;
import ru.nsu.ccfit.lisitsin.annotations.ColumnView;
import ru.nsu.ccfit.lisitsin.annotations.EnumColumn;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CreateForm extends DefaultForm {

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
                    inputSuppliers.add(() -> inputField.getValue() == null ? null : inputField.getValue().toString());

                } else {
                    inputField = INPUT_FIELD_MAPPER.get(field.getType()).apply(columnView.viewName());
                    inputSuppliers.add(inputField::getValue);
                }

                form.add(inputField);
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
}
