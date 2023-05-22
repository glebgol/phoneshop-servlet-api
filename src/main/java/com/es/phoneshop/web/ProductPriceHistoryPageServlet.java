package com.es.phoneshop.web;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.services.ProductPriceHistoryService;
import com.es.phoneshop.services.impl.ArrayListProductPriceHistoryService;
import com.es.phoneshop.web.utils.RequestParser;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ProductPriceHistoryPageServlet extends AbstractServlet {
    protected ProductPriceHistoryService priceHistoryService;
    private static final String PRODUCT_PRICE_HISTORY_JSP = "/WEB-INF/pages/productPriceHistory.jsp";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ProductDao productDao = ArrayListProductDao.getInstance();
        priceHistoryService = new ArrayListProductPriceHistoryService(productDao);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final long productId = RequestParser.getIdFromPath(request);
        request.setAttribute(PRODUCT_PRICE_HISTORY_ATTRIBUTE_NAME, priceHistoryService.getPriceHistory(productId));
        request.getRequestDispatcher(PRODUCT_PRICE_HISTORY_JSP).forward(request, response);
    }
}
