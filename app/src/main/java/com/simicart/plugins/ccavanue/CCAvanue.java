package com.simicart.plugins.ccavanue;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.simicart.MainActivity;
import com.simicart.core.checkout.entity.OrderEntity;
import com.simicart.core.checkout.entity.PaymentMethod;
import com.simicart.core.config.Config;
import com.simicart.core.event.checkout.CheckoutData;
import com.simicart.plugins.ccavanue.activity.WebViewActivity;
import com.simicart.plugins.ccavanue.utility.AvenuesParams;
import com.simicart.plugins.ccavanue.utility.ServiceUtility;

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
        Intent intent = new Intent(this.context, WebViewActivity.class);
        if (order.getID() != null) {
            intent.putExtra(AvenuesParams.ORDER_ID, order.getID());
            Log.e("CCAVANUE", "ORDERID: " + order.getID());
        }
        if(Config.getInstance().getCurrency_code() != null){
            intent.putExtra(AvenuesParams.CURRENCY, Config.getInstance().getCurrency_code());
            Log.e("CCAVANUE", "CURRENCY: " + Config.getInstance().getCurrency_code());
        }
        if (order.getGrandTotal() != null) {
            intent.putExtra(AvenuesParams.AMOUNT, (order.getGrandTotal()+""));
            Log.e("CCAVANUE", "GRANDTOTAL: " + order.getGrandTotal());
        }
        this.context.startActivity(intent);
    }
}
