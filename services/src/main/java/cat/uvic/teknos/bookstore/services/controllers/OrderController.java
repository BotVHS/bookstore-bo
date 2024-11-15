package cat.uvic.teknos.bookstore.services.controllers;

import cat.teknos.bookstore.domain.jpa.models.Order;
import cat.uvic.teknos.bookstore.services.exception.ResourceNotFoundException;
import cat.uvic.teknos.bookstore.services.exception.ServerErrorException;
import cat.uvic.teknos.bookstore.services.utils.Mappers;
import com.albertdiaz.bookstore.models.ModelFactory;
import com.albertdiaz.bookstore.repositories.RepositoryFactory;
import com.fasterxml.jackson.core.JsonProcessingException;

public class OrderController implements Controller {
    private final RepositoryFactory repositoryFactory;
    private final ModelFactory modelFactory;

    public OrderController(RepositoryFactory repositoryFactory, ModelFactory modelFactory) {
        this.repositoryFactory = repositoryFactory;
        this.modelFactory = modelFactory;
    }

    @Override
    public String get(int id) {
        var order = repositoryFactory.getOrderRepository().get(id);
        if (order == null) {
            throw new ResourceNotFoundException("Order not found with id: " + id);
        }
        try {
            return Mappers.get().writeValueAsString(order);
        } catch (JsonProcessingException e) {
            throw new ServerErrorException("Error serializing order", e);
        }
    }

    @Override
    public String get() {
        var orders = repositoryFactory.getOrderRepository().getAll();
        try {
            return Mappers.get().writeValueAsString(orders);
        } catch (JsonProcessingException e) {
            throw new ServerErrorException("Error serializing orders", e);
        }
    }

    @Override
    public void post(String json) {
        try {
            var order = modelFactory.createOrder();
            Mappers.get().readerForUpdating(order).readValue(json);

            // Verif user
            if (order.getUser() == null || order.getUser().getId() == 0) {
                throw new ServerErrorException("User is required for an order");
            }
            var user = repositoryFactory.getUserRepository().get(order.getUser().getId());
            if (user == null) {
                throw new ResourceNotFoundException("User not found with id: " + order.getUser().getId());
            }

            repositoryFactory.getOrderRepository().save(order);
        } catch (JsonProcessingException e) {
            throw new ServerErrorException("Error deserializing order: " + e.getMessage(), e);
        }
    }

    @Override
    public void put(int id, String json) {
        var existingOrder = repositoryFactory.getOrderRepository().get(id);
        if (existingOrder == null) {
            throw new ResourceNotFoundException("Order not found with id: " + id);
        }

        try {
            Mappers.get().readerForUpdating(existingOrder).readValue(json);
            repositoryFactory.getOrderRepository().save(existingOrder);
        } catch (JsonProcessingException e) {
            throw new ServerErrorException("Error updating order", e);
        }
    }

    @Override
    public void delete(int id) {
        var order = repositoryFactory.getOrderRepository().get(id);
        if (order == null) {
            throw new ResourceNotFoundException("Order not found with id: " + id);
        }

        repositoryFactory.getOrderRepository().delete(order);
    }
}