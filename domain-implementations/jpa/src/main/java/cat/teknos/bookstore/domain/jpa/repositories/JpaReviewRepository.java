package cat.teknos.bookstore.domain.jpa.repositories;

import cat.teknos.bookstore.domain.jpa.models.Review;
import com.albertdiaz.bookstore.repositories.ReviewRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JpaReviewRepository implements ReviewRepository {
    private final EntityManagerFactory entityManagerFactory;

    public JpaReviewRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public com.albertdiaz.bookstore.models.Review get(Integer id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.find(cat.teknos.bookstore.domain.jpa.models.Review.class, id);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void save(com.albertdiaz.bookstore.models.Review review) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            if (review.getId() == 0) {
                entityManager.persist(review);
            } else {
                entityManager.merge(review);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void delete(com.albertdiaz.bookstore.models.Review review) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            cat.teknos.bookstore.domain.jpa.models.Review managedReview =
                    entityManager.find(cat.teknos.bookstore.domain.jpa.models.Review.class, review.getId());
            if (managedReview != null) {
                entityManager.remove(managedReview);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Set<com.albertdiaz.bookstore.models.Review> getAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            List<cat.teknos.bookstore.domain.jpa.models.Review> reviews =
                    entityManager.createQuery(
                            "SELECT r FROM Review r LEFT JOIN FETCH r.book LEFT JOIN FETCH r.user",
                            cat.teknos.bookstore.domain.jpa.models.Review.class
                    ).getResultList();
            return new HashSet<>(reviews);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Set<com.albertdiaz.bookstore.models.Review> getByBookId(Integer bookId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            List<cat.teknos.bookstore.domain.jpa.models.Review> reviews =
                    entityManager.createQuery(
                                    "SELECT r FROM Review r LEFT JOIN FETCH r.user WHERE r.book.id = :bookId",
                                    cat.teknos.bookstore.domain.jpa.models.Review.class
                            )
                            .setParameter("bookId", bookId)
                            .getResultList();
            return new HashSet<>(reviews);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Set<com.albertdiaz.bookstore.models.Review> getByUserId(Integer userId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            List<cat.teknos.bookstore.domain.jpa.models.Review> reviews =
                    entityManager.createQuery(
                                    "SELECT r FROM Review r LEFT JOIN FETCH r.book WHERE r.user.id = :userId",
                                    cat.teknos.bookstore.domain.jpa.models.Review.class
                            )
                            .setParameter("userId", userId)
                            .getResultList();
            return new HashSet<>(reviews);
        } finally {
            entityManager.close();
        }
    }
}