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


    private final LiveData<CurrencyList> currencyListObservable;
    private final LiveData<List<CurrencyItem>> currencyItemListObservable;
    private final LiveData<CurrencyExchangeRatesList> currencyExchangeRatesListObservable;
    private final LiveData<CurrencyExchangeModel> currencyRateItemListObservable;


    public CurrencyConverterViewModel(@NonNull Application application) {
        super(application);
        LocalCacheHandler databaseWrapper=new LocalCacheHandler(application.getApplicationContext());
        currencyListObservable = CurrencyRepository.getInstance(databaseWrapper).getAvailableCurrencyList();
        currencyItemListObservable = Transformations.map(currencyListObservable, new Function<CurrencyList, List<CurrencyItem>>() {
            @Override
            public List<CurrencyItem> apply(CurrencyList input) {
                List<CurrencyItem> currencyItems = null;
                if (input != null && input.getCurrencies() != null) {
                    currencyItems = new ArrayList<>();
                    for (Map.Entry<String, String> entry : input.getCurrencies().entrySet()) {
                        currencyItems.add(new CurrencyItem(entry.getKey(), entry.getValue()));
                    }
                }
                return currencyItems;
            }
        });


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        currencyExchangeRatesListObservable = CurrencyRepository.getInstance(databaseWrapper).getLiveExchangeRates();
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
