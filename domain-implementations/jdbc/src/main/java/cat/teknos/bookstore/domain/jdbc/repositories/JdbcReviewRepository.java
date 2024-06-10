package cat.teknos.bookstore.domain.jdbc.repositories;

import cat.teknos.bookstore.domain.jdbc.models.Book;
import cat.teknos.bookstore.domain.jdbc.models.User;
import com.albertdiaz.bookstore.exceptions.RepositoryException;
import com.albertdiaz.bookstore.models.Review;
import com.albertdiaz.bookstore.repositories.ReviewRepository;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class JdbcReviewRepository implements ReviewRepository {
    private static final String INSERT_REVIEW = "INSERT INTO review (BOOKID, USERID, RATING, COMMENT, REVIEWDATE) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_REVIEW = "UPDATE review SET BOOKID = ?, USERID = ?, RATING = ?, COMMENT = ?, REVIEWDATE = ? WHERE ID = ?";
    private static final String DELETE_REVIEW = "DELETE FROM review WHERE ID = ?";
    private static final String GET_REVIEW_BY_ID = "SELECT * FROM review WHERE ID = ?";
    private static final String GET_ALL_REVIEWS = "SELECT * FROM review";
    private static final String GET_REVIEWS_BY_BOOK_ID = "SELECT * FROM review WHERE BOOKID = ?";
    private static final String GET_REVIEWS_BY_USER_ID = "SELECT * FROM review WHERE USERID = ?";

    private final Connection connection;

    public JdbcReviewRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Review review) {
        if (review.getId() <= 0) {
            insert(review);
        } else {
            update(review);
        }
    }
    private void insert(Review review) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_REVIEW, Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            statement.setInt(1, review.getBook().getId());
            statement.setInt(2, review.getUser().getId());
            statement.setInt(3, review.getRating());
            statement.setString(4, review.getComment());
            statement.setDate(5, Date.valueOf(review.getReviewDate()));

            statement.executeUpdate();

            var keys = statement.getGeneratedKeys();
            if (keys.next()) {
                review.setId(keys.getInt(1));
            }
            connection.commit();
        } catch (SQLException e) {
            rollback();
            throw new RepositoryException(e);
        } finally {
            setAutoCommitTrue();
        }
    }

    private void update(Review review) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_REVIEW)) {
            connection.setAutoCommit(false);
            statement.setInt(1, review.getBook().getId());
            statement.setInt(2, review.getUser().getId());
            statement.setInt(3, review.getRating());
            statement.setString(4, review.getComment());
            statement.setDate(5, Date.valueOf(review.getReviewDate()));
            statement.setInt(6, review.getId());

            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            rollback();
            throw new RepositoryException(e);
        } finally {
            setAutoCommitTrue();
        }
    }

    @Override
    public void delete(Review review) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_REVIEW)) {
            connection.setAutoCommit(false);
            statement.setInt(1, review.getId());
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            rollback();
            throw new RepositoryException(e);
        } finally {
            setAutoCommitTrue();
        }
    }

    @Override
    public Review get(Integer id) {
        try (PreparedStatement statement = connection.prepareStatement(GET_REVIEW_BY_ID)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRowToReview(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return null;
    }

    @Override
    public Set<Review> getAll() {
        try (PreparedStatement statement = connection.prepareStatement(GET_ALL_REVIEWS)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                Set<Review> reviews = new HashSet<>();
                while (resultSet.next()) {
                    reviews.add(mapRowToReview(resultSet));
                }
                return reviews;
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
    @Override
    public Set<Review> getByBookId(Integer bookId) {
        try (PreparedStatement statement = connection.prepareStatement(GET_REVIEWS_BY_BOOK_ID)) {
            statement.setInt(1, bookId);

            try (ResultSet resultSet = statement.executeQuery()) {
                Set<Review> reviews = new HashSet<>();
                while (resultSet.next()) {
                    reviews.add(mapRowToReview(resultSet));
                }
                return reviews;
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    private Review mapRowToReview(ResultSet resultSet) throws SQLException {
        Review review = new cat.teknos.bookstore.domain.jdbc.models.Review();
        review.setId(resultSet.getInt("ID"));

        Book book = new Book();
        book.setId(resultSet.getInt("BOOKID"));
        review.setBook(book);

        User user = new User();
        user.setId(resultSet.getInt("USERID"));
        review.setUser(user);

        review.setRating(resultSet.getInt("RATING"));
        review.setComment(resultSet.getString("COMMENT"));
        review.setReviewDate(resultSet.getDate("REVIEWDATE").toLocalDate());

        return review;
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

    @Override
    public Set<Review> getByUserId(Integer userId) {
        try (PreparedStatement statement = connection.prepareStatement(GET_REVIEWS_BY_USER_ID)) {
            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                Set<Review> reviews = new HashSet<>();
                while (resultSet.next()) {
                    reviews.add(mapRowToReview(resultSet));
                }
                return reviews;
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }
}
