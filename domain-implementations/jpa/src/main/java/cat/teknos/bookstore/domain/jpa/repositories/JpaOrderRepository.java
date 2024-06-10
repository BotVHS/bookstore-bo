package cat.teknos.bookstore.domain.jpa.repositories;

import com.albertdiaz.bookstore.models.Order;
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
    public void save(Order order) {
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
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void delete(Order order) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            entityManager.remove(order);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Order get(Integer id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.find(Order.class, id);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Set<Order> getAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            List<Order> orders = entityManager.createQuery("SELECT o FROM Order o", Order.class).getResultList();
            return new HashSet<>(orders);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Order getByName(String name) {
        return null;
    }
}
