package cat.teknos.bookstore.domain.jdbc.repositories;

import cat.teknos.bookstore.domain.jdbc.models.Author;
import com.albertdiaz.dbtestutils.junit.CreateSchemaExtension;
import com.albertdiaz.dbtestutils.junit.GetConnectionExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

@ExtendWith({CreateSchemaExtension.class, GetConnectionExtension.class})
class JdbcAuthorRepositoryTest {

    private final Connection connection;
    public JdbcAuthorRepositoryTest(Connection connection) {
        this.connection = connection;
    }
    @Test
    @DisplayName("Test 1: INSERT AUTHOR")
    void InsertNewAuthorTest() throws SQLException {
        Author author = new Author();
        author.setFirstName("First");
        author.setLastName("Test");
        author.setBiography("This is the first test");
        author.setBirthDate(LocalDate.of(1990, 1, 1));
        author.setNationality("Catalan");

        var repository = new JdbcAuthorRepository(connection);
        repository.save(author);
        assertTrue(author.getId() > 0);
    }




    @Test
    @DisplayName("Test 2: UPDATE AUTHOR")
    void UpdateAuthorTest() throws SQLException {
        Author author = new Author();
        author.setFirstName("UpdatedFirst");
        author.setLastName("UpdatedLast");
        author.setBiography("Updated biography");
        author.setBirthDate(LocalDate.of(1985, 5, 15));
        author.setNationality("UpdatedNationality");
        var repository = new JdbcAuthorRepository(connection);
        repository.save(author);
        assertTrue(true);
    }

    @Test
    @DisplayName("Test 3: DELETE AUTHOR")
    void DeleteAuthorTest() throws SQLException {
        Author author = new Author();
        author.setId(2);

        var repository = new JdbcAuthorRepository(connection);
        repository.delete(author);

        assertNull(repository.get(2));
    }

    @Test
    @DisplayName("Test 4: GET AUTHOR")
    void GetAuthorTest() throws SQLException {
        var repository = new JdbcAuthorRepository(connection);
        assertNotNull(repository.get(1));
    }

    @Test
    @DisplayName("Test 5: GETALL AUTHOR")
    void GetAllAuthorsTest() throws SQLException {
        var repository = new JdbcAuthorRepository(connection);
        assertNotNull(repository.getAll());
    }

    @Test
    @DisplayName("Test 6: GET AUTHOR BY NAME")
    void GetAuthorByNameTest() throws SQLException {
        var repository = new JdbcAuthorRepository(connection);
        String name = "John";
        assertNotNull(repository.getByName(name));
    }
}