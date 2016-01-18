package com.simicart.plugins.checkout.com.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.checkout.entity.OrderEntity;
import com.simicart.core.config.Rconfig;
import com.simicart.core.customer.controller.SignInController;
import com.simicart.core.customer.entity.OrderHisDetail;
import com.simicart.plugins.checkout.com.block.CheckoutComBlock;
import com.simicart.plugins.checkout.com.controller.CheckoutComController;

/**
 * Created by James Crabby on 1/15/2016.
 */
public class CheckoutComFragment extends SimiFragment {

    protected CheckoutComBlock checkoutComBlock;
    protected CheckoutComController checkoutComController;
    protected OrderHisDetail mOrderHistoryDetail;
    protected OrderEntity mOrderEntity;
    protected String orderID;

    public OrderEntity getmOrderEntity() {
        return mOrderEntity;
    }

    public void setmOrderEntity(OrderEntity mOrderEntity) {
        this.mOrderEntity = mOrderEntity;
    }

    public OrderHisDetail getmOrderHistoryDetail() {
        return mOrderHistoryDetail;
    }

    public void setmOrderHistoryDetail(OrderHisDetail mOrderHistoryDetail) {
        this.mOrderHistoryDetail = mOrderHistoryDetail;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(Rconfig.getInstance().layout("plugins_checkout_main"), container,
                false);

        Context context = getActivity();
        checkoutComBlock = new CheckoutComBlock(v, getActivity());
        checkoutComBlock.initView();
        checkoutComController = new CheckoutComController(getActivity());
        checkoutComController.setDelegate(checkoutComBlock);
        checkoutComController.setCvv(checkoutComBlock.getCvvField());
        checkoutComController.setMonth(checkoutComBlock.getSpinMonth());
        checkoutComController.setName(checkoutComBlock.getName());
        checkoutComController.setNumber(checkoutComBlock.getNumberField());
        checkoutComController.setYear(checkoutComBlock.getSpinYear());
        checkoutComController.setOrderID(orderID);
        checkoutComController.setmOrderEntity(mOrderEntity);
        checkoutComController.setmOrderHisDetail(mOrderHistoryDetail);
        checkoutComController.onStart();

        checkoutComBlock.setOnCheckoutButtonClicked(checkoutComController.getOnButtonCheckoutClick());

        return v;
    }
}
