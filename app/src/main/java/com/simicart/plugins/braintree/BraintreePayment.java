package com.simicart.plugins.braintree;


import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.checkout.entity.OrderEntity;
import com.simicart.core.checkout.entity.PaymentMethod;
import com.simicart.core.config.Constants;
import com.simicart.core.event.checkout.SimiEventCheckoutEntity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class BraintreePayment {

    Context mContext;

    public BraintreePayment()
    {
        Log.e("BraintreePayment", "constructor");
        mContext= SimiManager.getIntance().getCurrentActivity();
        IntentFilter filter = new IntentFilter("com.simicart.paymentmethod.placeorder");
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getBundleExtra(Constants.DATA);
                SimiEventCheckoutEntity entity = (SimiEventCheckoutEntity) bundle.getSerializable(Constants.ENTITY);
                PaymentMethod payment = entity.getPaymentMethod();
                OrderEntity order = entity.getOder();
                callBrainTreeServer(payment,order);
            }
        };

        LocalBroadcastManager.getInstance(mContext).registerReceiver(receiver,filter);

    }

    public void callBrainTreeServer(PaymentMethod paymentMethod,
                                    OrderEntity order) {
        if (paymentMethod.getMethodCode().equals("braintree")) {
            Intent intent = new Intent(this.mContext, BrainTreeActivity.class);
            String totalPrice = "";
            totalPrice = String.valueOf(order.getGrandTotal());
            intent.putExtra("EXTRA_TOTAL_PRICE", totalPrice);

            String orderID = "";
            orderID = order.getID();

            Log.e("BraintreePayment ","=====> ORDER ID " + orderID);

            intent.putExtra("EXTRA_INVOICE_NUMBER", orderID);
            this.mContext.startActivity(intent);
        }
    }

}
