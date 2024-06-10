package cat.teknos.bookstore.domain.jdbc.repositories;

import com.albertdiaz.bookstore.repositories.*;
import com.albertdiaz.bookstore.exceptions.RepositoryException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcRepositoryFactory implements RepositoryFactory {

    private Connection connection;

    public JdbcRepositoryFactory() {
        try {
            var properties = new Properties();
            properties.load(this.getClass().getResourceAsStream("/datasource.properties"));

            connection = DriverManager.getConnection(String.format("%s:%s://%s/%s",
                    properties.getProperty("protocol"),
                    properties.getProperty("subprotocol"),
                    properties.getProperty("url"),
                    properties.getProperty("database")), properties.getProperty("user"), properties.getProperty("password"));
        } catch (SQLException e) {
            throw new RepositoryException(e);
        } catch (IOException e) {
            throw new RepositoryException();
        }
    }

    @Override
    public AuthorRepository getAuthorRepository() {
        return new JdbcAuthorRepository(connection);
    }

    @Override
    public BookRepository getBookRepository() {
        return new JdbcBookRepository(connection);
    }

    @Override
    public OrderRepository getOrderRepository() { return new JdbcOrderRepository(connection); }

    @Override
    public OrderDetailRepository getOrderDetailRepository() {
        return null;
    }

    @Override
    public ReviewRepository getReviewRepository() {
        return null;
    }

    @Override
    public UserRepository getUserRepository() {
        return new JdbcUserRepository(connection);
    }
}
