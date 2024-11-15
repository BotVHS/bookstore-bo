package cat.teknos.bookstore.domain.jpa.repositories;

import cat.teknos.bookstore.domain.jpa.models.Order;
import com.albertdiaz.bookstore.repositories.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JpaOrderRepository implements OrderRepository {
    private final EntityManagerFactory entityManagerFactory;

    public JpaOrderRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void save(com.albertdiaz.bookstore.models.Order order) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            if (order.getId() == 0) {
                entityManager.persist(order);
            } else {
                entityManager.merge(order);
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
    public void delete(com.albertdiaz.bookstore.models.Order order) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            Order managedOrder = entityManager.find(Order.class, order.getId());
            if (managedOrder != null) {
                entityManager.remove(managedOrder);
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
    public com.albertdiaz.bookstore.models.Order get(Integer id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.find(cat.teknos.bookstore.domain.jpa.models.Order.class, id);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Set<com.albertdiaz.bookstore.models.Order> getAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            List<Order> orders = entityManager.createQuery(
                    "SELECT o FROM Order o LEFT JOIN FETCH o.user",
                    cat.teknos.bookstore.domain.jpa.models.Order.class
            ).getResultList();
            return new HashSet<>(orders);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public com.albertdiaz.bookstore.models.Order getByName(String name) {
        return null; // No es necesario para Orders
    }
}