package com.albertdiaz.bookstore.repositories;

import com.albertdiaz.bookstore.models.Book;

import java.util.Set;

public interface BookRepository extends Repository<Integer, Book> {
    Book getByName(String name);
}
