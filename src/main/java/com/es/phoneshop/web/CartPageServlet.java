package com.es.phoneshop.web;

import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.services.NumberParser;
import com.es.phoneshop.services.impl.DefaultNumberParser;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CartPageServlet extends AbstractServlet {
    protected NumberParser numberParser;
    private static final String CART_JSP = "/WEB-INF/pages/cart.jsp";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        numberParser = DefaultNumberParser.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request.getSession());

        request.setAttribute(CART_ATTRIBUTE_NAME, cart);

        request.getRequestDispatcher(CART_JSP).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<Long, String> errors = new HashMap<>();
        updateCart(request, errors);
        if (errors.isEmpty()) {
            response.sendRedirect(getRedirectUrl(request));
        } else {
            request.setAttribute(ERRORS_ATTRIBUTE_NAME, errors);
            doGet(request, response);
        }
    }

    private void updateCart(HttpServletRequest request, Map<Long, String> errors) {
        Locale locale = request.getLocale();
        Cart cart = cartService.getCart(request.getSession());
        String[] productIds = request.getParameterValues(PRODUCT_ID_ATTRIBUTE_NAME);
        String[] quantities = request.getParameterValues(QUANTITY_ATTRIBUTE_NAME);
        if (productIds == null || quantities == null) {
            return;
        }
        for (int i = 0; i < productIds.length; i++) {
            Long productId = Long.parseLong(productIds[i]);
            try {
                int quantity = numberParser.parseNumber(locale, quantities[i]);
                cartService.update(cart, productId, quantity);
            } catch (OutOfStockException | ParseException | NumberFormatException  e) {
                errors.put(productId, getQuantityErrorMessage(e));
            }
        }
    }

    private String getRedirectUrl(HttpServletRequest request) {
        return request.getContextPath() + "/cart?message=" + SUCCESSFUL_CART_UPDATE_MESSAGE;
    }
}
