package com.es.phoneshop.web;

import com.es.phoneshop.dao.impl.ArrayListOrderDao;
import com.es.phoneshop.exceptions.OrderNotFoundException;
import com.es.phoneshop.model.order.Order;
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

import static com.es.phoneshop.web.AbstractServlet.ORDER_ATTRIBUTE_NAME;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class OrderOverviewPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ServletConfig config;
    private final OrderOverviewPageServlet servlet = new OrderOverviewPageServlet();
    private static final String EXISTING_PATH_INFO = "/1234";
    private static final String EXISTING_SECURE_ID = "1234";
    private static final String NON_EXISTING_PATH_INFO = "/asd";
    private static final String NON_EXISTING_SECURE_ID = "asd";

    @Before
    public void setup() throws ServletException {
        servlet.init(config);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        servlet.orderDao = mock(ArrayListOrderDao.class);
        when(servlet.orderDao.getBySecureId(EXISTING_SECURE_ID)).thenReturn(new Order());
        when(servlet.orderDao.getBySecureId(NON_EXISTING_SECURE_ID)).thenThrow(OrderNotFoundException.class);
    }

    @Test
    public void testDoGetExistingOrderOverview() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn(EXISTING_PATH_INFO);

        servlet.doGet(request, response);

        verify(request).setAttribute(eq(ORDER_ATTRIBUTE_NAME), any());
        verify(request).getRequestDispatcher(anyString());
        verify(requestDispatcher).forward(request, response);
    }

    @Test(expected = OrderNotFoundException.class)
    public void testDoGetNonExistingOrderOverview() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn(NON_EXISTING_PATH_INFO);

        servlet.doGet(request, response);
    }
}
