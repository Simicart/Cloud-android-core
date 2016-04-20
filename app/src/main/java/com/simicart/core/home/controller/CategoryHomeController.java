package com.simicart.core.home.controller;

import java.util.ArrayList;

import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.catalog.category.entity.Category;
import com.simicart.core.home.delegate.CategoryHomeDelegate;
import com.simicart.core.home.model.CategoryWidgetModel;

public class CategoryHomeController extends SimiController {

    protected CategoryHomeDelegate mDelegate;
    protected ArrayList<Category> listCategory = new ArrayList<Category>();

    public CategoryHomeController() {
    }

    public void setDelegate(CategoryHomeDelegate delegate) {
        this.mDelegate = delegate;
    }

    public CategoryHomeDelegate getDelegate() {
        return mDelegate;
    }

    @Override
    public void onStart() {
        mDelegate.showLoadingHome();
        ModelDelegate delegate = new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if(error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "OK");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.dismissLoadingHome();
                mDelegate.updateView(mModel.getCollection());
            }
        };
        mModel = new CategoryWidgetModel();
        mModel.addDataParameter("order", "position");
        mModel.setDelegate(delegate);
        mModel.request();
    }

    @Override
    public void onResume() {
        mDelegate.updateView(mModel.getCollection());
        mDelegate.dismissLoadingHome();
    }

}
