package com.simicart.core.customer.model;

import android.util.Log;

import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.multi.SimiRequest;
import com.simicart.core.checkout.controller.ConfigCheckout;
import com.simicart.core.config.Constants;
import com.simicart.core.customer.entity.MyAddress;
import com.simicart.core.customer.entity.ProfileEntity;

public class AddressBookModel extends SimiModel {

	@Override
	protected void setUrlAction() {
		addDataExtendURL("customers");
	}

	@Override
	protected void setTypeMethod() {
		mTypeMethod = SimiRequest.Method.GET;
	}

	@Override
	protected void paserData() {
		super.paserData();
		if(mJSONResult != null){
			Log.e("AddressBookModel", mJSONResult.toString());
			if(mJSONResult.has("customer")) {
				try {
					JSONObject addressObj = mJSONResult.getJSONObject("customer");
					ProfileEntity profileEntity = new ProfileEntity();
					profileEntity.setJSONObject(addressObj);
					profileEntity.parse();
					collection.addEntity(profileEntity);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
