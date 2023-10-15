package com.es.phoneshop.strategies.impl;

import java.util.Arrays;

import static org.codehaus.plexus.util.StringUtils.isBlank;

public class AllWordsSearchStrategy extends AbstractSearchStrategy {
    public AllWordsSearchStrategy(String query) {
        super(query);
    }

    @Override
    public boolean search(String description) {
        return isBlank(query) || Arrays.stream(query.split(SPACE))
                .allMatch(word -> description.toLowerCase().contains(word.toLowerCase()));
    }
}
