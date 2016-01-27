package com.simicart.plugins.ipay.entity;

import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.common.Utils;

/**
 * Created by Sony on 1/26/2016.
 */
public class IpayEntity extends SimiEntity{
    protected String merchantCode;
    protected String merchantKey;
    protected String mTitle;
    protected boolean sandBox;
    protected String mCountry;
    protected String mUrl;

    private String merchant_code = "merchant_code";
    private String merchant_key = "merchant_key";
    private String title = "title";
    private String is_sandbox = "enable";
    private String country_id = "merchant_country";
    private String url = "backend_post_url";

    @Override
    public void parse() {
        if(mJSON != null){
            if(mJSON.has(merchant_code)){
                merchantCode = getData(merchant_code);
            }

            if(mJSON.has(merchant_key)){
                merchantKey = getData(merchant_key);
            }

            if(mJSON.has(title)){
                mTitle = getData(title);
            }

            if(mJSON.has(is_sandbox)){
                String sandBoxValue = getData(is_sandbox);
                if(Utils.validateString(sandBoxValue) && sandBoxValue.equals("1")){
                    sandBox = true;
                }
            }

            if(mJSON.has(country_id)){
                mCountry = getData(country_id);
            }

            if(mJSON.has(url)){
                mUrl = getData(url);
            }
        }
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getMerchantKey() {
        return merchantKey;
    }

    public void setMerchantKey(String merchantKey) {
        this.merchantKey = merchantKey;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public boolean isSandBox() {
        return sandBox;
    }

    public void setSandBox(boolean sandBox) {
        this.sandBox = sandBox;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String mCountry) {
        this.mCountry = mCountry;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }
}
