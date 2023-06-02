package com.es.phoneshop.services.impl;

import com.es.phoneshop.dao.OrderDao;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListOrderDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.PaymentMethod;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.services.CartService;
import com.es.phoneshop.services.OrderService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class DefaultOrderService implements OrderService {
    protected OrderDao orderDao;
    protected CartService cartService;
    protected ProductDao productDao;

    private DefaultOrderService() {
        orderDao = ArrayListOrderDao.getInstance();
        cartService = HttpSessionCartService.getInstance();
        productDao = ArrayListProductDao.getInstance();
    }

    private static final class SingletonHolder {
        private static final DefaultOrderService INSTANCE = new DefaultOrderService();
    }

    public static DefaultOrderService getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public synchronized Order getOrder(Cart cart) {
        BigDecimal deliveryCost = calculateDeliveryCost();
        BigDecimal subTotal = cart.getTotalCost();

        return Order.builder()
                .orderItems(cart.getItems())
                .deliveryCost(deliveryCost)
                .subTotal(subTotal)
                .totalCost(subTotal.add(deliveryCost))
                .build();
    }

    @Override
    public List<PaymentMethod> getPaymentMethods() {
        return Arrays.asList(PaymentMethod.values());
    }

    @Override
    public synchronized void placeOrder(Order order, Cart cart) {
        cart.getItems().forEach(this::updateStock);
        order.setSecureId(UUID.randomUUID().toString());
        orderDao.save(order);
        cartService.clear(cart);
    }

    private void updateStock(CartItem item) {
        Product product = item.getProduct();
        productDao.get(product.getId()).setStock(product.getStock() - item.getQuantity());
    }

    private BigDecimal calculateDeliveryCost() {
        return new BigDecimal(15);
    }
}
