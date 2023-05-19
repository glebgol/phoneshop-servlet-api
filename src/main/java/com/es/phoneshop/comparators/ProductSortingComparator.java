package com.es.phoneshop.comparators;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductSortingField;
import com.es.phoneshop.model.SortingOrder;

import java.util.Comparator;

public class ProductSortingComparator implements Comparator<Product> {
    private final ProductFieldComparator productFieldComparator;

    public ProductSortingComparator(ProductSortingField productSortingField, SortingOrder sortingOrder) {
        productFieldComparator = ProductFieldComparatorFactory.createFieldComparator(productSortingField, sortingOrder);
    }

    @Override
    public int compare(Product p1, Product p2) {
        return productFieldComparator.compare(p1, p2);
    }
}
