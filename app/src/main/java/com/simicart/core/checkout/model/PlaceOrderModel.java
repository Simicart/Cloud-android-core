package com.simicart.core.checkout.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.multi.SimiRequest;
import com.simicart.core.checkout.entity.OrderEntity;
import com.simicart.core.config.Constants;
import com.simicart.core.notification.entity.NotificationEntity;

public class PlaceOrderModel extends SimiModel {

	protected String mInvoiceNumber;
	protected String enable = "0";
	protected NotificationEntity notificationEntity;
	protected JSONObject js_placeOrder;

	public String getEnable() {
		return enable;
	}

	public NotificationEntity getNotificationEntity() {
		return notificationEntity;
	}

	public String getInvoiceNumber() {
		return mInvoiceNumber;
	}
	
	public JSONObject getJs_placeOrder() {
		return js_placeOrder;
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
			Log.e("PlaceOrderModel", mJSONResult.toString());
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
