package ru.nsu.ccfit.lisitsin.forms;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import ru.nsu.ccfit.lisitsin.tableview.FormBuilder;

public class DefaultForm extends Dialog {

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

}
