package com.simicart.plugins.braintree;


import com.simicart.MainActivity;
import com.simicart.core.checkout.entity.OrderEntity;
import com.simicart.core.checkout.entity.PaymentMethod;
import com.simicart.core.event.checkout.CheckoutData;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BraintreePayment {

    Context context;

    public BraintreePayment(String method, CheckoutData checkoutData) {
        this.context = MainActivity.context;
        if (method.equals("callBrainTreeServer")) {
            callBrainTreeServer(checkoutData.getPaymentMethod(),
                    checkoutData.getOder());
        }
    }

    public void callBrainTreeServer(PaymentMethod paymentMethod,
                                    OrderEntity order) {
        if (paymentMethod.getMethodCode().equals("braintree")) {
            Intent intent = new Intent(this.context, BrainTreeSimicart.class);
            String totalPrice = "";
            totalPrice = String.valueOf(order.getGrandTotal());
            intent.putExtra("EXTRA_TOTAL_PRICE", totalPrice);

            String orderID = "";
            orderID = order.getID();
            intent.putExtra("EXTRA_INVOICE_NUMBER", orderID);
            this.context.startActivity(intent);
        }
    }

}
