package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.services.CartService;
import jakarta.servlet.RequestDispatcher;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.eq;

@RunWith(MockitoJUnitRunner.class)
public class MiniCartServletTest {
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
    private final MiniCartServlet servlet = new MiniCartServlet();
    private static final String CART_ATTRIBUTE_NAME = "cart";
    private static final String MINI_CART_JSP = "/WEB-INF/pages/minicart.jsp";

    @Before
    public void setUp() {
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        servlet.cartService = mock(CartService.class);
        when(servlet.cartService.getCart(session)).thenReturn(cart);
    }

    @Test
    public void testDotGet() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(servlet.cartService).getCart(session);
        verify(request).setAttribute(eq(CART_ATTRIBUTE_NAME), any(Cart.class));
        verify(request).getRequestDispatcher(MINI_CART_JSP);
        verify(requestDispatcher).include(request, response);
    }
}
