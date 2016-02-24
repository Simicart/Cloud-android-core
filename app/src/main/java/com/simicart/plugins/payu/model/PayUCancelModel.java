package com.simicart.plugins.payu.model;

import android.util.Log;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.network.request.multi.SimiRequest;

/**
 * Created by Sony on 2/23/2016.
 */
public class PayUCancelModel extends SimiModel{
    @Override
    protected void setUrlAction() {
        addDataExtendURL("orders");
    }

    @Override
    protected void setTypeMethod() {
        mTypeMethod = SimiRequest.Method.GET;
    }

    @Override
    protected void paserData() {
        super.paserData();
        if(mJSONResult != null){
            Log.e("PayUCancelModel",mJSONResult.toString());
        }
    }
}
