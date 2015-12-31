package com.simicart.plugins.paypal.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.multi.SimiRequest;
import com.simicart.core.checkout.entity.OrderEntity;

public class PaypalModel extends SimiModel {

	@Override
	protected void setUrlAction() {
		addDataExtendURL("checkout");
	}

	@Override
	protected void setTypeMethod() {
		mTypeMethod = SimiRequest.Method.POST;
	}

	@Override
	protected void paserData() {
		super.paserData();
		if(mJSONResult != null){
			Log.e("PaypalModel", mJSONResult.toString());
			if(mJSONResult.has("invoice")){
				try {
					JSONObject orderObj = mJSONResult.getJSONObject("invoice");
					OrderEntity orderEntity = new OrderEntity();
					orderEntity.setJSONObject(orderObj);
					orderEntity.parse();
					collection.addEntity(orderEntity);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			if(mJSONResult.has("order")){
				try {
					JSONObject orderObj = mJSONResult.getJSONObject("order");
					OrderEntity orderEntity = new OrderEntity();
					orderEntity.setJSONObject(orderObj);
					orderEntity.parse();
					collection.addEntity(orderEntity);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
