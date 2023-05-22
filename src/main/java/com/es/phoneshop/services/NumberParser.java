package com.es.phoneshop.services;

import java.text.ParseException;
import java.util.Locale;

public interface NumberParser {
    Integer parseNumber(Locale locale, String str) throws ParseException;
}
