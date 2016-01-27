package com.simicart.plugins.ipay.model;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.network.request.multi.SimiRequest;
import com.simicart.plugins.ipay.entity.IpayEntity;

/**
 * Created by Sony on 1/26/2016.
 */
public class GetIpayConfigModel extends SimiModel{
    @Override
    protected void setUrlAction() {
        addDataExtendURL("ipay");
    }

    @Override
    protected void setTypeMethod() {
        mTypeMethod = SimiRequest.Method.GET;
    }

    @Override
    protected void paserData() {
        super.paserData();
        if (mJSONResult != null) {
            try {
                IpayEntity entity = new IpayEntity();
                entity.setJSONObject(mJSONResult);
                entity.parse();
                collection.addEntity(entity);
            }catch (Exception e){

            }
        }
    }
}
