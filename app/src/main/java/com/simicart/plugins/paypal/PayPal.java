package com.simicart.plugins.paypal;

import java.util.ArrayList;

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
import com.simicart.core.event.checkout.SimiEventCheckoutEntity;

public class PayPal {
    Context context;

    public PayPal() {
        IntentFilter filter = new IntentFilter("com.simicart.paymentmethod.placeorder");
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getBundleExtra(Constants.DATA);
                SimiEventCheckoutEntity entity = (SimiEventCheckoutEntity) bundle.getSerializable(Constants.ENTITY);
                PaymentMethod payment = entity.getPaymentMethod();
                OrderEntity order = entity.getOder();
                callPayPalServer(payment, order);
            }
        };

        context = SimiManager.getIntance().getCurrentContext();
        LocalBroadcastManager.getInstance(context).registerReceiver(receiver, filter);

    }

//	public PayPal(Context context, String method,
//			ArrayList<PaymentMethod> paymentList) {
//		this.context = context;
//		Log.e("Paypal","Paypal:"+method);
//		if (method.equals("refreshList")) {
//			Log.e("Paypal","refreshList");
//			this.refreshList(paymentList);
//		}
//	}

//	public void refreshList(ArrayList<PaymentMethod> paymentList) {
//		int i = 0;
//		int position = -1;
//
//		for (PaymentMethod payment : paymentList) {
//			if (payment.getMethodCode().equals("paypal")) {
//				if (payment.getClientId().equals("null")
//						|| payment.getClientId() == null
//						|| payment.getClientId().equals("")) {
//					position = i;
//					break;
//				}
//			}
//			i++;
//		}
//		if (position != -1) {
//			paymentList.remove(position);
//		}
//	}

    public void callPayPalServer(PaymentMethod paymentMethod, OrderEntity order) {
        if (paymentMethod.getMethodCode().equals("paypal")) {
            Intent intent = new Intent(this.context, PaypalSimicart.class);

            String client_id = paymentMethod.getClientId();
            intent.putExtra("EXTRA_CLIENT_ID", client_id);
            Log.e("PayPal CLIENT_ID ", client_id);

            String is_sandbox = paymentMethod.getSandBox();
            intent.putExtra("EXTRA_SANDBOX", is_sandbox);
            Log.e("PayPal IS_SANDBOX ", is_sandbox);

            intent.putExtra("EXTRA_PRICE", (order.getGrandTotal() + ""));
            Log.e("PayPal EXTRA_PRICE", "" + order.getGrandTotal());

            if (paymentMethod.getBnCode() != null) {
                String bnCode = paymentMethod.getBnCode();
                intent.putExtra("EXTRA_BNCODE", bnCode);
                Log.e("PayPal BNCODE ", bnCode);
            }

            if (paymentMethod.getPaypalAction() != null) {
                String payment_action = paymentMethod.getPaypalAction();
                intent.putExtra("PAYPAL_ACTION", payment_action);
            }

            intent.putExtra("EXTRA_INVOICE_NUMBER", order.getID());
            Log.e("PayPal INVOICE NUMBER ", order.getID());
            this.context.startActivity(intent);
        } else {
            return;
        }

    }
}
