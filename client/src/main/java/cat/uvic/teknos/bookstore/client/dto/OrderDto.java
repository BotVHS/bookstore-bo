package cat.uvic.teknos.bookstore.client.dto;

import java.time.LocalDate;

public class OrderDto implements com.albertdiaz.bookstore.models.Order {
    private int id;
    private com.albertdiaz.bookstore.models.User user;
    private LocalDate orderDate;
    private Float totalPrice;
    private String shippingAddress;
    private String orderStatus;

    @Override
    public int getId() { return id; }
    @Override
    public void setId(int id) { this.id = id; }

    @Override
    public com.albertdiaz.bookstore.models.User getUser() { return user; }
    @Override
    public void setUser(com.albertdiaz.bookstore.models.User user) { this.user = user; }

    @Override
    public LocalDate getOrderDate() { return orderDate; }
    @Override
    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate; }

    @Override
    public Float getTotalPrice() { return totalPrice; }
    @Override
    public void setTotalPrice(Float totalPrice) { this.totalPrice = totalPrice; }

    @Override
    public String getShippingAddress() { return shippingAddress; }
    @Override
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }

    @Override
    public String getOrderStatus() { return orderStatus; }
    @Override
    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }
}
