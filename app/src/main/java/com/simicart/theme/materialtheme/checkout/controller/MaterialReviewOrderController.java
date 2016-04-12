package com.simicart.theme.materialtheme.checkout.controller;

import com.simicart.core.base.controller.SimiController;
import com.simicart.core.customer.entity.MyAddress;
import com.simicart.theme.materialtheme.checkout.delegate.MaterialBillingManagerDelegate;
import com.simicart.theme.materialtheme.checkout.delegate.MaterialReviewOrderDelegate;

/**
 * Created by Sony on 4/12/2016.
 */
public class MaterialReviewOrderController extends SimiController implements MaterialBillingManagerDelegate {
    protected MaterialReviewOrderDelegate mDelegate;
    protected MyAddress mBillingAddress;

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
}
