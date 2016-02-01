package com.simicart.plugins.checkout.com;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.simicart.MainActivity;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.checkout.entity.OrderEntity;
import com.simicart.core.checkout.entity.PaymentMethod;
import com.simicart.core.config.Constants;
import com.simicart.core.customer.entity.OrderHisDetail;
import com.simicart.core.event.checkout.SimiEventCheckoutEntity;
import com.simicart.plugins.checkout.com.fragment.CheckoutComFragment;

public class CheckoutCom {

    String LIVE_URL = "https://secure.checkout.com/hpayment-tokenretry/pay.aspx?";

    Context context;
    String orderID = "";
    OrderEntity order;
    OrderHisDetail orderHisDetail;

    public CheckoutCom() {

        IntentFilter filter = new IntentFilter("com.simicart.paymentmethod.placeorder");

        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getBundleExtra(Constants.DATA);
                SimiEventCheckoutEntity entity = (SimiEventCheckoutEntity) bundle.getSerializable(Constants.ENTITY);
                PaymentMethod payment = entity.getPaymentMethod();
                OrderEntity order = entity.getOder();
                OrderHisDetail orderHisDetail = entity.getOrderHisDetail();
                onCheckOut(payment, orderHisDetail, order);
            }
        };

        Context context = SimiManager.getIntance().getCurrentContext();
        LocalBroadcastManager.getInstance(context).registerReceiver(receiver, filter);

    }

    private void onCheckOut(PaymentMethod paymentMethod, OrderHisDetail orderHisDetail, OrderEntity order) {
        if (paymentMethod.getMethodCode().equals("checkout")) {
            CheckoutComFragment fragment = new CheckoutComFragment();
            String orderID = order.getID();
            fragment.setOrderID(orderID);
            fragment.setmOrderEntity(order);
            fragment.setmOrderHistoryDetail(orderHisDetail);
            SimiManager.getIntance().replaceFragment(fragment);
        }
    }
}
