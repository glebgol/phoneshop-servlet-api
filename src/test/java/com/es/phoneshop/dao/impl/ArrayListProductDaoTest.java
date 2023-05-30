package com.es.phoneshop.dao.impl;

import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductSortingField;
import com.es.phoneshop.model.SortingOrder;
import com.es.phoneshop.values.Products;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.es.phoneshop.values.Products.sampleData;
import static org.junit.Assert.assertEquals;
import static junit.framework.TestCase.assertSame;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;

public class ArrayListProductDaoTest {
    private ArrayListProductDao productDao;
    private final int initialProductCount = 6;

    @Before
    public void setUp() {
        productDao = ArrayListProductDao.getInstance();
        sampleData().forEach(productDao::save);
    }

    @After
    public void tearDown() {
        productDao.clear();
    }

    @Test
    public void singletonTest() {
        assertSame(ArrayListProductDao.getInstance(), ArrayListProductDao.getInstance());
    }

    @Test
    public void testFindProductsNotEmptyResult() {
        assertFalse(productDao.getAllProducts().isEmpty());
    }

    @Test
    public void saveNewProduct() {
        Product product = Products.newProduct();
        int expectedProductCount = initialProductCount + 1;

        productDao.save(product);
        int actualProductCount = productDao.getAllProducts().size();

        assertEquals(expectedProductCount, actualProductCount);
    }

    @Test
    public void saveExistingProduct() {
        Product product = Products.forUpdate(1L);

        productDao.save(product);
        int actualProductCount = productDao.getAllProducts().size();

        assertEquals(initialProductCount, actualProductCount);
    }

    @Test
    public void deleteProductExistentId() {
        long id = 1L;
        int expectedProductCount = initialProductCount - 1;

        productDao.delete(id);
        int actualProductCount = productDao.getAllProducts().size();

        assertEquals(expectedProductCount, actualProductCount);
    }

    @Test
    public void deleteProductNonExistentId() {
        long id = -1L;

        productDao.delete(id);
        int actualProductCount = productDao.getAllProducts().size();

        assertEquals(initialProductCount, actualProductCount);
    }

    @Test(expected = ProductNotFoundException.class)
    public void getProductNonExistentIdThrowsException() {
        long id = -100L;
        productDao.get(id);
    }

    @Test
    public void searchSamsungProducts() {
        String search = "Samsung Galaxy III";
        String expectedFirstProductDescription = "Samsung Galaxy S III";

        List<Product> samsungProducts = productDao.findProducts(search);
        int samsungProductsCount = samsungProducts.size();
        String firstProductDescription = samsungProducts.get(0).getDescription();

        assertEquals(2, samsungProductsCount);
        assertEquals(expectedFirstProductDescription, firstProductDescription);
    }

    @Test
    public void searchAppleIPhone6() {
        String search = "Apple iPhone 6";

        Product firstIphone = productDao.findProducts(search).get(0);
        String description = firstIphone.getDescription();

        assertEquals(search, description);
    }

    @Test
    public void searchHTC() {
        String search = "HTC";
        String expectedDescription = "HTC EVO Shift 4G";

        Product iphone = productDao.findProducts(search).get(0);
        String description = iphone.getDescription();

        assertEquals(expectedDescription, description);
    }

    @Test
    public void sortByPriceAsc() {
        List<Product> expectedProducts = Products.sortedByPriceAsc();

        List<Product> actualProducts = productDao.findProducts(null, ProductSortingField.PRICE, SortingOrder.ASC);

        assertArrayEquals(expectedProducts.toArray(), actualProducts.toArray());
    }

    @Test
    public void sortByPriceDesc() {
        List<Product> expectedProducts = Products.sortedByPriceDesc();

        List<Product> actualProducts = productDao.findProducts(null, ProductSortingField.PRICE, SortingOrder.DESC);

        assertArrayEquals(expectedProducts.toArray(), actualProducts.toArray());
    }

    @Test
    public void sortByDescriptionAsc() {
        List<Product> expectedProducts = Products.sortedByDescriptionAsc();

        List<Product> actualProducts = productDao.findProducts(null, ProductSortingField.DESCRIPTION, SortingOrder.ASC);

        assertArrayEquals(expectedProducts.toArray(), actualProducts.toArray());
    }

    @Test
    public void sortByDescriptionDesc() {
        List<Product> expectedProducts = Products.sortedByDescriptionDesc();

        List<Product> actualProducts = productDao.findProducts(null, ProductSortingField.DESCRIPTION, SortingOrder.DESC);

        assertArrayEquals(expectedProducts.toArray(), actualProducts.toArray());
    }

    @Test
    public void searchSamsungAndIphoneAndSortByPriceAsc() {
        String search = "Samsung IPhone";
        List<Product> expectedProducts = Products.samsungsAndIphonesSortedByPriceAsc();

        List<Product> actualProducts = productDao.findProducts(search, ProductSortingField.PRICE, SortingOrder.ASC);

        assertArrayEquals(expectedProducts.toArray(), actualProducts.toArray());
    }
}
