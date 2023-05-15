package com.es.phoneshop.web;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.services.ProductPriceHistoryService;
import com.es.phoneshop.services.impl.ArrayListProductPriceHistoryService;
import com.es.phoneshop.web.utils.RequestParser;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ProductPriceHistoryPageServlet extends HttpServlet {
    protected ProductPriceHistoryService priceHistoryService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ProductDao productDao = ArrayListProductDao.getInstance();
        priceHistoryService = new ArrayListProductPriceHistoryService(productDao);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final long productId = RequestParser.getIdFromPath(request);
        request.setAttribute("productPriceHistory", priceHistoryService.getPriceHistory(productId));
        request.getRequestDispatcher("/WEB-INF/pages/productPriceHistory.jsp").forward(request, response);
    }
}
