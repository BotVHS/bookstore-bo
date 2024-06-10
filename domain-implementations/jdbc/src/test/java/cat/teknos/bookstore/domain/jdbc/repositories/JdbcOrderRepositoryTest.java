package cat.teknos.bookstore.domain.jdbc.repositories;

import cat.teknos.bookstore.domain.jdbc.models.Order;
import cat.teknos.bookstore.domain.jdbc.models.User;
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
public class JdbcOrderRepositoryTest {

    private final Connection connection;

    public JdbcOrderRepositoryTest(Connection connection) {
        this.connection = connection;
    }

    @Test
    @DisplayName("Test 1: INSERT ORDER")
    void insertOrderTest() throws SQLException {
        User user = new User();
        user.setId(1);

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDate.now());
        order.setTotalPrice(99.99f);
        order.setShippingAddress("123 Test Street");
        order.setOrderStatus("Pending");

        var repository = new JdbcOrderRepository(connection);
        repository.save(order);
        assertTrue(order.getId() > 0);
    }

    @Test
    @DisplayName("Test 2: UPDATE ORDER")
    void updateOrderTest() throws SQLException {
        User user = new User();
        user.setId(1);

        Order order = new Order();
        order.setId(1);
        order.setUser(user);
        order.setOrderDate(LocalDate.now());
        order.setTotalPrice(199.99f);
        order.setShippingAddress("456 Updated Street");
        order.setOrderStatus("Shipped");

        var repository = new JdbcOrderRepository(connection);
        repository.save(order);
        assertEquals(1, order.getId());
    }

    @Test
    @DisplayName("Test 3: DELETE ORDER")
    void deleteOrderTest() throws SQLException {
        Order order = new Order();
        order.setId(2);

        var repository = new JdbcOrderRepository(connection);
        repository.delete(order);

        assertNull(repository.get(2));
    }

    @Test
    @DisplayName("Test 4: GET ORDER")
    void getOrderTest() throws SQLException {
        var repository = new JdbcOrderRepository(connection);
        assertNotNull(repository.get(1));
    }

    @Test
    @DisplayName("Test 5: GET ALL ORDERS")
    void getAllOrdersTest() throws SQLException {
        var repository = new JdbcOrderRepository(connection);
        assertNotNull(repository.getAll());
    }

}
