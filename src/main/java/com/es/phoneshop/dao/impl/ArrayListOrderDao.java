package com.es.phoneshop.dao.impl;

import com.es.phoneshop.dao.OrderDao;
import com.es.phoneshop.exceptions.OrderNotFoundException;
import com.es.phoneshop.model.order.Order;

import java.util.List;
import java.util.Optional;

public class ArrayListOrderDao extends ArrayListDao<Order> implements OrderDao {
    private ArrayListOrderDao() {
    }

    private static class SingletonHelper {
        private static final ArrayListOrderDao INSTANCE = new ArrayListOrderDao();
    }

    public static ArrayListOrderDao getInstance() {
        return ArrayListOrderDao.SingletonHelper.INSTANCE;
    }

    @Override
    public Order get(Long id) {
        return Optional.ofNullable(super.get(id))
                .orElseThrow(OrderNotFoundException::new);
    }

    @Override
    public Order getBySecureId(String secureId) {
        return readWriteLock.read(() -> items.stream()
                .filter(order -> order.getSecureId().equals(secureId))
                .findAny()
                .orElseThrow(OrderNotFoundException::new)
        );
    }

    @Override
    public List<Order> getAllOrders() {
        return items;
    }
}
