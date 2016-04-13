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
import com.simicart.theme.materialtheme.checkout.delegate.MaterialBillingManagerDelegate;
import com.simicart.theme.materialtheme.checkout.model.MaterialAddressModel;

import java.util.ArrayList;

/**
 * Created by Sony on 4/12/2016.
 */
public class MaterialBillingInformationController extends SimiController {
    protected SimiDelegate mDelegate;
    protected MaterialBillingManagerDelegate mBillingManagerDelegate;
    protected AdapterView.OnItemSelectedListener onSelectedBilling;
    protected View.OnClickListener mOnClickShipToAddress;
    protected View.OnClickListener mOnClickShipToDifferentAdress;

    private int SHIP_TO_ADDRESS = 0;
    private int SHIP_TO_DIFFERENT_ADDRESS = 1;

    public void setDelegate(SimiDelegate mDelegate) {
        this.mDelegate = mDelegate;
    }

    public void setBillingManagerDelegate(MaterialBillingManagerDelegate mBillingManagerDelegate) {
        this.mBillingManagerDelegate = mBillingManagerDelegate;
    }

    public AdapterView.OnItemSelectedListener getOnSelectedBilling() {
        return onSelectedBilling;
    }

    public View.OnClickListener getOnClickShipToAddress() {
        return mOnClickShipToAddress;
    }

    public View.OnClickListener getOnClickShipToDifferentAdress() {
        return mOnClickShipToDifferentAdress;
    }

    @Override
    public void onStart() {
        // Request get all Address for Customer
        requestGetAddress();

        // Choose Billing
        actionSelectedBilling();

        // Click to Ship to Address
        actionClickShipToAddress();

        // Click to Ship different Address
        actionClickShipToDifferentAddress();
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

    private void actionSelectedBilling(){
        onSelectedBilling = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // get list Address from Model
                ArrayList<MyAddress> listBillingAddress = getListBillingAddress();

                if(listBillingAddress != null && listBillingAddress.size() > 0){
                    MyAddress address = listBillingAddress.get(position);
                    mBillingManagerDelegate.onSelectedBilling(address);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }

    private void actionClickShipToAddress(){
        mOnClickShipToAddress = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBillingManagerDelegate.chooseShipToAddress(SHIP_TO_ADDRESS);
            }
        };
    }

    private void actionClickShipToDifferentAddress(){
        mOnClickShipToDifferentAdress = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBillingManagerDelegate.chooseShipToAddress(SHIP_TO_DIFFERENT_ADDRESS);
            }
        };
    }

    public ArrayList<MyAddress> getListBillingAddress(){
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
