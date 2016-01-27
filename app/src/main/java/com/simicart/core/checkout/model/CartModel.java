package com.simicart.core.checkout.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.network.request.multi.SimiRequest;
import com.simicart.core.checkout.entity.QuoteEntity;
import com.simicart.core.checkout.entity.TotalPrice;

public class CartModel extends SimiModel {

	protected int mQty;
	protected TotalPrice mTotalPrice;

	public int getQty() {
		return mQty;
	}

	@Override
	protected void setUrlAction() {
		addDataExtendURL("quotes");
	}

	@Override
	protected void setTypeMethod() {
		mTypeMethod = SimiRequest.Method.GET;
	}

	@Override
	protected void paserData() {
		super.paserData();
		if(mJSONResult != null){
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

	public TotalPrice getTotalPrice() {
		return mTotalPrice;
	}


	@Override
	public void setShouldCache() {
		mShouldCache = true;
	}
}
