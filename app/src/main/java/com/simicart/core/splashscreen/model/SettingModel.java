
package com.simicart.core.splashscreen.model;

import android.util.Log;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.base.network.request.multi.SimiRequest;
import com.simicart.core.splashscreen.entity.ConfigEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by MSI on 02/12/2015.
 */
public class SettingModel extends SimiModel{
    private String settings = "settings";

    @Override
    protected void setUrlAction() {
        addDataExtendURL("settings");
    }

    @Override
    protected void setTypeMethod() {
        mTypeMethod = SimiRequest.Method.GET;
    }

    @Override
    protected void paserData() {
        super.paserData();

        Log.e("SettingModel", mJSONResult.toString());
        if(mJSONResult.has(settings)){
            try {
                ConfigEntity entity = new ConfigEntity();
                entity.setJSONObject(mJSONResult.getJSONObject(settings));
                entity.parse();
                ArrayList<SimiEntity> arrayEnitty = new ArrayList<SimiEntity>();
                arrayEnitty.add(entity);
                collection.setCollection(arrayEnitty);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

}



