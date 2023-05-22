package com.es.phoneshop.web;

import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.services.impl.DefaultNumberParser;
import com.es.phoneshop.services.impl.DefaultRecentlyViewedProductService;
import com.es.phoneshop.services.impl.HttpSessionCartService;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.contains;
import static org.mockito.Mockito.doThrow;

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
    private static final String QUANTITY_ATTRIBUTE_NAME = "quantity";
    private static final String NOT_VALID_QUANTITY_STRING = "-100";
    private static final String OUT_OF_STOCK_QUANTITY_STRING = "1000";
    private static final String QUANTITY_STRING = "2";
    private static final int QUANTITY = 2;
    private static final int OUT_OF_STOCK_QUANTITY = 1000;
    private static final String PRODUCT_ATTRIBUTE_NAME = "product";
    private static final String QUANTITY_ERROR_MESSAGE = "Quantity should be integer number greater than zero!";
    private static final String ERROR_MESSAGE_ATTRIBUTE_NAME = "error";
    private static final String OUT_OF_STOCK_ERROR_MESSAGE = "Not enough stock, available only ";
    private static final String RECENTLY_VIEWED_PRODUCTS_ATTRIBUTE_NAME = "recentlyViewedProducts";
    private static final List<Product> RECENTLY_VIEWED_PRODUCTS = new ArrayList<>();
    private static final Locale LOCALE = Locale.US;

    @Before
    public void setUp() throws ServletException {
        servlet.init(config);

        servlet.cartService = mock(HttpSessionCartService.class);
        servlet.recentlyViewedProductService = mock(DefaultRecentlyViewedProductService.class);
        servlet.productDao = mock(ArrayListProductDao.class);
        servlet.numberParser = mock(DefaultNumberParser.class);

        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getLocale()).thenReturn(LOCALE);
        when(servlet.recentlyViewedProductService.getRecentlyViewedProducts(request)).thenReturn(RECENTLY_VIEWED_PRODUCTS);
    }

    @Test
    public void testDoGetExistentProductDetails() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn(EXISTING_PRODUCT_PATH_INFO);
        when(servlet.productDao.getProduct(EXISTING_PRODUCT_ID)).thenReturn(EXISTING_PRODUCT);
        when(servlet.recentlyViewedProductService.getRecentlyViewedProducts(request)).thenReturn(RECENTLY_VIEWED_PRODUCTS);

        servlet.doGet(request, response);

        verify(request).getPathInfo();
        verify(request).setAttribute(eq(PRODUCT_ATTRIBUTE_NAME), any(Product.class));
        verify(servlet.recentlyViewedProductService).addProduct(anyLong(), eq(request));
        verify(request).setAttribute(RECENTLY_VIEWED_PRODUCTS_ATTRIBUTE_NAME, RECENTLY_VIEWED_PRODUCTS);
        verify(requestDispatcher).forward(request, response);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testDoGetNonExistentProductDetails() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn(NON_EXISTING_PRODUCT_PATH_INFO);
        when(servlet.productDao.getProduct(NON_EXISTING_PRODUCT_ID)).thenThrow(ProductNotFoundException.class);

        servlet.doGet(request, response);

        verify(request).getPathInfo();
    }

    @Test
    public void testDoPostSendRedirect() throws ServletException, IOException, ParseException {
        when(request.getParameter(QUANTITY_ATTRIBUTE_NAME)).thenReturn(QUANTITY_STRING);
        when(request.getPathInfo()).thenReturn(EXISTING_PRODUCT_PATH_INFO);
        when(servlet.numberParser.parseNumber(LOCALE, QUANTITY_STRING)).thenReturn(QUANTITY);

        servlet.doPost(request, response);

        verify(response).sendRedirect(anyString());
    }

    @Test
    public void testDoPostNotValidQuantity() throws ServletException, IOException, ParseException {
        when(request.getParameter(QUANTITY_ATTRIBUTE_NAME)).thenReturn(NOT_VALID_QUANTITY_STRING);
        servlet.numberParser = mock(DefaultNumberParser.class);
        when(request.getPathInfo()).thenReturn(EXISTING_PRODUCT_PATH_INFO);
        when(servlet.numberParser.parseNumber(LOCALE, NOT_VALID_QUANTITY_STRING)).thenThrow(NumberFormatException.class);

        servlet.doPost(request, response);

        verify(request).setAttribute(ERROR_MESSAGE_ATTRIBUTE_NAME, QUANTITY_ERROR_MESSAGE);
    }

    @Test
    public void testDoPostOutOfStock() throws ServletException, IOException, OutOfStockException, ParseException {
        when(request.getParameter(QUANTITY_ATTRIBUTE_NAME)).thenReturn(OUT_OF_STOCK_QUANTITY_STRING);
        when(request.getPathInfo()).thenReturn(EXISTING_PRODUCT_PATH_INFO);
        when(servlet.numberParser.parseNumber(LOCALE, OUT_OF_STOCK_QUANTITY_STRING)).thenReturn(OUT_OF_STOCK_QUANTITY);
        doThrow(OutOfStockException.class).when(servlet.cartService).add(any(), any(), eq(OUT_OF_STOCK_QUANTITY));

        servlet.doPost(request, response);

        verify(request).setAttribute(eq(ERROR_MESSAGE_ATTRIBUTE_NAME), contains(OUT_OF_STOCK_ERROR_MESSAGE));
    }
}
