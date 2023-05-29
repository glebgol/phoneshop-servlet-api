package com.es.phoneshop.services.impl;

import com.es.phoneshop.services.NumberParser;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class DefaultNumberParser implements NumberParser {
    private DefaultNumberParser() {
    }

    private static class SingletonHelper {
        private static final DefaultNumberParser INSTANCE = new DefaultNumberParser();
    }

    public static DefaultNumberParser getInstance() {
        return DefaultNumberParser.SingletonHelper.INSTANCE;
    }

    @Override
    public Integer parseNumber(Locale locale, String str) throws ParseException {
        NumberFormat numberFormat = NumberFormat.getInstance(locale);
        Number number = numberFormat.parse(str);
        if (!isValidQuantity(number)) {
            throw new NumberFormatException();
        }
        return numberFormat.parse(str).intValue();
    }

    private boolean isValidQuantity(Number number) {
        return number.doubleValue() != 0 && isPositive(number) && isInteger(number);
    }

    private boolean isInteger(Number number) {
        return number.doubleValue() % 1 == 0;
    }

    private boolean isPositive(Number number) {
        return number.doubleValue() >= 0;
    }
}
