package com.es.phoneshop.web;

import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.Product;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ServletConfig config;
    private final ProductDetailsPageServlet servlet = new ProductDetailsPageServlet();
    private static final Product EXISTING_PRODUCT = new Product();
    private static final Long EXISTING_PRODUCT_ID = 1L;
    private static final Long NON_EXISTING_PRODUCT_ID = 100L;
    private static final String EXISTING_PRODUCT_PATH_INFO = "/" + EXISTING_PRODUCT_ID;
    private static final String NON_EXISTING_PRODUCT_PATH_INFO = "/" + NON_EXISTING_PRODUCT_ID;

    @Before
    public void setup() throws ServletException {
        servlet.init(config);
        setUpProductDaoMock(servlet);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGetExistentProductDetails() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn(EXISTING_PRODUCT_PATH_INFO);

        servlet.doGet(request, response);

        verify(request).getPathInfo();
        verify(request).setAttribute(eq("product"), any(Product.class));
        verify(requestDispatcher).forward(request, response);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testDoGetNonExistentProductDetails() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn(NON_EXISTING_PRODUCT_PATH_INFO);

        servlet.doGet(request, response);

        verify(request).getPathInfo();
    }

    private void setUpProductDaoMock(ProductDetailsPageServlet servlet) {
        servlet.productDao = mock(ArrayListProductDao.class);
        when(servlet.productDao.getProduct(EXISTING_PRODUCT_ID)).thenReturn(EXISTING_PRODUCT);
        when(servlet.productDao.getProduct(NON_EXISTING_PRODUCT_ID)).thenThrow(ProductNotFoundException.class);
    }
}
