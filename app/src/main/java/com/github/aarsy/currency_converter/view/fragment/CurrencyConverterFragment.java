package com.github.aarsy.currency_converter.view.fragment;


import android.os.Bundle;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.github.aarsy.currency_converter.R;
import com.github.aarsy.currency_converter.service.model.CurrencyExchangeModel;
import com.github.aarsy.currency_converter.service.model.CurrencyItem;
import com.github.aarsy.currency_converter.service.model.CurrencyRateItem;
import com.github.aarsy.currency_converter.view.adapter.CurrencyAdapter;
import com.github.aarsy.currency_converter.view.adapter.CustomSpinnerArrayAdapter;
import com.github.aarsy.currency_converter.viewmodel.CurrencyConverterViewModel;

import java.math.BigDecimal;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CurrencyConverterFragment extends Fragment {
    public static final String TAG = CurrencyConverterFragment.class.getSimpleName();
    private CurrencyAdapter projectAdapter;
    private Spinner spinnerSourceCurrency;
    private RecyclerView rvTargetCurrencyList;
    private EditText etCurrencyValue;
    private CustomSpinnerArrayAdapter dataAdapter;
    private List<CurrencyItem> currencyList;
    private List<CurrencyRateItem> currencyRateList;
    private BigDecimal defaultMultiplierAmount = new BigDecimal(0);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_currency_converter, container, false);
        projectAdapter = new CurrencyAdapter(getContext());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinnerSourceCurrency = view.findViewById(R.id.source_currency_list);
        rvTargetCurrencyList = view.findViewById(R.id.currency_list);
        etCurrencyValue = view.findViewById(R.id.currency_value);
        rvTargetCurrencyList.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rvTargetCurrencyList.setAdapter(projectAdapter);
        setSpinnerItemSelectedListener();
        setAmountChangeListener();
    }

    private void setAmountChangeListener() {
        etCurrencyValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                CurrencyItem currencyItem = (CurrencyItem) spinnerSourceCurrency.getSelectedItem();
                projectAdapter.setSourceCurrency(currencyItem, getMultiplierAmount());
                projectAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setSpinnerItemSelectedListener() {
        spinnerSourceCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                projectAdapter.setSourceCurrency(currencyList.get(position), getMultiplierAmount());
                projectAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final CurrencyConverterViewModel viewModel =
                ViewModelProviders.of(this).get(CurrencyConverterViewModel.class);

        observeAvailableCurrencyViewModel(viewModel);
        observeCurrencyExchangeRatesViewModel(viewModel);
    }

    private void observeAvailableCurrencyViewModel(CurrencyConverterViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getCurrencyItemListObservable().observe(this, new Observer<List<CurrencyItem>>() {
            @Override
            public void onChanged(List<CurrencyItem> currencyItems) {
                dataAdapter = new CustomSpinnerArrayAdapter(getActivity(), R.layout.spinner_list_item, R.id.textView_name, currencyItems);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                currencyList = currencyItems;
                spinnerSourceCurrency.setAdapter(dataAdapter);
            }
        });
    }

    private void observeCurrencyExchangeRatesViewModel(CurrencyConverterViewModel viewModel) {
        viewModel.getCurrencyRateItemListObservable().observe(this, new Observer<CurrencyExchangeModel>() {
            @Override
            public void onChanged(CurrencyExchangeModel currencyExchangeModel) {
                currencyRateList = currencyExchangeModel.getCurrencyRateItems();
                if (projectAdapter != null) {
                    projectAdapter.setCurrencyRateList(currencyExchangeModel.getCurrencyRateItems(), currencyList);
                    CurrencyItem currencyItem = (CurrencyItem) spinnerSourceCurrency.getSelectedItem();
                    projectAdapter.setSourceCurrency(currencyItem, getMultiplierAmount());
                    projectAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private BigDecimal getMultiplierAmount() {
        if (TextUtils.isEmpty(etCurrencyValue.getText())) {
            return defaultMultiplierAmount;
        } else {
            return new BigDecimal(etCurrencyValue.getText().toString());
        }


    }
}
