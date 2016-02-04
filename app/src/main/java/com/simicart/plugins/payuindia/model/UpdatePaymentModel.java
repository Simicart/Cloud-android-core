package com.simicart.plugins.payuindia.model;

import android.util.Log;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.network.request.multi.SimiRequest;
import com.simicart.core.checkout.entity.OrderEntity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sony on 1/13/2016.
 */
public class UpdatePaymentModel extends SimiModel{
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
        if(mJSONResult != null){
            Log.e("CCAVANUE UpdatePayment", mJSONResult.toString());
            if(mJSONResult.has("invoice")){
                try {
                    JSONObject orderObj = mJSONResult.getJSONObject("invoice");
                    OrderEntity orderEntity = new OrderEntity();
                    orderEntity.setJSONObject(orderObj);
                    orderEntity.parse();
                    collection.addEntity(orderEntity);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
