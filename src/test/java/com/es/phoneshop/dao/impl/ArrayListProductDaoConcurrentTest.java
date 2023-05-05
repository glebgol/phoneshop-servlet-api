package com.es.phoneshop.dao.impl;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.values.Products;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class ArrayListProductDaoConcurrentTest {
    private ExecutorService executorService;
    private ProductDao productDao;
    private final int initialProductCount = 12;

    @Before
    public void setup() {
        executorService = Executors.newFixedThreadPool(3);
        productDao = new ArrayListProductDao();
    }

    @Test
    public void testConcurrentSave() throws InterruptedException {
        Product product = Products.newProduct();
        int expectedProductCount = initialProductCount + 1;

        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> productDao.save(product));
        }
        executorService.awaitTermination(100, TimeUnit.MILLISECONDS);
        int actualProductCount = productDao.findProducts().size();

        assertEquals(expectedProductCount, actualProductCount);
    }

    @Test
    public void testConcurrentUpdate() throws InterruptedException {
        Product product = Products.forUpdate(1L);

        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> productDao.save(product));
        }
        executorService.awaitTermination(100, TimeUnit.MILLISECONDS);
        int actualProductCount = productDao.findProducts().size();

        assertEquals(initialProductCount, actualProductCount);
    }
}
