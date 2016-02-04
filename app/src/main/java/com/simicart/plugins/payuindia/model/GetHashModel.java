package com.simicart.plugins.payuindia.model;

import android.util.Log;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.network.request.multi.SimiRequest;

import org.json.JSONException;

/**
 * Created by Sony on 2/3/2016.
 */
public class GetHashModel extends SimiModel {
    protected String merchantKey;
    protected String salt;
    protected String sUrl;
    protected String fUrl;

    @Override
    protected void setUrlAction() {
        addDataExtendURL("payu-biz");
    }

    @Override
    protected void setTypeMethod() {
        mTypeMethod = SimiRequest.Method.GET;
    }

    @Override
    protected void paserData() {
        super.paserData();
        if(mJSONResult != null){
            Log.e("GetHasModelPayUIndia", mJSONResult.toString());
            if(mJSONResult.has("merchant_key")){
                try {
                    merchantKey = mJSONResult.getString("merchant_key");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (mJSONResult.has("salt")) {
                try {
                    salt = mJSONResult.getString("salt");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (mJSONResult.has("surl")) {
                try {
                    sUrl = mJSONResult.getString("surl");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if(mJSONResult.has("furl")){
                try {
                    fUrl = mJSONResult.getString("furl");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getMerchantKey() {
        return merchantKey;
    }

    public void setMerchantKey(String merchantKey) {
        this.merchantKey = merchantKey;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getsUrl() {
        return sUrl;
    }

    public void setsUrl(String sUrl) {
        this.sUrl = sUrl;
    }

    public String getfUrl() {
        return fUrl;
    }

    public void setfUrl(String fUrl) {
        this.fUrl = fUrl;
    }
}
