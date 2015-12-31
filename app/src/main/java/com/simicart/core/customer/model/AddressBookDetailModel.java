package com.simicart.core.customer.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.multi.SimiRequest;
import com.simicart.core.config.Constants;
import com.simicart.core.customer.entity.MyAddress;

public class AddressBookDetailModel extends SimiModel{
	protected String mCountry;

	public void setTextCountry(String country) {
		mCountry = country;
	}

	@Override
	protected void setUrlAction() {
		addDataExtendURL("customers");
	}

	@Override
	protected void setTypeMethod() {
		mTypeMethod = SimiRequest.Method.PUT;
	}

	@Override
	protected void paserData() {
		super.paserData();

		if(mJSONResult != null){
			if(mJSONResult.has("customerAddress")){
				try {
					JSONObject addressObj = mJSONResult.getJSONObject("customerAddress");
					MyAddress myAddress = new MyAddress();
					myAddress.setJSONObject(addressObj);
					collection.addEntity(myAddress);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
