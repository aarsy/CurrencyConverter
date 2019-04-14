package com.github.aarsy.currency_converter.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.github.aarsy.currency_converter.service.model.CurrencyExchangeRatesList;
import com.github.aarsy.currency_converter.service.model.CurrencyList;
import com.google.gson.Gson;

public class LocalCacheHandler {
    private static final String KEY_CURRENCY_LIST = "currencyList";
    private static final String KEY_DURATION = "duration";
    private static final String KEY_EXCHANGE_LIST = "exchangeList";
    private final String name = "CurrencyConverterPref";
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPrefs;


    public LocalCacheHandler(Context context) {
        if (context != null && !TextUtils.isEmpty(name)) {
            sharedPrefs = context.getSharedPreferences(name, Context.MODE_PRIVATE);
            editor = sharedPrefs.edit();
        }
    }

    public void insertCurrencyList(CurrencyList currencyList, long duration) {
        Gson gson = new Gson();
        String json = gson.toJson(currencyList);
        editor.putString(KEY_CURRENCY_LIST, json);
        editor.putLong(KEY_DURATION, duration);
        editor.commit();
    }

    public CurrencyList getCurrencyList() {
        Gson gson = new Gson();
        String json = sharedPrefs.getString(KEY_CURRENCY_LIST, "");
        return gson.fromJson(json, CurrencyList.class);
    }

    public void insertExchangeRates(CurrencyExchangeRatesList exchangeRatesList) {
        Gson gson = new Gson();
        String json = gson.toJson(exchangeRatesList);
        editor.putString(KEY_EXCHANGE_LIST, json);
        editor.commit();
    }

    public CurrencyExchangeRatesList getExchangeRates() {
        Gson gson = new Gson();
        String json = sharedPrefs.getString(KEY_EXCHANGE_LIST, "");
        return gson.fromJson(json, CurrencyExchangeRatesList.class);
    }

    private void deleteCurrencyList(){
        editor.remove(KEY_CURRENCY_LIST);
        editor.commit();
    }

    private void deleteExchangeList(){
        editor.remove(KEY_EXCHANGE_LIST);
        editor.commit();
    }

    private void deleteDuration(){
        editor.remove(KEY_DURATION);
        editor.commit();
    }

    public void deleteAllData(){
        deleteCurrencyList();
        deleteDuration();
        deleteExchangeList();
    }


    public long getInsertTime() {
        return sharedPrefs.getLong(KEY_DURATION, 0);
    }
}
