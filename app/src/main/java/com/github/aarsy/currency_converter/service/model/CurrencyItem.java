package com.github.aarsy.currency_converter.service.model;

public class CurrencyItem {

    private String currencyName;
    private String currencyCounrty;

    public CurrencyItem(String currencyName, String currencyCounrty) {
        this.currencyName = currencyName;
        this.currencyCounrty = currencyCounrty;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencyCounrty() {
        return currencyCounrty;
    }

    public void setCurrencyCounrty(String currencyCounrty) {
        this.currencyCounrty = currencyCounrty;
    }

}
