package com.github.aarsy.currency_converter.service.model;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class CurrencyExchangeRatesList {

    @SerializedName("quotes")
    private Map<String, Double> currencies;

    @SerializedName("privacy")
    private String privacy;

    @SerializedName("source")
    private String source;

    @SerializedName("success")
    private Boolean success;

    @SerializedName("terms")
    private String terms;

    public Map<String, Double> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(Map<String, Double> currencies) {
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

}

