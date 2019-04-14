package com.github.aarsy.currency_converter.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.aarsy.currency_converter.R;
import com.github.aarsy.currency_converter.service.model.CurrencyItem;
import com.github.aarsy.currency_converter.service.model.CurrencyRateItem;

import java.math.BigDecimal;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyViewHolder> {
    List<CurrencyRateItem> currencyRateItems;
    List<CurrencyItem> currencyItems;
    private CurrencyRateItem selectedCurrencyRateItem;
    private BigDecimal rateMultiplier;


    public CurrencyAdapter(Context context) {
    }

    @NonNull
    @Override
    public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_exchange_rates_list_item, parent, false);
        return new CurrencyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyViewHolder holder, int position) {
        holder.setSourceAndMultipiler(selectedCurrencyRateItem, rateMultiplier);
        if (position < currencyRateItems.size() && position < currencyItems.size()) {
            holder.bindData(currencyRateItems.get(position), currencyItems.get(position));
        } else {
            holder.bindData(currencyRateItems.get(position));
        }
    }

    public void setCurrencyRateList(List<CurrencyRateItem> currencyRateItems, List<CurrencyItem> currencyItems){
        this.currencyRateItems =currencyRateItems;
        this.currencyItems=currencyItems;
    }

    @Override
    public int getItemCount() {
        return currencyRateItems == null ? 0 : currencyRateItems.size();
    }

    public void setSourceCurrency(CurrencyItem currencyItem, BigDecimal multiplier) {
        if(currencyRateItems==null){
            return;
        }
        CurrencyRateItem currencyRateItem=new CurrencyRateItem();
        currencyRateItem.setCurrencyCountry(currencyItem.getCurrencyName());
        int index=currencyRateItems.indexOf(currencyRateItem);
        this.rateMultiplier=multiplier;
        if(index!=-1){
            selectedCurrencyRateItem=currencyRateItems.get(index);
        }

    }
}
