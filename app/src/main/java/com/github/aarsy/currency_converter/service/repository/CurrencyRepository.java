package com.github.aarsy.currency_converter.service.repository;


import android.util.Log;

import com.github.aarsy.currency_converter.database.LocalCacheHandler;
import com.github.aarsy.currency_converter.service.model.CurrencyExchangeRatesList;
import com.github.aarsy.currency_converter.service.model.CurrencyList;
import com.github.aarsy.currency_converter.utils.CurrencyConverterConstants;
import com.github.aarsy.currency_converter.utils.CurrencyConverterUtils;

import java.util.HashMap;
import java.util.Map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrencyRepository {
    private CurrencyLayerService currencyService;
    private volatile static CurrencyRepository currencyRepository;
    private LocalCacheHandler localCacheHandler;
    private Map<String, String> apiKeyMap;
    private MutableLiveData<CurrencyList> currencyMutableData;
    private MutableLiveData<CurrencyExchangeRatesList> exchangeRatesMutableData;

    private CurrencyRepository(LocalCacheHandler localCacheHandler) {
        if (currencyRepository != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CurrencyConverterConstants.EndPoints.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiKeyMap = new HashMap<>();
        apiKeyMap.put(CurrencyConverterConstants.Headers.KEY_ACCESS_TOKEN, CurrencyConverterConstants.Headers.VALUE_ACCESS_TOKEN);
        this.localCacheHandler = localCacheHandler;
        currencyService = retrofit.create(CurrencyLayerService.class);
    }

    public static CurrencyRepository getInstance(LocalCacheHandler localCacheHandler) {
        if (currencyRepository == null) {
            synchronized (CurrencyRepository.class) {
                if (currencyRepository == null) {
                    currencyRepository = new CurrencyRepository(localCacheHandler);
                }
            }
        }
        return currencyRepository;
    }

    public LiveData<CurrencyList> getAvailableCurrencyList() {
        currencyMutableData = new MutableLiveData<>();
        CurrencyList currencyList = localCacheHandler.getCurrencyList();
        if (currencyList != null && CurrencyConverterUtils.isDifferenceLessThanThirtyMinutes(localCacheHandler.getInsertTime(), System.currentTimeMillis())) {
            currencyMutableData.setValue(currencyList);
            Log.d("FetchCurrencyList", "From Local");
        } else {
            localCacheHandler.deleteAllData();
            fetchCurrencyListFromRemote();
            Log.d("FetchCurrencyList", "From Remote");
        }
        return currencyMutableData;
    }

    private void fetchCurrencyListFromRemote() {
        currencyService.getCurrencyList(apiKeyMap).enqueue(new Callback<CurrencyList>() {
            @Override
            public void onResponse(Call<CurrencyList> call, Response<CurrencyList> response) {
                localCacheHandler.insertCurrencyList(response.body(), System.currentTimeMillis());
                currencyMutableData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<CurrencyList> call, Throwable t) {
                currencyMutableData.setValue(null);
            }
        });
    }

    public LiveData<CurrencyExchangeRatesList> getLiveExchangeRates() {
        exchangeRatesMutableData = new MutableLiveData<>();
        CurrencyExchangeRatesList exchangeRatesList = localCacheHandler.getExchangeRates();
        if (exchangeRatesList != null && CurrencyConverterUtils.isDifferenceLessThanThirtyMinutes(localCacheHandler.getInsertTime(), System.currentTimeMillis())) {
            exchangeRatesMutableData.setValue(exchangeRatesList);
            Log.d("FetchExchangeList", "From Local");
        } else {
            localCacheHandler.deleteAllData();
            fetchExchangeRatesFromRemote();
            Log.d("FetchExchangeList", "From Remote");
        }
        return exchangeRatesMutableData;
    }

    private void fetchExchangeRatesFromRemote() {
        currencyService.getCurrencyRates(apiKeyMap).enqueue(new Callback<CurrencyExchangeRatesList>() {
            @Override
            public void onResponse(Call<CurrencyExchangeRatesList> call, Response<CurrencyExchangeRatesList> response) {
                localCacheHandler.insertExchangeRates(response.body());
                exchangeRatesMutableData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<CurrencyExchangeRatesList> call, Throwable t) {
                exchangeRatesMutableData.setValue(null);
            }
        });
    }

}
