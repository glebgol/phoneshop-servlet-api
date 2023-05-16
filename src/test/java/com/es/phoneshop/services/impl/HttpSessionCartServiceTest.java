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

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionCartServiceTest {
    @Mock
    private Cart cart;
    private final HttpSessionCartService httpSessionCartService = HttpSessionCartService.getInstance();
    private final Long PRODUCT_ID = 1L;
    private final int PRODUCT_STOCK = 10;
    private final Product PRODUCT = Products.withIdAndStock(PRODUCT_ID, PRODUCT_STOCK);
    private final List<CartItem> EMPTY_CART_ITEMS = new ArrayList<>();
    private final List<CartItem> CART_ITEMS = List.of(new CartItem(PRODUCT, 1));

    @Before
    public void setUp() {
        httpSessionCartService.productDao = mock(ArrayListProductDao.class);
        when(httpSessionCartService.productDao.getProduct(PRODUCT_ID)).thenReturn(PRODUCT);
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

        verify(cart, times(2)).getItems();
    }
}
