package cat.teknos.bookstore.domain.jdbc.repositories;

import com.albertdiaz.bookstore.exceptions.RepositoryException;
import com.albertdiaz.bookstore.models.Author;
import com.albertdiaz.bookstore.repositories.AuthorRepository;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class JdbcAuthorRepository implements AuthorRepository {
    private static final String INSERT_AUTHOR = "INSERT INTO author (FIRSTNAME, LASTNAME, BIOGRAPHY, BIRTHDATE, COUNTRY) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_AUTHOR = "UPDATE author SET FIRSTNAME = ?, LASTNAME = ?, BIOGRAPHY = ?, BIRTHDATE = ?, COUNTRY = ? WHERE ID = ?";
    private final Connection connection;

    public JdbcAuthorRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Author model) {
        if (model.getId() <= 0) {
            insert(model);
        }
        else {
            update(model);
        }
    }

    private void insert(Author model) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_AUTHOR, Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            statement.setString(1, model.getFirstName());
            statement.setString(2, model.getLastName());
            statement.setString(3, model.getBiography());
            statement.setDate(4, java.sql.Date.valueOf(model.getBirthDate()));
            statement.setString(5, model.getNationality());

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

    private void update(Author model) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_AUTHOR)) {
            connection.setAutoCommit(false);
            statement.setString(1, model.getFirstName());
            statement.setString(2, model.getLastName());
            statement.setString(3, model.getBiography());
            statement.setDate(4, java.sql.Date.valueOf(model.getBirthDate()));
            statement.setString(5, model.getNationality());
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
    public void delete(Author model) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM AUTHOR WHERE ID = ?")) {
            connection.setAutoCommit(false);
            statement.setInt(1, model.getId());
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
    public Author get(Integer id) {
        String query = "SELECT * FROM AUTHOR WHERE ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Author result = new cat.teknos.bookstore.domain.jdbc.models.Author();
                    result.setId(id);
                    result.setFirstName(resultSet.getString("FIRSTNAME"));
                    result.setLastName(resultSet.getString("LASTNAME"));
                    result.setBiography(resultSet.getString("BIOGRAPHY"));
                    result.setBirthDate(resultSet.getDate("BIRTHDATE").toLocalDate());
                    result.setNationality(resultSet.getString("COUNTRY"));
                    return result;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Set<Author> getAll() {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM AUTHOR")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Set<Author> result = new HashSet<>();
            while (resultSet.next()) {
                Author author = new cat.teknos.bookstore.domain.jdbc.models.Author();
                author.setId(resultSet.getInt("ID"));
                author.setFirstName(resultSet.getString("FIRSTNAME"));
                author.setLastName(resultSet.getString("LASTNAME"));
                author.setBiography(resultSet.getString("BIOGRAPHY"));
                Date birthDate = resultSet.getDate("BIRTHDATE");
                if (birthDate != null) {
                    author.setBirthDate(birthDate.toLocalDate());
                }
                author.setNationality(resultSet.getString("COUNTRY"));
                result.add(author);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Author getByName(String name) {
        String query = "SELECT * FROM AUTHOR WHERE FIRSTNAME = ? OR LASTNAME = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Author author = new cat.teknos.bookstore.domain.jdbc.models.Author();
                    author.setId(resultSet.getInt("ID"));
                    author.setFirstName(resultSet.getString("FIRSTNAME"));
                    author.setLastName(resultSet.getString("LASTNAME"));
                    author.setBiography(resultSet.getString("BIOGRAPHY"));
                    author.setBirthDate(resultSet.getDate("BIRTHDATE").toLocalDate());
                    author.setNationality(resultSet.getString("COUNTRY"));
                    return author;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
