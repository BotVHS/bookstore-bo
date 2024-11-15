package cat.teknos.bookstore.domain.jpa.models;

import com.albertdiaz.bookstore.models.*;

public class JpaModelFactory implements ModelFactory {
    @Override
    public Author createAuthor() {
        return new Author();
    }

    @Override
    public Book createBook() {
        return new Book();
    }

    @Override
    public Order createOrder() {
        return new Order();
    }

    @Override
    public OrderDetail createOrderDetail() {
        return null;
    }

    @Override
    public Review createReview() {
        return new Review();
    }

    @Override
    public User createUser() {
        return new User();
    }
}