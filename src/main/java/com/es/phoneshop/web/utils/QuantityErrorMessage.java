package com.es.phoneshop.web.utils;

import com.es.phoneshop.exceptions.OutOfStockException;

public class QuantityErrorMessage {
    private static final String QUANTITY_ERROR_MESSAGE = "Quantity should be integer number greater than zero!";
    private static final String OUT_OF_STOCK_ERROR_MESSAGE = "Not enough stock, available only ";

    public static String getQuantityErrorMessage(Exception e) {
        if (e instanceof OutOfStockException) {
            return OUT_OF_STOCK_ERROR_MESSAGE + ((OutOfStockException) e).getAvailableStock();
        }
        else {
            return QUANTITY_ERROR_MESSAGE;
        }
    }
}
