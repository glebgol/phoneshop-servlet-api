package com.es.phoneshop.predicates;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.strategies.SearchMethod;
import com.es.phoneshop.strategies.SearchStrategyFactory;
import com.es.phoneshop.strategies.SearchStrategy;

import java.util.function.Predicate;

public class AdvancedSearchProductPredicate implements Predicate<Product> {
    private final SearchStrategy searchStrategy;

    public AdvancedSearchProductPredicate(String query, SearchMethod searchMethod) {
        searchStrategy = SearchStrategyFactory.createStrategy(query, searchMethod);
    }

    @Override
    public boolean test(Product product) {
        return searchStrategy.search(product.getDescription());
    }
}
