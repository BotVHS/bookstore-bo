package com.albertdiaz.bookstore.models;

public interface OrderDetail {

    int getId();
    void setId(int id);

    Order getOrder();
    void setOrder(Order orderID);

    Book getBook();
    void setBook(Book bookID);

    int getQuantity();
    void setQuantity(int quantity);

    Float getPricePerItem();
    void setPricePerItem(Float pricePerItem);
}
