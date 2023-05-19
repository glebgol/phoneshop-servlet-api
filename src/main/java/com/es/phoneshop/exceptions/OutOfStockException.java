package com.es.phoneshop.exceptions;

import com.es.phoneshop.model.product.Product;

public class OutOfStockException extends Exception {
    private final Product product;
    private final int availableStock;
    private final int requestedStock;

    public OutOfStockException(Product product, int availableStock, int requestedStock) {
        this.product = product;
        this.availableStock = availableStock;
        this.requestedStock = requestedStock;
    }

    public Product getProduct() {
        return product;
    }

    public int getAvailableStock() {
        return availableStock;
    }

    public int getRequestedStock() {
        return requestedStock;
    }
}
