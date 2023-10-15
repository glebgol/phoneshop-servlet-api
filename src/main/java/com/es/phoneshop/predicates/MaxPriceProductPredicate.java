package com.es.phoneshop.predicates;

import com.es.phoneshop.model.product.Product;

import java.math.BigDecimal;
import java.util.function.Predicate;

public class MaxPriceProductPredicate implements Predicate<Product> {
    private final BigDecimal maxPrice;

    public MaxPriceProductPredicate(BigDecimal minPrice) {
        this.maxPrice = minPrice;
    }

    @Override
    public boolean test(Product product) {
        return maxPrice == null || product.getPrice().compareTo(maxPrice) <= 0;
    }
}
