package com.simicart.core.customer.model;

import android.util.Log;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.base.network.request.multi.SimiRequest;
import com.simicart.core.customer.entity.ProfileEntity;
import com.simicart.core.splashscreen.entity.CMSPageEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by James Crabby on 12/4/2015.
 */
public class LogInModel extends SimiModel {
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

        Log.e("LogInModel", mJSONResult.toString());
        if (mJSONResult.has("customer")) {
            try {
                JSONObject customerObj = mJSONResult.getJSONObject("customer");
                ProfileEntity profileEntity = new ProfileEntity();
                profileEntity.setJSONObject(customerObj);
                profileEntity.parse();
                collection.addEntity(profileEntity);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
