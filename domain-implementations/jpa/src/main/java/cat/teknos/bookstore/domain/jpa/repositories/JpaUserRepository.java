package cat.teknos.bookstore.domain.jpa.repositories;

import com.albertdiaz.bookstore.models.User;
import com.albertdiaz.bookstore.repositories.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JpaUserRepository implements UserRepository {
    private final EntityManagerFactory entityManagerFactory;

    public JpaUserRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public User getByName(String name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            List<User> users = entityManager.createQuery(
                            "SELECT u FROM User u WHERE u.firstName LIKE CONCAT('%', :name, '%') OR u.lastName LIKE CONCAT('%', :name, '%')",
                            User.class)
                    .setParameter("name", name)
                    .getResultList();
            if (users.isEmpty()) {
                return null;
            } else {
                return users.get(0);
            }
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void save(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        if (user.getId() == 0) {
            entityManager.persist(user);
        } else {
            entityManager.merge(user);
        }
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void delete(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.contains(user) ? user : entityManager.merge(user));
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public User get(Integer id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.find(cat.teknos.bookstore.domain.jpa.models.User.class, id);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Set<User> getAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return new HashSet<>(entityManager.createQuery("SELECT u FROM User u", User.class).getResultList());
        } finally {
            entityManager.close();
        }
    }
}
