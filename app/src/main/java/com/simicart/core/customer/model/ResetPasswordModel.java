package com.simicart.core.customer.model;

import android.util.Log;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.network.request.multi.SimiRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by James Crabby on 12/11/2015.
 */
public class ResetPasswordModel extends SimiModel {
    @Override
    protected void setUrlAction() {
        addDataExtendURL("customer-account");
    }

    @Override
    protected void setTypeMethod() {
        mTypeMethod = SimiRequest.Method.POST;
    }

    @Override
    protected void paserData() {
        super.paserData();

        Log.e("ResetPasswordModel", mJSONResult.toString());
    }
}
