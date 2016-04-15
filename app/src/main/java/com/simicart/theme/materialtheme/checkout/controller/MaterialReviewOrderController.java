package com.simicart.theme.materialtheme.checkout.controller;

import android.util.Log;

import com.simicart.core.base.controller.SimiController;
import com.simicart.core.checkout.entity.ShippingMethod;
import com.simicart.core.customer.entity.MyAddress;
import com.simicart.theme.materialtheme.checkout.delegate.MaterialBillingManagerDelegate;
import com.simicart.theme.materialtheme.checkout.delegate.MaterialReviewOrderDelegate;
import com.simicart.theme.materialtheme.checkout.delegate.MaterialShippingManagerDelegate;
import com.simicart.theme.materialtheme.checkout.delegate.MaterialShippingMethodManagerDelegate;

import java.util.ArrayList;

/**
 * Created by Sony on 4/12/2016.
 */
public class MaterialReviewOrderController extends SimiController implements MaterialBillingManagerDelegate, MaterialShippingManagerDelegate, MaterialShippingMethodManagerDelegate {
    protected MaterialReviewOrderDelegate mDelegate;
    protected MyAddress mBillingAddress;
    protected MyAddress mShippingAddress;
    protected MaterialShippingMethodController mShippingMethodController;
    protected ArrayList<ShippingMethod> listShippingMethod;
    protected ShippingMethod shippingMethod;

    public void setDelegate(MaterialReviewOrderDelegate mDelegate) {
        this.mDelegate = mDelegate;
    }

    public void setShippingMethodController(MaterialShippingMethodController mShippingMethodController) {
        this.mShippingMethodController = mShippingMethodController;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onSelectedBilling(MyAddress address) {
        mBillingAddress = address;
    }

    @Override
    public void onSelectedShipping(MyAddress address) {
        mShippingAddress = address;
    }

    @Override
    public void useBillingAddress() {
        mShippingAddress = mBillingAddress;
    }

    @Override
    public void continueShippingInformation() {
        mShippingMethodController.requestGetShippingMethod(mShippingAddress);
    }

    @Override
    public void chooseShipToAddress(int type) {
        mDelegate.showShippingInformation(type);
    }

    @Override
    public void showShippingMethod(ArrayList<ShippingMethod> listShippingMethod) {
        this.listShippingMethod = listShippingMethod;
        mDelegate.showShippingMethod();
    }

    @Override
    public void selectedShippingMethod(ShippingMethod shippingMethod) {
        this.shippingMethod = shippingMethod;
        Log.e("MaterialReviewOrderController", "ShippingMethod: " + shippingMethod.getServiceName());
    }
}
