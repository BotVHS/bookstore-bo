package cat.uvic.teknos.bookstore.services.controllers;

import cat.teknos.bookstore.domain.jpa.models.Author;
import cat.uvic.teknos.bookstore.services.utils.Mappers;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class AuthorControllerTest {
    @Test
    void serializationAndDeserializationWithAuthor() throws JsonProcessingException {
        Author author = new Author();
        author.setFirstName("Stephen");
        author.setLastName("King");
        author.setBirthDate(LocalDate.of(1947, 9, 21));
        author.setNationality("American");
        author.setBiography("Famous horror author");

        var mapper = Mappers.get();
        var json = mapper.writeValueAsString(author);

        assertNotNull(json);
        var authorDeserialized = mapper.readValue(json, Author.class);
        assertNotNull(authorDeserialized);
        assertEquals("Stephen", authorDeserialized.getFirstName());
        assertEquals("King", authorDeserialized.getLastName());
        assertEquals(LocalDate.of(1947, 9, 21), authorDeserialized.getBirthDate());
    }
}