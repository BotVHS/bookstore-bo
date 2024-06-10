package cat.teknos.bookstore.domain.jpa.repositories;

import com.albertdiaz.bookstore.models.Author;
import com.albertdiaz.bookstore.repositories.AuthorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JpaAuthorRepository implements AuthorRepository {
    private final EntityManagerFactory entityManagerFactory;

    public JpaAuthorRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }


    @Override
    public Author getByName(String name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            List<Author> authors = entityManager.createQuery("SELECT a FROM Author a WHERE a.firstName LIKE CONCAT('%', :name, '%') OR a.lastName LIKE CONCAT('%', :name, '%')", Author.class)
                    .setParameter("name", name)
                    .getResultList();
            if (authors.isEmpty()) {
                return null;
            } else {
                return authors.get(0);
            }
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void save(Author model) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        if (model.getId() == 0) {
            entityManager.persist(model);
        } else {
            entityManager.merge(model);
        }
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void delete(Author model) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        cat.teknos.bookstore.domain.jpa.models.Author managedAuthor = entityManager.find(cat.teknos.bookstore.domain.jpa.models.Author.class, model.getId());
        if (managedAuthor != null) {
            entityManager.remove(managedAuthor);
        }
        entityManager.getTransaction().commit();
        entityManager.close();
    }
    @Override
    public Author get(Integer id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.find(cat.teknos.bookstore.domain.jpa.models.Author.class, id);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Set<Author> getAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return new HashSet<>(entityManager.createQuery("SELECT a FROM Author a", Author.class).getResultList());
        } finally {
            entityManager.close();
        }
    }
}
