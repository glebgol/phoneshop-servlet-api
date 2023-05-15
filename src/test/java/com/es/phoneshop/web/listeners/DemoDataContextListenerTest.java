package com.es.phoneshop.web.listeners;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DemoDataContextListenerTest {
    @Mock
    private ServletContextEvent sce;
    @Mock
    private ServletContext servletContext;
    private final DemoDataContextListener contextListener = new DemoDataContextListener();

    @Before
    public void setUp() {
        when(sce.getServletContext()).thenReturn(servletContext);
    }

    @Test
    public void contextInitialized() {
        contextListener.contextInitialized(sce);

        verify(sce).getServletContext();
        verify(servletContext).getInitParameter("insertDemoData");
    }
}
