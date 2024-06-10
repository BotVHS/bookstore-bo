package com.albertdiaz.bookstore.models;

import java.time.LocalDate;
import java.util.Set;

public interface Review {

    int getId();
    void setId(int id);

    Book getBook();
    void setBook(Book book);

    User getUser();
    void setUser(User user);

    int getRating();
    void setRating(int rating);

    String getComment();
    void setComment(String comment);

    LocalDate getReviewDate();
    void setReviewDate(LocalDate reviewDate);
}
