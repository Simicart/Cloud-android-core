package com.simicart.plugins.payu;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.checkout.entity.OrderEntity;
import com.simicart.core.checkout.entity.PaymentMethod;
import com.simicart.core.config.Constants;
import com.simicart.core.event.checkout.SimiEventCheckoutEntity;
import com.simicart.plugins.payu.fragment.PayUFragment;

public class PayU {


    public PayU() {
        IntentFilter filter = new IntentFilter("com.simicart.after.placeorder.webview");
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getBundleExtra(Constants.DATA);
                SimiEventCheckoutEntity entity = (SimiEventCheckoutEntity) bundle.getSerializable(Constants.ENTITY);
                PaymentMethod payment = entity.getPaymentMethod();
                OrderEntity order = entity.getOder();
                if (payment.getMethodCode().equals("payu")) {
                    String orderID = order.getID();
                    onCheckOut(orderID);
                }
            }
        };
        Context context = SimiManager.getIntance().getCurrentContext();
        LocalBroadcastManager.getInstance(context).registerReceiver(receiver, filter);
    }

    private void onCheckOut(String orderID) {
        PayUFragment fragment = PayUFragment.newInstance();
        fragment.setOrderID(orderID);
        SimiManager.getIntance().addFragment(fragment);
    }
}
