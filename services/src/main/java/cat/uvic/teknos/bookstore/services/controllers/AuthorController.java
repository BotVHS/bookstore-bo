package cat. uvic. teknos. bookstore. services. controllers;

import cat.uvic.teknos.bookstore.services.controllers.Controller;
import com.albertdiaz.bookstore.repositories.AuthorRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teknos.bookstore.domain.models.Author;

public class AuthorController implements Controller<Integer, Author> {
    AuthorRepository repository;
    @Override
    public String get(Integer integer) {
        return "";
    }

    @Override
    public String get() {
        var authors = repository.getAll();
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(authors);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void post(Author value) {
        repository.save(value);
    }

    @Override
    public void put(Integer key, Author value) {

    }

    @Override
    public void delete(Integer key) {

    }
}
