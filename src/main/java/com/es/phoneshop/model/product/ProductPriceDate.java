package com.es.phoneshop.model.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

public class ProductPriceDate implements Serializable {
    private BigDecimal price;
    private Currency currency;
    private Date startDate;

    public ProductPriceDate(BigDecimal price, Currency currency) {
        this.price = price;
        this.currency = currency;
        startDate = new Date();
    }

    public ProductPriceDate(BigDecimal price, Currency currency, Date startDate) {
        this.price = price;
        this.currency = currency;
        this.startDate = startDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
