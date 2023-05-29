package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.services.CartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyString;

@RunWith(MockitoJUnitRunner.class)
public class DeleteCartItemServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private Cart cart;
    private final DeleteCartItemServlet servlet = new DeleteCartItemServlet();
    private static final Long PRODUCT_ID = 1L;
    private static final String PRODUCT_PATH_INFO = "/" + PRODUCT_ID;

    @Before
    public void setUp() {
        when(request.getSession()).thenReturn(session);
        servlet.cartService = mock(CartService.class);
        when(servlet.cartService.getCart(session)).thenReturn(cart);
    }

    @Test
    public void testDoPost() throws IOException {
        when(request.getPathInfo()).thenReturn(PRODUCT_PATH_INFO);

        servlet.doPost(request, response);

        verify(servlet.cartService).getCart(session);
        verify(servlet.cartService).delete(cart, PRODUCT_ID);
        verify(response).sendRedirect(anyString());
    }
}
