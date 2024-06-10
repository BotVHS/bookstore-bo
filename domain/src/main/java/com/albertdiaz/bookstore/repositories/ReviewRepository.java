package com.albertdiaz.bookstore.repositories;

import com.albertdiaz.bookstore.models.Review;

import java.util.Set;

public interface ReviewRepository extends Repository<Integer, Review> {
    Set<Review> getByBookId(Integer bookId);

    Set<Review> getByUserId(Integer userId);
}
