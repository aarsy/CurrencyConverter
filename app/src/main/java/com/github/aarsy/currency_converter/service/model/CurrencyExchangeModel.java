package com.github.aarsy.currency_converter.service.model;

import java.util.List;

public class CurrencyExchangeModel {

    private List<CurrencyRateItem> currencyRateItems;
    private String source;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<CurrencyRateItem> getCurrencyRateItems() {
        return currencyRateItems;
    }

    public void setCurrencyRateItems(List<CurrencyRateItem> currencyRateItems) {
        this.currencyRateItems = currencyRateItems;
    }
}
