package com.simicart.theme.materialtheme.checkout.controller;

import com.simicart.core.base.controller.SimiController;
import com.simicart.theme.materialtheme.checkout.delegate.MaterialReviewOrderDelegate;

/**
 * Created by Sony on 4/12/2016.
 */
public class MaterialReviewOrderController extends SimiController{
    protected MaterialReviewOrderDelegate mDelegate;

    public void setDelegate(MaterialReviewOrderDelegate mDelegate) {
        this.mDelegate = mDelegate;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }
}
