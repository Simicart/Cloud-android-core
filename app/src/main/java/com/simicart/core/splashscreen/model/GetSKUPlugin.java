package com.simicart.core.splashscreen.model;

import android.util.Log;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.network.request.multi.SimiRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by MSI on 18/01/2016.
 */
public class GetSKUPlugin extends SimiModel {

    private ArrayList<String> mSKU;
    private String public_plugins = "public_plugins";
    private String sku = "sku";

    public ArrayList<String> getListSKU()
    {
        return mSKU;
    }

    @Override
    protected void setUrlAction() {
        addDataExtendURL("public_plugins");

    }

    @Override
    protected void setTypeMethod() {
        mTypeMethod = SimiRequest.Method.GET;
    }

    @Override
    protected void paserData() {
        super.paserData();
        if(mJSONResult.has(public_plugins))
        {
            try {
                mSKU = new ArrayList<String>();
                JSONArray array = mJSONResult.getJSONArray(public_plugins);
                if(null != array && array.length() > 0)
                {
                    for(int i = 0; i < array.length(); i++)
                    {
                        JSONObject jsonPlugin = array.getJSONObject(i);
                        if(jsonPlugin.has(sku))
                        {
                            String sku_plugin = jsonPlugin.getString(sku);
                            Log.e("GetSKUPlugin ", "SKU " + sku_plugin);
                            mSKU.add(sku_plugin);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
