package com.simicart.core.customer.model;

import android.util.Log;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.network.request.multi.SimiRequest;
import com.simicart.core.config.Constants;
import com.simicart.core.customer.entity.ProfileEntity;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgotPasswordModel extends SimiModel {

	private String message = "";

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

		Log.e("ForgetPasswordModel", mJSONResult.toString());
		if(mJSONResult.has("customer")){
			try {
				JSONObject customerObj = mJSONResult.getJSONObject("customer");
				setMessage(customerObj.getString("message"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
