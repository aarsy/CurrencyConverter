package com.github.aarsy.currency_converter.view.activity;

import android.os.Bundle;


import com.github.aarsy.currency_converter.R;
import com.github.aarsy.currency_converter.view.fragment.CurrencyConverterFragment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


/**
 * @author abhayrajyadav-pro
 */
public class CurrencyConverterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_converter);
        if (savedInstanceState == null) {
            CurrencyConverterFragment fragment = new CurrencyConverterFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, CurrencyConverterFragment.TAG).commit();
        }
    }
}
