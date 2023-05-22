package com.es.phoneshop.web;

import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.services.CartService;
import com.es.phoneshop.services.impl.HttpSessionCartService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;

public class AbstractServlet extends HttpServlet {
    protected CartService cartService;
    protected static final String CART_ATTRIBUTE_NAME = "cart";
    protected static final String PRODUCT_ID_ATTRIBUTE_NAME = "productId";
    protected static final String QUANTITY_ATTRIBUTE_NAME = "quantity";
    protected static final String ERRORS_ATTRIBUTE_NAME = "errors";
    protected static final String SUCCESSFUL_CART_UPDATE_MESSAGE = "Cart updated successfully";
    protected static final String QUANTITY_ERROR_MESSAGE = "Quantity should be integer number greater than zero!";
    protected static final String OUT_OF_STOCK_ERROR_MESSAGE = "Not enough stock, available only ";
    protected static final String SUCCESSFUL_CART_ITEM_REMOVE_MESSAGE = "Cart item removed successfully";
    protected static final String PRODUCT_ATTRIBUTE_NAME = "product";
    protected static final String ERROR_MESSAGE_ATTRIBUTE_NAME = "error";
    protected static final String SUCCESS_MESSAGE_ATTRIBUTE_NAME = "message";
    protected static final String SUCCESS_MESSAGE = "Product added to cart";
    protected static final String RECENTLY_VIEWED_PRODUCTS_ATTRIBUTE_NAME = "recentlyViewedProducts";
    protected static final String QUERY_PARAMETER_NAME = "query";
    protected static final String SORT_PARAMETER_NAME = "sort";
    protected static final String ORDER_PARAMETER_NAME = "order";
    protected static final String PRODUCTS_ATTRIBUTE_NAME = "products";
    protected final static String PRODUCT_PRICE_HISTORY_ATTRIBUTE_NAME = "productPriceHistory";

    @Override
    public void init(ServletConfig config) throws ServletException {
        cartService = HttpSessionCartService.getInstance();
        super.init(config);
    }

    protected static String getQuantityErrorMessage(Exception e) {
        if (e instanceof OutOfStockException) {
            return OUT_OF_STOCK_ERROR_MESSAGE + ((OutOfStockException) e).getAvailableStock();
        }
        else {
            return QUANTITY_ERROR_MESSAGE;
        }
    }
}
