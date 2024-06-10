package com.albertdiaz.bookstore.models;

public interface ModelFactory {
    Author createAuthor();
    Book createBook();
    Order createOrder();
    OrderDetail createOrderDetail();
    Review createReview();
    User createUser();
}
