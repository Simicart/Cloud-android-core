package com.simicart.core.customer.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.multi.SimiRequest;
import com.simicart.core.config.Constants;
import com.simicart.core.customer.entity.CountryAllowed;

public class GetCountryModel extends SimiModel {

    @Override
    protected void setUrlAction() {
        addDataExtendURL("countries");
    }

    @Override
    protected void setTypeMethod() {
        mTypeMethod = SimiRequest.Method.GET;
    }

    @Override
    protected void paserData() {
        super.paserData();
        try {
            Log.e("GetCountryModel", mJSONResult.toString());
            JSONArray list = mJSONResult.getJSONArray("countries");
            if(list.length() > 0){
                for (int i = 0; i < list.length(); i++) {
                    CountryAllowed country_allowed = new CountryAllowed();
                    country_allowed.setJSONObject(list.getJSONObject(i));
                    country_allowed.parse();
                    collection.addEntity(country_allowed);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
