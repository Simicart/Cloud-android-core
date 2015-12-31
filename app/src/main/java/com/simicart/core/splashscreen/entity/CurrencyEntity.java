package com.simicart.core.splashscreen.entity;

import android.util.Log;

import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.common.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sony on 12/3/2015.
 */
public class CurrencyEntity extends SimiEntity{
    protected BaseCurrencyEntity mBaseCurrency;
    protected String currencyPosition;
    protected String thousandSeparator;
    protected String decimalSeparator;
    protected String numberOfDecimals;

    private String base_currency = "base_currency";
    private String currency_position = "currency_position";
    private String thousand_separator = "thousand_separator";
    private String decimal_separator = "decimal_separator";
    private String number_of_decimals = "number_of_decimals";

    @Override
    public void parse() {
        if(mJSON != null){
            if(mJSON.has(base_currency)){
                String baseCurrencyValue = getData(base_currency);
                if(Utils.validateString(baseCurrencyValue)){
                    mBaseCurrency = new BaseCurrencyEntity();
                    try {
                        mBaseCurrency.setJSONObject(new JSONObject(baseCurrencyValue));
                        mBaseCurrency.parse();
                    } catch (JSONException e) {
                        mBaseCurrency = null;
                        Log.e("CurrencyEntity_Base", e.getMessage());
                    }
                }
            }

            if(mJSON.has(currency_position)){
                currencyPosition = getData(currency_position);
            }

            if(mJSON.has(thousand_separator)){
                thousandSeparator = getData(thousand_separator);
            }

            if(mJSON.has(decimal_separator)){
                decimalSeparator = getData(decimal_separator);
            }

            if(mJSON.has(number_of_decimals)){
                numberOfDecimals = getData(number_of_decimals);
            }
        }
    }

    public BaseCurrencyEntity getBaseCurrency() {
        return mBaseCurrency;
    }

    public void setBaseCurrency(BaseCurrencyEntity mBaseCurrency) {
        this.mBaseCurrency = mBaseCurrency;
    }

    public String getCurrencyPosition() {
        return currencyPosition;
    }

    public void setCurrencyPosition(String currencyPosition) {
        this.currencyPosition = currencyPosition;
    }

    public String getThousandSeparator() {
        return thousandSeparator;
    }

    public void setThousandSeparator(String thousandSeparator) {
        this.thousandSeparator = thousandSeparator;
    }

    public String getDecimalSeparator() {
        return decimalSeparator;
    }

    public void setDecimalSeparator(String decimalSeparator) {
        this.decimalSeparator = decimalSeparator;
    }

    public String getNumberOfDecimals() {
        return numberOfDecimals;
    }

    public void setNumberOfDecimals(String numberOfDecimals) {
        this.numberOfDecimals = numberOfDecimals;
    }
}
