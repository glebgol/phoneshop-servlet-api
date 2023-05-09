package com.es.phoneshop.web.utils;

import jakarta.servlet.http.HttpServletRequest;

public class RequestParser {
    public static Long getIdFromPath(HttpServletRequest request) {
        return Long.parseLong(request.getPathInfo().substring(1));
    }
}
