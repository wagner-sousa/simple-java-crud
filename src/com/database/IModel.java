package com.database;

import java.util.ArrayList;

public interface IModel {
    public Integer getId();
    public void setId(Integer id);

    public static ArrayList<IModel> list() {
        return new ArrayList<>();
    }

    public void insert();

    public static IModel getById() {
        return null;
    }

    public void destroy();
}

