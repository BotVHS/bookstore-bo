package cat.uvic.teknos.bookstore.services.controllers;

import cat.teknos.bookstore.domain.jpa.models.*;
import cat.uvic.teknos.bookstore.services.utils.Mappers;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class ReviewControllerTest {
    @Test
    void serializationAndDeserializationWithReview() throws JsonProcessingException {
        Author author = new Author();
        author.setId(1);
        author.setFirstName("Stephen");
        author.setLastName("King");

        Book book = new Book();
        book.setId(1);
        book.setTitle("The Shining");
        book.setAuthor(author);

        User user = new User();
        user.setId(1);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john@example.com");

        Review review = new Review();
        review.setBook(book);
        review.setUser(user);
        review.setRating(5);
        review.setComment("Excellent book!");
        review.setReviewDate(LocalDate.now());

        var mapper = Mappers.get();
        var json = mapper.writeValueAsString(review);

        assertNotNull(json);
        var reviewDeserialized = mapper.readValue(json, Review.class);
        assertNotNull(reviewDeserialized);
        assertNotNull(reviewDeserialized.getBook());
        assertNotNull(reviewDeserialized.getUser());
        assertEquals(5, reviewDeserialized.getRating());
        assertEquals("Excellent book!", reviewDeserialized.getComment());
        assertEquals("The Shining", reviewDeserialized.getBook().getTitle());
        assertEquals("John", reviewDeserialized.getUser().getFirstName());
    }
}
