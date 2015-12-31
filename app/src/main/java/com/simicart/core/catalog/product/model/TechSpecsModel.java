package com.simicart.core.catalog.product.model;

import android.util.Log;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.network.request.multi.SimiRequest;
import com.simicart.core.catalog.product.entity.Attributes;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Sony on 12/9/2015.
 */
public class TechSpecsModel extends SimiModel{
    @Override
    protected void setUrlAction() {
        addDataExtendURL("attributes");
    }

    @Override
    protected void setTypeMethod() {
        mTypeMethod = SimiRequest.Method.GET;
    }

    @Override
    protected void paserData() {
        super.paserData();
        if(mJSONResult != null){
            Log.e("TechSpecsModel", mJSONResult.toString());
            if(mJSONResult.has("attributes")){
                try {
                    JSONArray attributeArr = mJSONResult.getJSONArray("attributes");
                    if(attributeArr.length() > 0){
                        for (int i = 0; i < attributeArr.length(); i++){
                            Attributes attributes = new Attributes();
                            attributes.setJSONObject(attributeArr.getJSONObject(i));
                            attributes.parse();
                            if(attributes.IsVisibleOnFront()) {
                                collection.addEntity(attributes);
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
