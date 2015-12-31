package com.simicart.core.customer.model;

import android.util.Log;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.network.request.multi.SimiRequest;

/**
 * Created by MSI on 02/12/2015.
 */
public class CreateNewAccountModel extends SimiModel {

    @Override
    protected void setUrlAction() {
        addDataExtendURL("customer-account");
    }

    @Override
    protected void setTypeMethod() {
        mTypeMethod = SimiRequest.Method.POST;
    }

    @Override
    protected void paserData() {
       Log.e("CreateNewAccountModel", mJSONResult.toString());
    }
}
