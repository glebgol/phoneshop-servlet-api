package com.es.phoneshop.services.impl;

import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.values.Products;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.anyInt;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionCartServiceTest {
    @Mock
    private Cart cart;
    private final HttpSessionCartService httpSessionCartService = HttpSessionCartService.getInstance();
    private static final Long PRODUCT_ID = 1L;
    private static final int PRODUCT_STOCK = 10;
    private static final int OUT_OF_STOCK_QUANTITY = 1000;
    private static final Product PRODUCT = Products.withIdAndStock(PRODUCT_ID, PRODUCT_STOCK);
    private static final List<CartItem> EMPTY_CART_ITEMS = new ArrayList<>();
    private static final List<CartItem> CART_ITEMS = List.of(new CartItem(PRODUCT, 1));

    @Before
    public void setUp() {
        httpSessionCartService.productDao = mock(ArrayListProductDao.class);
        when(httpSessionCartService.productDao.get(PRODUCT_ID)).thenReturn(PRODUCT);
    }

    @Test(expected = OutOfStockException.class)
    public void addProductWithQuantityMoreThanStockToEmptyCart() throws OutOfStockException {
        int quantity = PRODUCT_STOCK + 1;
        when(cart.getItems()).thenReturn(EMPTY_CART_ITEMS);

        httpSessionCartService.add(cart, PRODUCT_ID, quantity);
    }

    @Test(expected = OutOfStockException.class)
    public void addProductWithQuantityMoreThanStockToCarContainingThisProduct() throws OutOfStockException {
        int quantity = 100;
        when(cart.getItems()).thenReturn(CART_ITEMS);

        httpSessionCartService.add(cart, PRODUCT_ID, quantity);
    }

    @Test
    public void addProductToEmptyCart() throws OutOfStockException {
        int quantity = 1;
        when(cart.getItems()).thenReturn(EMPTY_CART_ITEMS);

        httpSessionCartService.add(cart, PRODUCT_ID, quantity);

        verify(cart, times(4)).getItems();
    }

    @Test(expected = OutOfStockException.class)
    public void updateCart() throws OutOfStockException {
        when(cart.getItems()).thenReturn(CART_ITEMS);

        httpSessionCartService.update(cart, PRODUCT_ID, OUT_OF_STOCK_QUANTITY);
    }

    @Test
    public void deleteCartItem() {
        httpSessionCartService.delete(cart, PRODUCT_ID);

        verify(cart, times(3)).getItems();
        verify(cart).setTotalQuantity(anyInt());
    }
}
