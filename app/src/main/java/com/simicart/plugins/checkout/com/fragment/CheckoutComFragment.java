package com.simicart.plugins.checkout.com.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View.OnKeyListener;

import com.simicart.MainActivity;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.checkout.entity.OrderEntity;
import com.simicart.core.config.Config;
import com.simicart.core.config.Rconfig;
import com.simicart.core.customer.controller.SignInController;
import com.simicart.core.customer.entity.OrderHisDetail;
import com.simicart.plugins.checkout.com.block.CheckoutComBlock;
import com.simicart.plugins.checkout.com.controller.CheckoutComController;
import com.simicart.plugins.checkout.com.model.CheckOutCancelModel;

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
        View view = inflater.inflate(Rconfig.getInstance().layout("plugins_checkout_main"), container,
                false);
        SimiManager.getIntance().hidenMenuTop(true);
        Context context = getActivity();
        checkoutComBlock = new CheckoutComBlock(view, getActivity());
        checkoutComBlock.initView();
        checkoutComController = new CheckoutComController(getActivity());
        checkoutComController.setDelegate(checkoutComBlock);
        checkoutComController.setOrderID(orderID);
        checkoutComController.setmOrderEntity(mOrderEntity);
        checkoutComController.setmOrderHisDetail(mOrderHistoryDetail);
        checkoutComController.onStart();

        checkoutComBlock.setOnCheckoutButtonClicked(checkoutComController.getOnButtonCheckoutClick());
        checkoutComBlock.setOnNameListener(checkoutComController.getOnKeyListener());
        checkoutComBlock.setOnCardNumberListener(checkoutComController.getOnKeyListener());
        checkoutComBlock.setOnCVVListener(checkoutComController.getOnKeyListener());

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(checkoutComController.getOnKeyListener());

        return view;
    }

    @Override
    public void onDestroy() {
        SimiManager.getIntance().hidenMenuTop(false);
        super.onDestroy();
    }





}
