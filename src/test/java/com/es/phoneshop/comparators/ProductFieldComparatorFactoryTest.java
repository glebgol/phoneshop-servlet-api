package com.es.phoneshop.comparators;

import com.es.phoneshop.model.product.ProductSortingField;
import com.es.phoneshop.model.SortingOrder;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ProductFieldComparatorFactoryTest {
    @Test
    public void createPriceComparator() {
        ProductFieldComparator comparator = ProductFieldComparatorFactory
                .createFieldComparator(ProductSortingField.PRICE, SortingOrder.ASC);

        assertTrue(comparator instanceof PriceComparator);
    }

    @Test
    public void createDescriptionComparator() {
        ProductFieldComparator comparator = ProductFieldComparatorFactory
                .createFieldComparator(ProductSortingField.DESCRIPTION, SortingOrder.ASC);

        assertTrue(comparator instanceof DescriptionComparator);
    }
}
