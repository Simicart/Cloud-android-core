package com.simicart.core.catalog.category.controller;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.delegate.SimiDelegate;
import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.catalog.category.entity.CategoryEntity;
import com.simicart.core.catalog.category.fragment.CategoryFragment;
import com.simicart.core.catalog.category.model.CategoryModel;
import com.simicart.core.catalog.categorydetail.fragment.CategoryDetailFragment;
import com.simicart.core.config.DataLocal;
import com.simicart.core.slidemenu.fragment.CateSlideMenuFragment;

public class CategoryController extends SimiController {
    protected SimiDelegate mDelegate;
    protected String mID;
    protected OnItemClickListener mClicker;

    @Override
    public void onStart() {
        requestListCategories();

        mClicker = new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                selecteItem(position);
            }

        };

    }

    private void requestListCategories() {
        mDelegate.showLoading();
        ModelDelegate delegate = new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {

            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.dismissLoading();
                mDelegate.updateView(collection);
            }
        };
        mModel = new CategoryModel();
        mModel.setDelegate(delegate);
        if (!mID.equals("-1")) {
//            mModel.addDataBody("category_id", mID);
            mModel.addFilterDataParameter("parent", mID);
            mModel.addFilterDataParameter("status","1");
        }else{
            mModel.addFilterDataParameter("status","1");
        }
        mModel.request();

    }

    protected void selecteItem(int position) {
        CategoryEntity category = (CategoryEntity) mModel.getCollection().getCollection()
                .get(position);
        SimiFragment fragment = null;
        Log.e("CategoryController", category.isHasChild() + "");
        if (category.isHasChild()) {
            if (DataLocal.isTablet) {
                fragment = CategoryFragment.newInstance(
                        category.getName(), category.getId());
                CateSlideMenuFragment.getIntance().replaceFragmentCategoryMenu(
                        fragment);
            } else {
                fragment = CategoryFragment.newInstance(
                        category.getName(), category.getId());
                SimiManager.getIntance().addFragment(fragment);
            }
        } else {
            CategoryDetailFragment searchFragment = CategoryDetailFragment.newInstance();
            if (category.getId().equals("-1")) {
                searchFragment.setUrlSearch("products");
            } else {
                searchFragment.setUrlSearch("categories");
            }
            searchFragment.setCategoryId(category.getId());
            searchFragment.setCatName(category.getName());
            SimiManager.getIntance().replaceFragment(searchFragment);
        }
    }

    public OnItemClickListener getClicker() {
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

}
