package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.services.CartService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.anyString;

@RunWith(MockitoJUnitRunner.class)
public class CartPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private Cart cart;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ServletConfig config;
    private final CartPageServlet servlet = new CartPageServlet();
    private static final String CART_JSP = "/WEB-INF/pages/cart.jsp";
    private static final String CART_ATTRIBUTE_NAME = "cart";
    private static final String PRODUCT_ID_ATTRIBUTE_NAME = "productId";
    private static final String QUANTITY_ATTRIBUTE_NAME = "quantity";
    private static final String[] PRODUCT_IDS = {"1", "2", "3"};
    private static final String[] QUANTITIES = {"1", "1", "1"};
    private static final String[] NOT_VALID_QUANTITIES = {"ff", "1", "1"};
    private static final String ERROR_MESSAGE_ATTRIBUTE_NAME = "errors";

    @Before
    public void setUp() throws ServletException {
        servlet.init(config);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        servlet.cartService = mock(CartService.class);
        when(servlet.cartService.getCart(session)).thenReturn(cart);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(request).getSession();
        verify(request).setAttribute(eq(CART_ATTRIBUTE_NAME), any(Cart.class));
        verify(request).getRequestDispatcher(CART_JSP);
    }

    @Test
    public void doPostResponseSendRedirect() throws ServletException, IOException {
        when(request.getParameterValues(PRODUCT_ID_ATTRIBUTE_NAME)).thenReturn(PRODUCT_IDS);
        when(request.getParameterValues(QUANTITY_ATTRIBUTE_NAME)).thenReturn(QUANTITIES);
        when(request.getLocale()).thenReturn(Locale.US);

        servlet.doPost(request, response);

        verify(response).sendRedirect(anyString());
    }

    @Test
    public void doPostNotValidQuantities() throws ServletException, IOException {
        when(request.getParameterValues(PRODUCT_ID_ATTRIBUTE_NAME)).thenReturn(PRODUCT_IDS);
        when(request.getParameterValues(QUANTITY_ATTRIBUTE_NAME)).thenReturn(NOT_VALID_QUANTITIES);
        when(request.getLocale()).thenReturn(Locale.US);

        servlet.doPost(request, response);

        verify(request).setAttribute(eq(ERROR_MESSAGE_ATTRIBUTE_NAME), anyMap());
    }
}
