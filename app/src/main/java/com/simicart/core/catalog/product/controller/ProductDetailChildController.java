package com.simicart.core.catalog.product.controller;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;

import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.catalog.product.delegate.ProductDelegate;
import com.simicart.core.catalog.product.delegate.ProductDetailAdapterDelegate;
import com.simicart.core.catalog.product.delegate.ProductDetailChildDelegate;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.catalog.product.model.ProductDetailModel;

public class ProductDetailChildController extends SimiController {

    protected ProductDetailChildDelegate mDelegate;
    protected ProductDetailAdapterDelegate mAdapterDelegate;
    protected ProductDetailParentController mController;
    private ProductDelegate productDelegate;

    public void setProductDelegate(ProductDelegate productDelegate) {
        this.productDelegate = productDelegate;
    }

    public void setParentController(ProductDetailParentController controller) {
        mController = controller;
    }

    public void setAdapterDelegate(ProductDetailAdapterDelegate delegate) {
        mAdapterDelegate = delegate;
    }

    protected String mID;

    public void setProductID(String id) {
        mID = id;
    }

    public void setDelegate(ProductDetailChildDelegate delegate) {
        mDelegate = delegate;
    }

    @Override
    public void onStart() {
        requestData(mID);
    }

    @Override
    public void onResume() {
        mDelegate.updateView(mModel.getCollection());
        String current_id = mAdapterDelegate.getCurrentID();
        if (getProductFromCollection() != null) {
            String id = getProductFromCollection().getID();
            if (current_id.equals(id)) {
                mDelegate.updateIndicator();
            }
        }
    }

    protected void requestData(final String id) {
        mDelegate.showLoading();
        if (productDelegate != null) {
            productDelegate.getLayoutMore().setVisibility(View.GONE);
        }
        mModel = new ProductDetailModel();
        ModelDelegate delegate = new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                mDelegate.dismissLoading();
                Log.e("Detail Child Controller ", "Request Error");
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.dismissLoading();
                if (productDelegate != null) {
                    productDelegate.getLayoutMore().setVisibility(View.VISIBLE);
                }
                mDelegate.updateView(mModel.getCollection());

                if (null != mAdapterDelegate) {
                    String current_id = mAdapterDelegate.getCurrentID();
                    String id = getProductFromCollection().getID();
                    Log.e("ProductDetailChildController", "ID" + id);
                    if (current_id.equals(id)) {
                        mController
                                .onUpdateTopBottom((ProductDetailModel) mModel);
                        mDelegate.updateIndicator();
                    }
                }

            }
        };

        mModel.addDataExtendURL(id);
        mModel.setDelegate(delegate);
        mModel.addDataParameter("attributes", "1");
        mModel.addDataParameter("variants", "1");
        mModel.addDataParameter("categories", "1");
        mModel.addDataParameter("reviews", "1");
        mModel.addDataParameter("customs", "1");
        mModel.addDataParameter("group_items", "1");
        mModel.addDataParameter("bundle_items", "1");
        mModel.addDataParameter("include_tax", "1");
        mModel.addDataParameter("related_products", "1");
        mModel.addDataParameter("variants_attribute", "1");


        mModel.request();
    }


    public void onUpdateTopBottom() {
        if (null != mModel && null != mController) {
            mController.onUpdateTopBottom((ProductDetailModel) mModel);
        }
    }


    protected ProductEntity getProductFromCollection() {

        ProductEntity product = null;
        ArrayList<SimiEntity> entity = mModel.getCollection().getCollection();
        if (null != entity && entity.size() > 0) {
            product = (ProductEntity) entity.get(0);
        }
        return product;
    }

}
