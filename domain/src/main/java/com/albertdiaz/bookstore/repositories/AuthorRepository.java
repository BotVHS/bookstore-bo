package com.albertdiaz.bookstore.repositories;

import com.albertdiaz.bookstore.models.Author;

import java.util.Set;

public interface AuthorRepository extends Repository<Integer, Author> {
    Author getByName(String name);

    void save(Author model);
}
