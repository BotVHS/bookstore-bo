package cat.teknos.bookstore.domain.jpa.repositories;

import com.albertdiaz.bookstore.repositories.*;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaRepositoryFactory implements RepositoryFactory {
    private final EntityManagerFactory entityManagerFactory;

    public JpaRepositoryFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("bookstoredb");
    }

    @Override
    public AuthorRepository getAuthorRepository() {
        return new JpaAuthorRepository(entityManagerFactory);
    }

    @Override
    public BookRepository getBookRepository() {
        return new JpaBookRepository(entityManagerFactory);
    }

    @Override
    public OrderRepository getOrderRepository() {
        return null;
    }

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
        return new JpaUserRepository(entityManagerFactory);
    }
}
