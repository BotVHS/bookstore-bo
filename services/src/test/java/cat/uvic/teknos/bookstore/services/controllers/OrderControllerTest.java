package cat.uvic.teknos.bookstore.services.controllers;

import cat.teknos.bookstore.domain.jpa.models.*;
import cat.uvic.teknos.bookstore.services.utils.Mappers;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class OrderControllerTest {
    @Test
    void serializationAndDeserializationWithOrder() throws JsonProcessingException {
        User user = new User();
        user.setId(1);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john@example.com");

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDate.now());
        order.setTotalPrice(29.99f);
        order.setShippingAddress("123 Main St");
        order.setOrderStatus("Pending");

        var mapper = Mappers.get();
        var json = mapper.writeValueAsString(order);

        assertNotNull(json);
        var orderDeserialized = mapper.readValue(json, Order.class);
        assertNotNull(orderDeserialized);
        assertNotNull(orderDeserialized.getUser());
        assertEquals(29.99f, orderDeserialized.getTotalPrice());
        assertEquals("Pending", orderDeserialized.getOrderStatus());
        assertEquals("John", orderDeserialized.getUser().getFirstName());
        assertEquals("123 Main St", orderDeserialized.getShippingAddress());
    }
}
