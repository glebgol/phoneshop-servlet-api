package com.es.phoneshop.services.impl;

import com.es.phoneshop.dao.OrderDao;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.order.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class DefaultOrderServiceTest {
    @Mock
    private Cart cart;
    @Mock
    private Order order;
    private final DefaultOrderService orderService = DefaultOrderService.getInstance();
    private static final BigDecimal TOTAL_COST = new BigDecimal(100);

    @Test
    public void testGetPaymentMethods() {
        assertNotNull(orderService.getPaymentMethods());
    }

    @Test
    public void testGetOrder() {
        when(cart.getTotalCost()).thenReturn(TOTAL_COST);

        Order order = orderService.getOrder(cart);
        BigDecimal subTotal = order.getSubtotal();

        assertEquals(TOTAL_COST, subTotal);
    }

    @Test
    public void testPlaceOrder() {
        orderService.orderDao = mock(OrderDao.class);
        orderService.placeOrder(order, cart);

        verify(order).setSecureId(anyString());
        verify(orderService.orderDao).save(order);
    }
}
