package com.simicart.core.checkout.model;

import android.util.Log;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.network.request.multi.SimiRequest;
import com.simicart.core.checkout.entity.PaymentMethod;
import com.simicart.core.checkout.entity.QuoteEntity;
import com.simicart.core.common.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;

/**
 * Created by Sony on 12/10/2015.
 */
public class UpdateBillingToQuoteModel extends SimiModel {
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
            if (mJSONResult.has("payment")) {
                Log.e("UpdateBillingToQuoteModel", mJSONResult.toString());
                try {
                    JSONArray paymentArr = mJSONResult.getJSONArray("payment");
                    if (paymentArr != null && paymentArr.length() > 0) {
                        for (int i = 0; i < paymentArr.length(); i++) {
                            PaymentMethod payment = new PaymentMethod();
                            payment.setJSONObject(paymentArr.getJSONObject(i));
                            payment.parse();

//                                Collections.swap();
                            if (Utils.validateString(payment.getTitle())) {
                                collection.addEntity(payment);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
