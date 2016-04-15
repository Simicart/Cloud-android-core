package com.simicart.theme.materialtheme.checkout.controller;

import android.view.View;
import android.widget.AdapterView;

import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.delegate.SimiDelegate;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.config.DataLocal;
import com.simicart.core.customer.entity.MyAddress;
import com.simicart.core.customer.entity.ProfileEntity;
import com.simicart.theme.materialtheme.checkout.delegate.MaterialShippingManagerDelegate;
import com.simicart.theme.materialtheme.checkout.model.MaterialAddressModel;

import java.util.ArrayList;

/**
 * Created by Sony on 4/12/2016.
 */
public class MaterialShippingInformationController extends SimiController {
    protected SimiDelegate mDelegate;
    protected MaterialShippingManagerDelegate mShippingManagerDelegater;
    protected AdapterView.OnItemSelectedListener onSelectedShipping;
    protected View.OnClickListener onUseBliingAddress;
    protected View.OnClickListener onClickContinue;

    public void setDelegate(SimiDelegate mDelegate) {
        this.mDelegate = mDelegate;
    }

    public void setShippingManagerDelegater(MaterialShippingManagerDelegate mShippingManagerDelegater) {
        this.mShippingManagerDelegater = mShippingManagerDelegater;
    }

    public View.OnClickListener getOnUseBliingAddress() {
        return onUseBliingAddress;
    }

    public View.OnClickListener getOnClickContinue() {
        return onClickContinue;
    }

    public AdapterView.OnItemSelectedListener getOnSelectedShipping() {
        return onSelectedShipping;
    }

    @Override
    public void onStart() {
        // Request get all Address for Customer
        requestGetAddress();

        // Choose Shipping
        actionSelectedShipping();

        // Use Billing Address;
        actionUseBillingAddress();

        // Click Continue
        actionClickContinue();
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

    private void actionSelectedShipping(){
        onSelectedShipping = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // get list Address from Model
                ArrayList<MyAddress> listBillingAddress = getListShippingAddress();

                if(listBillingAddress != null && listBillingAddress.size() > 0){
                    MyAddress address = listBillingAddress.get(position);
                    mShippingManagerDelegater.onSelectedShipping(address);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }

    private void actionUseBillingAddress(){
        onUseBliingAddress = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShippingManagerDelegater.useBillingAddress();
            }
        };
    }

    private void actionClickContinue(){
        onClickContinue = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShippingManagerDelegater.continueShippingInformation();
            }
        };
    }

    public ArrayList<MyAddress> getListShippingAddress(){
        SimiCollection collection = mModel.getCollection();
        if (collection != null && collection.getCollection().size() > 0) {
            ProfileEntity entity = (ProfileEntity) collection.getCollection().get(0);
            if (null != entity) {
                ArrayList<MyAddress> listAddress = new ArrayList<MyAddress>();
                if (entity.getAddress() != null) {
                    if (entity.getAddress().size() > 0) {
                        for (SimiEntity simiEntity : entity.getAddress()) {
                            MyAddress myAddress = (MyAddress) simiEntity;
                            listAddress.add(myAddress);
                        }
                        return listAddress;
                    }
                }
            }
        }
        return null;
    }
}
