package com.es.phoneshop.dao.impl;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.values.Products;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.es.phoneshop.values.Products.sampleData;
import static org.junit.Assert.assertEquals;

public class ArrayListProductDaoConcurrentTest {
    private ExecutorService executorService;
    private ArrayListProductDao productDao;
    private final int initialProductCount = 6;

    @Before
    public void setUp() {
        productDao = ArrayListProductDao.getInstance();
        sampleData().forEach(productDao::save);
        executorService = Executors.newFixedThreadPool(3);
    }

    @After
    public void tearDown() {
        productDao.clear();
    }

    @Test
    public void testConcurrentSave() throws InterruptedException {
        Product product = Products.newProduct();
        int expectedProductCount = initialProductCount + 1;

        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> productDao.save(product));
        }
        executorService.awaitTermination(100, TimeUnit.MILLISECONDS);
        int actualProductCount = productDao.getAllProducts().size();

        assertEquals(expectedProductCount, actualProductCount);
    }

    @Test
    public void testConcurrentUpdate() throws InterruptedException {
        Product product = Products.forUpdate(1L);

        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> productDao.save(product));
        }
        executorService.awaitTermination(100, TimeUnit.MILLISECONDS);
        int actualProductCount = productDao.getAllProducts().size();

        assertEquals(initialProductCount, actualProductCount);
    }
}
