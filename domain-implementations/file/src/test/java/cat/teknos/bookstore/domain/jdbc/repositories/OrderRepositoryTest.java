package cat.teknos.bookstore.domain.jdbc.repositories;

import cat.teknos.bookstore.domain.jdbc.models.Order;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OrderRepositoryTest {
    @Test
    void saveOrder() {
        var repository = new OrderRepository();

        var order = new Order();
        order.setUser(null);
        order.setOrderDate(LocalDate.now());
        order.setTotalPrice(100.00F);
        order.setShippingAddress("Carrer les espigues, vic");
        order.setOrderStatus("Pending");

        repository.save(order);

        assertTrue(order.getId() > 0);
        assertNotNull(repository.get(order.getId()));
    }

    @Test
    void updateOrder() {
        var repository = new OrderRepository();

        var order = new Order();
        order.setUser(null);
        order.setOrderDate(LocalDate.now());
        order.setTotalPrice(100.00F);
        order.setShippingAddress("Carrer les espigues, vic");
        order.setOrderStatus("Pending");
        repository.save(order);

        order.setTotalPrice(75.00F);

        repository.save(order);

        assertEquals(75.00F, repository.get(order.getId()).getTotalPrice());
    }

    @Test
    void deleteOrder() {
        var repository = new OrderRepository();

        var order = new Order();
        order.setUser(null);
        order.setOrderDate(LocalDate.now());
        order.setTotalPrice(100.00F);
        order.setShippingAddress("Carrer les espigues, vic");
        order.setOrderStatus("Pending");
        repository.save(order);

        repository.delete(order);

        assertNull(repository.get(order.getId()));
    }
    @Test
    void getOrder() {
        var repository = new OrderRepository();

        var order = new Order();
        order.setUser(null);
        order.setOrderDate(LocalDate.now());
        order.setTotalPrice(100.00F);
        order.setShippingAddress("Carrer les espigues, vic");
        order.setOrderStatus("Pending");
        repository.save(order);

        var retrievedOrder = repository.get(order.getId());

        assertEquals(order.getId(), retrievedOrder.getId());
        assertEquals(order.getUser(), retrievedOrder.getUser());
        assertEquals(order.getOrderDate(), retrievedOrder.getOrderDate());
        assertEquals(order.getTotalPrice(), retrievedOrder.getTotalPrice());
        assertEquals(order.getShippingAddress(), retrievedOrder.getShippingAddress());
        assertEquals(order.getOrderStatus(), retrievedOrder.getOrderStatus());
    }

    @Test
    void getAllOrders() {
        var repository = new OrderRepository();

        var order = new Order();
        order.setUser(null);
        order.setOrderDate(LocalDate.now());
        order.setTotalPrice(100.00F);
        order.setShippingAddress("Carrer les espigues, vic");
        order.setOrderStatus("Pending");

        var order2 = new Order();
        order2.setUser(null);
        order2.setOrderDate(LocalDate.now());
        order2.setTotalPrice(150.00F);
        order2.setShippingAddress("Carrer blau, Barcelona");
        order2.setOrderStatus("Shipped");

        repository.save(order);
        repository.save(order2);

        Set<com.albertdiaz.bookstore.models.Order> allOrders = repository.getAll();

        assertEquals(2, allOrders.size());
        assertTrue(allOrders.contains(order));
        assertTrue(allOrders.contains(order2));
    }


}