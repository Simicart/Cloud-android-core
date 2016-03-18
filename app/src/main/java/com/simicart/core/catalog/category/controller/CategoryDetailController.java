package com.simicart.core.catalog.category.controller;

import android.view.View;
import android.view.View.OnClickListener;

import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.delegate.SimiDelegate;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.catalog.category.model.ListProductModel;
import com.simicart.core.catalog.categorydetail.fragment.CategoryDetailFragment;
import com.simicart.core.config.DataLocal;

public class CategoryDetailController extends SimiController {
    protected SimiDelegate mDelegate;
    protected String mID;
    protected OnClickListener mClicker;
    protected String mCatename;

    @Override
    public void onStart() {
        if (DataLocal.isTablet) {
            mDelegate.updateView(null);
        } else {
            requestListProducts();
        }

        mClicker = new OnClickListener() {

            @Override
            public void onClick(View v) {
                CategoryDetailFragment searchFragment = CategoryDetailFragment.newInstance();
                searchFragment.setCategoryId(mID);
                searchFragment.setCategoryName(mCatename);
                if (mID.equals("-1")) {
                    searchFragment.setUrlSearch("products");
                } else {
                    searchFragment.setUrlSearch("categories");
                }
                SimiManager.getIntance().replaceFragment(searchFragment);
            }
        };

    }

    private void requestListProducts() {
        mModel = new ListProductModel();
        ((ListProductModel) mModel).setCategoryID(mID);
        if (!mID.equals("-1")) {
            mModel.addDataExtendURL(mID);
            mModel.addDataExtendURL("products");
        }

        mModel.addOffsetDataParameter("0");
        mModel.addLimitDataParameter("10");
        mModel.addDataParameter("stock_info", "1");
        mModel.addFilterDataParameter("status", "1");

        mModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {

            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.updateView(collection);
            }
        });
        mModel.request();

    }


    public OnClickListener getClicker() {
        return mClicker;
    }

    @Override
    public void onResume() {
        mDelegate.updateView(mModel.getCollection());
    }

    public void setDelegate(SimiDelegate delegate) {
        mDelegate = delegate;
    }

    public void setCategoryID(String id) {
        mID = id;
    }

    public void setCatename(String mCatename) {
        this.mCatename = mCatename;
    }

}
