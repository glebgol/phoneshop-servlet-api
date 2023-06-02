package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.services.impl.DefaultOrderService;
import com.es.phoneshop.services.impl.HttpSessionCartService;
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
import java.time.LocalDate;
import java.util.List;

import static com.es.phoneshop.web.AbstractServlet.EMPTY_CART_JSP;
import static com.es.phoneshop.web.AbstractServlet.ORDER_ATTRIBUTE_NAME;
import static com.es.phoneshop.web.AbstractServlet.PAYMENT_METHOD_ATTRIBUTE_NAME;
import static com.es.phoneshop.web.AbstractServlet.PAYMENT_METHODS_ATTRIBUTE_NAME;
import static com.es.phoneshop.web.AbstractServlet.PHONE_ATTRIBUTE_NAME;
import static com.es.phoneshop.web.AbstractServlet.FIRST_NAME_ATTRIBUTE_NAME;
import static com.es.phoneshop.web.AbstractServlet.LAST_NAME_ATTRIBUTE_NAME;
import static com.es.phoneshop.web.AbstractServlet.ERRORS_ATTRIBUTE_NAME;
import static com.es.phoneshop.web.AbstractServlet.DELIVERY_DATE_ATTRIBUTE_NAME;
import static com.es.phoneshop.web.AbstractServlet.DELIVERY_ADDRESS_ATTRIBUTE_NAME;
import static com.es.phoneshop.web.CheckoutPageServlet.CHECKOUT_JSP;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class CheckoutPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ServletConfig config;
    @Mock
    private HttpSession session;
    private final CheckoutPageServlet servlet = new CheckoutPageServlet();
    private static final List<CartItem> NOT_EMPTY_CART_ITEM_LIST = List.of(new CartItem(null, 0));
    private static final String VALID_PHONE = "+375331445587";
    private static final String NOT_VALID_PHONE = "rvmmkvkrm";
    private static final String VALID_FIRST_NAME = "Gleb";
    private static final String VALID_LAST_NAME = "Golovnev";
    private static final String PAYMENT_METHOD = "CACHE";
    private static final String VALID_DATE = LocalDate.now().toString();
    private static final String VALID_ADDRESS = "Belarus";

    @Before
    public void setup() throws ServletException {
        servlet.init(config);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        servlet.cartService = mock(HttpSessionCartService.class);
        servlet.orderService = mock(DefaultOrderService.class);
    }

    @Test
    public void testDoGetEmptyCartItems() throws ServletException, IOException {
        when(servlet.cartService.getCart(session)).thenReturn(new Cart());

        servlet.doGet(request, response);

        verify(request).getRequestDispatcher(EMPTY_CART_JSP);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        Cart cart = new Cart();
        cart.setItems(NOT_EMPTY_CART_ITEM_LIST);
        when(servlet.cartService.getCart(session)).thenReturn(cart);

        servlet.doGet(request, response);

        verify(request).setAttribute(eq(ORDER_ATTRIBUTE_NAME), any());
        verify(request).setAttribute(eq(PAYMENT_METHODS_ATTRIBUTE_NAME), any());
        verify(request).getRequestDispatcher(CHECKOUT_JSP);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        Cart cart = new Cart();
        cart.setItems(NOT_EMPTY_CART_ITEM_LIST);
        when(servlet.cartService.getCart(session)).thenReturn(cart);
        when(request.getParameter(PHONE_ATTRIBUTE_NAME)).thenReturn(VALID_PHONE);
        when(request.getParameter(FIRST_NAME_ATTRIBUTE_NAME)).thenReturn(VALID_FIRST_NAME);
        when(request.getParameter(LAST_NAME_ATTRIBUTE_NAME)).thenReturn(VALID_LAST_NAME);
        when(request.getParameter(PAYMENT_METHOD_ATTRIBUTE_NAME)).thenReturn(PAYMENT_METHOD);
        when(request.getParameter(DELIVERY_DATE_ATTRIBUTE_NAME)).thenReturn(VALID_DATE);
        when(request.getParameter(DELIVERY_ADDRESS_ATTRIBUTE_NAME)).thenReturn(VALID_ADDRESS);
        when(servlet.orderService.getOrder(cart)).thenReturn(new Order());

        servlet.doPost(request, response);

        verify(response).sendRedirect(any());
    }

    @Test
    public void testDoPostNotValidParameters() throws ServletException, IOException {
        Cart cart = new Cart();
        cart.setItems(NOT_EMPTY_CART_ITEM_LIST);
        when(servlet.cartService.getCart(session)).thenReturn(cart);
        when(request.getParameter(PHONE_ATTRIBUTE_NAME)).thenReturn(NOT_VALID_PHONE);
        when(request.getParameter(FIRST_NAME_ATTRIBUTE_NAME)).thenReturn(VALID_FIRST_NAME);
        when(request.getParameter(LAST_NAME_ATTRIBUTE_NAME)).thenReturn(VALID_LAST_NAME);
        when(request.getParameter(PAYMENT_METHOD_ATTRIBUTE_NAME)).thenReturn(PAYMENT_METHOD);
        when(request.getParameter(DELIVERY_DATE_ATTRIBUTE_NAME)).thenReturn(VALID_DATE);
        when(request.getParameter(DELIVERY_ADDRESS_ATTRIBUTE_NAME)).thenReturn(VALID_ADDRESS);
        when(servlet.orderService.getOrder(cart)).thenReturn(new Order());

        servlet.doPost(request, response);

        verify(request).setAttribute(eq(ERRORS_ATTRIBUTE_NAME), anyMap());
    }
}
