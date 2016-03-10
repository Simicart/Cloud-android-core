package com.simicart.plugins.klarna.model;

import android.util.Log;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.network.request.multi.SimiRequest;

/**
 * Created by MSI on 09/03/2016.
 */
public class KlarnaModel extends SimiModel {


    @Override
    protected void setUrlAction() {
        addDataExtendURL("klarna/checkout");
    }

    @Override
    protected void setTypeMethod() {
        mTypeMethod = SimiRequest.Method.POST;
    }

    @Override
    protected void paserData() {
        super.paserData();
    }
}
