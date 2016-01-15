package com.simicart.core.banner.controller;

import org.json.JSONObject;

import com.simicart.core.banner.delegate.BannerDelegate;
import com.simicart.core.banner.model.BannerModel;
import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.error.SimiError;

public class BannerController extends SimiController {
    protected BannerDelegate mDelegate;

    public BannerController(BannerDelegate delegate) {
        mDelegate = delegate;
    }

    public void setDelegate(BannerDelegate delegate) {
        mDelegate = delegate;
    }

    @Override
    public void onStart() {
//        mDelegate.showLoading();
        ModelDelegate delegate = new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
//                mDelegate.dismissLoading();
            }

            @Override
            public void onSuccess(SimiCollection collection) {
//                mDelegate.dismissLoading();
                mDelegate.updateView(collection);
            }
        };
        mModel = new BannerModel();
        mModel.setDelegate(delegate);
        mModel.addDataParameter("order","position");
        mModel.request();
    }

    @Override
    public void onResume() {
        mDelegate.updateView(mModel.getCollection());
    }

}
