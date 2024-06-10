package cat.teknos.bookstore.domain.jdbc.repositories;

import cat.teknos.bookstore.domain.jdbc.models.Book;
import cat.teknos.bookstore.domain.jdbc.models.Order;
import com.albertdiaz.bookstore.exceptions.RepositoryException;
import com.albertdiaz.bookstore.models.OrderDetail;
import com.albertdiaz.bookstore.repositories.OrderDetailRepository;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class JdbcOrderDetailRepository implements OrderDetailRepository {
    private static final String INSERT_ORDER_DETAIL = "INSERT INTO orderdetail (ORDERID, BOOKID, QUANTITY, PRICEPERITEM) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_ORDER_DETAIL = "UPDATE orderdetail SET ORDERID = ?, BOOKID = ?, QUANTITY = ?, PRICEPERITEM = ? WHERE ID = ?";
    private static final String DELETE_ORDER_DETAIL = "DELETE FROM orderdetail WHERE ID = ?";
    private static final String GET_ORDER_DETAIL_BY_ID = "SELECT * FROM orderdetail WHERE ID = ?";
    private static final String GET_ALL_ORDER_DETAILS = "SELECT * FROM orderdetail";
    private static final String GET_ORDER_DETAILS_BY_ORDER_ID = "SELECT * FROM orderdetail WHERE ORDERID = ?";

    private final Connection connection;

    public JdbcOrderDetailRepository(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void save(OrderDetail model) {
        if (model.getId() <= 0) {
            insert(model);
        } else {
            update(model);
        }
    }

    private void insert(OrderDetail model) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_ORDER_DETAIL, Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            statement.setInt(1, model.getOrder().getId());
            statement.setInt(2, model.getBook().getId());
            statement.setInt(3, model.getQuantity());
            statement.setFloat(4, model.getPricePerItem());

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

    private void update(OrderDetail model) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_ORDER_DETAIL)) {
            connection.setAutoCommit(false);
            statement.setInt(1, model.getOrder().getId());
            statement.setInt(2, model.getBook().getId());
            statement.setInt(3, model.getQuantity());
            statement.setFloat(4, model.getPricePerItem());
            statement.setInt(5, model.getId());

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
    public void delete(OrderDetail model) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_ORDER_DETAIL)) {
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
    public OrderDetail get(Integer id) {
        try (PreparedStatement statement = connection.prepareStatement(GET_ORDER_DETAIL_BY_ID)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRowToOrderDetail(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Set<OrderDetail> getAll() {
        try (PreparedStatement statement = connection.prepareStatement(GET_ALL_ORDER_DETAILS)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                Set<OrderDetail> orderDetails = new HashSet<>();
                while (resultSet.next()) {
                    orderDetails.add(mapRowToOrderDetail(resultSet));
                }
                return orderDetails;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public OrderDetail getByOrderId(Integer name) {
        try (PreparedStatement statement = connection.prepareStatement(GET_ORDER_DETAILS_BY_ORDER_ID)) {
            statement.setString(1, String.valueOf(name));

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRowToOrderDetail(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private OrderDetail mapRowToOrderDetail(ResultSet resultSet) throws SQLException {
        OrderDetail orderDetail = new cat.teknos.bookstore.domain.jdbc.models.OrderDetail();
        orderDetail.setId(resultSet.getInt("ID"));

        Order order = new Order();
        order.setId(resultSet.getInt("ORDERID"));
        orderDetail.setOrder(order);

        Book book = new Book();
        book.setId(resultSet.getInt("BOOKID"));
        orderDetail.setBook(book);

        orderDetail.setQuantity(resultSet.getInt("QUANTITY"));
        orderDetail.setPricePerItem(resultSet.getFloat("PRICEPERITEM"));

        return orderDetail;
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


