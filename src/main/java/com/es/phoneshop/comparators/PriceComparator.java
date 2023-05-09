package com.es.phoneshop.comparators;

import com.es.phoneshop.model.Product;
import com.es.phoneshop.model.SortingOrder;

public class PriceComparator extends ProductFieldComparator {
    public PriceComparator(SortingOrder sortingOrder) {
        super(sortingOrder);
    }

    @Override
    public int compare(Product p1, Product p2) {
        int comparison = p1.getPrice().compareTo(p2.getPrice());
        return sortingOrder == SortingOrder.ASC ? comparison : -comparison;
    }
}
