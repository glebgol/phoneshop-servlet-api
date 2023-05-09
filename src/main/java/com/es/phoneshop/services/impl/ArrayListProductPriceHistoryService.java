package com.es.phoneshop.services.impl;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.model.Product;
import com.es.phoneshop.model.ProductPriceHistory;
import com.es.phoneshop.services.ProductPriceHistoryService;

public class ArrayListProductPriceHistoryService implements ProductPriceHistoryService {
    private final ProductDao productDao;

    public ArrayListProductPriceHistoryService(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public ProductPriceHistory getPriceHistory(long id) {
        Product product = productDao.getProduct(id);
        return new ProductPriceHistory(product.getPriceHistory(), product.getDescription());
    }
}
