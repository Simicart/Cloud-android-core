package com.simicart.core.notification.model;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.network.request.multi.SimiRequest;

public class RegisterIDModel extends SimiModel {

	@Override
	protected void setShowNotifi() {
		isShowNotify = false;
	}

	@Override
	protected void paserData() {
		// try {
		// JSONArray list = this.mJSONResult.getJSONArray("data");
		//
		// if (null == collection) {
		// collection = new SimiCollection();
		// }
		// for (int i = 0; i < list.length(); i++) {
		// Product product = new Product();
		// product.setJSONObject(list.getJSONObject(i));
		// collection.addEntity(product);
		// }
		// } catch (JSONException e) {
		// e.printStackTrace();
		// }
		super.paserData();
	}

	@Override
	protected void setUrlAction() {
		addDataExtendURL("devices");
	}

	@Override
	protected void setTypeMethod() {
		mTypeMethod = SimiRequest.Method.POST;
	}
}
