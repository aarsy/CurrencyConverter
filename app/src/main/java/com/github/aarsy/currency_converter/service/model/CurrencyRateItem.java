package com.github.aarsy.currency_converter.service.model;

import androidx.annotation.Nullable;

public class CurrencyRateItem {
    private String currencyCountry;
    private Double currencyExchangeRate;

    public CurrencyRateItem(String currencyCountry, Double currencyExchangeRate) {
        this.currencyCountry = currencyCountry;
        this.currencyExchangeRate = currencyExchangeRate;
    }

    public CurrencyRateItem() {
    }

    public String getCurrencyCountry() {
        return currencyCountry;
    }

    public void setCurrencyCountry(String currencyCountry) {
        this.currencyCountry = currencyCountry;
    }

    public Double getCurrencyExchangeRate() {
        return currencyExchangeRate;
    }

    public void setCurrencyExchangeRate(Double currencyExchangeRate) {
        this.currencyExchangeRate = currencyExchangeRate;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof CurrencyRateItem) {
            CurrencyRateItem currencyRateItem = (CurrencyRateItem) obj;
            return currencyRateItem.getCurrencyCountry().substring(3).contains(this.getCurrencyCountry());
        }
        return false;

    }
}
