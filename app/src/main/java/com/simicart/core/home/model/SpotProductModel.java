package com.simicart.core.home.model;

import android.util.Log;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.base.network.request.multi.SimiRequest;
import com.simicart.core.home.entity.SpotProductEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by MSI on 04/12/2015.
 */
public class SpotProductModel extends SimiModel {


    private String spot_products = "spot-products";
    private int mTotalSport = -1;

    @Override
    protected void setUrlAction() {
        addDataExtendURL("spot-products");
    }

    @Override
    protected void setTypeMethod() {
        mTypeMethod = SimiRequest.Method.GET;
    }

    @Override
    protected void paserData() {
        super.paserData();
        if (mJSONResult.has(spot_products)) {
            mTotalSport = mTotal;
            JSONArray array = null;
            try {
                array = mJSONResult.getJSONArray(spot_products);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (null != array && array.length() > 0) {
                ArrayList<SimiEntity> spots = new ArrayList<SimiEntity>();
                for (int i = 0; i < array.length(); i++) {
                    try {
                        JSONObject json = array.getJSONObject(i);
                        SpotProductEntity entity = new SpotProductEntity();
                        entity.setJSONObject(json);
                        entity.parse();
                        spots.add(entity);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                collection.setCollection(spots);
            }
        }
    }

    public int getTotalSport() {
        return mTotalSport;
    }
}
