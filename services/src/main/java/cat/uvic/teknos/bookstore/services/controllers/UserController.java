package cat.uvic.teknos.bookstore.services.controllers;

import cat.teknos.bookstore.domain.jpa.models.User;
import cat.uvic.teknos.bookstore.services.exception.ResourceNotFoundException;
import cat.uvic.teknos.bookstore.services.exception.ServerErrorException;
import cat.uvic.teknos.bookstore.services.utils.Mappers;
import com.albertdiaz.bookstore.models.ModelFactory;
import com.albertdiaz.bookstore.repositories.RepositoryFactory;
import com.fasterxml.jackson.core.JsonProcessingException;

public class UserController implements Controller {
    private final RepositoryFactory repositoryFactory;
    private final ModelFactory modelFactory;

    public UserController(RepositoryFactory repositoryFactory, ModelFactory modelFactory) {
        this.repositoryFactory = repositoryFactory;
        this.modelFactory = modelFactory;
    }

    @Override
    public String get(int id) {
        var user = repositoryFactory.getUserRepository().get(id);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        try {
            return Mappers.get().writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new ServerErrorException("Error serializing user", e);
        }
    }

    @Override
    public String get() {
        var users = repositoryFactory.getUserRepository().getAll();
        try {
            return Mappers.get().writeValueAsString(users);
        } catch (JsonProcessingException e) {
            throw new ServerErrorException("Error serializing users", e);
        }
    }

    @Override
    public void post(String json) {
        try {
            var user = modelFactory.createUser();
            Mappers.get().readerForUpdating(user).readValue(json);
            repositoryFactory.getUserRepository().save(user);
        } catch (JsonProcessingException e) {
            throw new ServerErrorException("Error deserializing user: " + e.getMessage(), e);
        }
    }

    @Override
    public void put(int id, String json) {
        var existingUser = repositoryFactory.getUserRepository().get(id);
        if (existingUser == null) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }

        try {
            Mappers.get().readerForUpdating(existingUser).readValue(json);
            repositoryFactory.getUserRepository().save(existingUser);
        } catch (JsonProcessingException e) {
            throw new ServerErrorException("Error updating user", e);
        }
    }

    @Override
    public void delete(int id) {
        var user = repositoryFactory.getUserRepository().get(id);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }

        repositoryFactory.getUserRepository().delete(user);
    }
}