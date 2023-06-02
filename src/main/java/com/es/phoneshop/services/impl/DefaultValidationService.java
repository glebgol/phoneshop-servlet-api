package com.es.phoneshop.services.impl;

import com.es.phoneshop.services.ValidationService;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class DefaultValidationService implements ValidationService {
    private DefaultValidationService() {
    }

    private static final class SingletonHolder {
        private static final DefaultValidationService INSTANCE = new DefaultValidationService();
    }

    public static DefaultValidationService getInstance() {
        return DefaultValidationService.SingletonHolder.INSTANCE;
    }

    private static final String PHONE_REGEX = "^([+]?[\\s0-9]+)?(\\d{3}|[(]?[0-9]+[)])?(-?\\s?[0-9])+$";
    private static final String ADDRESS_REGEX = "^([a-zA-z0-9/\\\\''(),\\s]{2,255})$";
    private static final String NAME_REGEX = "^[a-zA-Z .]+";

    @Override
    public boolean isValidName(String name) {
        return name.matches(NAME_REGEX);
    }

    @Override
    public boolean isValidPhone(String phone) {
        return phone.trim().matches(PHONE_REGEX);
    }

    @Override
    public boolean isValidDate(String dateString) {
        try {
            List<Integer> yearMonthDay = Arrays.stream(dateString.split("([:/.\\-])"))
                    .map(Integer::parseInt)
                    .toList();
            if (yearMonthDay.size() != 3) {
                return false;
            }
            LocalDate date = LocalDate.of(yearMonthDay.get(0), yearMonthDay.get(1), yearMonthDay.get(2));
            return !date.isBefore(LocalDate.now());
        } catch (NumberFormatException | DateTimeException e) {
            return false;
        }
    }

    @Override
    public boolean isValidAddress(String address) {
        return address.matches(ADDRESS_REGEX);
    }
}
