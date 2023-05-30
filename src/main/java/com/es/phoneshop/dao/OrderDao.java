package com.es.phoneshop.dao;

import com.es.phoneshop.model.order.Order;

import java.util.List;

public interface OrderDao {
    Order get(Long id);
    Order getBySecureId(String secureId);
    List<Order> getAllOrders();
    void save(Order order);
}
