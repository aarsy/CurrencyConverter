package com.github.aarsy.currency_converter.view.adapter;

import android.view.View;
import android.widget.TextView;

import com.github.aarsy.currency_converter.R;
import com.github.aarsy.currency_converter.service.model.CurrencyItem;
import com.github.aarsy.currency_converter.service.model.CurrencyRateItem;
import com.github.aarsy.currency_converter.utils.CurrencyConverterUtils;

import java.math.BigDecimal;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class CurrencyViewHolder extends RecyclerView.ViewHolder {
    private final TextView place;
    private final TextView amount;
    private BigDecimal rateMultiplier;
    private CurrencyRateItem selectedCurrencyRateItem;

    public CurrencyViewHolder(@NonNull View itemView) {
        super(itemView);
        place = itemView.findViewById(R.id.place);
        amount = itemView.findViewById(R.id.amount);
    }

    public void bindData(CurrencyRateItem currencyRateItem, CurrencyItem currencyItem) {
        place.setText(currencyItem.getCurrencyCounrty()+ " ("+currencyItem.getCurrencyName()+")");
        if (currencyRateItem.getCurrencyExchangeRate() != null) {
            BigDecimal currencyPerUnit=new BigDecimal(currencyRateItem.getCurrencyExchangeRate() / selectedCurrencyRateItem.getCurrencyExchangeRate());
            BigDecimal mappedCurrencyFromSource = rateMultiplier.multiply(currencyPerUnit);
            amount.setText(String.valueOf(CurrencyConverterUtils.formatBigDecimalToThreeDecimalPlaces(mappedCurrencyFromSource)));
        }

    }

    public void bindData(CurrencyRateItem currencyRateItem) {
        place.setText(currencyRateItem.getCurrencyCountry());
        amount.setText(String.valueOf(currencyRateItem.getCurrencyExchangeRate()));
    }

    public void setSourceAndMultipiler(CurrencyRateItem selectedCurrencyRateItem, BigDecimal rateMultiplier) {
        this.selectedCurrencyRateItem = selectedCurrencyRateItem;
        this.rateMultiplier = rateMultiplier;
    }

}
