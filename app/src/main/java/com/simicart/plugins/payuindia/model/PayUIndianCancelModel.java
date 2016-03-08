package com.simicart.plugins.payuindia.model;

import android.util.Log;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.network.request.multi.SimiRequest;

/**
 * Created by MSI on 01/03/2016.
 */
public class PayUIndianCancelModel extends SimiModel {
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
            Log.e("BraintreCancelModel", mJSONResult.toString());
        }
    }
}
