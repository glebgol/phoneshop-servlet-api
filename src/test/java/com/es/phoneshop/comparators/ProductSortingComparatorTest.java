package com.es.phoneshop.comparators;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductSortingField;
import com.es.phoneshop.model.SortingOrder;
import com.es.phoneshop.values.Products;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class ProductSortingComparatorTest {
    private ProductSortingComparator productSortingComparator;
    private static final int HIGHER_PRICE = 100;
    private static final int LOWER_PRICE = 99;
    private static final String HIGHER_ALPHABETICALLY_DESCRIPTION = "Apple";
    private static final String LOWER_ALPHABETICALLY_DESCRIPTION = "Samsung";

    @Test
    public void compareByPriceDescOrder1() {
        Product product1 = Products.withPrice(LOWER_PRICE);
        Product product2 = Products.withPrice(HIGHER_PRICE);
        productSortingComparator = new ProductSortingComparator(ProductSortingField.PRICE, SortingOrder.DESC);

        int result = productSortingComparator.compare(product1, product2);

        assertTrue(result > 0);
    }

    @Test
    public void compareByPriceDescOrder2() {
        Product product1 = Products.withPrice(HIGHER_PRICE);
        Product product2 = Products.withPrice(LOWER_PRICE);
        productSortingComparator = new ProductSortingComparator(ProductSortingField.PRICE, SortingOrder.DESC);

        int result = productSortingComparator.compare(product1, product2);

        assertTrue(result < 0);
    }

    @Test
    public void compareByEqualPrice() {
        Product product1 = Products.withPrice(HIGHER_PRICE);
        Product product2 = Products.withPrice(HIGHER_PRICE);
        productSortingComparator = new ProductSortingComparator(ProductSortingField.PRICE, SortingOrder.DESC);

        int result = productSortingComparator.compare(product1, product2);

        assertEquals(0, result);
    }

    @Test
    public void compareByPriceAscOrder1() {
        Product product1 = Products.withPrice(LOWER_PRICE);
        Product product2 = Products.withPrice(HIGHER_PRICE);
        productSortingComparator = new ProductSortingComparator(ProductSortingField.PRICE, SortingOrder.ASC);

        int result = productSortingComparator.compare(product1, product2);

        assertTrue(result < 0);
    }

    @Test
    public void compareByPriceAscOrder2() {
        Product product1 = Products.withPrice(HIGHER_PRICE);
        Product product2 = Products.withPrice(LOWER_PRICE);
        productSortingComparator = new ProductSortingComparator(ProductSortingField.PRICE, SortingOrder.ASC);

        int result = productSortingComparator.compare(product1, product2);

        assertTrue(result > 0);
    }

    @Test
    public void compareByDescriptionAscOrder() {
        Product product1 = Products.withDescription(HIGHER_ALPHABETICALLY_DESCRIPTION);
        Product product2 = Products.withDescription(LOWER_ALPHABETICALLY_DESCRIPTION);
        productSortingComparator = new ProductSortingComparator(ProductSortingField.DESCRIPTION, SortingOrder.ASC);

        int result = productSortingComparator.compare(product1, product2);

        assertTrue(result < 0);
    }

    @Test
    public void compareByDescriptionDescOrder() {
        Product product1 = Products.withDescription(HIGHER_ALPHABETICALLY_DESCRIPTION);
        Product product2 = Products.withDescription(LOWER_ALPHABETICALLY_DESCRIPTION);
        productSortingComparator = new ProductSortingComparator(ProductSortingField.DESCRIPTION, SortingOrder.DESC);

        int result = productSortingComparator.compare(product1, product2);

        assertTrue(result > 0);
    }

    @Test
    public void compareByEqualDescription() {
        Product product1 = Products.withDescription(HIGHER_ALPHABETICALLY_DESCRIPTION);
        Product product2 = Products.withDescription(HIGHER_ALPHABETICALLY_DESCRIPTION);
        productSortingComparator = new ProductSortingComparator(ProductSortingField.DESCRIPTION, SortingOrder.DESC);

        int result = productSortingComparator.compare(product1, product2);

        assertEquals(0, result);
    }
}
