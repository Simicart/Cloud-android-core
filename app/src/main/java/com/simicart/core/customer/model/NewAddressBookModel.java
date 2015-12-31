package com.simicart.core.customer.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.multi.SimiRequest;
import com.simicart.core.config.Constants;
import com.simicart.core.customer.entity.MyAddress;

public class NewAddressBookModel extends SimiModel {

	@Override
	protected void setUrlAction() {
		addDataExtendURL("customers");
	}

	@Override
	protected void setTypeMethod() {
		mTypeMethod = SimiRequest.Method.POST;
	}

	@Override
	protected void paserData() {
		super.paserData();
		if(mJSONResult != null){
			Log.e("NewAddressBookModel", mJSONResult.toString());
			if(mJSONResult.has("customerAddress")){
				try {
					JSONObject addressObj = mJSONResult.getJSONObject("customerAddress");
					MyAddress myAddress = new MyAddress();
					myAddress.setJSONObject(addressObj);
					myAddress.parse();
					collection.addEntity(myAddress);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
