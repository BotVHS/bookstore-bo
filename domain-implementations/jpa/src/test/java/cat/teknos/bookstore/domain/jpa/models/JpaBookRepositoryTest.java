package cat.teknos.bookstore.domain.jpa.models;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JpaBookRepositoryTest {
    private static EntityManagerFactory entityManagerFactory;

    @BeforeAll
    static void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("bookstore");
    }

    @AfterAll
    static void tearDown() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }

    @Test
    void insertBookTest() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            var book = new Book();
            book.setTitle("Java");
            var author = new Author();
            author.setFirstName("John");
            book.setAuthor(author);

            entityManager.persist(author);
            entityManager.persist(book);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    @Test
    void updateBookTest() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            var book = new Book();
            book.setTitle("Java");
            var author = new Author();
            author.setFirstName("John");
            book.setAuthor(author);

            entityManager.persist(author);
            entityManager.persist(book);
            entityManager.getTransaction().commit();

            entityManager.getTransaction().begin();
            book.setTitle("Java Updated");
            entityManager.merge(book);
            entityManager.getTransaction().commit();

            Book updatedBook = entityManager.find(Book.class, book.getId());
            assertEquals("Java Updated", updatedBook.getTitle());
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    @Test
    void deleteBookTest() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            var book = new Book();
            book.setTitle("Java");
            var author = new Author();
            author.setFirstName("John");
            book.setAuthor(author);

            entityManager.persist(author);
            entityManager.persist(book);
            entityManager.getTransaction().commit();

            entityManager.getTransaction().begin();
            entityManager.remove(book);
            entityManager.getTransaction().commit();

            Book deletedBook = entityManager.find(Book.class, book.getId());
            assertNull(deletedBook);
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    @Test
    void getBookTest() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            var book = new Book();
            book.setTitle("Java");
            var author = new Author();
            author.setFirstName("John");
            book.setAuthor(author);

            entityManager.getTransaction().begin();
            entityManager.persist(author);
            entityManager.persist(book);
            entityManager.getTransaction().commit();

            Book foundBook = entityManager.find(Book.class, book.getId());
            assertNotNull(foundBook);
            assertEquals("Java", foundBook.getTitle());
        } finally {
            entityManager.close();
        }
    }

    @Test
    void getAllBooksTest() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();

            var author = new Author();
            author.setFirstName("John");
            entityManager.persist(author);
            var book = new Book();
            book.setTitle("Java");
            book.setAuthor(author);
            entityManager.persist(book);
            entityManager.getTransaction().commit();
            List<Book> books = entityManager.createQuery("SELECT b FROM Book b", Book.class).getResultList();
            assertFalse(books.isEmpty());
        } finally {
            entityManager.close();
        }
    }

    @Test
    void getBookByTitleTest() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            var book = new Book();
            book.setTitle("Java");
            var author = new Author();
            author.setFirstName("John");
            book.setAuthor(author);

            entityManager.getTransaction().begin();
            entityManager.persist(author);
            entityManager.persist(book);
            entityManager.getTransaction().commit();

            List<Book> foundBooks = entityManager.createQuery(
                            "SELECT b FROM Book b WHERE b.title LIKE CONCAT('%', :title, '%')", Book.class)
                    .setParameter("title", "Java")
                    .getResultList();
            assertFalse(foundBooks.isEmpty());
        } finally {
            entityManager.close();
        }
    }
}
