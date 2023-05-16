package com.es.phoneshop.parser.impl;

import com.es.phoneshop.parser.NumberParser;

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

    public boolean isValidQuantity(Locale locale, String str) {
        NumberFormat format = NumberFormat.getInstance(locale);
        try {
            Number number = format.parse(str);
            return number.doubleValue() != 0 && isPositive(number) && isInteger(number);
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean isInteger(Number number) {
        return number.doubleValue() % 1 == 0;
    }

    private boolean isPositive(Number number) {
        return number.doubleValue() >= 0;
    }

    @Override
    public Integer parseNumber(Locale locale, String str) {
        NumberFormat format = NumberFormat.getInstance(locale);
        try {
            return format.parse(str).intValue();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
