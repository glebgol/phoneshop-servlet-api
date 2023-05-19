package com.es.phoneshop.predicates;

import com.es.phoneshop.model.product.Product;

import java.util.Arrays;
import java.util.function.Predicate;

import static org.codehaus.plexus.util.StringUtils.isBlank;

public class ContainsSearchStringProductPredicate implements Predicate<Product> {
    private final String search;

    public ContainsSearchStringProductPredicate(String search) {
        this.search = search;
    }

    @Override
    public boolean test(Product product) {
        return isBlank(search) || Arrays.stream(search.split("\s"))
                .anyMatch(word -> product.getDescription().toLowerCase().contains(word.toLowerCase()));
    }
}
