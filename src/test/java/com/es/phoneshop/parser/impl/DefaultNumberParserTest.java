package com.es.phoneshop.parser.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Locale;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class DefaultNumberParserTest {
    private final Locale locale = Locale.US;
    private final DefaultNumberParser numberParser = DefaultNumberParser.getInstance();

    @Test
    public void isValidQuantityDoubleNumber() {
        String doubleNumberString = "123.45";

        boolean isValid = numberParser.isValidQuantity(locale, doubleNumberString);

        assertFalse(isValid);
    }

    @Test
    public void isValidNegativeNumber() {
        String negativeNumber = "-1";

        boolean isValid = numberParser.isValidQuantity(locale, negativeNumber);

        assertFalse(isValid);
    }

    @Test
    public void isValidZeroNumber() {
        String zero = "0";

        boolean isValid = numberParser.isValidQuantity(locale, zero);

        assertFalse(isValid);
    }

    @Test
    public void isValidPositiveIntegerNumber() {
        String positiveIntegerNumber = "5";

        boolean isValid = numberParser.isValidQuantity(locale, positiveIntegerNumber);

        assertTrue(isValid);
    }

    @Test
    public void testParseNumber() {
        String positiveIntegerNumber = "5";
        int expectedNumber = 5;

        int number = numberParser.parseNumber(locale, positiveIntegerNumber);

        assertEquals(expectedNumber, number);
    }
}
