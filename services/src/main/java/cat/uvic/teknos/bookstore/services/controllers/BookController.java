package cat.uvic.teknos.bookstore.services.controllers;

import com.albertdiaz.bookstore.models.ModelFactory;
import com.albertdiaz.bookstore.repositories.RepositoryFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BookController implements Controller {
    private final RepositoryFactory repositoryFactory;
    private final ModelFactory modelFactory;
    private ObjectMapper objectMapper = new ObjectMapper();

    public BookController(RepositoryFactory repositoryFactory, ModelFactory modelFactory) {
        this.repositoryFactory = repositoryFactory;
        this.modelFactory = modelFactory;
    }

    @Override
    public String get(int id) {
        return "";
    }

    @Override
    public String get() {
        var authors = repositoryFactory.getBookRepository().getAll();
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
