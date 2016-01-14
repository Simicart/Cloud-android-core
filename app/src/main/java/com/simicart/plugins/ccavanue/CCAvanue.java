package com.simicart.plugins.ccavanue;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.simicart.MainActivity;
import com.simicart.core.checkout.entity.OrderEntity;
import com.simicart.core.checkout.entity.PaymentMethod;
import com.simicart.core.event.checkout.CheckoutData;
import com.simicart.plugins.ccavanue.activity.InitialActivity;

/**
 * Created by Sony on 1/12/2016.
 */
public class CCAvanue {
    Context context;

    public CCAvanue(String method, CheckoutData checkoutData) {
        this.context = MainActivity.context;
        Log.e("CCAvanue", "CCAvanue" + method);
        if (method.equals("callCCAvanueServer")) {
            this.callCCAvanueServer(checkoutData.getPaymentMethod(),
                    checkoutData.getOder());
        }
    }

    public void callCCAvanueServer(PaymentMethod paymentMethod, OrderEntity order){

        //DONG + trunk local Frank
        //Online
        Intent intent = new Intent(this.context, InitialActivity.class);
        this.context.startActivity(intent);
    }
}
