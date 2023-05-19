package com.es.phoneshop.web;

import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.services.CartService;
import com.es.phoneshop.services.RecentlyViewedProductService;
import com.es.phoneshop.services.impl.DefaultRecentlyViewedProductService;
import com.es.phoneshop.services.impl.HttpSessionCartService;
import com.es.phoneshop.parser.NumberParser;
import com.es.phoneshop.parser.impl.DefaultNumberParser;
import com.es.phoneshop.web.utils.RequestParser;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {
    protected ArrayListProductDao productDao;
    protected CartService cartService;
    protected NumberParser numberParser;
    protected RecentlyViewedProductService recentlyViewedProductService;
    private static final String PRODUCT_ATTRIBUTE_NAME = "product";
    private static final String CART_ATTRIBUTE_NAME = "cart";
    private static final String QUANTITY_ATTRIBUTE_NAME = "quantity";
    private static final String QUANTITY_ERROR_MESSAGE = "Quantity should be integer number greater than zero!";
    private static final String ERROR_MESSAGE_ATTRIBUTE_NAME = "error";
    private static final String OUT_OF_STOCK_ERROR_MESSAGE = "Not enough stock, available only ";
    private static final String SUCCESS_MESSAGE_ATTRIBUTE_NAME = "message";
    private static final String SUCCESS_MESSAGE = "Product added to cart";
    private static final String RECENTLY_VIEWED_PRODUCTS_ATTRIBUTE_NAME = "recentlyViewedProducts";
    private static final String PRODUCT_DETAILS_JSP = "/WEB-INF/pages/productDetails.jsp";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        productDao = ArrayListProductDao.getInstance();
        cartService = HttpSessionCartService.getInstance();
        numberParser = DefaultNumberParser.getInstance();
        recentlyViewedProductService = DefaultRecentlyViewedProductService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final long productId = RequestParser.getIdFromPath(request);
        request.setAttribute(PRODUCT_ATTRIBUTE_NAME, productDao.getProduct(productId));
        request.setAttribute(CART_ATTRIBUTE_NAME, cartService.getCart(request));

        recentlyViewedProductService.addProduct(productId, request);
        request.setAttribute(RECENTLY_VIEWED_PRODUCTS_ATTRIBUTE_NAME, recentlyViewedProductService.getRecentlyViewedProducts(request));

        request.getRequestDispatcher(PRODUCT_DETAILS_JSP).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String quantityString = request.getParameter(QUANTITY_ATTRIBUTE_NAME);
        if (!numberParser.isValidQuantity(request.getLocale(), quantityString)) {
            request.setAttribute(ERROR_MESSAGE_ATTRIBUTE_NAME, QUANTITY_ERROR_MESSAGE);
            doGet(request, response);
            return;
        }

        Long productId = RequestParser.getIdFromPath(request);
        int quantity = numberParser.parseNumber(request.getLocale(), quantityString);
        try {
            cartService.add(cartService.getCart(request), productId, quantity);
        } catch (OutOfStockException e) {
            request.setAttribute(ERROR_MESSAGE_ATTRIBUTE_NAME, OUT_OF_STOCK_ERROR_MESSAGE + e.getAvailableStock());
            doGet(request, response);
            return;
        }

        response.sendRedirect(getRedirectUrl(request, productId));
    }

    private String getRedirectUrl(HttpServletRequest request, Long productId) {
        return request.getContextPath() + "/products/" + productId + "?" + SUCCESS_MESSAGE_ATTRIBUTE_NAME + "=" + SUCCESS_MESSAGE;
    }
}
