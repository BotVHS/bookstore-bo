package cat.teknos.bookstore.domain.jpa.models;

import cat.teknos.bookstore.domain.jpa.repositories.JpaReviewRepository;
import com.albertdiaz.bookstore.models.Review;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class JpaReviewRepositoryTest {
    private static EntityManagerFactory entityManagerFactory;
    private JpaReviewRepository reviewRepository;

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
        reviewRepository = new JpaReviewRepository(entityManagerFactory);
    }

    @Test
    void insertReviewTest() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        var review = new cat.teknos.bookstore.domain.jpa.models.Review();
        review.setRating(5);
        review.setComment("Great book!");
        review.setReviewDate(LocalDate.now());

        var book = new cat.teknos.bookstore.domain.jpa.models.Book();
        book.setTitle("Java");
        var author = new cat.teknos.bookstore.domain.jpa.models.Author();
        author.setFirstName("John");
        book.setAuthor(author);

        var user = new cat.teknos.bookstore.domain.jpa.models.User();
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setEmail("jane.doasdasffde@example.com");
        user.setPasswordHash("password123");
        user.setJoinDate(LocalDate.now());

        entityManager.persist(author);
        entityManager.persist(book);
        entityManager.persist(user);
        entityManager.getTransaction().commit();
    }

    @Test
    void updateReviewTest() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        var review = new cat.teknos.bookstore.domain.jpa.models.Review();
        review.setRating(5);
        review.setComment("Great book!");
        review.setReviewDate(LocalDate.now());

        var book = new cat.teknos.bookstore.domain.jpa.models.Book();
        book.setTitle("Java");
        var author = new cat.teknos.bookstore.domain.jpa.models.Author();
        author.setFirstName("John");
        book.setAuthor(author);

        var user = new cat.teknos.bookstore.domain.jpa.models.User();
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setEmail("jane.doeasdffpfad@example.com");
        user.setPasswordHash("password123");
        user.setJoinDate(LocalDate.now());


        entityManager.persist(author);
        entityManager.persist(book);
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();

        review.setRating(4);
        review.setComment("Good book!");
        entityManager.merge(review);
        entityManager.getTransaction().commit();

        Review updatedReview = entityManager.find(cat.teknos.bookstore.domain.jpa.models.Review.class, review.getId());
        assertEquals(4, updatedReview.getRating());
    }

    @Test
    void deleteReviewTest() {
        var review = new cat.teknos.bookstore.domain.jpa.models.Review();
        review.setRating(5);
        review.setComment("Great book!");
        review.setReviewDate(LocalDate.now());

        var book = new cat.teknos.bookstore.domain.jpa.models.Book();
        book.setTitle("Java");
        var author = new cat.teknos.bookstore.domain.jpa.models.Author();
        author.setFirstName("John");
        book.setAuthor(author);

        var user = new cat.teknos.bookstore.domain.jpa.models.User();
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setEmail("jane.dogfdfgdfge@example.com");
        user.setPasswordHash("password123");
        user.setJoinDate(LocalDate.now());

        // Save book and user first to set them in the review
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(author);
        entityManager.persist(book);
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        entityManager.close();

        review.setBook(book);
        review.setUser(user);
        reviewRepository.save(review);

        reviewRepository.delete(review);

        Review deletedReview = reviewRepository.get(review.getId());
        assertNull(deletedReview);
    }

    @Test
    void getReviewTest() {
        var review = new cat.teknos.bookstore.domain.jpa.models.Review();
        review.setRating(5);
        review.setComment("Great book!");
        review.setReviewDate(LocalDate.now());

        var book = new cat.teknos.bookstore.domain.jpa.models.Book();
        book.setTitle("Java");
        var author = new cat.teknos.bookstore.domain.jpa.models.Author();
        author.setFirstName("John");
        book.setAuthor(author);

        var user = new cat.teknos.bookstore.domain.jpa.models.User();
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setEmail("jane.dodhgfhhfghe@example.com");
        user.setPasswordHash("password123");
        user.setJoinDate(LocalDate.now());

        // Save book and user first to set them in the review
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(author);
        entityManager.persist(book);
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        entityManager.close();

        review.setBook(book);
        review.setUser(user);
        reviewRepository.save(review);

        Review foundReview = reviewRepository.get(review.getId());
        assertNotNull(foundReview);
        assertEquals("Great book!", foundReview.getComment());
    }

    @Test
    void getAllReviewsTest() {
        var review1 = new cat.teknos.bookstore.domain.jpa.models.Review();
        review1.setRating(5);
        review1.setComment("Great book!");
        review1.setReviewDate(LocalDate.now());

        var review2 = new cat.teknos.bookstore.domain.jpa.models.Review();
        review2.setRating(4);
        review2.setComment("Good book!");
        review2.setReviewDate(LocalDate.now());

        var book = new cat.teknos.bookstore.domain.jpa.models.Book();
        book.setTitle("Java");
        var author = new cat.teknos.bookstore.domain.jpa.models.Author();
        author.setFirstName("John");
        book.setAuthor(author);

        var user = new cat.teknos.bookstore.domain.jpa.models.User();
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setEmail("janfghfhe.doe@example.com");
        user.setPasswordHash("password123");
        user.setJoinDate(LocalDate.now());

        // Save book and user first to set them in the reviews
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(author);
        entityManager.persist(book);
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        entityManager.close();

        review1.setBook(book);
        review1.setUser(user);
        review2.setBook(book);
        review2.setUser(user);
        reviewRepository.save(review1);
        reviewRepository.save(review2);

        Set<Review> reviews = reviewRepository.getAll();
        assertFalse(reviews.isEmpty());
        assertTrue(reviews.size() >= 2);
    }

    @Test
    void getReviewsByBookIdTest() {
        var review = new cat.teknos.bookstore.domain.jpa.models.Review();
        review.setRating(5);
        review.setComment("Great book!");
        review.setReviewDate(LocalDate.now());

        var book = new cat.teknos.bookstore.domain.jpa.models.Book();
        book.setTitle("Java");
        var author = new cat.teknos.bookstore.domain.jpa.models.Author();
        author.setFirstName("John");
        book.setAuthor(author);

        var user = new cat.teknos.bookstore.domain.jpa.models.User();
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setEmail("jane.dkjhjjhoe@example.com");
        user.setPasswordHash("password123");
        user.setJoinDate(LocalDate.now());

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(author);
        entityManager.persist(book);
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        entityManager.close();

        review.setBook(book);
        review.setUser(user);
        reviewRepository.save(review);

        Set<Review> reviews = reviewRepository.getByBookId(book.getId());
        assertFalse(reviews.isEmpty());
    }

    @Test
    void getReviewsByUserIdTest() {
        var review = new cat.teknos.bookstore.domain.jpa.models.Review();
        review.setRating(5);
        review.setComment("Great book!");
        review.setReviewDate(LocalDate.now());

        var book = new cat.teknos.bookstore.domain.jpa.models.Book();
        book.setTitle("Java");
        var author = new cat.teknos.bookstore.domain.jpa.models.Author();
        author.setFirstName("John");
        book.setAuthor(author);

        var user = new cat.teknos.bookstore.domain.jpa.models.User();
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setEmail("jane.dasasasasoe@example.com");
        user.setPasswordHash("password123");
        user.setJoinDate(LocalDate.now());

        // Save book and user first to set them in the review
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(author);
        entityManager.persist(book);
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        entityManager.close();

        review.setBook(book);
        review.setUser(user);
        reviewRepository.save(review);

        Set<Review> reviews = reviewRepository.getByUserId(user.getId());
        assertFalse(reviews.isEmpty());
    }
}
