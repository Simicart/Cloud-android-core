package com.simicart.plugins.checkout.com.model;

import android.util.Log;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.network.request.multi.SimiRequest;

/**
 * Created by Sony on 2/24/2016.
 */
public class CheckOutCancelModel extends SimiModel{
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
            Log.e("CheckOutCancelModel", mJSONResult.toString());
        }
    }
}
