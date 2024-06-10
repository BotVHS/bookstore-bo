package cat.teknos.bookstore.domain.jdbc.models;

import com.albertdiaz.bookstore.models.User;

import java.io.Serializable;
import java.time.LocalDate;

public class Order implements com.albertdiaz.bookstore.models.Order, Serializable {

    private int id;
    private User userID;
    private LocalDate orderDate;
    private float totalPrice;
    private String shippingAddress;
    private String orderStatus;


    @Override
    public int getId() { return id; }

    @Override
    public void setId(int OrderID) { this.id = OrderID; }


    @Override
    public User getUser() { return userID; }

    @Override
    public void setUser(User user) { this.userID = user; }

    @Override
    public LocalDate getOrderDate() { return orderDate; }

    @Override
    public void setOrderDate(LocalDate OrderDate) { this.orderDate = OrderDate; }

    @Override
    public Float getTotalPrice() { return totalPrice; }

    @Override
    public void setTotalPrice(Float TotalPrice) { this.totalPrice = TotalPrice; }

    @Override
    public String getShippingAddress() { return shippingAddress; }

    @Override
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }

    @Override
    public String getOrderStatus() { return orderStatus; }

    @Override
    public void setOrderStatus(String OrderStatus) { this.orderStatus = OrderStatus; }
}
