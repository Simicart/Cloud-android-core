package com.simicart.plugins.ccavanue.model;

import android.util.Log;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.network.request.multi.SimiRequest;

import org.json.JSONException;

/**
 * Created by Sony on 1/13/2016.
 */
public class GetRSAModel extends SimiModel{
    private String rsaKey = "";
    private String merchanID = "";
    private String accessCode = "";
    private String urlRedirect = "";

    @Override
    protected void setUrlAction() {
        addDataExtendURL("ccavenue");
    }

    @Override
    protected void setTypeMethod() {
        mTypeMethod = SimiRequest.Method.POST;
    }

    @Override
    protected void paserData() {
        super.paserData();
        if(mJSONResult != null){
            Log.e("CCAVANUE GetRSAModel", mJSONResult.toString());
            if(mJSONResult.has("rsa_key")){
                try {
                    rsaKey = mJSONResult.getString("rsa_key");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if(mJSONResult.has("merchant_id")){
                try {
                    merchanID = mJSONResult.getString("merchant_id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if(mJSONResult.has("access_code")){
                try {
                    accessCode = mJSONResult.getString("access_code");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if(mJSONResult.has("url")){
                try {
                    urlRedirect = mJSONResult.getString("url");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getRsaKey() {
        return rsaKey;
    }

    public void setRsaKey(String rsaKey) {
        this.rsaKey = rsaKey;
    }

    public String getMerchanID() {
        return merchanID;
    }

    public void setMerchanID(String merchanID) {
        this.merchanID = merchanID;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public String getUrlRedirect() {
        return urlRedirect;
    }

    public void setUrlRedirect(String urlRedirect) {
        this.urlRedirect = urlRedirect;
    }
}
