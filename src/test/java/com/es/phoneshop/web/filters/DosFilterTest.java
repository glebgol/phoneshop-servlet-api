package com.es.phoneshop.web.filters;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class DosFilterTest {
    @Mock
    private ServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;
    @Mock
    private FilterConfig config;
    private final DosFilter filter = new DosFilter();
    private static final int REQUESTS_COUNT = 22;
    private static final String IP = "127.0.0.1";

    @Before
    public void setUp() throws ServletException {
        filter.init(config);
        when(request.getRemoteAddr()).thenReturn(IP);
    }

    @Test
    public void manyRequests() throws ServletException, IOException {
        for (int i = 0; i < REQUESTS_COUNT; i++) {
            filter.doFilter(request, response, filterChain);
        }

        verify(response).setStatus(429);
        verify(filterChain, times(REQUESTS_COUNT - 1)).doFilter(request, response);
    }
}
