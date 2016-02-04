package com.simicart.core.checkout.model;

import android.util.Log;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.network.request.multi.SimiRequest;

/**
 * Created by James Crabby on 2/1/2016.
 */
public class CreditCardModel extends SimiModel {
    @Override
    protected void setUrlAction() {
        addDataExtendURL("creditcard");
    }

    @Override
    protected void setTypeMethod() {
        mTypeMethod = SimiRequest.Method.PUT;
    }

    @Override
    protected void paserData() {
        super.paserData();
        if(mJSONResult != null){
            Log.e("CreditCardModel", mJSONResult.toString());
        }
    }
}
