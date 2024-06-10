package cat.uvic.teknos.bookstore.domain.fake.models;

import com.albertdiaz.bookstore.models.Order;
import com.albertdiaz.bookstore.models.OrderDetail;
import com.albertdiaz.bookstore.models.Review;
import com.albertdiaz.bookstore.models.User;

public class FakeModelFactory implements com.albertdiaz.bookstore.models.ModelFactory{
    @Override
    public Author createAuthor() {
        return new cat.uvic.teknos.bookstore.domain.fake.models.Author();
    }


    @Override
    public Book createBook() {
        return new cat.uvic.teknos.bookstore.domain.fake.models.Book();
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
        return null;
    }
}
