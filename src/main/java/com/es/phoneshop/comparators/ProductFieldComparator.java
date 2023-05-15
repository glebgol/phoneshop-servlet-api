package com.es.phoneshop.comparators;

import com.es.phoneshop.model.Product;
import com.es.phoneshop.model.SortingOrder;

import java.util.Comparator;

public abstract class ProductFieldComparator implements Comparator<Product> {
    protected final SortingOrder sortingOrder;

    public ProductFieldComparator(SortingOrder sortingOrder) {
        this.sortingOrder = sortingOrder;
    }
}
