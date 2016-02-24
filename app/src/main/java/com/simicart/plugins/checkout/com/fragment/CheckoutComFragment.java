package com.simicart.plugins.checkout.com.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        showDialog();
                        return true;
                    }
                }
                return false;
            }
        });

        return v;
    }

    private void showDialog() {
        new AlertDialog.Builder(SimiManager.getIntance().getCurrentActivity())
                .setMessage(
                        Config.getInstance()
                                .getText(
                                        "Are you sure that you want to cancel the order?"))
                .setPositiveButton(Config.getInstance().getText("Yes"),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                requestCancelOrder();
                                showToastMessage("Your order has been canceled");
                                SimiManager.getIntance().backToHomeFragment();
                            }
                        })
                .setNegativeButton(Config.getInstance().getText("No"),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // do nothing
                            }
                        }).show();

    }

    private void requestCancelOrder(){
        CheckOutCancelModel checkoutCancelModel = new CheckOutCancelModel();
        checkoutCancelModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if(error != null){
                    showToastMessage(error.getMessage());
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {

            }
        });
        checkoutCancelModel.addDataExtendURL(orderID);
        checkoutCancelModel.addDataExtendURL("cancel");
        checkoutCancelModel.request();
    }

    public void showToastMessage(String message) {
        Toast toast = Toast.makeText(MainActivity.context, Config.getInstance()
                .getText(message), Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

}
