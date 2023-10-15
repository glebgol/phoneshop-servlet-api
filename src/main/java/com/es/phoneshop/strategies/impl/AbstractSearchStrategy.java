package com.es.phoneshop.strategies.impl;

import com.es.phoneshop.strategies.SearchStrategy;

public abstract class AbstractSearchStrategy implements SearchStrategy {
    protected final String query;
    protected static final String SPACE = "\s";

    protected AbstractSearchStrategy(String query) {
        this.query = query;
    }
}
