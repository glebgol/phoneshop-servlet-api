package com.es.phoneshop.dao;

import com.es.phoneshop.model.Product;
import com.es.phoneshop.model.ProductSortingField;
import com.es.phoneshop.model.SortingOrder;

import java.util.List;

public interface ProductDao {
    Product getProduct(Long id);
    List<Product> getAllProducts();
    List<Product> findProducts(String search, ProductSortingField productSortingField, SortingOrder sortingOrder);
    List<Product> findProducts(String search);
    void save(Product product);
    void delete(Long id);
}
