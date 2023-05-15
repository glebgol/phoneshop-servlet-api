package com.es.phoneshop.comparators;

import com.es.phoneshop.model.Product;
import com.es.phoneshop.values.Products;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ProductSearchingComparatorTest {
    private ProductSearchingComparator comparator;
    private static final String SAMSUNG_S_SEARCHING = "Samsung S";
    private static final String S3_SEARCHING = "S III";
    private static final String IPHONE_XS_MAX_13_SEARCHING = "IPhone XS MAX 13";

    @Test
    public void compareSamsungGalaxySWithIphone() {
        Product samsungGalaxyS = Products.samsungGalaxyS();
        Product iphone = Products.iphone();
        comparator = new ProductSearchingComparator(SAMSUNG_S_SEARCHING);

        int result = comparator.compare(samsungGalaxyS, iphone);

        assertTrue(result < 0);
    }

    @Test
    public void compareSamsungGalaxySWithSamsungGalaxyS3() {
        Product samsungGalaxyS = Products.samsungGalaxyS();
        Product samsungGalaxyS3 = Products.samsungGalaxyS3();
        comparator = new ProductSearchingComparator(S3_SEARCHING);

        int result = comparator.compare(samsungGalaxyS, samsungGalaxyS3);

        assertTrue(result > 0);
    }

    @Test
    public void compareSamsungGalaxyS2WithSamsungGalaxyS3() {
        Product samsungGalaxyS2 = Products.samsungGalaxyS2();
        Product samsungGalaxyS3 = Products.samsungGalaxyS3();
        comparator = new ProductSearchingComparator(SAMSUNG_S_SEARCHING);

        int result = comparator.compare(samsungGalaxyS2, samsungGalaxyS3);

        assertEquals(0, result);
    }

    @Test
    public void compareSamsungGalaxyS2WithSamsungGalaxyS3WithIphoneDescription() {
        Product samsungGalaxyS2 = Products.samsungGalaxyS2();
        Product samsungGalaxyS3 = Products.samsungGalaxyS3();
        comparator = new ProductSearchingComparator(IPHONE_XS_MAX_13_SEARCHING);

        int result = comparator.compare(samsungGalaxyS2, samsungGalaxyS3);

        assertEquals(0, result);
    }
}
