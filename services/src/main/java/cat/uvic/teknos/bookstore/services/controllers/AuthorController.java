package cat. uvic. teknos. bookstore. services. controllers;

import cat.uvic.teknos.bookstore.services.controllers.Controller;
import com.albertdiaz.bookstore.models.ModelFactory;
import com.albertdiaz.bookstore.repositories.AuthorRepository;
import com.albertdiaz.bookstore.repositories.RepositoryFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teknos.bookstore.domain.models.Author;

public class AuthorController implements Controller {
    private final RepositoryFactory repositoryFactory;
    private final ModelFactory modelFactory;
    private ObjectMapper objectMapper = new ObjectMapper();

    public AuthorController(RepositoryFactory repositoryFactory, ModelFactory modelFactory) {
        this.repositoryFactory = repositoryFactory;
        this.modelFactory = modelFactory;
    }

    @Override
    public String get(int id) {
        // TODO: retrieve individual author from id, serialize authors in JSON format
        return "";
    }

    @Override
    public String get() {
        var authors = repositoryFactory.getAuthorRepository().getAll();
        try {
            return objectMapper.writeValueAsString(authors);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void post(String json) {

    }

    @Override
    public void put(int id, String json) {

    }

    @Override
    public void delete(int id) {

    }
}