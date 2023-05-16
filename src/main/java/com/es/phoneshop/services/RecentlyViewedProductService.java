package com.es.phoneshop.services;

import com.es.phoneshop.model.product.Product;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface RecentlyViewedProductService {
    List<Product> getRecentlyViewedProducts(HttpServletRequest request);
    void addProduct(Long productId, HttpServletRequest request);
}
