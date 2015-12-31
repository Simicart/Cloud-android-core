package com.simicart.core.checkout.model;

import android.util.Log;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.network.request.multi.SimiRequest;
import com.simicart.core.checkout.entity.ShippingMethod;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Sony on 12/10/2015.
 */
public class UpdateShippingToQuoteModel extends SimiModel {
    @Override
    protected void setUrlAction() {
        addDataExtendURL("quotes");
    }

    @Override
    protected void setTypeMethod() {
        mTypeMethod = SimiRequest.Method.PUT;
    }

    @Override
    protected void paserData() {
        super.paserData();
        if (mJSONResult != null) {
            if (mJSONResult.has("shipping")) {
                Log.e("UpdateShppingToQuoteModel", mJSONResult.toString());
                try {
                    JSONArray shippingArr = mJSONResult.getJSONArray("shipping");
                    if (shippingArr != null && shippingArr.length() > 0) {
                        for (int i = 0; i < shippingArr.length(); i++) {
                            ShippingMethod shipping = new ShippingMethod();
                            shipping.setJSONObject(shippingArr.getJSONObject(i));
                            shipping.parse();
                            collection.addEntity(shipping);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
