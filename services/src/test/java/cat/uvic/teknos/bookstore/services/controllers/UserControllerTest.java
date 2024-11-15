package cat.uvic.teknos.bookstore.services.controllers;

import cat.teknos.bookstore.domain.jpa.models.User;
import cat.uvic.teknos.bookstore.services.utils.Mappers;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserControllerTest {
    @Test
    void serializationAndDeserializationWithUser() throws JsonProcessingException {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john@example.com");
        user.setPasswordHash("hashedpassword");
        user.setAddress("123 Main St");
        user.setCity("New York");
        user.setCountry("USA");
        user.setPostalCode("10001");
        user.setJoinDate(LocalDate.now());

        var mapper = Mappers.get();
        var json = mapper.writeValueAsString(user);

        assertNotNull(json);
        var userDeserialized = mapper.readValue(json, User.class);
        assertNotNull(userDeserialized);
        assertEquals("John", userDeserialized.getFirstName());
        assertEquals("Doe", userDeserialized.getLastName());
        assertEquals("john@example.com", userDeserialized.getEmail());
        assertEquals("123 Main St", userDeserialized.getAddress());
        assertEquals("New York", userDeserialized.getCity());
        assertEquals("USA", userDeserialized.getCountry());
        assertEquals("10001", userDeserialized.getPostalCode());
    }
}