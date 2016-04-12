package com.simicart.theme.materialtheme.checkout.controller;

import com.simicart.core.base.controller.SimiController;
import com.simicart.core.customer.entity.MyAddress;
import com.simicart.theme.materialtheme.checkout.delegate.MaterialBillingManagerDelegate;
import com.simicart.theme.materialtheme.checkout.delegate.MaterialReviewOrderDelegate;
import com.simicart.theme.materialtheme.checkout.delegate.MaterialShippingManagerDelegate;

/**
 * Created by Sony on 4/12/2016.
 */
public class MaterialReviewOrderController extends SimiController implements MaterialBillingManagerDelegate, MaterialShippingManagerDelegate {
    protected MaterialReviewOrderDelegate mDelegate;
    protected MyAddress mBillingAddress;
    protected MyAddress mShippingAddress;

    public void setDelegate(MaterialReviewOrderDelegate mDelegate) {
        this.mDelegate = mDelegate;
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
    public void chooseShipToAddress(int type) {
        mDelegate.showShippingInformation(type);
    }

    @Override
    public void onSelectedShipping(MyAddress address) {
        mShippingAddress = address;
    }
}
