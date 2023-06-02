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
    protected final static String ORDER_ATTRIBUTE_NAME = "order";
    protected static final String FIRST_NAME_ATTRIBUTE_NAME = "firstName";
    protected static final String FIRST_NAME_ERROR_MESSAGE = "First name is not valid";
    protected static final String LAST_NAME_ATTRIBUTE_NAME = "lastName";
    protected static final String LAST_NAME_ERROR_MESSAGE = "Last name is not valid";
    protected static final String PHONE_ATTRIBUTE_NAME = "phone";
    protected static final String PHONE_ERROR_MESSAGE = "Is not valid phone number";
    protected static final String DELIVERY_DATE_ATTRIBUTE_NAME = "deliveryDate";
    protected static final String DELIVERY_DATE_ERROR_MESSAGE = "Is not valid date";
    protected static final String DELIVERY_ADDRESS_ATTRIBUTE_NAME = "deliveryAddress";
    protected static final String DELIVERY_ADDRESS_ERROR_MESSAGE = "Is not valid delivery address";
    protected static final String PAYMENT_METHOD_ATTRIBUTE_NAME = "paymentMethod";
    protected static final String PAYMENT_METHOD_ERROR_MESSAGE = "Payment method is required";
    protected static final String PAYMENT_METHODS_ATTRIBUTE_NAME = "paymentMethods";
    protected static final String EMPTY_CART_JSP = "/WEB-INF/pages/error/emptyCart.jsp";

    @Override
    public void init(ServletConfig config) throws ServletException {
        cartService = HttpSessionCartService.getInstance();
        super.init(config);
    }

    protected static String getQuantityErrorMessage(Exception e) {
        if (e instanceof OutOfStockException) {
            return OUT_OF_STOCK_ERROR_MESSAGE + ((OutOfStockException) e).getAvailableStock();
        } else {
            return QUANTITY_ERROR_MESSAGE;
        }
    }
}
