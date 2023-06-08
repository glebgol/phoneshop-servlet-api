package com.es.phoneshop.strategies.impl;

import java.util.Arrays;

import static org.codehaus.plexus.util.StringUtils.isBlank;

public class AnyWordSearchStrategy extends AbstractSearchStrategy {
    public AnyWordSearchStrategy(String query) {
        super(query);
    }

    @Override
    public boolean search(String description) {
        return isBlank(query) || Arrays.stream(query.split(SPACE))
                .anyMatch(word -> description.toLowerCase().contains(word.toLowerCase()));
    }
}
