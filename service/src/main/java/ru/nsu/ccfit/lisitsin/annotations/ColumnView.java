package ru.nsu.ccfit.lisitsin.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ColumnView {

    String viewName();

    String columnName();

    boolean isVisible() default true;

    boolean isEditable() default true;

    boolean isCreationRequired() default true;

}
