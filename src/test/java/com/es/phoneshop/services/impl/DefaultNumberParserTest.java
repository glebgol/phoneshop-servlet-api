package com.es.phoneshop.services.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class DefaultNumberParserTest {
    private final Locale locale = Locale.US;
    private final DefaultNumberParser numberParser = DefaultNumberParser.getInstance();

    @Test(expected = ParseException.class)
    public void parseQuantityNotNumber() throws ParseException {
        String notNumber = "AAA";

        numberParser.parseNumber(locale, notNumber);
    }

    @Test(expected = NumberFormatException.class)
    public void parseQuantityDoubleNumber() throws ParseException {
        String doubleNumberString = "123.45";

        numberParser.parseNumber(locale, doubleNumberString);
    }

    @Test(expected = NumberFormatException.class)
    public void parseNegativeNumber() throws ParseException {
        String negativeNumber = "-1";

        numberParser.parseNumber(locale, negativeNumber);
    }

    @Test(expected = NumberFormatException.class)
    public void parseZeroNumber() throws ParseException {
        String zero = "0";

        numberParser.parseNumber(locale, zero);
    }

    @Test
    public void parsePositiveIntegerNumber() throws ParseException {
        String positiveIntegerNumber = "5";
        int expectedNumber = 5;

        int number = numberParser.parseNumber(locale, positiveIntegerNumber);

        assertEquals(expectedNumber, number);
    }
}
