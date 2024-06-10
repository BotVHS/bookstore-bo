package cat.teknos.bookstore.domain.jdbc.models;
import com.albertdiaz.bookstore.models.Author;
import com.albertdiaz.bookstore.models.Book;
import com.albertdiaz.bookstore.models.Order;
import com.albertdiaz.bookstore.models.OrderDetail;
import com.albertdiaz.bookstore.models.Review;
import com.albertdiaz.bookstore.models.User;
import com.albertdiaz.bookstore.models.ModelFactory;

public class JdbcModelFactory implements ModelFactory {

    @Override
    public Author createAuthor() {
        return new cat.teknos.bookstore.domain.jdbc.models.Author();
    }

    @Override
    public Book createBook() {
        return new cat.teknos.bookstore.domain.jdbc.models.Book();
    }

    @Override
    public Order createOrder() {
        return new cat.teknos.bookstore.domain.jdbc.models.Order();
    }

    @Override
    public OrderDetail createOrderDetail() {
        return new cat.teknos.bookstore.domain.jdbc.models.OrderDetail();
    }

    @Override
    public Review createReview() {
        return new cat.teknos.bookstore.domain.jdbc.models.Review();
    }

    @Override
    public User createUser() {
        return new cat.teknos.bookstore.domain.jdbc.models.User();
    }
}
