package com.simicart.core.customer.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.base.network.request.multi.SimiRequest;
import com.simicart.core.config.Constants;
import com.simicart.core.customer.entity.CountryAllowed;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
            ArrayList<SimiEntity> listCollection = new ArrayList<SimiEntity>();
            if(list.length() > 0){
                for (int i = 0; i < list.length(); i++) {
                    CountryAllowed country_allowed = new CountryAllowed();
                    country_allowed.setJSONObject(list.getJSONObject(i));
                    country_allowed.parse();
                    listCollection.add(country_allowed);
                }
            }
            Collections.sort(listCollection, new Comparator<SimiEntity>() {
                @Override
                public int compare(SimiEntity lhs, SimiEntity rhs) {
                    CountryAllowed c1 = (CountryAllowed) lhs;
                    CountryAllowed c2 = (CountryAllowed) rhs;
                    return c1.getName().compareToIgnoreCase(c2.getName());
                }
            });
            collection.setCollection(listCollection);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
