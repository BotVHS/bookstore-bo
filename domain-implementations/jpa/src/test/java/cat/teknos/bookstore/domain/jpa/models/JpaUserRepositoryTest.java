package cat.teknos.bookstore.domain.jpa.models;

import cat.teknos.bookstore.domain.jpa.repositories.JpaUserRepository;
import com.albertdiaz.bookstore.models.User;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class JpaUserRepositoryTest {
    private static EntityManagerFactory entityManagerFactory;
    private JpaUserRepository userRepository;

    @BeforeAll
    static void setUpClass() {
        entityManagerFactory = Persistence.createEntityManagerFactory("bookstore");
    }

    @AfterAll
    static void tearDownClass() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }

    @BeforeEach
    void setUp() {
        userRepository = new JpaUserRepository(entityManagerFactory);
    }

    @Test
    void insertUserTest() {
        var user = new cat.teknos.bookstore.domain.jpa.models.User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe1234121212@example.com");
        user.setPasswordHash("password123");
        user.setAddress("123 Main St");
        user.setCity("Somewhere");
        user.setCountry("Country");
        user.setPostalCode("12345");
        user.setJoinDate(LocalDate.now());

        userRepository.save(user);

        User foundUser = userRepository.get(user.getId());
        assertNotNull(foundUser);
        assertEquals("John", foundUser.getFirstName());
    }

    @Test
    void updateUserTest() {
        var user = new cat.teknos.bookstore.domain.jpa.models.User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe121212@example.com");
        user.setPasswordHash("password123");
        user.setAddress("123 Main St");
        user.setCity("Somewhere");
        user.setCountry("Country");
        user.setPostalCode("12345");
        user.setJoinDate(LocalDate.now());

        userRepository.save(user);

        user.setCity("New City");
        userRepository.save(user);

        User updatedUser = userRepository.get(user.getId());
        assertEquals("New City", updatedUser.getCity());
    }

    @Test
    void deleteUserTest() {
        var user = new cat.teknos.bookstore.domain.jpa.models.User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe234234@example.com");
        user.setPasswordHash("password123");
        user.setAddress("123 Main St");
        user.setCity("Somewhere");
        user.setCountry("Country");
        user.setPostalCode("12345");
        user.setJoinDate(LocalDate.now());

        userRepository.save(user);
        userRepository.delete(user);

        User deletedUser = userRepository.get(user.getId());
        assertNull(deletedUser);
    }

    @Test
    void getUserTest() {
        var user = new cat.teknos.bookstore.domain.jpa.models.User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.do234234234e@example.com");
        user.setPasswordHash("password123");
        user.setAddress("123 Main St");
        user.setCity("Somewhere");
        user.setCountry("Country");
        user.setPostalCode("12345");
        user.setJoinDate(LocalDate.now());

        userRepository.save(user);

        User foundUser = userRepository.get(user.getId());
        assertNotNull(foundUser);
        assertEquals("John", foundUser.getFirstName());
    }

    @Test
    void getAllUsersTest() {
        var user1 = new cat.teknos.bookstore.domain.jpa.models.User();
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setEmail("john.do2342424234e@example.com");
        user1.setPasswordHash("password123");
        user1.setAddress("123 Main St");
        user1.setCity("Somewhere");
        user1.setCountry("Country");
        user1.setPostalCode("12345");
        user1.setJoinDate(LocalDate.now());

        var user2 = new cat.teknos.bookstore.domain.jpa.models.User();
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setEmail("jane.377745@example.com");
        user2.setPasswordHash("password1234");
        user2.setAddress("456 Elm St");
        user2.setCity("Elsewhere");
        user2.setCountry("Country");
        user2.setPostalCode("67890");
        user2.setJoinDate(LocalDate.now());

        userRepository.save(user1);
        userRepository.save(user2);

        Set<User> users = userRepository.getAll();
        assertFalse(users.isEmpty());
        assertNotNull(users.size());
    }

    @Test
    void getUserByNameTest() {
        var user = new cat.teknos.bookstore.domain.jpa.models.User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.do54645666456e@example.com");
        user.setPasswordHash("password123");
        user.setAddress("123 Main St");
        user.setCity("Somewhere");
        user.setCountry("Country");
        user.setPostalCode("12345");
        user.setJoinDate(LocalDate.now());

        userRepository.save(user);

        User foundUser = userRepository.getByName("John");
        assertNotNull(foundUser);
        assertEquals("John", foundUser.getFirstName());
    }
}
