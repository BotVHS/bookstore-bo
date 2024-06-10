package com.albertdiaz.bookstore.repositories;

import com.albertdiaz.bookstore.models.OrderDetail;

public interface OrderDetailRepository extends Repository<Integer, OrderDetail> {
    OrderDetail getByOrderId(Integer name);
}
