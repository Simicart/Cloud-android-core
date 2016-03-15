package com.simicart.core.customer.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.base.network.request.multi.SimiRequest;
import com.simicart.core.config.Constants;
import com.simicart.core.customer.entity.OrderHistory;
import com.simicart.core.splashscreen.entity.CMSPageEntity;

import java.util.ArrayList;

public class OrderHistoryModel extends SimiModel {

    private OrderHistory orderHistory;

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

        if(null == orderHistory) {
            orderHistory = new OrderHistory();
        }
        orderHistory.setJSONObject(mJSONResult);
        orderHistory.parse();

        ArrayList<SimiEntity> arrayEntity = new ArrayList<>();
        arrayEntity.add(orderHistory);
        collection.setCollection(arrayEntity);
    }

}
