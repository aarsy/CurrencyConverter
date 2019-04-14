package com.github.aarsy.currency_converter.viewmodel;

import android.app.Application;

import com.github.aarsy.currency_converter.database.LocalCacheHandler;
import com.github.aarsy.currency_converter.service.model.CurrencyExchangeModel;
import com.github.aarsy.currency_converter.service.model.CurrencyExchangeRatesList;
import com.github.aarsy.currency_converter.service.model.CurrencyItem;
import com.github.aarsy.currency_converter.service.model.CurrencyList;
import com.github.aarsy.currency_converter.service.model.CurrencyRateItem;
import com.github.aarsy.currency_converter.service.repository.CurrencyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

public class CurrencyConverterViewModel extends AndroidViewModel {

    private LiveData<List<CurrencyItem>> currencyItemListObservable;
    private LiveData<CurrencyExchangeModel> currencyRateItemListObservable;
    private final LocalCacheHandler databaseWrapper;


    public CurrencyConverterViewModel(@NonNull Application application) {
        super(application);
        databaseWrapper = new LocalCacheHandler(application.getApplicationContext());
        fetchCurrencyList();
        //Wait a few millis to fetch Exchange Rates
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        fetchExchangeRatesList();

    }


    private void fetchCurrencyList() {
        LiveData<CurrencyList> currencyListObservable = CurrencyRepository.getInstance(databaseWrapper).getAvailableCurrencyList();
        currencyItemListObservable = Transformations.map(currencyListObservable, new Function<CurrencyList, List<CurrencyItem>>() {
            @Override
            public List<CurrencyItem> apply(CurrencyList input) {
                List<CurrencyItem> currencyItems = null;
                if (input == null || input.getCurrencies() == null) {
                    return currencyItems;
                }
                currencyItems = new ArrayList<>();
                for (Map.Entry<String, String> entry : input.getCurrencies().entrySet()) {
                    currencyItems.add(new CurrencyItem(entry.getKey(), entry.getValue()));
                }

                return currencyItems;
            }
        });
    }

    private void fetchExchangeRatesList() {
        LiveData<CurrencyExchangeRatesList> currencyExchangeRatesListObservable = CurrencyRepository.getInstance(databaseWrapper).getLiveExchangeRates();
        currencyRateItemListObservable = Transformations.map(currencyExchangeRatesListObservable, new Function<CurrencyExchangeRatesList, CurrencyExchangeModel>() {

            @Override
            public CurrencyExchangeModel apply(CurrencyExchangeRatesList input) {
                CurrencyExchangeModel currencyRateModel = null;
                if (input == null || input.getCurrencies() == null || input.getCurrencies().size() == 0) {
                    return currencyRateModel;
                }
                currencyRateModel = new CurrencyExchangeModel();
                ArrayList<CurrencyRateItem> currencyRateItems = new ArrayList<>();
                for (Map.Entry<String, Double> entry : input.getCurrencies().entrySet()) {
                    currencyRateItems.add(new CurrencyRateItem(entry.getKey(), entry.getValue()));
                }
                currencyRateModel.setCurrencyRateItems(currencyRateItems);
                currencyRateModel.setSource(input.getSource());

                return currencyRateModel;
            }
        });
    }


    public LiveData<List<CurrencyItem>> getCurrencyItemListObservable() {
        return currencyItemListObservable;
    }

    public LiveData<CurrencyExchangeModel> getCurrencyRateItemListObservable() {
        return currencyRateItemListObservable;
    }
}
