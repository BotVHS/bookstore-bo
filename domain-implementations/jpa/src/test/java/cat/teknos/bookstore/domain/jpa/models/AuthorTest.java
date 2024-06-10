package cat.teknos.bookstore.domain.jpa.models;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AuthorTest {
    private static EntityManagerFactory entityManagerFactory;

    @BeforeAll
    static void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("bookstore");
    }

    @AfterAll
    static void tearDown() {
        entityManagerFactory.close();
    }

    @Test
    void insertAuthorTest() {
        var entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            var author = new Author();
            author.setFirstName("John");
            author.setLastName("Doe");
            entityManager.persist(author);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }
}