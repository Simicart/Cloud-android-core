package com.simicart.core.customer.model;

import android.util.Log;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.network.request.multi.SimiRequest;
import com.simicart.core.config.Constants;

public class ChangePassModel extends SimiModel {

	@Override
	protected void setTypeMethod() {
		mTypeMethod = SimiRequest.Method.POST;
	}

	@Override
	protected void paserData() {
		Log.e("EditProfile", "" + mJSONResult.toString());
	}

	@Override
	protected void setUrlAction() {
		addDataExtendURL("customer-account");
	}

}