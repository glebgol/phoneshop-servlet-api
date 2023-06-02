package com.es.phoneshop.web;

import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.services.RecentlyViewedProductService;
import com.es.phoneshop.services.impl.DefaultRecentlyViewedProductService;
import com.es.phoneshop.services.NumberParser;
import com.es.phoneshop.services.impl.DefaultNumberParser;
import com.es.phoneshop.web.utils.RequestParser;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.ParseException;

public class ProductDetailsPageServlet extends AbstractServlet {
    protected ArrayListProductDao productDao;
    protected NumberParser numberParser;
    protected RecentlyViewedProductService recentlyViewedProductService;
    protected static final String PRODUCT_DETAILS_JSP = "/WEB-INF/pages/productDetails.jsp";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
        numberParser = DefaultNumberParser.getInstance();
        recentlyViewedProductService = DefaultRecentlyViewedProductService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final long productId = RequestParser.getIdFromPath(request);
        request.setAttribute(PRODUCT_ATTRIBUTE_NAME, productDao.get(productId));
        request.setAttribute(CART_ATTRIBUTE_NAME, cartService.getCart(request.getSession()));

        recentlyViewedProductService.addProduct(productId, request);
        request.setAttribute(RECENTLY_VIEWED_PRODUCTS_ATTRIBUTE_NAME, recentlyViewedProductService.getRecentlyViewedProducts(request));

        request.getRequestDispatcher(PRODUCT_DETAILS_JSP).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String quantityString = request.getParameter(QUANTITY_ATTRIBUTE_NAME);
        Long productId = RequestParser.getIdFromPath(request);
        try {
            int quantity = numberParser.parseNumber(request.getLocale(), quantityString);
            cartService.add(cartService.getCart(request.getSession()), productId, quantity);
        } catch (OutOfStockException | ParseException | NumberFormatException e) {
            request.setAttribute(ERROR_MESSAGE_ATTRIBUTE_NAME, getQuantityErrorMessage(e));
            doGet(request, response);
            return;
        }

        response.sendRedirect(getRedirectUrl(request, productId));
    }

    private String getRedirectUrl(HttpServletRequest request, Long productId) {
        return request.getContextPath() + "/products/" + productId + "?" + SUCCESS_MESSAGE_ATTRIBUTE_NAME + "=" + SUCCESS_MESSAGE;
    }
}
