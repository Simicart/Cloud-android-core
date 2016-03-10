package com.simicart.plugins.klarnausuk.model;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.network.request.multi.SimiRequest;

/**
 * Created by MSI on 09/03/2016.
 */
public class KlarnaUSUKModel extends SimiModel {

    @Override
    protected void setUrlAction() {
        addDataExtendURL("klarna-usa/checkout");
    }

    @Override
    protected void setTypeMethod() {
        mTypeMethod = SimiRequest.Method.POST;
    }

}
