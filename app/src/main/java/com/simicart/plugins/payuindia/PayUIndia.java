package com.simicart.plugins.payuindia;

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
import com.simicart.plugins.payuindia.activity.PayUIndiaActivity;

/**
 * Created by Sony on 1/27/2016.
 */
public class PayUIndia {
    Context context;

    public PayUIndia() {
        IntentFilter filter = new IntentFilter("com.simicart.paymentmethod.placeorder");
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getBundleExtra(Constants.DATA);
                SimiEventCheckoutEntity entity = (SimiEventCheckoutEntity) bundle.getSerializable(Constants.ENTITY);
                PaymentMethod payment = entity.getPaymentMethod();
                OrderEntity order = entity.getOder();
                callPayUIndiaServer(payment, order);
            }
        };
        context = SimiManager.getIntance().getCurrentContext();
        LocalBroadcastManager.getInstance(context).registerReceiver(receiver, filter);
    }

    public void callPayUIndiaServer(PaymentMethod paymentMethod, OrderEntity order){
        if(paymentMethod.getMethodCode().equals("payubiz")){
            Intent intent = new Intent(this.context, PayUIndiaActivity.class);
            intent.putExtra("EXTRA_ORDERID", order.getID());
            intent.putExtra("EXTRA_AMOUNT", order.getGrandTotal() + "");
            this.context.startActivity(intent);
        }
    }
}
