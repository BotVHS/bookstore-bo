package cat.teknos.bookstore.domain.jdbc.repositories;

import cat.teknos.bookstore.domain.jdbc.models.OrderDetail;
import cat.teknos.bookstore.domain.jdbc.models.Order;
import cat.teknos.bookstore.domain.jdbc.models.Book;
import com.albertdiaz.dbtestutils.junit.CreateSchemaExtension;
import com.albertdiaz.dbtestutils.junit.GetConnectionExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({CreateSchemaExtension.class, GetConnectionExtension.class})
public class JdbcOrderDetailRepositoryTest {

    private final Connection connection;

    public JdbcOrderDetailRepositoryTest(Connection connection) {
        this.connection = connection;
    }

    @Test
    @DisplayName("Test 1: INSERT ORDER DETAIL")
    void insertOrderDetailTest() throws SQLException {
        Order order = new Order();
        order.setId(1);

        Book book = new Book();
        book.setId(1);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrder(order);
        orderDetail.setBook(book);
        orderDetail.setQuantity(2);
        orderDetail.setPricePerItem(49.99f);

        var repository = new JdbcOrderDetailRepository(connection);
        repository.save(orderDetail);
        assertTrue(orderDetail.getId() > 0);
    }

    @Test
    @DisplayName("Test 2: UPDATE ORDER DETAIL")
    void updateOrderDetailTest() throws SQLException {
        Order order = new Order();
        order.setId(1);

        Book book = new Book();
        book.setId(2);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setId(1);
        orderDetail.setOrder(order);
        orderDetail.setBook(book);
        orderDetail.setQuantity(3);
        orderDetail.setPricePerItem(69.99f);

        var repository = new JdbcOrderDetailRepository(connection);
        repository.save(orderDetail);
        assertEquals(1, orderDetail.getId());
    }

    @Test
    @DisplayName("Test 3: DELETE ORDER DETAIL")
    void deleteOrderDetailTest() throws SQLException {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setId(2);

        var repository = new JdbcOrderDetailRepository(connection);
        repository.delete(orderDetail);

        assertNull(repository.get(2));
    }

    @Test
    @DisplayName("Test 4: GET ORDER DETAIL")
    void getOrderDetailTest() throws SQLException {
        var repository = new JdbcOrderDetailRepository(connection);
        assertNotNull(repository.get(1));
    }

    @Test
    @DisplayName("Test 5: GET ALL ORDER DETAILS")
    void getAllOrderDetailsTest() throws SQLException {
        var repository = new JdbcOrderDetailRepository(connection);
        assertNotNull(repository.getAll());
    }

    @Test
    @DisplayName("Test 6: GET ORDER DETAILS BY ORDER ID")
    void getOrderDetailsByOrderIdTest() throws SQLException {
        var repository = new JdbcOrderDetailRepository(connection);
        int orderId = 1;
        OrderDetail orderDetail = (OrderDetail) repository.getByOrderId(orderId);
        assertNotNull(orderDetail);
        assertEquals(orderId, orderDetail.getOrder().getId());
    }
}
