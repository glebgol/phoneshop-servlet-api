package com.es.phoneshop.services;

public interface ValidationService {
    boolean isValidName(String name);
    boolean isValidPhone(String phone);
    boolean isValidDate(String dateString);
    boolean isValidAddress(String address);
}
