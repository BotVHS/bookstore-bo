package cat.uvic.teknos.bookstore.services.controllers;

import cat.teknos.bookstore.domain.jpa.models.Author;
import cat.teknos.bookstore.domain.jpa.models.Book;
import cat.uvic.teknos.bookstore.services.utils.Mappers;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class BookControllerTest {
    @Test
    void serializationAndDeserializationWithBookAndAuthor() throws JsonProcessingException {
        Author author = new Author();
        author.setId(1);
        author.setFirstName("Stephen");
        author.setLastName("King");

        Book book = new Book();
        book.setTitle("The Shining");
        book.setAuthor(author);
        book.setIsbn("978-0307743657");
        book.setPrice(9.99f);
        book.setGenre("Horror");
        book.setPublishDate(LocalDate.of(1977, 1, 28));
        book.setPublisher("Doubleday");
        book.setPageCount(447);

        var mapper = Mappers.get();
        var json = mapper.writeValueAsString(book);

        assertNotNull(json);
        var bookDeserialized = mapper.readValue(json, Book.class);
        assertNotNull(bookDeserialized);
        assertNotNull(bookDeserialized.getAuthor());
        assertEquals("The Shining", bookDeserialized.getTitle());
        assertEquals("Stephen", bookDeserialized.getAuthor().getFirstName());
        assertEquals("King", bookDeserialized.getAuthor().getLastName());
    }
}