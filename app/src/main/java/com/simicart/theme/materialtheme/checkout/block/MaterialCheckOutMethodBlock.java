package com.simicart.theme.materialtheme.checkout.block;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.config.Config;
import com.simicart.core.config.Rconfig;

/**
 * Created by Sony on 4/12/2016.
 */
public class MaterialCheckOutMethodBlock extends SimiBlock {
    protected TextView tv_checkout_information;
    protected TextView tv_checkout_existing_customer;
    protected TextView tv_checkout_new_customer;
    protected TextView tv_checkout_as_guest;

    public MaterialCheckOutMethodBlock(View view, Context context) {
        super(view, context);
    }

    public void setOnClickCheckoutExistingCustomer(View.OnClickListener listener){
        tv_checkout_existing_customer.setOnClickListener(listener);
    }

    public void setOnClickCheckoutNewCustomer(View.OnClickListener listener){
        tv_checkout_new_customer.setOnClickListener(listener);
    }

    public void setOnClickCheckoutAsGuest(View.OnClickListener listener){
        tv_checkout_as_guest.setOnClickListener(listener);
    }

    @Override
    public void initView() {
        tv_checkout_information = (TextView) mView.findViewById(Rconfig.getInstance().id("tv_checkout_information"));
        tv_checkout_information.setText("1" + " " + Config.getInstance().getText("Checkout Method"));
        tv_checkout_information.setTextColor(Config.getInstance().getSection_text_color());
        tv_checkout_information.setBackgroundColor((Color.parseColor(Config.getInstance().getSection_color())));

        tv_checkout_existing_customer = (TextView) mView.findViewById(Rconfig.getInstance().id("tv_checkout_existing_customer"));
        tv_checkout_existing_customer.setText(Config.getInstance().getText("Checkout as existing customer"));

        tv_checkout_new_customer = (TextView) mView.findViewById(Rconfig.getInstance().id("tv_checkout_new_customer"));
        tv_checkout_new_customer.setText(Config.getInstance().getText("Checkout as new customer"));

        tv_checkout_as_guest = (TextView) mView.findViewById(Rconfig.getInstance().id("tv_checkout_as_guest"));
        tv_checkout_as_guest.setText(Config.getInstance().getText("Checkout as guest"));
    }
}
