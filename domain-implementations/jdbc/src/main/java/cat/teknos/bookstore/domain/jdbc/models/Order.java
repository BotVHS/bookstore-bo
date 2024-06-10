package cat.teknos.bookstore.domain.jdbc.models;

import com.albertdiaz.bookstore.models.User;

import java.io.Serializable;
import java.time.LocalDate;

public class Order implements com.albertdiaz.bookstore.models.Order, Serializable {

    private int id;
    private User user;
    private LocalDate OrderDate;
    private float totalPrice;
    private String shippingAddress;
    private String orderStatus;


    @Override
    public int getId() { return id; }

    @Override
    public void setId(int OrderID) { this.id = OrderID; }

    @Override
    public com.albertdiaz.bookstore.models.User getUser() { return (com.albertdiaz.bookstore.models.User) user; }

    @Override
    public void setUser(com.albertdiaz.bookstore.models.User user) {
        this.user = (User) user;
    }

    @Override
    public LocalDate getOrderDate() { return OrderDate; }

    @Override
    public void setOrderDate(LocalDate OrderDate) { this.OrderDate = OrderDate; }

    @Override
    public Float getTotalPrice() { return totalPrice; }

    @Override
    public void setTotalPrice(Float TotalPrice) { this.totalPrice = TotalPrice; }

    @Override
    public String getShippingAddress() {
        return shippingAddress;
    }

    @Override
    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    @Override
    public String getOrderStatus() { return orderStatus; }

    @Override
    public void setOrderStatus(String OrderStatus) { this.orderStatus = OrderStatus; }
}
