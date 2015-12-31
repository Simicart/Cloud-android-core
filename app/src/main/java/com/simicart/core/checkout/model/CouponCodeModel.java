package com.simicart.core.checkout.model;

import android.util.Log;

import com.simicart.core.base.network.request.multi.SimiRequest;
import com.simicart.core.checkout.entity.QuoteEntity;
import com.simicart.core.config.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class CouponCodeModel extends ShippingMethodModel {

	@Override
	protected void setUrlAction() {
		addDataExtendURL("promotions");
	}

	@Override
	protected void setTypeMethod() {
		mTypeMethod = SimiRequest.Method.POST;
	}

	@Override
	protected void paserData() {
		super.paserData();
		if(mJSONResult != null){
			if(mJSONResult != null){
				Log.e("CouponCodeModel", mJSONResult.toString());
				if(mJSONResult.has("quote")){
					try {
						JSONObject quoteArr = mJSONResult.getJSONObject("quote");
						QuoteEntity cart = new QuoteEntity();
						cart.setJSONObject(quoteArr);
						cart.parse();
						collection.addEntity(cart);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
