package com.es.phoneshop.comparators;

import com.es.phoneshop.model.Product;
import com.es.phoneshop.model.SortingOrder;
import com.es.phoneshop.values.Products;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class DescriptionComparatorTest {
    private DescriptionComparator comparator;
    private static final String HIGHER_ALPHABETICALLY_DESCRIPTION = "Apple";
    private static final String LOWER_ALPHABETICALLY_DESCRIPTION = "Samsung";


    @Test
    public void compareProducts() {
        comparator = new DescriptionComparator(SortingOrder.ASC);
        Product p1 = Products.withDescription(HIGHER_ALPHABETICALLY_DESCRIPTION);
        Product p2 = Products.withDescription(LOWER_ALPHABETICALLY_DESCRIPTION);

        int result = comparator.compare(p1, p2);

        assertTrue(result < 0);
    }

    @Test
    public void compareEqualProducts() {
        comparator = new DescriptionComparator(SortingOrder.ASC);
        Product p1 = Products.withDescription(HIGHER_ALPHABETICALLY_DESCRIPTION);
        Product p2 = Products.withDescription(HIGHER_ALPHABETICALLY_DESCRIPTION);

        int result = comparator.compare(p1, p2);

        assertEquals(0, result);
    }
}
