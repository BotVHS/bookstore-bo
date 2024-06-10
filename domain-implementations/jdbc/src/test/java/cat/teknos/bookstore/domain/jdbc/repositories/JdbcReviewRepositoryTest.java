package cat.teknos.bookstore.domain.jdbc.repositories;

import cat.teknos.bookstore.domain.jdbc.models.Book;
import cat.teknos.bookstore.domain.jdbc.models.Review;
import cat.teknos.bookstore.domain.jdbc.models.User;
import com.albertdiaz.bookstore.exceptions.RepositoryException;
import com.albertdiaz.bookstore.repositories.ReviewRepository;
import com.albertdiaz.dbtestutils.junit.CreateSchemaExtension;
import com.albertdiaz.dbtestutils.junit.GetConnectionExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith({CreateSchemaExtension.class, GetConnectionExtension.class})
public class JdbcReviewRepositoryTest {

    private final Connection connection;

    public JdbcReviewRepositoryTest(Connection connection) {
        this.connection = connection;
    }

    @Test
    @DisplayName("Test 1: INSERT REVIEW")
    void insertReviewTest() throws SQLException {
        Book book = new Book();
        book.setId(1);

        User user = new User();
        user.setId(1);

        Review review = new Review();
        review.setBook(book);
        review.setUser(user);
        review.setRating(5);
        review.setComment("Great book!");
        review.setReviewDate(LocalDate.now());

        var repository = new JdbcReviewRepository(connection);
        repository.save(review);
        assertTrue(review.getId() > 0);
    }

    @Test
    @DisplayName("Test 2: UPDATE REVIEW")
    void updateReviewTest() throws SQLException {
        Book book = new Book();
        book.setId(1);

        User user = new User();
        user.setId(1);

        Review review = new Review();
        review.setId(1);
        review.setBook(book);
        review.setUser(user);
        review.setRating(4);
        review.setComment("Updated comment");
        review.setReviewDate(LocalDate.now());

        var repository = new JdbcReviewRepository(connection);
        repository.save(review);
        assertEquals(1, review.getId());
    }

    @Test
    @DisplayName("Test 3: DELETE REVIEW")
    void deleteReviewTest() throws SQLException {
        Review review = new Review();
        review.setId(2);

        var repository = new JdbcReviewRepository(connection);
        repository.delete(review);

        assertNull(repository.get(2));
    }

    @Test
    @DisplayName("Test 4: GET REVIEW")
    void getReviewTest() throws SQLException {
        var repository = new JdbcReviewRepository(connection);
        assertNotNull(repository.get(1));
    }

    @Test
    @DisplayName("Test 5: GET ALL REVIEWS")
    void getAllReviewsTest() throws SQLException {
        var repository = new JdbcReviewRepository(connection);
        assertNotNull(repository.getAll());
    }

    @Test
    @DisplayName("Test 6: GET REVIEWS BY BOOK ID")
    void getReviewsByBookIdTest() throws SQLException {
        var repository = new JdbcReviewRepository(connection);
        int bookId = 1;
        assertNotNull(repository.getByBookId(bookId));
    }

    @Test
    @DisplayName("Test 7: GET REVIEWS BY USER ID")
    void getReviewsByUserIdTest() throws SQLException {
        var repository = new JdbcReviewRepository(connection);
        int userId = 1;
        assertNotNull(repository.getByUserId(userId));
    }
}
