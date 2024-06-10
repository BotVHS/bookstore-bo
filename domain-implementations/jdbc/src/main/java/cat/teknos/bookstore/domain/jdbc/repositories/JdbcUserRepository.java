package cat.teknos.bookstore.domain.jdbc.repositories;

import com.albertdiaz.bookstore.exceptions.RepositoryException;
import com.albertdiaz.bookstore.models.User;
import com.albertdiaz.bookstore.repositories.UserRepository;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class JdbcUserRepository implements UserRepository {
    private static final String INSERT_USER = "INSERT INTO user (FIRSTNAME, LASTNAME, EMAIL, PASSWORDHASH, ADDRESS, CITY, COUNTRY, POSTALCODE, JOINDATE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_USER = "UPDATE user SET FIRSTNAME = ?, LASTNAME = ?, EMAIL = ?, PASSWORDHASH = ?, ADDRESS = ?, CITY = ?, COUNTRY = ?, POSTALCODE = ?, JOINDATE = ? WHERE ID = ?";
    private static final String DELETE_USER = "DELETE FROM user WHERE ID = ?";
    private static final String GET_USER_BY_ID = "SELECT * FROM user WHERE ID = ?";
    private static final String GET_ALL_USERS = "SELECT * FROM user";
    private static final String GET_USER_BY_NAME = "SELECT * FROM user WHERE FIRSTNAME = ? OR LASTNAME = ?";


    private final Connection connection;

    public JdbcUserRepository(Connection connection) {
        this.connection = connection;
    }


    @Override
    public User getByName(String name) {
        try (PreparedStatement statement = connection.prepareStatement(GET_USER_BY_NAME)) {
            statement.setString(1, name);
            statement.setString(2, name);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRowToUser(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void save(User model) {
        if (model.getId() <= 0) {
            insert(model);
        } else {
            update(model);
        }
    }

    private void insert(User model) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            statement.setString(1, model.getFirstName());
            statement.setString(2, model.getLastName());
            statement.setString(3, model.getEmail());
            statement.setString(4, model.getPasswordHash());
            statement.setString(5, model.getAddress());
            statement.setString(6, model.getCity());
            statement.setString(7, model.getCountry());
            statement.setString(8, model.getPostalCode());
            statement.setDate(9, java.sql.Date.valueOf(model.getJoinDate()));

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

    private void update(User model) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_USER)) {
            connection.setAutoCommit(false);
            statement.setString(1, model.getFirstName());
            statement.setString(2, model.getLastName());
            statement.setString(3, model.getEmail());
            statement.setString(4, model.getPasswordHash());
            statement.setString(5, model.getAddress());
            statement.setString(6, model.getCity());
            statement.setString(7, model.getCountry());
            statement.setString(8, model.getPostalCode());
            statement.setDate(9, java.sql.Date.valueOf(model.getJoinDate()));
            statement.setInt(10, model.getId());

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
    public void delete(User model) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_USER)) {
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
    public User get(Integer id) {
        try (PreparedStatement statement = connection.prepareStatement(GET_USER_BY_ID)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRowToUser(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Set<User> getAll() {
        try (PreparedStatement statement = connection.prepareStatement(GET_ALL_USERS)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                Set<User> users = new HashSet<>();
                while (resultSet.next()) {
                    users.add(mapRowToUser(resultSet));
                }
                return users;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private User mapRowToUser(ResultSet resultSet) throws SQLException {
        User user = new cat.teknos.bookstore.domain.jdbc.models.User();
        user.setId(resultSet.getInt("ID"));
        user.setFirstName(resultSet.getString("FIRSTNAME"));
        user.setLastName(resultSet.getString("LASTNAME"));
        user.setEmail(resultSet.getString("EMAIL"));
        user.setPasswordHash(resultSet.getString("PASSWORDHASH"));
        user.setAddress(resultSet.getString("ADDRESS"));
        user.setCity(resultSet.getString("CITY"));
        user.setCountry(resultSet.getString("COUNTRY"));
        user.setPostalCode(resultSet.getString("POSTALCODE"));
        user.setJoinDate(resultSet.getDate("JOINDATE").toLocalDate());

        return user;
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
