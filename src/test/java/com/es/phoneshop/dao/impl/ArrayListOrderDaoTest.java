package com.es.phoneshop.dao.impl;

import com.es.phoneshop.exceptions.OrderNotFoundException;
import com.es.phoneshop.model.order.Order;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ArrayListOrderDaoTest {
    private final ArrayListOrderDao orderDao = ArrayListOrderDao.getInstance();

    @After
    public void tearDown() {
        orderDao.clear();
    }

    @Test
    public void saveOrder() {
        Order order = new Order();
        int expectedOrderCount = 1;

        orderDao.save(order);
        int actualOrderCount = orderDao.getAllOrders().size();

        assertEquals(expectedOrderCount, actualOrderCount);
    }

    @Test(expected = OrderNotFoundException.class)
    public void getOrderNonExistentIdThrowsException() {
        long id = -100L;
        orderDao.get(id);
    }
}
