package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.web.utils.RequestParser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class DeleteCartItemServlet extends AbstractServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long productId = RequestParser.getIdFromPath(request);

        Cart cart = cartService.getCart(request.getSession());
        cartService.delete(cart, productId);

        response.sendRedirect(getRedirectUrl(request));
    }

    private String getRedirectUrl(HttpServletRequest request) {
        return request.getContextPath() + "/cart?message=" + SUCCESSFUL_CART_ITEM_REMOVE_MESSAGE;
    }
}
