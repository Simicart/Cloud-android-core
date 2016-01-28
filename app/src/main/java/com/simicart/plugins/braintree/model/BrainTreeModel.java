package com.simicart.plugins.braintree.model;

import org.json.JSONArray;
import org.json.JSONException;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.multi.SimiRequest;

public class BrainTreeModel extends SimiModel {

	@Override
	protected void paserData() {
		try {
			JSONArray js_data = mJSONResult.getJSONArray("data");
			if (null == collection) {
				collection = new SimiCollection();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void setUrlAction() {
		url_action = "/braintree/update-braintree-payment";
	}

	@Override
	protected void setTypeMethod() {
		mTypeMethod = SimiRequest.Method.POST;
	}
}
