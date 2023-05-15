package com.es.phoneshop.predicates;

import com.es.phoneshop.model.Product;
import com.es.phoneshop.values.Products;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class ContainsSearchStringProductPredicateTest {
    private ContainsSearchStringProductPredicate productPredicate;
    private static final String SAMSUNG_SEARCH = "Samsung";
    private static final String SAMSUNG_DESCRIPTION = "Samsung S8";
    private static final String IPHONE_DESCRIPTION = "Iphone 8";
    private static final String NULL_SEARCH = null;
    private static final String EMPTY_SEARCH = "";
    private static final Product PRODUCT = new Product();

    @Test
    public void testPredicateNullSearch() {
        productPredicate = new ContainsSearchStringProductPredicate(NULL_SEARCH);

        boolean test = productPredicate.test(PRODUCT);

        assertTrue(test);
    }

    @Test
    public void testPredicateEmptySearch() {
        productPredicate = new ContainsSearchStringProductPredicate(EMPTY_SEARCH);

        boolean test = productPredicate.test(PRODUCT);

        assertTrue(test);
    }

    @Test
    public void testPredicateWithSamsung() {
        Product samsung = Products.withDescription(SAMSUNG_DESCRIPTION);
        productPredicate = new ContainsSearchStringProductPredicate(SAMSUNG_SEARCH);

        boolean test = productPredicate.test(samsung);

        assertTrue(test);
    }

    @Test
    public void testPredicateWithIphone() {
        Product iphone = Products.withDescription(IPHONE_DESCRIPTION);
        productPredicate = new ContainsSearchStringProductPredicate(SAMSUNG_SEARCH);

        boolean test = productPredicate.test(iphone);

        assertFalse(test);
    }
}
