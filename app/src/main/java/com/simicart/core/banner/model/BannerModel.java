package com.simicart.core.banner.model;

import android.util.Log;

import com.simicart.core.banner.entity.BannerEntity;
import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.base.network.request.multi.SimiRequest;
import com.simicart.core.config.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BannerModel extends SimiModel {

    private String banner = "banners";


    @Override
    protected void setUrlAction() {
        addDataExtendURL("banners");
    }

    @Override
    protected void setTypeMethod() {
        mTypeMethod = SimiRequest.Method.GET;
    }

    @Override
    protected void paserData() {
        super.paserData();
        if (mJSONResult.has(banner)) {
            Log.e("BannerModel", mJSONResult.toString());
            JSONArray array = null;
            try {
                array = mJSONResult.getJSONArray(banner);
                if (null != array && array.length() > 0) {
                    ArrayList<SimiEntity> banners = new ArrayList<SimiEntity>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject js = array.getJSONObject(i);
                        BannerEntity entity = new BannerEntity();
                        entity.setJSONObject(js);
                        entity.parse();
                        if(entity.isEnable()){
                            banners.add(entity);
                        }
                    }
                    collection.setCollection(banners);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
