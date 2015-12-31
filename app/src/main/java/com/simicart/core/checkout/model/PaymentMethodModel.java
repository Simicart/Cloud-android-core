package com.simicart.core.checkout.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.network.request.multi.SimiRequest;
import com.simicart.core.checkout.entity.QuoteEntity;
import com.simicart.core.checkout.entity.TotalPrice;
import com.simicart.core.config.Constants;

public class PaymentMethodModel extends SimiModel{
	protected TotalPrice mTotalPrice;
	
	public TotalPrice getTotalPrice() {
		return mTotalPrice;
	}

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
		if(mJSONResult != null){
			Log.e("PaymentMethodModel", mJSONResult.toString());
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
