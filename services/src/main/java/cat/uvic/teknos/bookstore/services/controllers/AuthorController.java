package cat.uvic.teknos.bookstore.services.controllers;

import cat.teknos.bookstore.domain.jpa.models.Author;
import cat.uvic.teknos.bookstore.services.exception.ResourceNotFoundException;
import cat.uvic.teknos.bookstore.services.exception.ServerErrorException;
import cat.uvic.teknos.bookstore.services.utils.Mappers;
import com.albertdiaz.bookstore.models.ModelFactory;
import com.albertdiaz.bookstore.repositories.RepositoryFactory;
import com.fasterxml.jackson.core.JsonProcessingException;

public class AuthorController implements Controller {
    private final RepositoryFactory repositoryFactory;
    private final ModelFactory modelFactory;

    public AuthorController(RepositoryFactory repositoryFactory, ModelFactory modelFactory) {
        this.repositoryFactory = repositoryFactory;
        this.modelFactory = modelFactory;
    }

    @Override
    public String get(int id) {
        var author = repositoryFactory.getAuthorRepository().get(id);
        if (author == null) {
            throw new ResourceNotFoundException("Author not found with id: " + id);
        }
        try {
            return Mappers.get().writeValueAsString(author);
        } catch (JsonProcessingException e) {
            throw new ServerErrorException("Error serializing author", e);
        }
    }

    @Override
    public String get() {
        var authors = repositoryFactory.getAuthorRepository().getAll();
        try {
            return Mappers.get().writeValueAsString(authors);
        } catch (JsonProcessingException e) {
            throw new ServerErrorException("Error serializing authors", e);
        }
    }

    @Override
    public void post(String json) {
        try {
            var author = modelFactory.createAuthor();
            Mappers.get().readerForUpdating(author).readValue(json);
            repositoryFactory.getAuthorRepository().save(author);
        } catch (JsonProcessingException e) {
            throw new ServerErrorException("Error deserializing author: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new ServerErrorException("Error creating author: " + e.getMessage(), e);
        }
    }

    @Override
    public void put(int id, String json) {
        var author = repositoryFactory.getAuthorRepository().get(id);
        if (author == null) {
            throw new ResourceNotFoundException("Author not found with id: " + id);
        }

        try {
            Mappers.get().readerForUpdating(author).readValue(json);
            repositoryFactory.getAuthorRepository().save(author);
        } catch (JsonProcessingException e) {
            throw new ServerErrorException("Error updating author", e);
        }
    }

    @Override
    public void delete(int id) {
        var author = repositoryFactory.getAuthorRepository().get(id);
        if (author == null) {
            throw new ResourceNotFoundException("Author not found with id: " + id);
        }

        repositoryFactory.getAuthorRepository().delete(author);
    }
}