package com.github.aarsy.currency_converter.service.repository;

import com.github.aarsy.currency_converter.service.model.CurrencyExchangeRatesList;
import com.github.aarsy.currency_converter.service.model.CurrencyList;
import com.github.aarsy.currency_converter.utils.CurrencyConverterConstants;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface CurrencyLayerService {

    @GET(CurrencyConverterConstants.EndPoints.API_CURRENCY_LIST)
    Call<CurrencyList> getCurrencyList(@QueryMap Map<String, String> apiKeyMap);

    @GET(CurrencyConverterConstants.EndPoints.API_EXCHANGE_RATES)
    Call<CurrencyExchangeRatesList> getCurrencyRates(@QueryMap Map<String, String> apiKeyMap);

}
