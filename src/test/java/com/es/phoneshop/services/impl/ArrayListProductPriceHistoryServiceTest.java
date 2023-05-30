package com.es.phoneshop.services.impl;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductPriceDate;
import com.es.phoneshop.model.product.ProductPriceHistory;
import com.es.phoneshop.values.Products;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArrayListProductPriceHistoryServiceTest {
    @Mock
    private ProductDao productDao;
    @InjectMocks
    private ArrayListProductPriceHistoryService service;

    @Test
    public void getProductPriceHistory() {
        Product product = Products.iphone();
        when(productDao.get(anyLong())).thenReturn(product);
        String expectedDescription = product.getDescription();
        List<ProductPriceDate> expectedHistory = product.getPriceHistory();

        ProductPriceHistory productPriceHistory = service.getPriceHistory(1);
        String actualDescription = productPriceHistory.getProductDescription();
        List<ProductPriceDate> actualHistory = productPriceHistory.getPriceHistory();

        assertEquals(expectedDescription, actualDescription);
        assertEquals(expectedHistory, actualHistory);
    }
}
