package com.simicart.theme.materialtheme.checkout.model;

import android.util.Log;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.network.request.multi.SimiRequest;
import com.simicart.core.customer.entity.ProfileEntity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sony on 4/12/2016.
 */
public class MaterialAddressModel extends SimiModel {

    @Override
    protected void setUrlAction() {
        addDataExtendURL("customers");
    }

    @Override
    protected void setTypeMethod() {
        mTypeMethod = SimiRequest.Method.GET;
    }

    @Override
    protected void paserData() {
        super.paserData();
        if(mJSONResult != null){
            Log.e("AddressBookModel", mJSONResult.toString());
            if(mJSONResult.has("customer")) {
                try {
                    JSONObject addressObj = mJSONResult.getJSONObject("customer");
                    ProfileEntity profileEntity = new ProfileEntity();
                    profileEntity.setJSONObject(addressObj);
                    profileEntity.parse();
                    collection.addEntity(profileEntity);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
