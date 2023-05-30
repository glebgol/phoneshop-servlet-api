package com.es.phoneshop.services.impl;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DefaultValidationServiceTest {
    private final DefaultValidationService service = DefaultValidationService.getInstance();

    @Test
    public void isNotValidDateBeforeCurrentDate() {
        String date = "2004-05-15";

        boolean result = service.isValidDate(date);

        assertFalse(result);
    }

    @Test
    public void isNotValidDateFebruary29() {
        String date = "2025-02-29";

        boolean result = service.isValidDate(date);

        assertFalse(result);
    }

    @Test
    public void isValidDateFebruary29LeapYear() {
        String date = "2024-02-29";

        boolean result = service.isValidDate(date);

        assertTrue(result);
    }

    @Test
    public void isValidDate1() {
        String date = "2024/10/23";

        boolean result = service.isValidDate(date);

        assertTrue(result);
    }

    @Test
    public void isValidCurrentDate() {
        String date = LocalDate.now().toString();

        boolean result = service.isValidDate(date);

        assertTrue(result);
    }

    @Test
    public void isValidPhone1() {
        String phone = "375(33)114-55-86";

        boolean result = service.isValidPhone(phone);

        assertTrue(result);
    }

    @Test
    public void isValidPhone2() {
        String phone = "+375(33)114-55-86";

        boolean result = service.isValidPhone(phone);

        assertTrue(result);
    }

    @Test
    public void isValidPhone3() {
        String phone = "+375331145586";

        boolean result = service.isValidPhone(phone);

        assertTrue(result);
    }

    @Test
    public void isNotValidAddress() {
        String notValidAddress = "/*-##%&";

        boolean result = service.isValidAddress(notValidAddress);

        assertFalse(result);
    }

    @Test
    public void isValidAddress() {
        String address = "Minsk, Belarus, 2";

        boolean result = service.isValidAddress(address);

        assertTrue(result);
    }

    @Test
    public void isNotValidEmptyName() {
        String emptyName = "";

        boolean result = service.isValidName(emptyName);

        assertFalse(result);
    }

    @Test
    public void isValidName() {
        String name = "glebgol";

        boolean result = service.isValidName(name);

        assertTrue(result);
    }
}
