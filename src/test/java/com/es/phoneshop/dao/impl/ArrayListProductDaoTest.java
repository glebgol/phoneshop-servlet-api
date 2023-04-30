package com.es.phoneshop.dao.impl;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.values.Products;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;

public class ArrayListProductDaoTest {
    private ProductDao productDao;
    private final int initialProductCount = 12;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
    }

    @Test
    public void testFindProductsNotEmptyResult() {
        assertFalse(productDao.findProducts().isEmpty());
    }

    @Test
    public void saveNewProduct() {
        Product product = Products.newProduct();
        int expectedProductCount = initialProductCount + 1;

        productDao.save(product);
        int actualProductCount = productDao.findProducts().size();

        assertEquals(expectedProductCount, actualProductCount);
    }

    @Test
    public void saveExistingProduct() {
        Product product = Products.forUpdate(1L);

        productDao.save(product);
        int actualProductCount = productDao.findProducts().size();

        assertEquals(initialProductCount, actualProductCount);
    }

    @Test
    public void deleteProductExistentId() {
        long id = 1L;
        int expectedProductCount = initialProductCount - 1;

        productDao.delete(id);
        int actualProductCount = productDao.findProducts().size();

        assertEquals(expectedProductCount, actualProductCount);
    }

    @Test
    public void deleteProductNonExistentId() {
        long id = -1L;

        productDao.delete(id);
        int actualProductCount = productDao.findProducts().size();

        assertEquals(initialProductCount, actualProductCount);
    }

    @Test(expected = NoSuchElementException.class)
    public void getProductNonExistentIdThrowsException() {
        long id = -100L;
        productDao.getProduct(id);
    }
}
