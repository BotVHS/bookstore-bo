package cat.teknos.bookstore.domain.jpa.models;

import com.albertdiaz.bookstore.models.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JpaOrderRepositoryTest {
    private static EntityManagerFactory entityManagerFactory;

    @BeforeAll
    static void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("bookstore");
    }

    @AfterAll
    static void tearDown() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }

    @Test
    void insertOrderTest() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            var order = new cat.teknos.bookstore.domain.jpa.models.Order();
            order.setOrderDate(LocalDate.now());
            order.setTotalPrice(100.0f);
            order.setShippingAddress("123 Main St");
            order.setOrderStatus("Pending");

            entityManager.persist(order);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    @Test
    void updateOrderTest() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            var order = new cat.teknos.bookstore.domain.jpa.models.Order();
            order.setOrderDate(LocalDate.now());
            order.setTotalPrice(100.0f);
            order.setShippingAddress("123 Main St");
            order.setOrderStatus("Pending");

            entityManager.persist(order);
            entityManager.getTransaction().commit();

            entityManager.getTransaction().begin();
            order.setOrderStatus("Shipped");
            entityManager.merge(order);
            entityManager.getTransaction().commit();

            Order updatedOrder = entityManager.find(Order.class, order.getId());
            assertEquals("Shipped", updatedOrder.getOrderStatus());
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    @Test
    void deleteOrderTest() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            var order = new cat.teknos.bookstore.domain.jpa.models.Order();
            order.setOrderDate(LocalDate.now());
            order.setTotalPrice(100.0f);
            order.setShippingAddress("123 Main St");
            order.setOrderStatus("Pending");

            entityManager.persist(order);
            entityManager.getTransaction().commit();

            entityManager.getTransaction().begin();
            entityManager.remove(order);
            entityManager.getTransaction().commit();

            Order deletedOrder = entityManager.find(Order.class, order.getId());
            assertNull(deletedOrder);
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    @Test
    void getOrderTest() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            var order1 = new cat.teknos.bookstore.domain.jpa.models.Order();
            order1.setOrderDate(LocalDate.now());
            order1.setTotalPrice(100.0f);
            order1.setShippingAddress("123 Main St");
            order1.setOrderStatus("Pending");

            entityManager.getTransaction().begin();
            entityManager.persist(order1);
            entityManager.getTransaction().commit();

            Order foundOrder = entityManager.find(cat.teknos.bookstore.domain.jpa.models.Order.class, order1.getId());
            assertNotNull(foundOrder);
        } finally {
            entityManager.close();
        }
    }

    @Test
    void getAllOrdersTest() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            Order order1 = new cat.teknos.bookstore.domain.jpa.models.Order();
            order1.setOrderDate(LocalDate.now());
            order1.setTotalPrice(100.0f);
            order1.setShippingAddress("123 Main St");
            order1.setOrderStatus("Pending");

            Order order2 = new cat.teknos.bookstore.domain.jpa.models.Order();
            order2.setOrderDate(LocalDate.now());
            order2.setTotalPrice(150.0f);
            order2.setShippingAddress("456 Oak St");
            order2.setOrderStatus("Shipped");

            entityManager.persist(order1);
            entityManager.persist(order2);
            entityManager.getTransaction().commit();

            List<cat.teknos.bookstore.domain.jpa.models.Order> allOrders = entityManager.createQuery("SELECT o FROM Order o", cat.teknos.bookstore.domain.jpa.models.Order.class).getResultList();
            assertFalse(allOrders.isEmpty());
            assertNotNull(allOrders.size());
        } finally {
            entityManager.close();
        }
    }

}
