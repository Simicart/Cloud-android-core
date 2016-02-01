package com.simicart.plugins.ccavanue;

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
import com.simicart.core.config.Config;
import com.simicart.core.config.Constants;
import com.simicart.core.event.checkout.SimiEventCheckoutEntity;
import com.simicart.plugins.ccavanue.activity.WebViewActivity;
import com.simicart.plugins.ccavanue.utility.AvenuesParams;

/**
 * Created by Sony on 1/12/2016.
 */
public class CCAvanue {
    Context context;

    public CCAvanue() {
        IntentFilter filter = new IntentFilter("com.simicart.paymentmethod.placeorder");
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getBundleExtra(Constants.DATA);
                SimiEventCheckoutEntity entity = (SimiEventCheckoutEntity) bundle.getSerializable(Constants.ENTITY);
                PaymentMethod payment = entity.getPaymentMethod();
                OrderEntity order = entity.getOder();
                callCCAvanueServer(payment, order);
            }
        };

        context = SimiManager.getIntance().getCurrentContext();
        LocalBroadcastManager.getInstance(context).registerReceiver(receiver, filter);
    }


    public void callCCAvanueServer(PaymentMethod paymentMethod, OrderEntity order) {
        if (paymentMethod.getMethodCode().equals("ccavenue")) {
            Intent intent = new Intent(this.context, WebViewActivity.class);
            if (order.getID() != null) {
                intent.putExtra(AvenuesParams.ORDER_ID, order.getID());
            }
            if (Config.getInstance().getCurrency_code() != null) {
                intent.putExtra(AvenuesParams.CURRENCY, Config.getInstance().getCurrency_code());
            }
            if (order.getGrandTotal() != null) {
                intent.putExtra(AvenuesParams.AMOUNT, (order.getGrandTotal() + ""));
            }
            this.context.startActivity(intent);
        }
    }
}
