package com.github.aarsy.currency_converter.service.model;

import com.google.gson.annotations.SerializedName;

import java.util.Map;


public class CurrencyList {
    @SerializedName("currencies")
    private Map<String, String> currencies;

    @SerializedName("privacy")
    private String privacy;

    @SerializedName("success")
    private Boolean success;

    @SerializedName("terms")
    private String terms;

    public Map<String, String> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(Map<String, String> currencies) {
        this.currencies = currencies;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

}
