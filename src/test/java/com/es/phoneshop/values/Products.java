package com.es.phoneshop.values;

import com.es.phoneshop.model.Product;

import java.math.BigDecimal;
import java.util.Currency;

public class Products {
    private static final Currency USD = Currency.getInstance("USD");

    public static Product newProduct() {
        return new Product(
                "iphone13",
                "Apple iPhone 13",
                new BigDecimal(1500),
                USD,
                25,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg"
        );
    }

    public static Product forUpdate(long id) {
        return new Product(
                id,
                "iphone6",
                "Apple iPhone 6",
                new BigDecimal(1000),
                USD,
                30,
                "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg"
        );
    }
}
