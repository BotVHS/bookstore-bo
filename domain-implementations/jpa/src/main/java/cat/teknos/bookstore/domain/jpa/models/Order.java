package cat.teknos.bookstore.domain.jpa.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "ORDERS")
public class Order implements com.albertdiaz.bookstore.models.Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @ManyToOne
    @JoinColumn(name = "USERID")
    private User user;

    @Column(name = "ORDERDATE")
    private LocalDate orderDate;

    @Column(name = "TOTALPRICE")
    private Float totalPrice;

    @Column(name = "SHIPPINGADDRESS")
    private String shippingAddress;

    @Column(name = "ORDERSTATUS")
    private String orderStatus;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(com.albertdiaz.bookstore.models.User user) {
        this.user = (User) user;
    }

    @Override
    public LocalDate getOrderDate() {
        return orderDate;
    }

    @Override
    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public Float getTotalPrice() {
        return totalPrice;
    }

    @Override
    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String getShippingAddress() {
        return shippingAddress;
    }

    @Override
    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    @Override
    public String getOrderStatus() {
        return orderStatus;
    }

    @Override
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
