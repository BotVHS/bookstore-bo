package com.teknos.bookstore.domain.services.impl;

import com.teknos.bookstore.domain.models.Author;
import com.teknos.bookstore.domain.services.ModelFactoryService;

public class ModelFactoryImpl implements ModelFactoryService {
    @Override
    public Author newAuthor() {
        return new com.teknos.bookstore.domain.models.impl.Author();
    }
}
