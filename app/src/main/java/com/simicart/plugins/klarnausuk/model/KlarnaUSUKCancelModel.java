package com.simicart.plugins.klarnausuk.model;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.network.request.multi.SimiRequest;

/**
 * Created by MSI on 09/03/2016.
 */
public class KlarnaUSUKCancelModel extends SimiModel {
    @Override
    protected void setUrlAction() {
        addDataExtendURL("orders");
    }

    @Override
    protected void setTypeMethod() {
        mTypeMethod = SimiRequest.Method.GET;
    }
}
