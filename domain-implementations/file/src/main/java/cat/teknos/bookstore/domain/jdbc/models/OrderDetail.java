package cat.teknos.bookstore.domain.jdbc.models;

import com.albertdiaz.bookstore.models.Book;
import com.albertdiaz.bookstore.models.Order;

import java.io.Serializable;

public class OrderDetail implements com.albertdiaz.bookstore.models.OrderDetail, Serializable {

    private int id;
    private Order orderID;
    private Book bookID;
    private int quantity;
    private float pricePerItem;

    @Override
    public int getId() { return id; }

    @Override
    public void setId(int OrderDetailID) { this.id = OrderDetailID; }

    @Override
    public Order getOrder() { return orderID; }

    @Override
    public void setOrder(Order OrderID) { this.orderID = OrderID; }

    @Override
    public Book getBook() { return bookID; }

    @Override
    public void setBook(Book BookID) { this.bookID = BookID; }

    @Override
    public int getQuantity() { return quantity; }

    @Override
    public void setQuantity(int Quantity) { this.quantity = Quantity; }

    @Override
    public Float getPricePerItem() { return pricePerItem; }

    @Override
    public void setPricePerItem(Float PricePerItem) { this.pricePerItem = PricePerItem; }
}
