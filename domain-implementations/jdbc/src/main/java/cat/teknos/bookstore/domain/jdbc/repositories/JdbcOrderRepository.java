package cat.teknos.bookstore.domain.jdbc.repositories;

import cat.teknos.bookstore.domain.jdbc.models.User;
import com.albertdiaz.bookstore.exceptions.RepositoryException;
import com.albertdiaz.bookstore.models.Order;
import com.albertdiaz.bookstore.repositories.OrderRepository;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class JdbcOrderRepository implements OrderRepository {
    private static final String INSERT_ORDER = "INSERT INTO orders (USERID, ORDERDATE, TOTALPRICE, SHIPPINGADDRESS, ORDERSTATUS) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_ORDER = "UPDATE orders SET USERID = ?, ORDERDATE = ?, TOTALPRICE = ?, SHIPPINGADDRESS = ?, ORDERSTATUS = ? WHERE ID = ?";
    private static final String DELETE_ORDER = "DELETE FROM orders WHERE ID = ?";
    private static final String GET_ORDER_BY_ID = "SELECT * FROM orders WHERE ID = ?";
    private static final String GET_ALL_ORDERS = "SELECT * FROM orders";

    private final Connection connection;

    public JdbcOrderRepository(Connection connection) {
        this.connection = connection;
    }


    @Override
    public Order getByName(String name) {
        return null;
    }

    @Override
    public void save(Order model) {
        if (model.getId() <= 0) {
            insert(model);
        } else {
            update(model);
        }
    }

    private void insert(Order model) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            statement.setInt(1, model.getUser().getId());
            statement.setDate(2, Date.valueOf(model.getOrderDate()));
            statement.setFloat(3, model.getTotalPrice());
            statement.setString(4, model.getShippingAddress());
            statement.setString(5, model.getOrderStatus());

            statement.executeUpdate();

            var keys = statement.getGeneratedKeys();
            if (keys.next()) {
                model.setId(keys.getInt(1));
            }
            connection.commit();
        } catch (SQLException e) {
            rollback();
            throw new RuntimeException(e);
        } finally {
            setAutoCommitTrue();
        }
    }

    private void update(Order model) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_ORDER)) {
            connection.setAutoCommit(false);
            statement.setInt(1, model.getUser().getId());
            statement.setDate(2, Date.valueOf(model.getOrderDate()));
            statement.setFloat(3, model.getTotalPrice());
            statement.setString(4, model.getShippingAddress());
            statement.setString(5, model.getOrderStatus());
            statement.setInt(6, model.getId());

            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            rollback();
            throw new RuntimeException(e);
        } finally {
            setAutoCommitTrue();
        }
    }
    @Override
    public void delete(Order model) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_ORDER)) {
            connection.setAutoCommit(false);
            statement.setInt(1, model.getId());
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            rollback();
            throw new RuntimeException(e);
        } finally {
            setAutoCommitTrue();
        }
    }

    @Override
    public Order get(Integer id) {
        try (PreparedStatement statement = connection.prepareStatement(GET_ORDER_BY_ID)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRowToOrder(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Set<Order> getAll() {
        try (PreparedStatement statement = connection.prepareStatement(GET_ALL_ORDERS)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                Set<Order> orders = new HashSet<>();
                while (resultSet.next()) {
                    orders.add(mapRowToOrder(resultSet));
                }
                return orders;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private Order mapRowToOrder(ResultSet resultSet) throws SQLException {
        Order order = new cat.teknos.bookstore.domain.jdbc.models.Order();
        order.setId(resultSet.getInt("ID"));
        User user = new User();
        user.setId(resultSet.getInt("USERID"));
        order.setUser(user);
        order.setOrderDate(resultSet.getDate("ORDERDATE").toLocalDate());
        order.setTotalPrice(resultSet.getFloat("TOTALPRICE"));
        order.setShippingAddress(resultSet.getString("SHIPPINGADDRESS"));
        order.setOrderStatus(resultSet.getString("ORDERSTATUS"));

        return order;
    }

    private void setAutoCommitTrue() {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    private void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
}
