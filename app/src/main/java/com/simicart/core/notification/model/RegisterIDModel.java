package com.simicart.core.notification.model;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.network.request.multi.SimiRequest;

public class RegisterIDModel extends SimiModel {

	@Override
	protected void setShowNotifi() {
		isShowNotify = false;
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
