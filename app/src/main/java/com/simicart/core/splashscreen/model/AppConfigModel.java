package com.simicart.core.splashscreen.model;

import android.util.Log;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.network.request.multi.SimiRequest;
import com.simicart.core.splashscreen.entity.AppConfigEnitity;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Sony on 12/10/2015.
 */
public class AppConfigModel extends SimiModel{
    @Override
    protected void setUrlAction() {
        addDataExtendURL("app-configs");
    }

    @Override
    protected void setTypeMethod() {
        mTypeMethod = SimiRequest.Method.GET;
    }

    @Override
    protected void paserData() {
        super.paserData();
        if(mJSONResult != null){
            if(mJSONResult.has("app-configs")){
                try {
                    JSONArray appConfigArr = mJSONResult.getJSONArray("app-configs");
                    if(appConfigArr != null) {
                        if (appConfigArr.length() > 0) {
                            for (int i = 0; i < appConfigArr.length(); i++){
                                AppConfigEnitity appConfigEnitity = new AppConfigEnitity();
                                appConfigEnitity.setJSONObject(appConfigArr.getJSONObject(i));
                                appConfigEnitity.parse();
                                collection.addEntity(appConfigEnitity);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
