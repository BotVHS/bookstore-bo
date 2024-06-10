package com.albertdiaz.bookstore.repositories;

import com.albertdiaz.bookstore.models.Order;

import java.util.Set;

public interface OrderRepository extends Repository<Integer, Order> {
    Order getByName(String name);
}
