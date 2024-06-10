package cat.teknos.bookstore.domain.jdbc.repositories;

import cat.teknos.bookstore.domain.jdbc.models.Book;
import cat.teknos.bookstore.domain.jdbc.models.Order;
import cat.teknos.bookstore.domain.jdbc.models.OrderDetail;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderDetailRepositoryTest {
    @Test
    void saveNewOrderDetail() {
        var repository = new OrderDetailRepository();

        // Create a new order detail
        var orderDetail = new OrderDetail();
        orderDetail.setOrder(new Order());
        orderDetail.setBook(new Book());
        orderDetail.setQuantity(2);
        orderDetail.setPricePerItem(25.0f);

        repository.save(orderDetail);

        assertTrue(orderDetail.getId() > 0);
        assertNotNull(repository.get(orderDetail.getId()));

        OrderDetailRepository.load();

        assertNotNull(repository.get(orderDetail.getId()));
        assertEquals(2, repository.get(orderDetail.getId()).getQuantity());
    }

    @Test
    void updateOrderDetail() {
        var repository = new OrderDetailRepository();

        var orderDetail = new OrderDetail();
        orderDetail.setOrder(new Order());
        orderDetail.setBook(new Book());
        orderDetail.setQuantity(3);
        orderDetail.setPricePerItem(20.0f);

        repository.save(orderDetail);

        orderDetail.setQuantity(5);
        orderDetail.setPricePerItem(15.0f);
        repository.save(orderDetail);

        var updatedOrderDetail = repository.get(orderDetail.getId());
        assertEquals(5, updatedOrderDetail.getQuantity());
        assertEquals(15.0f, updatedOrderDetail.getPricePerItem());
    }

    @Test
    void deleteOrderDetail() {
        var repository = new OrderDetailRepository();

        var orderDetail = new OrderDetail();
        orderDetail.setOrder(new Order());
        orderDetail.setBook(new Book());
        orderDetail.setQuantity(3);
        orderDetail.setPricePerItem(20.0f);

        repository.save(orderDetail);

        repository.delete(orderDetail);

        assertNull(repository.get(orderDetail.getId()));
    }

    @Test
    void getOrderDetail() {
        var repository = new OrderDetailRepository();

        var orderDetail = new OrderDetail();
        orderDetail.setOrder(new Order());
        orderDetail.setBook(new Book());
        orderDetail.setQuantity(4);
        orderDetail.setPricePerItem(30.0f);

        repository.save(orderDetail);

        var retrievedOrderDetail = repository.get(orderDetail.getId());

        assertEquals(orderDetail.getId(), retrievedOrderDetail.getId());
        assertEquals(orderDetail.getQuantity(), retrievedOrderDetail.getQuantity());
    }

    @Test
    void getAllOrderDetails() {
        var repository = new OrderDetailRepository();

        var orderDetail1 = new OrderDetail();
        orderDetail1.setOrder(new Order());
        orderDetail1.setBook(new Book());
        orderDetail1.setQuantity(5);
        orderDetail1.setPricePerItem(15.0f);

        var orderDetail2 = new OrderDetail();
        orderDetail2.setOrder(new Order());
        orderDetail2.setBook(new Book());
        orderDetail2.setQuantity(6);
        orderDetail2.setPricePerItem(18.0f);

        repository.save(orderDetail1);
        repository.save(orderDetail2);

        var allOrderDetails = repository.getAll();

        assertEquals(2, allOrderDetails.size());
        assertTrue(allOrderDetails.contains(orderDetail1));
        assertTrue(allOrderDetails.contains(orderDetail2));
    }


}