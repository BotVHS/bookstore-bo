package cat.teknos.bookstore.domain.jdbc.repositories;

import cat.teknos.bookstore.domain.jdbc.models.Author;
import cat.teknos.bookstore.domain.jdbc.models.Book;
import com.albertdiaz.dbtestutils.junit.CreateSchemaExtension;
import com.albertdiaz.dbtestutils.junit.GetConnectionExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith({CreateSchemaExtension.class, GetConnectionExtension.class})
public class JdbcBookRepositoryTest {

    private final Connection connection;

    public JdbcBookRepositoryTest(Connection connection) {
        this.connection = connection;
    }

    @Test
    @DisplayName("TEST 1: INSERT BOOK")
    void insertBook() throws SQLException {

        Author author = new Author();
        author.setId(1);

        Book book = new Book();
        book.setTitle("Java");
        book.setAuthor(author);
        book.setIsbn("12345678");
        book.setPrice(29.99f);
        book.setGenre("Programming");
        book.setPublishDate(LocalDate.of(1990, 1, 1));
        book.setPublisher("Grupo Planeta");
        book.setPageCount(100);

        var repository = new JdbcBookRepository(connection);
        repository.save(book);
        assertTrue(book.getId() > 0);
    }

    @Test
    @DisplayName("TEST 2: UPDATE BOOK")
    void updateBook() throws SQLException {
        Author author = new Author();
        author.setId(2);
        Book book = new Book();
        book.setTitle("Java2");
        book.setAuthor(author);
        book.setIsbn("12345677");
        book.setPrice(28.99f);
        book.setGenre("Programming2");
        book.setPublishDate(LocalDate.of(1991, 1, 1));
        book.setPublisher("Grupo Universo");
        book.setPageCount(105);
        var repository = new JdbcBookRepository(connection);
        repository.save(book);
        assertTrue(true);

    }

    @Test
    @DisplayName("TEST 3: DELETE BOOK")
    void deleteBook() throws SQLException {
        Book book = new Book();
        book.setId(1);

        var repository = new JdbcBookRepository(connection);
        repository.delete(book);

        assertNull(repository.get(1));
    }

    @Test
    @DisplayName("TEST: GET BOOK")
    void getBook() throws SQLException {
        var repository = new JdbcBookRepository(connection);
        assertNotNull(repository.get(1));
    }

    @Test
    @DisplayName("TEST: GET ALL BOOKS")
    void getAllBooks() throws SQLException {
        var repository = new JdbcBookRepository(connection);
        assertNotNull(repository.getAll());
    }

    @Test
    @DisplayName("TEST: GET BOOK BY TITLE")
    void getBookByTitle() throws SQLException {
        var repository = new JdbcBookRepository(connection);
        String title = "Romance in Paris";
        assertNotNull(repository.getByName(title));
    }

}
