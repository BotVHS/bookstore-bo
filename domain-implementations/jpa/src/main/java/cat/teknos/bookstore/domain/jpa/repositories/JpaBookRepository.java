package cat.teknos.bookstore.domain.jpa.repositories;

import com.albertdiaz.bookstore.models.Book;
import com.albertdiaz.bookstore.repositories.BookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JpaBookRepository implements BookRepository {
    private final EntityManagerFactory entityManagerFactory;

    public JpaBookRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Book getByName(String title) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery(
                            "SELECT b FROM Book b LEFT JOIN FETCH b.author WHERE b.title LIKE CONCAT('%', :title, '%')",
                            Book.class)
                    .setParameter("title", title)
                    .getResultList()
                    .stream()
                    .findFirst()
                    .orElse(null);
        } finally {
            entityManager.close();
        }
    }


    @Override
    public void save(Book model) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            if (model.getId() == 0) {
                entityManager.persist(model);
            } else {
                entityManager.merge(model);
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
    public void delete(Book model) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            Book managedBook = entityManager.find(Book.class, model.getId());
            if (managedBook != null) {
                entityManager.remove(managedBook);
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
    public Book get(Integer id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.createQuery(
                            "SELECT b FROM Book b LEFT JOIN FETCH b.author WHERE b.id = :id",
                            Book.class)
                    .setParameter("id", id)
                    .getResultList()
                    .stream()
                    .findFirst()
                    .orElse(null);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Set<Book> getAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return new HashSet<>(
                    entityManager.createQuery(
                                    "SELECT DISTINCT b FROM Book b LEFT JOIN FETCH b.author",
                                    Book.class)
                            .getResultList()
            );
        } finally {
            entityManager.close();
        }
    }
}
