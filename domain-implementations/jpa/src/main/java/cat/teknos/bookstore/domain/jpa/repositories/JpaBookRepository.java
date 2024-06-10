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
            List<Book> books = entityManager.createQuery("SELECT b FROM Book b WHERE b.title LIKE CONCAT('%', :title, '%')", Book.class)
                    .setParameter("title", title)
                    .getResultList();
            if (books.isEmpty()) {
                return null;
            } else {
                return books.get(0);
            }
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
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void delete(Book model) {

    }

    @Override
    public Book get(Integer id) {
        return null;
    }

    @Override
    public Set<Book> getAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return new HashSet<>(entityManager.createQuery("SELECT b FROM Book b", Book.class).getResultList());
        } finally {
            entityManager.close();
        }
    }
}
