package com.es.phoneshop.web.utils;

import jakarta.servlet.http.HttpServletRequest;

public class RequestParser {
    private static final String PRODUCT_ID_ATTRIBUTE_NAME = "productId";

    public static Long getIdFromPath(HttpServletRequest request) {
        return Long.parseLong(request.getPathInfo().substring(1));
    }

    public static Long getProductIdParameter(HttpServletRequest request) {
        return Long.parseLong(request.getParameter(PRODUCT_ID_ATTRIBUTE_NAME));
    }
}
