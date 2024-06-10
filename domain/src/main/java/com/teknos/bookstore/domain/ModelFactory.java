package com.teknos.bookstore.domain;

import java.util.ServiceLoader;

import com.teknos.bookstore.domain.models.Author;
import com.teknos.bookstore.domain.services.ModelFactoryService;

public class ModelFactory implements ModelFactoryService{
    private static final ModelFactoryService SERVICE;

    static{
        SERVICE = load();
    }

    private static ModelFactoryService load() {
        var serviceLoader = ServiceLoader.load(ModelFactoryService.class);

        return serviceLoader.findFirst().orElseThrow();
    }

    @Override
    public Author newAuthor() {
        return SERVICE.newAuthor();
    }
}