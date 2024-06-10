package com.albertdiaz.bookstore.repositories;

public interface RepositoryFactory {
    AuthorRepository getAuthorRepository();
    BookRepository getBookRepository();
    OrderRepository getOrderRepository();
    OrderDetailRepository getOrderDetailRepository();
    ReviewRepository getReviewRepository();
    UserRepository getUserRepository();
}
