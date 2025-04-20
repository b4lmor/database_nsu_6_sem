package ru.nsu.ccfit.lisitsin.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface LinkTableView {

    Class<?> linkClass();

    String joinColumn() default "id";

}
