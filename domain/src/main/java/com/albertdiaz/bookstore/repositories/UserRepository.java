package com.albertdiaz.bookstore.repositories;

import com.albertdiaz.bookstore.models.User;

import java.util.Set;

public interface UserRepository extends Repository<Integer, User> {
    User getByName(String name);
}
