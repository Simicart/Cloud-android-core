package com.simicart.core.customer.model;

import android.util.Log;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.network.request.multi.SimiRequest;
import com.simicart.core.config.Constants;
import com.simicart.core.customer.entity.ProfileEntity;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgotPasswordModel extends SimiModel {

	private String resetToken = "";

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
				setResetToken(customerObj.getString("reset_token"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	public String getResetToken() {
		return resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}
}
