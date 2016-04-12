package com.simicart.theme.materialtheme.checkout.controller;

import com.simicart.core.base.controller.SimiController;
import com.simicart.theme.materialtheme.checkout.delegate.MaterialBillingInformationDelegate;

/**
 * Created by Sony on 4/12/2016.
 */
public class MaterialBillingInformationController extends SimiController {
    protected MaterialBillingInformationDelegate mDelegate;

    public void setDelegate(MaterialBillingInformationDelegate mDelegate) {
        this.mDelegate = mDelegate;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }
}
