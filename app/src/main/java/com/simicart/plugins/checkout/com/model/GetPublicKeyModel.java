package com.simicart.plugins.checkout.com.model;

import android.util.Log;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.network.request.multi.SimiRequest;

import org.json.JSONException;

/**
 * Created by James Crabby on 1/14/2016.
 */
public class GetPublicKeyModel extends SimiModel {

    public String publickey = "";

    @Override
    protected void setUrlAction() {
        addDataExtendURL("checkout-com/public-key");
    }

    @Override
    protected void setTypeMethod() {
        mTypeMethod = SimiRequest.Method.GET;
    }

    @Override
    protected void paserData() {
        super.paserData();

        Log.e("GetPublicKeyModel", mJSONResult.toString());
        if(mJSONResult.has("public_key")) {
            try {
                publickey = mJSONResult.getString("public_key");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public String getPublickey() {
        return publickey;
    }

    public void setPublickey(String publickey) {
        this.publickey = publickey;
    }
}
