package com.simicart.theme.matrixtheme.home.controller;

import android.util.Log;

import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.delegate.SimiDelegate;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.config.Constants;
import com.simicart.core.config.DataLocal;
import com.simicart.core.home.model.SpotProductModel;
import com.simicart.theme.matrixtheme.home.model.SpotProductHomeTheme1Model;
import com.simicart.theme.ztheme.home.entity.CategoryZTheme;
import com.simicart.theme.ztheme.home.entity.SpotProductZTheme;

import java.util.ArrayList;

public class SpotProductHomeTheme1Controller extends SimiController {

	protected SimiDelegate mDelegate;

	@Override
	public void onStart() {
		mModel = new SpotProductModel();
		mModel.setDelegate(new ModelDelegate() {
			@Override
			public void onFail(SimiError error) {

			}

			@Override
			public void onSuccess(SimiCollection collection) {
				mDelegate.updateView(mModel.getCollection());

			}
		});
		mModel.addDataParameter("order","position");
		mModel.request();
	}

	@Override
	public void onResume() {
		mDelegate.updateView(mModel.getCollection());
	}

	public void setDelegate(SimiDelegate delegate) {
		this.mDelegate = delegate;
	}
}