package com.simicart.plugins.payuindia.model;

import android.util.Log;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.network.request.multi.SimiRequest;
import com.simicart.plugins.payuindia.entity.PayUEntity;

import org.json.JSONException;

/**
 * Created by Sony on 2/3/2016.
 */
public class GetHashModel extends SimiModel {

    @Override
    protected void setUrlAction() {
        addDataExtendURL("payu-biz");
    }

    @Override
    protected void setTypeMethod() {
        mTypeMethod = SimiRequest.Method.POST;
    }

    @Override
    protected void paserData() {
        super.paserData();
        if (mJSONResult != null) {
            PayUEntity payUEntity = new PayUEntity();
            payUEntity.setJSONObject(mJSONResult);
            payUEntity.parse();
            collection.addEntity(payUEntity);
        }
    }
}
