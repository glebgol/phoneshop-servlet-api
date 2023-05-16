package com.es.phoneshop.comparators;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.SortingOrder;

public class DescriptionComparator extends ProductFieldComparator {
    public DescriptionComparator(SortingOrder sortingOrder) {
        super(sortingOrder);
    }

    @Override
    public int compare(Product p1, Product p2) {
        int comparison = p1.getDescription().compareTo(p2.getDescription());
        return sortingOrder == SortingOrder.ASC ? comparison : -comparison;
    }
}
