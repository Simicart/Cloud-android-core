package com.simicart.core.customer.block;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.base.delegate.SimiDelegate;
import com.simicart.core.checkout.adapter.ProductOrderAdapter;
import com.simicart.core.common.Utils;
import com.simicart.core.common.price.OrderHistoryDetailPriceView;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.core.customer.entity.BillingAddress;
import com.simicart.core.customer.entity.OrderHisDetail;
import com.simicart.core.customer.entity.ShippingAddress;
import com.simicart.core.material.ButtonRectangle;

@SuppressLint("DefaultLocale")
public class OrderHistoryDetailBlock extends SimiBlock implements
        SimiDelegate {
    protected OrderHisDetail orderHisDetail;
    private View view_top;
    private View view_bottom;
    private View view_order_date;

    public OrderHistoryDetailBlock(View view, Context context) {
        super(view, context);
    }

    public void setOrderHisDetail(OrderHisDetail orderHisDetail) {
        this.orderHisDetail = orderHisDetail;
    }


    @Override
    public void initView() {
        view_top = mView.findViewById(Rconfig.getInstance()
                .id("view_top"));
        view_bottom = mView.findViewById(Rconfig.getInstance()
                .id("view_bottom"));
        view_order_date = mView.findViewById(Rconfig.getInstance().id("view_order_date"));
        view_order_date.setVisibility(View.GONE);
        view_top.setBackgroundColor(Config.getInstance().getApp_backrground());
        view_bottom.setBackgroundColor(Config.getInstance().getApp_backrground());
        view_order_date.setBackgroundColor(Config.getInstance().getApp_backrground());

        if (null != orderHisDetail) {
            String symbol = orderHisDetail.getmOrderCurrencyCode();
            initOrderDetail();
            initShipping(symbol);
            initListItem(symbol);
            initFeeDetail();
            initPayment();
        }
    }

    public void initOrderDetail() {
        TextView lb_date = (TextView) mView.findViewById(Rconfig.getInstance()
                .id("lb_date"));
        lb_date.setText(Config.getInstance().getText("Order Date"));
        lb_date.setTextColor(Config.getInstance().getContent_color());
        TextView lb_orderT = (TextView) mView.findViewById(Rconfig
                .getInstance().id("lb_orderT"));
        lb_orderT.setText(Config.getInstance().getText("Order #"));
        lb_orderT.setTextColor(Config.getInstance().getContent_color());
        TextView lb_total = (TextView) mView.findViewById(Rconfig.getInstance()
                .id("lb_total"));
        lb_total.setText(Config.getInstance().getText("Order Total"));
        lb_total.setTextColor(Config.getInstance().getContent_color());
    }

    public void initShipping(String symbol) {
        TextView lb_shipto = (TextView) mView.findViewById(Rconfig
                .getInstance().id("lb_shipto"));
        lb_shipto
                .setText(Config.getInstance().getText("SHIP TO").toUpperCase());
        lb_shipto.setTextColor(Config.getInstance().getSection_text_color());
        lb_shipto.setBackgroundColor(Color.parseColor(Config.getInstance().getSection_color()));
        TextView lb_items = (TextView) mView.findViewById(Rconfig.getInstance()
                .id("lb_items"));
        lb_items.setText(Config.getInstance().getText("ITEMS").toUpperCase());
        lb_items.setTextColor(Config.getInstance().getSection_text_color());
        lb_items.setBackgroundColor(Color.parseColor(Config.getInstance().getSection_color()));

        TextView tv_date = (TextView) mView.findViewById(Rconfig
                .getInstance().id("tv_date"));
        tv_date.setText(orderHisDetail.getmCreateAt());
        tv_date.setTextColor(Config.getInstance().getContent_color());
        TextView tv_orderT = (TextView) mView.findViewById(Rconfig
                .getInstance().id("tv_orderT"));
        tv_orderT.setText(String.valueOf(orderHisDetail.getmSeqNum()));
        tv_orderT.setTextColor(Config.getInstance().getContent_color());
        TextView tv_total = (TextView) mView.findViewById(Rconfig
                .getInstance().id("tv_total"));
        tv_total.setTextColor(Color.parseColor(Config.getInstance()
                .getPrice_color()));


        String price = Config.getInstance().getPrice(
                orderHisDetail.getmGrandTotal());
        if (null != symbol) {
            price = Config.getInstance().getPrice(
                    orderHisDetail.getmGrandTotal());
        }
        tv_total.setText(price);

        ShippingAddress shippingAddress = orderHisDetail
                .getmShippingAddress();
        if (shippingAddress != null) {
            // name
            TextView st_name = (TextView) mView.findViewById(Rconfig
                    .getInstance().id("st_name"));
            st_name.setTextColor(Config.getInstance().getContent_color());
            String name = shippingAddress.getmFirstName() + " " + shippingAddress.getmLastName();
            if (Utils.validateString(name)) {
                st_name.setText(name);
            } else {
                st_name.setVisibility(View.GONE);
            }
            // street
            TextView st_street = (TextView) mView.findViewById(Rconfig
                    .getInstance().id("st_street"));
            st_street.setTextColor(Config.getInstance().getContent_color());
            String street = shippingAddress.getmStreet();
            if (Utils.validateString(street)) {
                st_street.setText(street);
            } else {
                st_street.setVisibility(View.GONE);
            }
            // city
            TextView st_city = (TextView) mView.findViewById(Rconfig
                    .getInstance().id("st_city"));
            st_city.setTextColor(Config.getInstance().getContent_color());
            String city = shippingAddress.getmCity();
            if (Utils.validateString(city)) {
                if (shippingAddress.getmStateName() == null
                        || shippingAddress.getmStateName().equals("null")) {
                    st_city.setText(city + ", " + shippingAddress.getmPostCode());
                } else {
                    st_city.setText(city + ", "
                            + shippingAddress.getmStateName() + ", "
                            + shippingAddress.getmPostCode());
                }
            } else {
                st_city.setVisibility(View.GONE);
            }

            // country
            TextView st_country = (TextView) mView.findViewById(Rconfig
                    .getInstance().id("st_country"));
            st_country
                    .setTextColor(Config.getInstance().getContent_color());
            String country = shippingAddress.getmCountryName();
            if (Utils.validateString(country)) {
                st_country.setText(country);
            } else {
                st_country.setVisibility(View.GONE);
            }
            // phone
            TextView st_phone = (TextView) mView.findViewById(Rconfig
                    .getInstance().id("st_phone"));
            st_phone.setTextColor(Config.getInstance().getContent_color());
            String phone = shippingAddress.getmPhone();
            if (Utils.validateString(phone)) {
                st_phone.setText(phone);
            } else {
                st_phone.setVisibility(View.GONE);
            }
            // email
            TextView st_email = (TextView) mView.findViewById(Rconfig
                    .getInstance().id("st_email"));
            st_email.setTextColor(Config.getInstance().getContent_color());
            String email = DataLocal.getEmailRemember();
            if (Utils.validateString(email)) {
                st_email.setText(email);
            } else {
                st_email.setVisibility(View.GONE);
            }
        } else {
            LinearLayout ll_shipping = (LinearLayout) mView
                    .findViewById(Rconfig.getInstance().id("ll_shipping"));
            ll_shipping.setVisibility(View.GONE);
        }

        TextView st_shipingmethod = (TextView) mView.findViewById(Rconfig
                .getInstance().id("st_shipingmethod"));
        st_shipingmethod.setTextColor(Config.getInstance()
                .getContent_color());

        String shippingMethod = "";
        if (orderHisDetail.getmShipping() != null)
            shippingMethod = orderHisDetail.getmShipping().getmShippingTitle();
        if (Utils.validateString(shippingMethod)) {
            st_shipingmethod.setText(shippingMethod);
        } else {
            lb_shipto.setVisibility(View.GONE);
            st_shipingmethod.setVisibility(View.GONE);
        }
    }

    public void initListItem(String symbol) {
        ListView list_item = (ListView) mView.findViewById(Rconfig
                .getInstance().id("list_item"));
        ProductOrderAdapter orderAdapter = new ProductOrderAdapter(
                mContext, orderHisDetail.getmItems(), 1);
        if (null != symbol) {
            orderAdapter.setCurrencySymbol(symbol);
        }
        list_item.setAdapter(orderAdapter);
    }

    public void initFeeDetail() {
        LinearLayout ll_price = (LinearLayout) mView.findViewById(Rconfig
                .getInstance().id("ll_price"));
        TextView tv_label_price = (TextView) mView.findViewById(Rconfig
                .getInstance().id("lb_feeDetail"));
        tv_label_price.setTextColor(Config.getInstance().getSection_text_color());
        tv_label_price.setBackgroundColor(Color.parseColor(Config.getInstance().getSection_color()));
        OrderHistoryDetailPriceView viewPrice = new OrderHistoryDetailPriceView(orderHisDetail);

        View view = viewPrice.createTotalView();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.RIGHT;
        params.setMargins(0, 5, 15, 5);
        if (null != view) {
            ll_price.removeAllViews();
            ll_price.addView(view, params);
            tv_label_price.setText(Config.getInstance()
                    .getText("FEE DETAIL").toUpperCase());
        } else {
            tv_label_price.setVisibility(View.GONE);
            ll_price.setVisibility(View.GONE);
        }
    }

    public void initPayment() {
        TextView lb_payment = (TextView) mView.findViewById(Rconfig
                .getInstance().id("lb_payment"));
        lb_payment.setText(Config.getInstance().getText("PAYMENT")
                .toUpperCase());
        lb_payment.setTextColor(Config.getInstance().getSection_text_color());
        lb_payment.setBackgroundColor(Color.parseColor(Config.getInstance().getSection_color()));
        TextView p_paymentmethod = (TextView) mView.findViewById(Rconfig
                .getInstance().id("p_paymentmethod"));
        if(orderHisDetail.getPayment() != null){
            p_paymentmethod.setText(orderHisDetail.getPayment().getTitle());
        }else{
            lb_payment.setVisibility(View.GONE);
            p_paymentmethod.setVisibility(View.GONE);
        }
        p_paymentmethod.setTextColor(Config.getInstance()
                .getContent_color());

        BillingAddress billingAddress = orderHisDetail.getmBillingAddress();
        TextView p_name = (TextView) mView.findViewById(Rconfig
                .getInstance().id("p_name"));
        p_name.setTextColor(Config.getInstance().getContent_color());
        p_name.setText(billingAddress.getmFirstName() + " " + billingAddress.getmLastName());
        TextView p_street = (TextView) mView.findViewById(Rconfig
                .getInstance().id("p_street"));
        p_street.setTextColor(Config.getInstance().getContent_color());
        p_street.setText(billingAddress.getmStreet());
        TextView p_city = (TextView) mView.findViewById(Rconfig
                .getInstance().id("p_city"));
        p_city.setTextColor(Config.getInstance().getContent_color());
        if (billingAddress.getmStateName() == null
                || billingAddress.getmStateName().equals("null")) {
            p_city.setText(billingAddress.getmCity() + ", "
                    + billingAddress.getmPostCode());
        } else {
            p_city.setText(billingAddress.getmCity() + ", "
                    + billingAddress.getmStateName() + ", "
                    + billingAddress.getmPostCode());
        }
        TextView p_country = (TextView) mView.findViewById(Rconfig
                .getInstance().id("p_country"));
        p_country.setTextColor(Config.getInstance().getContent_color());
        p_country.setText(billingAddress.getmCountryName());
        TextView p_phone = (TextView) mView.findViewById(Rconfig
                .getInstance().id("p_phone"));
        p_phone.setTextColor(Config.getInstance().getContent_color());
        p_phone.setText(billingAddress.getmPhone());
        TextView p_email = (TextView) mView.findViewById(Rconfig
                .getInstance().id("p_email"));
        p_email.setTextColor(Config.getInstance().getContent_color());
        p_email.setText(DataLocal.getEmailRemember());

        TextView p_couponCode = (TextView) mView.findViewById(Rconfig
                .getInstance().id("p_couponCode"));
        p_couponCode.setTextColor(Config.getInstance().getContent_color());
        if(orderHisDetail.getmCoupon() != null && orderHisDetail.getmCoupon().size() > 0) {
            String couponCode = orderHisDetail.getmCoupon().get(0);
            if (Utils.validateString(couponCode)) {
                p_couponCode.setText(Config.getInstance()
                        .getText("Coupon Code")
                        + ": "
                        + couponCode);
            } else {
                p_couponCode.setText(Config.getInstance()
                        .getText("Coupon Code")
                        + ": "
                        + Config.getInstance().getText("NONE").toUpperCase());
            }
        }else{
            p_couponCode.setText(Config.getInstance()
                    .getText("Coupon Code")
                    + ": "
                    + Config.getInstance().getText("NONE").toUpperCase());
        }
    }

}
