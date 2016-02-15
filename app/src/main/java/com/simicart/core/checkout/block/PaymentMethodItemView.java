package com.simicart.core.checkout.block;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.checkout.delegate.PaymentMethodManageDelegate;
import com.simicart.core.checkout.entity.PaymentMethod;
import com.simicart.core.checkout.fragment.CreditCardFragment;
import com.simicart.core.common.Utils;
import com.simicart.core.common.ViewIdGenerator;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;

/**
 * Created by MSI on 04/02/2016.
 */
public class PaymentMethodItemView {

    private PaymentMethod mPayment;
    private Context mContext;
    protected int mIDIconNormal;
    protected int mIDIconChecked;
    protected TextView tv_title;
    protected TextView tv_content;
    protected ImageView img_check;
    protected boolean isChecked = false;
    protected PaymentMethodManageDelegate mDelegate;
    int dp10 = Utils.getValueDp(10);
    int dp20 = Utils.getValueDp(20);
    int dp5 = Utils.getValueDp(5);
    int dp30 = Utils.getValueDp(30);

    public PaymentMethodItemView(PaymentMethod payment, Context context, PaymentMethodManageDelegate delegate) {
        mPayment = payment;
        mContext = context;
        mDelegate = delegate;
        mIDIconNormal = Rconfig.getInstance().drawable("core_radiobox");
        mIDIconChecked = Rconfig.getInstance().drawable("core_radiobox2");
    }


    public View initView() {
        String titleMethod = mPayment.getTitle();
        String contentMethod = mPayment.getContent();

        Log.e("PaymentMethodItemView ", "=============> TITLE METHOD " + titleMethod);

        RelativeLayout rl_value = new RelativeLayout(mContext);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        rl_value.setGravity(RelativeLayout.CENTER_VERTICAL);
        rl_value.setLayoutParams(lp);
        if (DataLocal.isTablet) {
            rl_value.setPadding(dp20, dp5,
                    dp20, dp10);
        } else {
            rl_value.setPadding(dp10, dp5,
                    dp10, dp10);
        }

        // title
        tv_title = createTitleTV();
        tv_title.setText(titleMethod,
                TextView.BufferType.SPANNABLE);
        rl_value.addView(tv_title);
        // content
        TextView tv_content = createContentTV();
        tv_content.setText(contentMethod,
                TextView.BufferType.SPANNABLE);
        rl_value.addView(tv_content);
        // image icon for checkbox
        img_check = createCheckIMG();
        rl_value.addView(img_check);

        rl_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processEventChecked();
            }
        });


        return rl_value;

    }

    private TextView createTitleTV() {
        TextView tv_title = new TextView(mContext);
        tv_title.setTextColor(Config.getInstance().getContent_color());
        tv_title.setId(ViewIdGenerator.generateViewId());
        RelativeLayout.LayoutParams tvtitle_lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        if (DataLocal.isLanguageRTL) {
            tvtitle_lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            tvtitle_lp.setMargins(0, 0, dp30, 0);
        } else {
            tvtitle_lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            tvtitle_lp.setMargins(dp30, 0, 0, 0);
        }

        tvtitle_lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        tv_title.setLayoutParams(tvtitle_lp);
        tv_title.setPadding(0, dp10, dp20,
                0);
        tv_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);

        return tv_title;
    }

    private TextView createContentTV() {
        TextView tv_content = new TextView(mContext);
        tv_content.setTextColor(Config.getInstance().getContent_color());
        tv_content.setId(ViewIdGenerator.generateViewId());
        RelativeLayout.LayoutParams tvcontent_lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        if (DataLocal.isLanguageRTL) {
            tvcontent_lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        } else {
            tvcontent_lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        }
        tvcontent_lp.addRule(RelativeLayout.BELOW, tv_title.getId());
        tvcontent_lp.setMargins(Utils.getValueDp(30), 0, 0, 0);
        tv_content.setLayoutParams(tvcontent_lp);
        tv_content.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        tv_content.setVisibility(View.GONE);

        return tv_content;
    }

    private ImageView createCheckIMG() {
        ImageView img_check = new ImageView(mContext);
        RelativeLayout.LayoutParams checkbox_lp = new RelativeLayout.LayoutParams(
                dp20, dp20);
        checkbox_lp.addRule(RelativeLayout.CENTER_VERTICAL);
        if (DataLocal.isLanguageRTL) {
            checkbox_lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        } else {
            checkbox_lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        }
        img_check.setLayoutParams(checkbox_lp);
        Drawable icon_nomal = mContext.getResources().getDrawable(
                mIDIconNormal);
        icon_nomal.setColorFilter(Config.getInstance().getContent_color(),
                PorterDuff.Mode.SRC_ATOP);
        img_check.setImageDrawable(icon_nomal);
        img_check.setId(ViewIdGenerator.generateViewId());

        return img_check;
    }

    private void processEventChecked() {
        if (!isChecked) {
            isChecked = true;
            updateChecked(true);
            mDelegate.updatePaymentSelected(mPayment);

        }
    }

    public void updateChecked(boolean is_checked) {
        if (is_checked) {
            Drawable icon = mContext.getResources().getDrawable(
                    mIDIconChecked);
            icon.setColorFilter(Config.getInstance().getContent_color(),
                    PorterDuff.Mode.SRC_ATOP);
            img_check.setImageDrawable(icon);
        } else {
            isChecked = true;
            Drawable icon = mContext.getResources().getDrawable(
                    mIDIconNormal);
            icon.setColorFilter(Config.getInstance().getContent_color(),
                    PorterDuff.Mode.SRC_ATOP);
            img_check.setImageDrawable(icon);
        }
    }

    public PaymentMethod getPaymentMethod() {
        return mPayment;
    }



}
