package com.es.phoneshop.dao;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductSortingField;
import com.es.phoneshop.model.SortingOrder;
import com.es.phoneshop.strategies.SearchMethod;

import java.math.BigDecimal;
import java.util.List;

public interface ProductDao {
    Product get(Long id);
    List<Product> getAllProducts();
    List<Product> advancedProductSearch(String search, SearchMethod searchMethod, BigDecimal minPrice, BigDecimal maxPrice);
    List<Product> findProducts(String search, ProductSortingField productSortingField, SortingOrder sortingOrder);
    List<Product> findProducts(String search);
    void save(Product product);
    void delete(Long id);
}
