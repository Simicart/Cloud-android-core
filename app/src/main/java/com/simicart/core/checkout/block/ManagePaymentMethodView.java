package com.simicart.core.checkout.block;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.checkout.delegate.PaymentDelegate;
import com.simicart.core.checkout.delegate.PaymentMethodManageDelegate;
import com.simicart.core.checkout.entity.PaymentMethod;

import java.util.ArrayList;

/**
 * Created by MSI on 04/02/2016.
 */
public class ManagePaymentMethodView implements PaymentMethodManageDelegate {
    protected ArrayList<PaymentMethod> mPayments;
    protected ArrayList<PaymentMethodItemView> mItems;
    protected Context mContext;
    protected PaymentDelegate mDelegate;

    public ManagePaymentMethodView(ArrayList<PaymentMethod> payments, PaymentDelegate delegate) {
        mPayments = payments;
        mContext = SimiManager.getIntance().getCurrentActivity();
        mItems = new ArrayList<PaymentMethodItemView>();
        mDelegate = delegate;
    }


    public View createView() {
        if (null != mPayments && mPayments.size() > 0) {
            LinearLayout ll_payment = new LinearLayout(mContext);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            ll_payment.setOrientation(LinearLayout.VERTICAL);
            for (int i = 0; i < mPayments.size(); i++) {
                PaymentMethod payment = mPayments.get(i);
                PaymentMethodItemView item = new PaymentMethodItemView(payment, mContext, this);
                View view = item.initView();
                if (null != view) {
                    ll_payment.addView(view, param);
                    mItems.add(item);
                }
            }
            return ll_payment;
        }
        return null;
    }


    @Override
    public void updatePaymentSelected(PaymentMethod payment) {

        if (mItems.size() > 0) {
            for (int i = 0; i < mItems.size(); i++) {
                PaymentMethodItemView item = mItems.get(i);
                PaymentMethod cPayment = item.getPaymentMethod();
                if (!cPayment.equal(payment)) {
                    item.updateChecked(false);
                }
            }
        }
        mDelegate.updatePaymentChecked(payment);
    }
}
