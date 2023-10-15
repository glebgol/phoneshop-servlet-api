package com.es.phoneshop.strategies;

import com.es.phoneshop.strategies.impl.AllWordsSearchStrategy;
import com.es.phoneshop.strategies.impl.AnyWordSearchStrategy;

public class SearchStrategyFactory {
    public static SearchStrategy createStrategy(String query, SearchMethod searchMethod) {
        if (searchMethod == SearchMethod.ANY_WORD) {
            return new AnyWordSearchStrategy(query);
        } else if (searchMethod == SearchMethod.ALL_WORDS) {
            return new AllWordsSearchStrategy(query);
        } else {
            throw new RuntimeException("No strategies for " + searchMethod);
        }
    }
}
