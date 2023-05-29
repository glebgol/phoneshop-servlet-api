package com.es.phoneshop.model.product;

import java.io.Serializable;
import java.util.LinkedList;

public class RecentlyViewedProducts implements Serializable {
    private final LinkedList<Product> products;

    public RecentlyViewedProducts() {
        this.products = new LinkedList<>();
    }

    public LinkedList<Product> getProducts() {
        return products;
    }
}
