package com.es.phoneshop.comparators;

import com.es.phoneshop.model.product.Product;
import org.codehaus.plexus.util.StringUtils;

import java.util.Arrays;
import java.util.Comparator;

public class ProductSearchingComparator implements Comparator<Product> {
    private final String search;
    private final String regex = "\s";

    public ProductSearchingComparator(String search) {
        this.search = search;
    }

    @Override
    public int compare(Product p1, Product p2) {
        if (StringUtils.isBlank(search)) {
            return 0;
        }
        String[] searchingWords = search.split(regex);

        long p1MatchesCount = searchingWordsCount(searchingWords, p1.getDescription());
        long p2MatchesCount = searchingWordsCount(searchingWords, p2.getDescription());

        int comparison = Long.compare(p2MatchesCount, p1MatchesCount);
        if (comparison == 0) {
            return compareWordsCount(p1, p2);
        }
        return comparison;
    }

    private long searchingWordsCount(String[] searchingWords, String str) {
        return Arrays.stream(searchingWords)
                .filter(word -> containsIgnoreCase(str, word))
                .count();
    }

    private boolean containsIgnoreCase(String str, String word) {
        return str.toLowerCase().contains(word.toLowerCase());
    }

    private int compareWordsCount(Product p1, Product p2) {
        return Integer.compare(p1.getDescription().split(regex).length, p2.getDescription().split(regex).length);
    }
}
