package com.es.phoneshop.web;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductListPageServletTest {
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
    private final ProductListPageServlet servlet = new ProductListPageServlet();
    private static final String QUERY_PARAMETER_NAME = "query";
    private static final String ORDER_PARAMETER_NAME = "order";
    private static final String SORT_PARAMETER_NAME = "sort";
    private static final String PRODUCTS_ATTRIBUTE_NAME = "products";

    @Before
    public void setup() throws ServletException {
        servlet.init(config);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(request).getParameter(QUERY_PARAMETER_NAME);
        verify(request).getParameter(SORT_PARAMETER_NAME);
        verify(request).getParameter(ORDER_PARAMETER_NAME);
        verify(request).setAttribute(eq(PRODUCTS_ATTRIBUTE_NAME), anyList());
        verify(requestDispatcher).forward(request, response);
    }
}
