package com.simicart.plugins.facebooklogin;

import android.util.Log;
import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.network.request.multi.SimiRequest;
import com.simicart.core.customer.entity.ProfileEntity;
import org.json.JSONException;
import org.json.JSONObject;

public class FacebookModel extends SimiModel {

	@Override
	protected void setUrlAction() {
		addDataExtendURL("customer-account");
	}

	@Override
	protected void setTypeMethod() {
		mTypeMethod = SimiRequest.Method.POST;
	}

	@Override
	protected void paserData() {
		super.paserData();
		if(mJSONResult != null){
			Log.e("FacebookModel", mJSONResult.toString());
			if(mJSONResult.has("customer")){
				try {
					JSONObject customerObj = mJSONResult.getJSONObject("customer");
					ProfileEntity customer = new ProfileEntity();
					customer.setJSONObject(customerObj);
					customer.parse();
					collection.addEntity(customer);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
