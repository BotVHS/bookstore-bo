package com.albertdiaz.bookstore.models;

import java.time.LocalDate;

public interface Order {
    int getId();
    void setId(int id);

    User getUser();
    void setUser(User user);

    LocalDate getOrderDate();
    void setOrderDate(LocalDate orderDate);

    Float getTotalPrice();
    void setTotalPrice(Float totalPrice);

    String getShippingAddress();
    void setShippingAddress(String shippingAddress);

    String getOrderStatus();
    void setOrderStatus(String orderStatus);
}
