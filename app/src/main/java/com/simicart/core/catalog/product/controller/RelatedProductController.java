package com.simicart.core.catalog.product.controller;

import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.delegate.SimiDelegate;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.catalog.product.model.RelatedProductModel;

public class RelatedProductController extends SimiController {

	protected SimiDelegate mDelegate;
	protected String mID;

	public void setDelegate(SimiDelegate delegate) {
		mDelegate = delegate;
	}

	public void setProductId(String id) {
		mID = id;
	}

	@Override
	public void onStart() {
		mDelegate.showLoading();
		ModelDelegate delegate = new ModelDelegate() {
			@Override
			public void onFail(SimiError error) {

			}

			@Override
			public void onSuccess(SimiCollection collection) {

			}
		};
		mModel = new RelatedProductModel();
		mModel.addDataBody("product_id", mID);
		mModel.addDataBody("limit", "15");
		mModel.addDataBody("width", "300");
		mModel.addDataBody("height", "300");
		mModel.setDelegate(delegate);
		mModel.request();
	}

	@Override
	public void onResume() {
		mDelegate.updateView(mModel.getCollection());
	}

}
