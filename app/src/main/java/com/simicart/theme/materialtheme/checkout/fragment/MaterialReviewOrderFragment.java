package com.simicart.theme.materialtheme.checkout.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.config.Rconfig;
import com.simicart.theme.materialtheme.checkout.block.MaterialBillingInformationBlock;
import com.simicart.theme.materialtheme.checkout.block.MaterialCheckOutMethodBlock;
import com.simicart.theme.materialtheme.checkout.block.MaterialReviewOrderBlock;
import com.simicart.theme.materialtheme.checkout.controller.MaterialBillingInformationController;
import com.simicart.theme.materialtheme.checkout.controller.MaterialCheckOutMethodController;
import com.simicart.theme.materialtheme.checkout.controller.MaterialReviewOrderController;

/**
 * Created by Sony on 4/12/2016.
 */
public class MaterialReviewOrderFragment extends SimiFragment {
    protected MaterialReviewOrderController mController;
    protected MaterialReviewOrderBlock mBlock;
    protected MaterialCheckOutMethodBlock mCheckOutMethodBlock;
    protected MaterialCheckOutMethodController mCheckOutMethodController;
    protected MaterialBillingInformationBlock mBillingInformationBlock;
    protected MaterialBillingInformationController mBillingInformationController;

    public static MaterialReviewOrderFragment newInstance() {
        MaterialReviewOrderFragment fragment = new MaterialReviewOrderFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                Rconfig.getInstance().layout("material_review_order_layout"), container, false);
        Context context = getActivity();

        // Review Order
        initReviewOrder(rootView, context);

        // CheckOut Method
        initCheckOutMethod(rootView, context);

        // Billing Information
        initBillingInformation(rootView, context);

        return rootView;
    }

    private void initReviewOrder(View rootView, Context context){
        mBlock = new MaterialReviewOrderBlock(rootView, context);
        mBlock.initView();
        if (mController == null) {
            mController = new MaterialReviewOrderController();
            mController.setDelegate(mBlock);
            mController.onStart();
        } else {
            mController.setDelegate(mBlock);
            mController.onResume();
        }
    }

    private void initCheckOutMethod(View rootView, Context context){
        View view_checkout_method = (View) rootView.findViewById(Rconfig.getInstance().id("inc_checkout_method"));
        mCheckOutMethodBlock = new MaterialCheckOutMethodBlock(view_checkout_method, context);
        mCheckOutMethodBlock.initView();
        if (mCheckOutMethodController == null) {
            mCheckOutMethodController = new MaterialCheckOutMethodController();
            mCheckOutMethodController.onStart();
        } else {
            mCheckOutMethodController.onResume();
        }
        mCheckOutMethodBlock.setOnClickCheckoutExistingCustomer(mCheckOutMethodController.getOnClickCheckoutExistingCustomer());
    }

    private void initBillingInformation(View rootView, Context context){
        View view_billing_information = (View) rootView.findViewById(Rconfig.getInstance().id("inc_billing_infomation"));
        mBillingInformationBlock = new MaterialBillingInformationBlock(view_billing_information, context);
        mBillingInformationBlock.initView();
        if (mBillingInformationController == null) {
            mBillingInformationController = new MaterialBillingInformationController();
            mBillingInformationController.setDelegate(mBillingInformationBlock);
            mBillingInformationController.onStart();
        } else {
            mBillingInformationController.setDelegate(mBillingInformationBlock);
            mBillingInformationController.onResume();
        }
    }

    @Override
    public void onDestroy() {
        SimiManager.getIntance().showCartLayout(true);
        super.onDestroy();
    }

}
