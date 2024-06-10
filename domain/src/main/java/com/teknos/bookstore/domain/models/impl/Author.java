package com.teknos.bookstore.domain.models.impl;

public class Author implements com.teknos.bookstore.domain.models.Author{
    private int id;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
