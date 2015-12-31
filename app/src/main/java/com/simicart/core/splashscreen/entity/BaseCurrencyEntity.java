package com.simicart.core.splashscreen.entity;

import com.simicart.core.base.model.entity.SimiEntity;

/**
 * Created by Sony on 12/3/2015.
 */
public class BaseCurrencyEntity extends SimiEntity{
    protected String mCode;
    protected String mSymbol;
    protected String mName;

    private String code = "code";
    private String symbol = "symbol";
    private String name = "name";

    @Override
    public void parse() {
        if(mJSON != null){
            if(mJSON.has(code)){
                mCode = getData(code);
            }

            if(mJSON.has(symbol)){
                mSymbol = getData(symbol);
            }

            if(mJSON.has(name)){
                mName = getData(name);
            }
        }
    }

    public String getCode() {
        return mCode;
    }

    public void setCode(String mCode) {
        this.mCode = mCode;
    }

    public String getSymbol() {
        return mSymbol;
    }

    public void setSymbol(String mSymbol) {
        this.mSymbol = mSymbol;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }
}
