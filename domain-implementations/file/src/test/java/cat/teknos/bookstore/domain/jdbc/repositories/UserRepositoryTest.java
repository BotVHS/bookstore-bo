package cat.teknos.bookstore.domain.jdbc.repositories;

import cat.teknos.bookstore.domain.jdbc.models.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {
    @Test
    void saveUser() {
        var repository = new UserRepository();

        var user = new User();
        user.setFirstName("Albert");
        user.setLastName("Diaz");
        user.setEmail("example@example.com");
        user.setPasswordHash("password123");
        user.setAddress("Joan flocs 12342");
        user.setCity("Vic");
        user.setCountry("Catalunya");
        user.setPostalCode("12345");
        user.setJoinDate(LocalDate.now());
        repository.save(user);

        assertTrue(user.getId() > 0);
        assertNotNull(repository.get(user.getId()));
    }

    @Test
    void updateUser() {
        var repository = new UserRepository();

        var user = new User();
        user.setFirstName("Albert");
        user.setLastName("Diaz");
        user.setEmail("example@example.com");
        user.setPasswordHash("password123");
        user.setAddress("Joan flocs 12342");
        user.setCity("Vic");
        user.setCountry("Catalunya");
        user.setPostalCode("12345");
        user.setJoinDate(LocalDate.now());
        repository.save(user);

        user.setPostalCode("ABCDE");

        repository.save(user);

        assertEquals("ABCDE", repository.get(user.getId()).getPostalCode());
    }

    @Test
    void deleteUser() {
        var repository = new UserRepository();

        var user = new User();
        user.setFirstName("Albert");
        user.setLastName("Diaz");
        user.setEmail("example@example.com");
        user.setPasswordHash("password123");
        user.setAddress("Joan flocs 12342");
        user.setCity("Vic");
        user.setCountry("Catalunya");
        user.setPostalCode("12345");
        user.setJoinDate(LocalDate.now());
        repository.save(user);

        repository.delete(user);

        assertNull(repository.get(user.getId()));
    }

    @Test
    void getUser() {
        var repository = new UserRepository();

        var user = new User();
        user.setFirstName("Albert");
        user.setLastName("Diaz");
        user.setEmail("example@example.com");
        user.setPasswordHash("password123");
        user.setAddress("Joan flocs 12342");
        user.setCity("Vic");
        user.setCountry("Catalunya");
        user.setPostalCode("12345");
        user.setJoinDate(LocalDate.now());
        repository.save(user);

        var retrievedUser = repository.get(user.getId());

        assertEquals(user.getId(), retrievedUser.getId());
        assertEquals(user.getFirstName(), retrievedUser.getFirstName());
        assertEquals(user.getLastName(), retrievedUser.getLastName());
        assertEquals(user.getEmail(), retrievedUser.getEmail());
        assertEquals(user.getPasswordHash(), retrievedUser.getPasswordHash());
        assertEquals(user.getAddress(), retrievedUser.getAddress());
        assertEquals(user.getCity(), retrievedUser.getCity());
        assertEquals(user.getCountry(), retrievedUser.getCountry());
        assertEquals(user.getPostalCode(), retrievedUser.getPostalCode());
        assertEquals(user.getJoinDate(), retrievedUser.getJoinDate());
    }

    @Test
    void getAllUsers() {
        var repository = new UserRepository();

        var user = new User();
        user.setFirstName("Albert");
        user.setLastName("Diaz");
        user.setEmail("example@example.com");
        user.setPasswordHash("password123");
        user.setAddress("Joan flocs 12342");
        user.setCity("Vic");
        user.setCountry("Catalunya");
        user.setPostalCode("12345");
        user.setJoinDate(LocalDate.now());
        repository.save(user);

        var user2 = new User();
        user2.setFirstName("Joan");
        user2.setLastName("Sampere");
        user2.setEmail("joansampere@example.com");
        user2.setPasswordHash("password123");
        user2.setAddress("Carrer dels homs");
        user2.setCity("Barcelona");
        user2.setCountry("Catalunya");
        user2.setPostalCode("24680");
        user2.setJoinDate(LocalDate.now());

        repository.save(user);
        repository.save(user2);

        Set<com.albertdiaz.bookstore.models.User> allUsers = repository.getAll();

        assertEquals(2, allUsers.size());
        assertTrue(allUsers.contains(user));
        assertTrue(allUsers.contains(user2));
    }

    @Test
    void getUserByName() {
        var repository = new UserRepository();

        var user = new User();
        user.setFirstName("Albert");
        user.setLastName("Diaz");
        user.setEmail("example@example.com");
        user.setPasswordHash("password123");
        user.setAddress("Joan flocs 12342");
        user.setCity("Vic");
        user.setCountry("Catalunya");
        user.setPostalCode("12345");
        user.setJoinDate(LocalDate.now());
        repository.save(user);

        var retrievedUser = repository.getByName("Albert Diaz");

        assertNotNull(retrievedUser);
        assertEquals(user.getId(), retrievedUser.getId());
        assertEquals(user.getFirstName(), retrievedUser.getFirstName());
        assertEquals(user.getLastName(), retrievedUser.getLastName());
        assertEquals(user.getEmail(), retrievedUser.getEmail());
        assertEquals(user.getPasswordHash(), retrievedUser.getPasswordHash());
        assertEquals(user.getAddress(), retrievedUser.getAddress());
        assertEquals(user.getCity(), retrievedUser.getCity());
        assertEquals(user.getCountry(), retrievedUser.getCountry());
        assertEquals(user.getPostalCode(), retrievedUser.getPostalCode());
        assertEquals(user.getJoinDate(), retrievedUser.getJoinDate());
    }

}