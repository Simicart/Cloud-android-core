package com.simicart.plugins.klarnausuk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.checkout.entity.OrderEntity;
import com.simicart.core.checkout.entity.PaymentMethod;
import com.simicart.core.config.Constants;
import com.simicart.core.event.checkout.SimiEventCheckoutEntity;
import com.simicart.plugins.klarna.fragment.KlarnaFragment;
import com.simicart.plugins.klarnausuk.fragment.KlarnaUSUKFragment;

/**
 * Created by MSI on 09/03/2016.
 */
public class KlarnaUSUK {

    public KlarnaUSUK() {
        IntentFilter filter = new IntentFilter("com.simicart.after.placeorder.webview");
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getBundleExtra(Constants.DATA);
                SimiEventCheckoutEntity entity = (SimiEventCheckoutEntity) bundle.getSerializable(Constants.ENTITY);
                PaymentMethod payment = entity.getPaymentMethod();
                OrderEntity order = entity.getOder();
                if (payment.getMethodCode().equals("klarna-usuk")) {
                    String orderID = order.getID();
                    onCheckOut(orderID);
                }
            }
        };

        Context context = SimiManager.getIntance().getCurrentContext();
        LocalBroadcastManager.getInstance(context).registerReceiver(receiver, filter);

    }


    protected void onCheckOut(String orderID) {
        KlarnaUSUKFragment fragment = new KlarnaUSUKFragment();
        fragment.setOrderID(orderID);
        SimiManager.getIntance().replacePopupFragment(fragment);
    }

}
