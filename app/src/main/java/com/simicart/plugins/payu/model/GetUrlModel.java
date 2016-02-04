package com.simicart.plugins.payu.model;

import android.util.Log;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.network.request.multi.SimiRequest;

import org.json.JSONException;

/**
 * Created by James Crabby on 1/26/2016.
 */
public class GetUrlModel extends SimiModel {

    String url = "";

    @Override
    protected void setUrlAction() {
        addDataExtendURL("payu/checkout");
    }

    @Override
    protected void setTypeMethod() {
        mTypeMethod = SimiRequest.Method.POST;
    }

    @Override
    protected void paserData() {
        super.paserData();

        Log.e("GetUrlModel", mJSONResult.toString());
        if(mJSONResult.has("url")) {
            try {
                url = mJSONResult.getString("url");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public String getUrl() {
        return url;
    }
}
