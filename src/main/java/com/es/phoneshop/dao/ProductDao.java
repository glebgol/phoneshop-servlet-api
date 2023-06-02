package com.es.phoneshop.dao;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductSortingField;
import com.es.phoneshop.model.SortingOrder;

import java.util.List;

public interface ProductDao {
    Product get(Long id);
    List<Product> getAllProducts();
    List<Product> findProducts(String search, ProductSortingField productSortingField, SortingOrder sortingOrder);
    List<Product> findProducts(String search);
    void save(Product product);
    void delete(Long id);
}
