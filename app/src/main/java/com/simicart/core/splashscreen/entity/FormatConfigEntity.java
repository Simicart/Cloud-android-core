package com.simicart.core.splashscreen.entity;

import android.util.Log;

import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.common.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by MSI on 02/12/2015.
 */
public class FormatConfigEntity extends SimiEntity {
    protected CurrencyEntity mCurrency;
    protected DateTimeEntity mDateTime;

    private String currency = "currency";
    private String datetime = "datetime";

    @Override
    public void parse() {
        if (mJSON != null) {
            Log.e("FormatCurrentCy1", "Currency1");
            if (mJSON.has(currency)) {
                Log.e("FormatCurrentCy", "Currency");
                String currencyValue = getData(currency);
                if (Utils.validateString(currencyValue)) {
                    mCurrency = new CurrencyEntity();
                    try {
                        mCurrency.setJSONObject(new JSONObject(currencyValue));
                        mCurrency.parse();
                    } catch (JSONException e) {
                        mCurrency = null;
                        Log.e("FormatConfig_Currency", e.getMessage());
                    }
                }
            }

            if (mJSON.has(datetime)) {
                String dateTimeValue = getData(datetime);
                if (Utils.validateString(dateTimeValue)) {
                    mDateTime = new DateTimeEntity();
                    try {
                        mDateTime.setJSONObject(new JSONObject(dateTimeValue));
                        mDateTime.parse();
                    } catch (JSONException e) {
                        mDateTime = null;
                        Log.e("FormatConfig_Datetime", e.getMessage());
                    }
                }
            }
        }
    }

    public CurrencyEntity getCurrency() {
        return mCurrency;
    }

    public void setCurrency(CurrencyEntity mCurrency) {
        this.mCurrency = mCurrency;
    }

    public DateTimeEntity getDateTime() {
        return mDateTime;
    }

    public void setDateTime(DateTimeEntity mDateTime) {
        this.mDateTime = mDateTime;
    }
}
