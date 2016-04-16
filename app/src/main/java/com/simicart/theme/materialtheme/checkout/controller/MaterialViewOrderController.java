package com.simicart.theme.materialtheme.checkout.controller;

import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.delegate.SimiDelegate;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.checkout.entity.QuoteEntity;
import com.simicart.core.checkout.model.ReviewOrderModel;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.theme.materialtheme.checkout.delegate.MaterialViewOrderManagerDelegate;

/**
 * Created by Sony on 4/16/2016.
 */
public class MaterialViewOrderController extends SimiController {
    protected SimiDelegate mDelegate;
    protected MaterialViewOrderManagerDelegate mViewOrderManagerDelegate;

    public void setDelegate(SimiDelegate mDelegate) {
        this.mDelegate = mDelegate;
    }

    public void setViewOrderManagerDelegate(MaterialViewOrderManagerDelegate mViewOrderManagerDelegate) {
        this.mViewOrderManagerDelegate = mViewOrderManagerDelegate;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {
        mDelegate.updateView(mModel.getCollection());
    }

    public void getOrderInformation(){
        mDelegate.showDialogLoading();
        mModel = new ReviewOrderModel();
        mModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {

            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.dismissDialogLoading();
                mDelegate.updateView(collection);
                if (collection != null && collection.getCollection().size() > 0) {
                    mViewOrderManagerDelegate.showViewOrder();
                }
            }
        });

        if (!Config.getInstance().getQuoteCustomerSignIn().equals("")) {
            mModel.addDataExtendURL(Config.getInstance().getQuoteCustomerSignIn());
        }

        if (!DataLocal.getQuoteCustomerNotSigin().equals("")) {
            mModel.addDataExtendURL(DataLocal.getQuoteCustomerNotSigin());
        }

        mModel.request();
    }
}
