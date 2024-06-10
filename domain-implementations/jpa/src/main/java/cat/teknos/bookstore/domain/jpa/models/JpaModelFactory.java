package cat.teknos.bookstore.domain.jpa.models;

import com.albertdiaz.bookstore.models.*;

public class JpaModelFactory implements ModelFactory {
    @Override
    public Author createAuthor() {
        return new cat.teknos.bookstore.domain.jpa.models.Author();
    }

    @Override
    public Book createBook() {
        return new cat.teknos.bookstore.domain.jpa.models.Book();
    }

    @Override
    public Order createOrder() {
        return null;
    }

    @Override
    public OrderDetail createOrderDetail() {
        return null;
    }

    @Override
    public Review createReview() {
        return null;
    }

    @Override
    public User createUser() {
        return new cat.teknos.bookstore.domain.jpa.models.User();
    }
}
