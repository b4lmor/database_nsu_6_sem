package ru.nsu.ccfit.lisitsin.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface TableView {

    String viewName() default "";

    String tableName();

    boolean isVisible() default true;

    int order();

}
