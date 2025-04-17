package ru.nsu.ccfit.lisitsin.tableview;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;

@FunctionalInterface
public interface FormBuilder {

    void build(FormLayout formLayout, Dialog dialog);

}
