package com.simicart.plugins.checkout.com.model;

import android.util.Log;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.network.request.multi.SimiRequest;

public class UpdatePaymentModel extends SimiModel {

	@Override
	protected void paserData() {
		Log.e("Checkout.com.Model", "++" + mJSONResult.toString());
	}

	@Override
	protected void setTypeMethod() {
		mTypeMethod = SimiRequest.Method.POST;
	}

	@Override
	protected void setUrlAction() {
		url_action = "/checkout-com/update-checkout-payment";
	}
}
