package com.simicart.theme.materialtheme.checkout.block;

import android.content.Context;
import android.view.View;

import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.config.Rconfig;
import com.simicart.theme.materialtheme.checkout.delegate.MaterialReviewOrderDelegate;

/**
 * Created by Sony on 4/12/2016.
 */
public class MaterialReviewOrderBlock extends SimiBlock implements MaterialReviewOrderDelegate{
    protected View inc_checkout_method;
    protected View inc_billing_infomation;
    protected View inc_shipping_infomation;
    protected View inc_shipping_method;
    protected View inc_payment_infomation;
    protected View inc_order_reivew;

    public MaterialReviewOrderBlock(View view, Context context) {
        super(view, context);
    }

    @Override
    public void initView() {
        inc_checkout_method = (View) mView.findViewById(Rconfig.getInstance().id("inc_checkout_method"));
        inc_billing_infomation = (View) mView.findViewById(Rconfig.getInstance().id("inc_billing_infomation"));
        inc_shipping_infomation = (View) mView.findViewById(Rconfig.getInstance().id("inc_shipping_infomation"));
        inc_shipping_method = (View) mView.findViewById(Rconfig.getInstance().id("inc_shipping_method"));
        inc_payment_infomation = (View) mView.findViewById(Rconfig.getInstance().id("inc_payment_infomation"));
        inc_order_reivew = (View) mView.findViewById(Rconfig.getInstance().id("inc_order_reivew"));
    }
}
