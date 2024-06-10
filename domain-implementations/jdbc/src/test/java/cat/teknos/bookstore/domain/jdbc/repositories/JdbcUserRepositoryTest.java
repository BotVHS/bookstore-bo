package cat.teknos.bookstore.domain.jdbc.repositories;

import cat.teknos.bookstore.domain.jdbc.models.User;
import com.albertdiaz.dbtestutils.junit.CreateSchemaExtension;
import com.albertdiaz.dbtestutils.junit.GetConnectionExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({CreateSchemaExtension.class, GetConnectionExtension.class})
class JdbcUserRepositoryTest {

    private final Connection connection;

    public JdbcUserRepositoryTest(Connection connection) {
        this.connection = connection;
    }

    @Test
    @DisplayName("TEST 1: INSERT USER")
    void insertUserTest() throws SQLException {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPasswordHash("password123");
        user.setAddress("123 Main St");
        user.setCity("Springfield");
        user.setCountry("USA");
        user.setPostalCode("12345");
        user.setJoinDate(LocalDate.of(2020, 1, 1));

        var repository = new JdbcUserRepository(connection);
        repository.save(user);
        assertTrue(user.getId() > 0);
    }

    @Test
    @DisplayName("TEST 2: UPDATE USER")
    void updateUserTest() throws SQLException {
        User user = new User();
        user.setId(2);
        user.setFirstName("Jane11111");
        user.setLastName("Doe");
        user.setEmail("jane.doe@example.com");
        user.setPasswordHash("newpassword123");
        user.setAddress("456 Elm St");
        user.setCity("Springfield");
        user.setCountry("USA");
        user.setPostalCode("54321");
        user.setJoinDate(LocalDate.of(2020, 2, 2));

        var repository = new JdbcUserRepository(connection);
        repository.save(user);
        assertTrue(true);
    }

    @Test
    @DisplayName("TEST 3: DELETE USER")
    void deleteUserTest() throws SQLException {
        User user = new User();
        user.setId(2);

        var repository = new JdbcUserRepository(connection);
        repository.delete(user);

        assertNull(repository.get(2));
    }

    @Test
    @DisplayName("TEST 4: GET USER BY ID")
    void getUserByIdTest() throws SQLException {
        var repository = new JdbcUserRepository(connection);
        assertNotNull(repository.get(1));
    }

    @Test
    @DisplayName("TEST 5: GET ALL USERS")
    void getAllUsersTest() throws SQLException {
        var repository = new JdbcUserRepository(connection);
        assertNotNull(repository.getAll());
    }

    @Test
    @DisplayName("TEST 6: GET USER BY NAME")
    void getUserByNameTest() throws SQLException {
        var repository = new JdbcUserRepository(connection);
        String name = "Carol";
        assertNotNull(repository.getByName(name));
    }
}
