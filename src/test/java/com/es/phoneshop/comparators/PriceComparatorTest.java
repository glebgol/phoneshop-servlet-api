package com.es.phoneshop.comparators;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.SortingOrder;
import com.es.phoneshop.values.Products;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class PriceComparatorTest {
    private PriceComparator comparator;
    private static final int HIGHER_PRICE = 100;
    private static final int LOWER_PRICE = 99;

    @Test
    public void compareProducts() {
        comparator = new PriceComparator(SortingOrder.ASC);
        Product p1 = Products.withPrice(HIGHER_PRICE);
        Product p2 = Products.withPrice(LOWER_PRICE);

        int result = comparator.compare(p1, p2);

        assertTrue(result > 0);
    }

    @Test
    public void compareEqualProducts() {
        comparator = new PriceComparator(SortingOrder.ASC);
        Product p1 = Products.withPrice(HIGHER_PRICE);
        Product p2 = Products.withPrice(HIGHER_PRICE);

        int result = comparator.compare(p1, p2);

        assertEquals(0, result);
    }
}
