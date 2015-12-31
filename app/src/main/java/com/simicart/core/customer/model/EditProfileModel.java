package com.simicart.core.customer.model;

import android.util.Log;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.network.request.multi.SimiRequest;
import com.simicart.core.customer.entity.ProfileEntity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by James Crabby on 12/14/2015.
 */
public class EditProfileModel extends SimiModel {

    @Override
    protected void setUrlAction() {
        addDataExtendURL("customers");
    }

    @Override
    protected void setTypeMethod() {
        mTypeMethod = SimiRequest.Method.PUT;
    }

    @Override
    protected void paserData() {
        super.paserData();
        if(mJSONResult != null){
            Log.e("EditProfileModel", mJSONResult.toString());
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
