package com.es.phoneshop.parser;

import java.util.Locale;

public interface NumberParser {
    boolean isValidQuantity(Locale locale, String str);
    Integer parseNumber(Locale locale, String str);
}
