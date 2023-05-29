package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class MiniCartServlet extends AbstractServlet {
    private static final String MINI_CART_JSP = "/WEB-INF/pages/minicart.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request.getSession());
        request.setAttribute(CART_ATTRIBUTE_NAME, cart);

        request.getRequestDispatcher(MINI_CART_JSP).include(request, response);
    }
}
