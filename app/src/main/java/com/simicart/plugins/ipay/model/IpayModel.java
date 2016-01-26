package com.simicart.plugins.ipay.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.multi.SimiRequest;
import com.simicart.core.checkout.entity.OrderEntity;

public class IpayModel extends SimiModel {
	@Override
	protected void setUrlAction() {
		addDataExtendURL("ipay");
	}

	@Override
	protected void setTypeMethod() {
		mTypeMethod = SimiRequest.Method.POST;
	}

	@Override
	protected void paserData() {
		super.paserData();
		if(mJSONResult != null){
			Log.e("Ipay88 UpdatePayment", mJSONResult.toString());
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
		}
	}
}
