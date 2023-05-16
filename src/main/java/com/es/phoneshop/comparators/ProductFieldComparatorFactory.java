package com.es.phoneshop.comparators;

import com.es.phoneshop.model.product.ProductSortingField;
import com.es.phoneshop.model.SortingOrder;

public class ProductFieldComparatorFactory {
    public static ProductFieldComparator createFieldComparator(ProductSortingField productSortingField,
                                                               SortingOrder sortingOrder) {
        if (productSortingField == ProductSortingField.DESCRIPTION) {
            return new DescriptionComparator(sortingOrder);
        }
        else if (productSortingField == ProductSortingField.PRICE) {
            return new PriceComparator(sortingOrder);
        }
        throw new IllegalArgumentException("There is no comparators for: " + productSortingField.name());
    }
}
