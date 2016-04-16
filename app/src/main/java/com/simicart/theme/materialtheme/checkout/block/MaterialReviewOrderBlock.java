package com.simicart.theme.materialtheme.checkout.block;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.theme.materialtheme.checkout.delegate.MaterialReviewOrderDelegate;

/**
 * Created by Sony on 4/12/2016.
 */
public class MaterialReviewOrderBlock extends SimiBlock implements MaterialReviewOrderDelegate{
    protected TextView tv_checkout_information;
    protected View inc_checkout_method;
    protected TextView tv_billing_information;
    protected View inc_billing_infomation;
    protected TextView tv_shipping_information;
    protected View inc_shipping_infomation;
    protected TextView tv_shipping_method;
    protected View inc_shipping_method;
    protected TextView tv_payment_infomation;
    protected View inc_payment_infomation;
    protected TextView tv_order_review;
    protected View inc_order_reivew;

    private int SHIP_TO_ADDRESS = 0;
    private int SHIP_TO_DIFFERENT_ADDRESS = 1;

    public MaterialReviewOrderBlock(View view, Context context) {
        super(view, context);
    }

    @Override
    public void initView() {
        // View Checkout Method
        initCheckoutMethod();

        // View Billing Information
        initBillingInformation();

        // View Shipping Information
        initShippingInformation();

        // View Shipping Method
        initShippingMethod();

        // View Payment Method
        initPaymentInformation();

        // View Order Review
        initOrderReview();

        // Check Show Checkout Method
        checkShowCheckOutMethod();
    }

    private void initCheckoutMethod(){
        tv_checkout_information = (TextView) mView.findViewById(Rconfig.getInstance().id("tv_checkout_method"));
        tv_checkout_information.setText(Config.getInstance().getText("Checkout Method"));
        tv_checkout_information.setTextColor(Config.getInstance().getSection_text_color());
        tv_checkout_information.setBackgroundColor((Color.parseColor(Config.getInstance().getSection_color())));
        inc_checkout_method = (View) mView.findViewById(Rconfig.getInstance().id("inc_checkout_method"));
    }

    private void initBillingInformation(){
        tv_billing_information = (TextView) mView.findViewById(Rconfig.getInstance().id("tv_billing_information"));
        tv_billing_information.setText(Config.getInstance().getText("Billing Information"));
        tv_billing_information.setTextColor(Config.getInstance().getSection_text_color());
        tv_billing_information.setBackgroundColor((Color.parseColor(Config.getInstance().getSection_color())));
        inc_billing_infomation = (View) mView.findViewById(Rconfig.getInstance().id("inc_billing_infomation"));
    }

    private void initShippingInformation(){
        tv_shipping_information = (TextView) mView.findViewById(Rconfig.getInstance().id("tv_shipping_information"));
        tv_shipping_information.setText(Config.getInstance().getText("Shipping Information"));
        tv_shipping_information.setTextColor(Config.getInstance().getSection_text_color());
        tv_shipping_information.setBackgroundColor((Color.parseColor(Config.getInstance().getSection_color())));
        inc_shipping_infomation = (View) mView.findViewById(Rconfig.getInstance().id("inc_shipping_infomation"));
    }

    private void initShippingMethod(){
        tv_shipping_method = (TextView) mView.findViewById(Rconfig.getInstance().id("tv_shipping_method"));
        tv_shipping_method.setText(Config.getInstance().getText("Shipping Method"));
        tv_shipping_method.setTextColor(Config.getInstance().getSection_text_color());
        tv_shipping_method.setBackgroundColor((Color.parseColor(Config.getInstance().getSection_color())));
        inc_shipping_method = (View) mView.findViewById(Rconfig.getInstance().id("inc_shipping_method"));
    }

    private void initPaymentInformation(){
        tv_payment_infomation = (TextView) mView.findViewById(Rconfig.getInstance().id("tv_payment_infomation"));
        tv_payment_infomation.setText(Config.getInstance().getText("Payment Information"));
        tv_payment_infomation.setTextColor(Config.getInstance().getSection_text_color());
        tv_payment_infomation.setBackgroundColor((Color.parseColor(Config.getInstance().getSection_color())));
        inc_payment_infomation = (View) mView.findViewById(Rconfig.getInstance().id("inc_payment_infomation"));
    }

    private void initOrderReview(){
        tv_order_review = (TextView) mView.findViewById(Rconfig.getInstance().id("tv_order_review"));
        tv_order_review.setText(Config.getInstance().getText("Order Review"));
        tv_order_review.setTextColor(Config.getInstance().getSection_text_color());
        tv_order_review.setBackgroundColor((Color.parseColor(Config.getInstance().getSection_color())));
        inc_order_reivew = (View) mView.findViewById(Rconfig.getInstance().id("inc_order_reivew"));
    }

    private void checkShowCheckOutMethod(){
        if (DataLocal.isSignInComplete()){
            tv_checkout_information.setVisibility(View.GONE);
            inc_checkout_method.setVisibility(View.GONE);
            inc_billing_infomation.setVisibility(View.VISIBLE);
        }else{
            tv_checkout_information.setVisibility(View.VISIBLE);
            inc_checkout_method.setVisibility(View.VISIBLE);
            inc_billing_infomation.setVisibility(View.GONE);
        }
    }

    @Override
    public void showShippingInformation(int type) {
        if(type == SHIP_TO_ADDRESS) {
            inc_shipping_infomation.setVisibility(View.VISIBLE);
        }else if (type == SHIP_TO_DIFFERENT_ADDRESS){
            inc_shipping_infomation.setVisibility(View.GONE);
        }
    }

    @Override
    public void showShippingMethod() {
        inc_shipping_method.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPaymentInformation() {
        inc_payment_infomation.setVisibility(View.VISIBLE);
    }
}
