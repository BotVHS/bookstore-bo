package cat.uvic.teknos.bookstore.client.dto;

import com.albertdiaz.bookstore.models.*;

public class DtoModelFactory implements ModelFactory {
    @Override
    public Author createAuthor() {
        return new AuthorDto();
    }

    @Override
    public Book createBook() {
        return new BookDto();
    }

    @Override
    public Order createOrder() {
        return new OrderDto();
    }

    @Override
    public OrderDetail createOrderDetail() {
        return null; // No implementem
    }

    @Override
    public Review createReview() {
        return new ReviewDto();
    }

    @Override
    public User createUser() {
        return new UserDto();
    }
}