package cat.teknos.bookstore.domain.jdbc.repositories;

import cat.teknos.bookstore.domain.jdbc.models.Author;
import com.albertdiaz.bookstore.exceptions.RepositoryException;
import com.albertdiaz.bookstore.models.Book;
import com.albertdiaz.bookstore.repositories.BookRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.Set;
import java.util.HashSet;


public class JdbcBookRepository implements BookRepository {
    private static final String INSERT_BOOK = "INSERT INTO book (TITLE, AUTHORID, ISBN, PRICE, GENRE, PUBLISHDATE, PUBLISHER, PAGECOUNT) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_BOOK = "UPDATE book SET TITLE = ?, AUTHORID = ?, ISBN = ?, PRICE = ?, GENRE = ?, PUBLISHDATE = ?, PUBLISHER = ?, PAGECOUNT = ? WHERE BOOKID = ?";
    private static final String DELETE_BOOK = "DELETE FROM book WHERE ID = ?";
    private static final String GET_BOOK_BY_ID = "SELECT * FROM book WHERE ID = ?";
    private static final String GET_ALL_BOOKS = "SELECT * FROM book";
    private static final String GET_BOOK_BY_NAME = "SELECT * FROM book WHERE TITLE = ?";

    private final Connection connection;

    public JdbcBookRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Book model) {
        if (model.getId() <= 0) {
            insert(model);
        }
        else {
            update(model);
        }
    }

    private void insert(Book model) {
        try (
                var preparedStatement = connection.prepareStatement(INSERT_BOOK, Statement.RETURN_GENERATED_KEYS);) {
            connection.setAutoCommit(false);

            preparedStatement.setString(1, model.getTitle());
            preparedStatement.setInt(2, model.getAuthor().getId());
            preparedStatement.setString(3, model.getIsbn());
            preparedStatement.setFloat(4, model.getPrice());
            preparedStatement.setString(5, model.getGenre());
            preparedStatement.setDate(6, java.sql.Date.valueOf(model.getPublishDate()));
            preparedStatement.setString(7, model.getPublisher());
            preparedStatement.setInt(8, model.getPageCount());

            preparedStatement.executeUpdate();
            var bookKeys = preparedStatement.getGeneratedKeys();
            if (bookKeys.next()) {
                model.setId(bookKeys.getInt(1));
            }

            connection.commit();
        } catch (SQLException e) {
            rollback();
            throw new RuntimeException(e);
        } finally {
            setAutocommitFalse();
        }
    }


    private void update(Book model) {
        try (
                var preparedStatement = connection.prepareStatement(UPDATE_BOOK)) {
            connection.setAutoCommit(false);

            preparedStatement.setString(1, model.getTitle());
            preparedStatement.setInt(2, model.getAuthor().getId());
            preparedStatement.setString(3, model.getIsbn());
            preparedStatement.setFloat(4, model.getPrice());
            preparedStatement.setString(5, model.getGenre());
            preparedStatement.setDate(6, java.sql.Date.valueOf(model.getPublishDate()));
            preparedStatement.setString(7, model.getPublisher());
            preparedStatement.setInt(8, model.getPageCount());
            preparedStatement.setInt(9, model.getId());

            preparedStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            rollback();
            throw new RuntimeException(e);
        } finally {
            setAutocommitFalse();
        }
    }

    private void setAutocommitFalse() {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RepositoryException();
        }
    }

    private void rollback() {
        try {
            connection.rollback();
        } catch (SQLException ex) {
            throw new RepositoryException(ex);
        }
    }

    @Override
    public void delete(Book model) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BOOK)) {
            connection.setAutoCommit(false);

            preparedStatement.setInt(1, model.getId());
            preparedStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            rollback();
            throw new RuntimeException(e);
        } finally {
            setAutocommitFalse();
        }
    }

    @Override
    public Book get(Integer id) {
        try (PreparedStatement statement = connection.prepareStatement(GET_BOOK_BY_ID)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRowToBook(resultSet);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Set<Book> getAll() {
        try (PreparedStatement statement = connection.prepareStatement(GET_ALL_BOOKS)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                Set<Book> books = new HashSet<>();
                while (resultSet.next()) {
                    books.add(mapRowToBook(resultSet));
                }
                return books;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Book getByName(String name) {
        try (PreparedStatement statement = connection.prepareStatement(GET_BOOK_BY_NAME)) {
            statement.setString(1, name);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRowToBook(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private Book mapRowToBook(ResultSet resultSet) throws SQLException {
        Book book = new cat.teknos.bookstore.domain.jdbc.models.Book();
        book.setId(resultSet.getInt("ID"));
        book.setTitle(resultSet.getString("TITLE"));
        book.setIsbn(resultSet.getString("ISBN"));
        book.setPrice(resultSet.getFloat("PRICE"));
        book.setGenre(resultSet.getString("GENRE"));
        java.sql.Date publishdate = resultSet.getDate("PUBLISHDATE");
        book.setPublishDate(LocalDate.of(publishdate.getYear(), publishdate.getMonth(), publishdate.getDay()));
        book.setPublisher(resultSet.getString("PUBLISHER"));
        book.setPageCount(resultSet.getInt("PAGECOUNT"));
        Author author = new Author();
        author.setId(resultSet.getInt("AUTHORID"));
        book.setAuthor(author);

        return book;
    }
}
