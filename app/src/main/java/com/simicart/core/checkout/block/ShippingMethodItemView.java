package com.simicart.core.checkout.block;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simicart.core.checkout.delegate.ShippingMethodManageDelegate;
import com.simicart.core.checkout.entity.ShippingMethod;
import com.simicart.core.common.Utils;
import com.simicart.core.common.ViewIdGenerator;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;

/**
 * Created by MSI on 04/02/2016.
 */
public class ShippingMethodItemView {

    protected ShippingMethod mShippingMethod;
    protected Context mContext;
    protected TextView tv_price;
    protected TextView tv_content;
    protected TextView tv_title;
    protected RelativeLayout rlt_body;
    protected ImageView img_checked;
    protected int mIDIconNormal;
    protected int mIDIconChecked;
//    protected boolean isChecked = false;
    protected ShippingMethodManageDelegate mDelegate;

    private int dp5 = Utils.getValueDp(5);
    private int dp10 = Utils.getValueDp(10);
    private int dp20 = Utils.getValueDp(20);
    private int dp30 = Utils.getValueDp(30);


    public ShippingMethodItemView(ShippingMethod shippingMethod, Context context, ShippingMethodManageDelegate delegate) {
        mShippingMethod = shippingMethod;
        mContext = context;
        mDelegate = delegate;
        mIDIconNormal = Rconfig.getInstance().drawable("core_radiobox");
        mIDIconChecked = Rconfig.getInstance().drawable("core_radiobox2");
    }

