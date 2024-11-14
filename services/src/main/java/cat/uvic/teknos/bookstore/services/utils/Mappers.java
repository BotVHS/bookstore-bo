package cat.uvic.teknos.bookstore.services.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class Mappers {
    private static final ObjectMapper mapper;

    static {
        final var typeMapping = new SimpleModule()
                .addAbstractTypeMapping(com.albertdiaz.bookstore.models.Author.class, cat.teknos.bookstore.domain.jpa.models.Author.class)
                .addAbstractTypeMapping(com.albertdiaz.bookstore.models.Book.class, cat.teknos.bookstore.domain.jpa.models.Book.class)
                .addAbstractTypeMapping(com.albertdiaz.bookstore.models.Order.class, cat.teknos.bookstore.domain.jpa.models.Order.class)
                .addAbstractTypeMapping(com.albertdiaz.bookstore.models.Review.class, cat.teknos.bookstore.domain.jpa.models.Review.class)
                .addAbstractTypeMapping(com.albertdiaz.bookstore.models.User.class, cat.teknos.bookstore.domain.jpa.models.User.class);

        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule())
                .registerModule(typeMapping)
                .findAndRegisterModules();
    }

    public static ObjectMapper get() {
        return mapper;
    }
}