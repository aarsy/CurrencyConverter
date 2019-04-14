package com.github.aarsy.currency_converter.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CurrencyConverterUtils {

    public static boolean isDifferenceLessThanThirtyMinutes(long timestamp, long timestamp2) {
        return (Math.max(timestamp, timestamp2) - Math.min(timestamp, timestamp2)) <= 30 * 60 * 1000;
    }

    public static String formatBigDecimalToThreeDecimalPlaces(BigDecimal bigDecimal){
        bigDecimal = bigDecimal.setScale(3, BigDecimal.ROUND_DOWN);
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(3);
        df.setMinimumFractionDigits(0);
        df.setGroupingUsed(false);
        return df.format(bigDecimal);
    }
}

