package com.es.phoneshop.model;

import java.util.List;

public class ProductPriceHistory {
    private List<ProductPriceDate> priceHistory;
    private String productDescription;

    public ProductPriceHistory(List<ProductPriceDate> priceHistory, String productDescription) {
        this.priceHistory = priceHistory;
        this.productDescription = productDescription;
    }

    public List<ProductPriceDate> getPriceHistory() {
        return priceHistory;
    }

    public void setPriceHistory(List<ProductPriceDate> priceHistory) {
        this.priceHistory = priceHistory;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
}
