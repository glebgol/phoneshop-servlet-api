package com.es.phoneshop.services.impl;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.RecentlyViewedProducts;
import com.es.phoneshop.values.Products;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultRecentlyViewedProductServiceTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    private final DefaultRecentlyViewedProductService recentlyViewedProductService = DefaultRecentlyViewedProductService.getInstance();
    private final RecentlyViewedProducts EMPTY_RECENT_PRODUCTS = new RecentlyViewedProducts();
    private static final String RECENTLY_VIEWED_PRODUCT_SESSION_ATTRIBUTE =
            DefaultRecentlyViewedProductService.class.getName();
    private static final Long PRODUCT_1_ID = 1L;
    private static final Product PRODUCT_1 = Products.withId(PRODUCT_1_ID);
    private static final Long PRODUCT_2_ID = 2L;
    private static final Product PRODUCT_2 = Products.withId(PRODUCT_2_ID);
    private static final Long PRODUCT_3_ID = 3L;
    private static final Product PRODUCT_3 = Products.withId(PRODUCT_3_ID);
    private static final Long PRODUCT_4_ID = 4L;
    private static final Product PRODUCT_4 = Products.withId(PRODUCT_4_ID);

    @Before
    public void setUp() {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(RECENTLY_VIEWED_PRODUCT_SESSION_ATTRIBUTE)).thenReturn(EMPTY_RECENT_PRODUCTS);

        recentlyViewedProductService.productDao = mock(ArrayListProductDao.class);
        ProductDao productDaoMock = recentlyViewedProductService.productDao;
        when(productDaoMock.get(PRODUCT_1_ID)).thenReturn(PRODUCT_1);
        when(productDaoMock.get(PRODUCT_2_ID)).thenReturn(PRODUCT_2);
        when(productDaoMock.get(PRODUCT_3_ID)).thenReturn(PRODUCT_3);
        when(productDaoMock.get(PRODUCT_4_ID)).thenReturn(PRODUCT_4);
    }

    @Test
    public void getRecentlyViewedProductsWithoutPreviousHistory() {
        when(session.getAttribute(RECENTLY_VIEWED_PRODUCT_SESSION_ATTRIBUTE)).thenReturn(EMPTY_RECENT_PRODUCTS);

        List<Product> recentProducts = recentlyViewedProductService.getRecentlyViewedProducts(request);

        assertTrue(recentProducts.isEmpty());
    }

    @Test
    public void getRecentlyViewedProductsAfterAddingOneProductToHistory() {
        final int expectedSize = 1;
        when(session.getAttribute(RECENTLY_VIEWED_PRODUCT_SESSION_ATTRIBUTE)).thenReturn(EMPTY_RECENT_PRODUCTS);

        recentlyViewedProductService.addProduct(PRODUCT_1_ID, request);
        List<Product> recentProducts = recentlyViewedProductService.getRecentlyViewedProducts(request);

        assertEquals(expectedSize, recentProducts.size());
    }

    @Test
    public void getRecentlyViewedProductsAfterAddingTwoSameProductToHistory() {
        final int expectedSize = 1;
        recentlyViewedProductService.addProduct(PRODUCT_1_ID, request);
        recentlyViewedProductService.addProduct(PRODUCT_1_ID, request);

        List<Product> recentProducts = recentlyViewedProductService.getRecentlyViewedProducts(request);

        assertEquals(expectedSize, recentProducts.size());
    }

    @Test
    public void getRecentlyViewedProductsAfterAddingThreeProductToHistory() {
        final int expectedSize = 3;
        recentlyViewedProductService.addProduct(PRODUCT_1_ID, request);
        recentlyViewedProductService.addProduct(PRODUCT_2_ID, request);
        recentlyViewedProductService.addProduct(PRODUCT_3_ID, request);

        List<Product> recentProducts = recentlyViewedProductService.getRecentlyViewedProducts(request);

        assertEquals(expectedSize, recentProducts.size());
    }

    @Test
    public void getRecentlyViewedProductsAfterAddingFourProductToHistory() {
        final int expectedSize = 3;
        recentlyViewedProductService.addProduct(PRODUCT_1_ID, request);
        recentlyViewedProductService.addProduct(PRODUCT_2_ID, request);
        recentlyViewedProductService.addProduct(PRODUCT_3_ID, request);
        recentlyViewedProductService.addProduct(PRODUCT_4_ID, request);

        List<Product> recentProducts = recentlyViewedProductService.getRecentlyViewedProducts(request);
        Product actualRecentProduct = getFirstRecentProduct(recentProducts);

        assertEquals(PRODUCT_4, actualRecentProduct);
        assertEquals(expectedSize, recentProducts.size());
    }

    private Product getFirstRecentProduct(List<Product> recentProducts) {
        return recentProducts.get(0);
    }
}
