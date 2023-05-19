package com.es.phoneshop.model.product;

import java.util.LinkedList;

public class RecentlyViewedProducts {
    private final LinkedList<Product> products;

    public RecentlyViewedProducts() {
        this.products = new LinkedList<>();
    }

    public LinkedList<Product> getProducts() {
        return products;
    }
}
