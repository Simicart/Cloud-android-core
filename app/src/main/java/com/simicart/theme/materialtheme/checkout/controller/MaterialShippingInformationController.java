package com.simicart.theme.materialtheme.checkout.controller;

import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.config.DataLocal;
import com.simicart.theme.materialtheme.checkout.delegate.MaterialShippingInformationDelegate;
import com.simicart.theme.materialtheme.checkout.model.MaterialAddressModel;

/**
 * Created by Sony on 4/12/2016.
 */
public class MaterialShippingInformationController extends SimiController {
    MaterialShippingInformationDelegate mDelegate;

    public void setDelegate(MaterialShippingInformationDelegate mDelegate) {
        this.mDelegate = mDelegate;
    }

    @Override
    public void onStart() {
        requestGetAddress();
    }

    @Override
    public void onResume() {
        mDelegate.updateView(mModel.getCollection());
    }

    private void requestGetAddress(){
        mDelegate.showDialogLoading();
        mModel = new MaterialAddressModel();
        mModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                mDelegate.dismissDialogLoading();
                if (error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "Ok");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.dismissDialogLoading();
                mDelegate.updateView(collection);
            }
        });
        mModel.addDataExtendURL(DataLocal.getCustomerID());
        mModel.request();
    }
}
