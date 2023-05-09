package com.es.phoneshop.services;

import com.es.phoneshop.model.ProductPriceHistory;

public interface ProductPriceHistoryService {
    ProductPriceHistory getPriceHistory(long id);
}
