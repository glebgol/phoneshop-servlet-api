package com.es.phoneshop.web;

import com.es.phoneshop.dao.OrderDao;
import com.es.phoneshop.dao.impl.ArrayListOrderDao;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.es.phoneshop.web.utils.RequestParser.getSecureIdFromPath;

public class OrderOverviewPageServlet extends AbstractServlet {
    protected OrderDao orderDao;
    protected static final String ORDER_OVERVIEW_JSP = "/WEB-INF/pages/orderOverview.jsp";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        orderDao = ArrayListOrderDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(ORDER_ATTRIBUTE_NAME, orderDao.getBySecureId(getSecureIdFromPath(request)));
        request.getRequestDispatcher(ORDER_OVERVIEW_JSP).forward(request, response);
    }
}
