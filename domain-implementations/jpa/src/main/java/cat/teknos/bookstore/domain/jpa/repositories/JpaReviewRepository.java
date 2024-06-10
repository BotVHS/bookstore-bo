package cat.teknos.bookstore.domain.jpa.repositories;

import com.albertdiaz.bookstore.models.Review;
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
    public Review get(Integer id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.find(Review.class, id);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void save(Review review) {
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
    public void delete(Review review) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            Review managedReview = entityManager.find(Review.class, review.getId());
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
    public Set<Review> getAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            List<Review> reviews = entityManager.createQuery("SELECT r FROM Review r", Review.class).getResultList();
            return new HashSet<>(reviews);
        } finally {
            entityManager.close();
        }
    }



    @Override
    public Set<Review> getByBookId(Integer bookId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            List<Review> reviews = entityManager.createQuery("SELECT r FROM Review r WHERE r.book.id = :bookId", Review.class)
                    .setParameter("bookId", bookId)
                    .getResultList();
            return new HashSet<>(reviews);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Set<Review> getByUserId(Integer userId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            List<Review> reviews = entityManager.createQuery("SELECT r FROM Review r WHERE r.user.id = :userId", Review.class)
                    .setParameter("userId", userId)
                    .getResultList();
            return new HashSet<>(reviews);
        } finally {
            entityManager.close();
        }
    }
}
