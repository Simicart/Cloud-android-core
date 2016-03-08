package com.simicart.plugins.paypal.model;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.network.request.multi.SimiRequest;

/**
 * Created by MSI on 01/03/2016.
 */
public class CancelPaypalOrder extends SimiModel {

    @Override
    protected void setUrlAction() {
        addDataExtendURL("orders");
    }

    @Override
    protected void setTypeMethod() {
        mTypeMethod = SimiRequest.Method.GET;
    }
}
