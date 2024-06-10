package cat.teknos.bookstore.domain.jdbc.repositories;

import cat.teknos.bookstore.domain.jdbc.models.Review;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class ReviewRepositoryTest {
    @Test
    void saveNewReview() {
        var repository = new ReviewRepository();

        var review = new Review();
        review.setRating(5);
        review.setComment("Excellent book!");
        review.setReviewDate(LocalDate.now());

        repository.save(review);

        assertTrue(review.getId() > 0);
        assertNotNull(repository.get(review.getId()));

        ReviewRepository.load();

        assertNotNull(repository.get(review.getId()));
        assertEquals("Excellent book!", repository.get(review.getId()).getComment());
    }
    @Test
    void updateReview() {
        var repository = new ReviewRepository();

        var review = new Review();
        review.setRating(5);
        review.setComment("Excellent book!");
        review.setReviewDate(LocalDate.now());
        repository.save(review);

        review.setRating(5);
        review.setComment("Bad Book");

        repository.save(review);

        var updatedReview = repository.get(review.getId());

        assertEquals(5, updatedReview.getRating());
        assertEquals("Bad Book", updatedReview.getComment());
    }
    @Test
    void deleteReview() {
        var repository = new ReviewRepository();

        var review = new Review();
        review.setRating(5);
        review.setComment("Excellent book!");
        review.setReviewDate(LocalDate.now());

        repository.save(review);

        repository.delete(review);

        assertNull(repository.get(review.getId()));
    }

    @Test
    void getReview() {
        var repository = new ReviewRepository();

        var review = new Review();
        review.setRating(5);
        review.setComment("Excellent book!");
        review.setReviewDate(LocalDate.now());

        repository.save(review);

        var retrievedReview = repository.get(review.getId());

        assertEquals(review.getId(), retrievedReview.getId());
        assertEquals(review.getRating(), retrievedReview.getRating());
        assertEquals(review.getComment(), retrievedReview.getComment());
        assertEquals(review.getReviewDate(), retrievedReview.getReviewDate());
    }

    @Test
    void getAllReviews() {
        var repository = new ReviewRepository();

        var review = new Review();
        review.setRating(5);
        review.setComment("Excellent book!");
        review.setReviewDate(LocalDate.now());

        var review2 = new Review();
        review2.setRating(5);
        review2.setComment("A masterpiece");
        review2.setReviewDate(LocalDate.of(2024, 4, 15));

        repository.save(review);
        repository.save(review2);

        var allReviews = repository.getAll();

        assertEquals(2, allReviews.size());
        assertTrue(allReviews.contains(review));
        assertTrue(allReviews.contains(review2));
    }
}