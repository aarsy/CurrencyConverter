package com.github.aarsy.currency_converter.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.github.aarsy.currency_converter.R;
import com.github.aarsy.currency_converter.service.model.CurrencyItem;

import java.util.List;

public class CustomSpinnerArrayAdapter extends ArrayAdapter<CurrencyItem> {

    LayoutInflater flater;

    public CustomSpinnerArrayAdapter(Activity context, int resouceId, int textviewId, List<CurrencyItem> list) {

        super(context, resouceId, textviewId, list);
//        flater = context.getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return rowview(convertView, position);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return rowview(convertView, position);
    }

    private View rowview(View convertView, int position) {

        CurrencyItem rowItem = getItem(position);

        AdapterViewHolder holder;
        View rowview = convertView;
        if (rowview == null) {

            holder = new AdapterViewHolder();
            flater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowview = flater.inflate(R.layout.spinner_list_item, null, false);

            holder.txtTitle = (TextView) rowview.findViewById(R.id.textView_name);
            rowview.setTag(holder);
        } else {
            holder = (AdapterViewHolder) rowview.getTag();
        }
        if (rowItem != null && !TextUtils.isEmpty(rowItem.getCurrencyName()) && !TextUtils.isEmpty(rowItem.getCurrencyName()))
            holder.txtTitle.setText(new StringBuilder()
                    .append(rowItem.getCurrencyCounrty())
                    .append(" (")
                    .append(rowItem.getCurrencyName())
                    .append(")").toString());

        return rowview;
    }

    private class AdapterViewHolder {
        TextView txtTitle;
    }
}