    public View initView() {
        RelativeLayout rl_shipping = new RelativeLayout(mContext);
        RelativeLayout.LayoutParams lp_shipping = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        rl_shipping.setLayoutParams(lp_shipping);

        // price
        TextView tv_price = createPriceTV();
        rl_shipping.addView(tv_price);

        // body
        rlt_body = crateBodyRLT();

        rl_shipping.addView(rlt_body);

        rl_shipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processEventChecked();
            }
        });

        //check select
        if(mShippingMethod.getIsSelected()){
            mDelegate.updateShippingMethod(mShippingMethod);
            updateChecked(true);
        }

        return rl_shipping;


    }

    private TextView createPriceTV() {
        tv_price = new TextView(mContext);
        if (DataLocal.isTablet) {
            tv_price.setPadding(dp10, 0, dp20, 0);
        } else {
            tv_price.setPadding(dp5, 0, dp10, 0);
        }

        String s_incl_tax = mShippingMethod.getS_method_fee_incl_tax();
        Float price = mShippingMethod.getPrice();
        Float incl_tax = null;
        try {
            incl_tax = Float.parseFloat(s_incl_tax);
        } catch (Exception e) {
            incl_tax = null;
        }

        if (incl_tax == null) {
            tv_price.setText(Config.getInstance().getPrice(price));
            tv_price.setTextColor(Color.parseColor(Config.getInstance().getPrice_color()));
        } else {
            String price_method = "<font  color='" + Config.getInstance().getPrice_color() + "'>"
                    + Config.getInstance().getPrice(price)
                    + "</font> <font color='" + Config.getInstance().getContent_color_string() + "'>+"
                    + "(" + Config.getInstance().getText("Incl. Tax")
                    + "</font> <font  color='" + Config.getInstance().getPrice_color() + "'> "
                    + Config.getInstance().getPrice(incl_tax)
                    + "</font>" + "<font color='" + Config.getInstance().getContent_color_string() + "'>" + ")" + "</font>";
            tv_price.setText(Html.fromHtml(price_method));
        }


        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        param.addRule(RelativeLayout.CENTER_VERTICAL);
        if (DataLocal.isLanguageRTL) {
            param.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            param.setMargins(dp30, 0,
                    dp30, 0);
        } else {
            param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        }
        tv_price.setLayoutParams(param);
        return tv_price;
    }


    private RelativeLayout crateBodyRLT() {
        RelativeLayout rl_value = new RelativeLayout(
                mContext);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_VERTICAL);

        rl_value.setGravity(RelativeLayout.CENTER_VERTICAL);
        rl_value.setLayoutParams(lp);
        if (DataLocal.isLanguageRTL) {
            lp.addRule(RelativeLayout.ALIGN_RIGHT, tv_price.getId());
        } else {
            lp.addRule(RelativeLayout.ALIGN_LEFT, tv_price.getId());
        }

        if (DataLocal.isTablet) {
            rl_value.setPadding(dp20, dp5, dp20, dp10);
        } else {
            rl_value.setPadding(dp10, dp5, dp10, dp10);
        }

        // title
        tv_title = createTitleTV();
        rl_value.addView(tv_title);

        // content
        tv_content = createContentTV();
        if (null != tv_content) {
            rl_value.addView(tv_content);
        }

        // checked image
        img_checked = createCheckedIMG();
        rl_value.addView(img_checked);

        return rl_value;
    }


    private TextView createTitleTV() {
        TextView tv_title = new TextView(mContext);
        tv_title.setId(ViewIdGenerator.generateViewId());
        tv_title.setText(mShippingMethod.getServiceName(),
                TextView.BufferType.SPANNABLE);
        tv_title.setTextColor(Config.getInstance().getContent_color());
        RelativeLayout.LayoutParams tvtitle_lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        if (DataLocal.isLanguageRTL) {
            tvtitle_lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            tvtitle_lp.setMargins(Utils.getValueDp(30), 0, 0, 0);
            tv_title.setPadding(0, Utils.getValueDp(10), 0, 0);
        } else {
            tvtitle_lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            tvtitle_lp.setMargins(Utils.getValueDp(30), 0,
                    Utils.getValueDp(30), 0);
            tv_title.setPadding(0, Utils.getValueDp(10),
                    Utils.getValueDp(20), 0);
        }

        tvtitle_lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        tv_title.setLayoutParams(tvtitle_lp);
        tv_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
        return tv_title;
    }


    private TextView createContentTV() {
        String description = mShippingMethod.getDescription();
        if (Utils.validateString(description)) {
            TextView tv_content = new TextView(mContext);
            tv_content.setTextColor(Config.getInstance().getContent_color());
            tv_content.setId(ViewIdGenerator.generateViewId());
            tv_content.setText(description);
            RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            if (DataLocal.isLanguageRTL) {
                param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                param.setMargins(dp30, 0, 0, 0);
            } else {
                param.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                param.setMargins(dp30, 0, dp30, 0);
                tv_content.setPadding(0, dp10, dp20, 0);
            }
            param.addRule(RelativeLayout.BELOW, tv_title.getId());

            tv_content.setLayoutParams(param);
            tv_content.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            return tv_content;
        }
        return null;
    }


    private ImageView createCheckedIMG() {
        ImageView img_checked = new ImageView(mContext);
        RelativeLayout.LayoutParams checkbox_lp = new RelativeLayout.LayoutParams(
                dp20, dp20);
        checkbox_lp.addRule(RelativeLayout.CENTER_VERTICAL);
        checkbox_lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        img_checked.setLayoutParams(checkbox_lp);

        Drawable icon = null;
        if (mShippingMethod.getIsSelected()) {
            icon = mContext.getResources().getDrawable(
                    mIDIconChecked);
        } else {
            icon = mContext.getResources().getDrawable(
                    mIDIconNormal);
        }

        icon.setColorFilter(Config.getInstance().getContent_color(),
                PorterDuff.Mode.SRC_ATOP);
        img_checked.setImageDrawable(icon);
        img_checked.setId(ViewIdGenerator.generateViewId());

        return img_checked;
    }

    private void processEventChecked() {
        if (!mShippingMethod.getIsSelected()) {
//            isChecked = true;
            updateChecked(true);
            mDelegate.updateShippingMethod(mShippingMethod);
            mShippingMethod.setIsSelected(true);
        }
    }

    public void updateChecked(boolean is_checked) {
        if (is_checked) {
            Drawable icon = mContext.getResources().getDrawable(
                    mIDIconChecked);
            icon.setColorFilter(Config.getInstance().getContent_color(),
                    PorterDuff.Mode.SRC_ATOP);
            img_checked.setImageDrawable(icon);
        } else {
//            isChecked = false;
            mShippingMethod.setIsSelected(false);
            Drawable icon = mContext.getResources().getDrawable(
                    mIDIconNormal);
            icon.setColorFilter(Config.getInstance().getContent_color(),
                    PorterDuff.Mode.SRC_ATOP);
            img_checked.setImageDrawable(icon);
        }
    }

    public ShippingMethod getShippingMethod() {
        return mShippingMethod;
    }
}
