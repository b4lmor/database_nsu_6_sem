package ru.nsu.ccfit.lisitsin.entity;

import java.util.List;

public interface Identical {

    List<Object> getIds();

    default List<String> getIdColumns() {
        return List.of("id");
    }

}
