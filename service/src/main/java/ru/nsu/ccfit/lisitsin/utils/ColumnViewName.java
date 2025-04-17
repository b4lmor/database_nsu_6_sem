package ru.nsu.ccfit.lisitsin.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ColumnViewName {

    String value();

    boolean isVisible() default true;

    boolean isEditable() default true;

}
