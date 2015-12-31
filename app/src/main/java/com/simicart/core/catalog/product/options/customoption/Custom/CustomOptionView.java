package com.simicart.core.catalog.product.options.customoption.Custom;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.catalog.product.options.customoption.Custom.entity.CustomOptionEntity;
import com.simicart.core.catalog.product.options.customoption.ManageCustomOptionDelegate;
import com.simicart.core.catalog.product.options.customoption.value.ValueView;
import com.simicart.core.catalog.product.options.customoption.value.entity.ValueCustomOptionEntity;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.config.Rconfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CustomOptionView {

    public static boolean ADD_OPERATOR = true;
    public static boolean SUB_OPERATOR = false;

    protected CustomOptionEntity mCustomOption;
    protected View mView;
    protected RelativeLayout rlt_header;
    protected ImageView imv_arr;
    protected LinearLayout ll_body;
    protected TextView tv_name;
    protected TextView tv_required;
    protected Context mContext;
    protected ManageCustomOptionDelegate mDelegate;
    protected boolean isShowWhenStart = true;
    protected ArrayList<ValueView> mListValueView;

    public void setShowWhenStart(boolean isShow) {
        isShowWhenStart = isShow;
    }

    public CustomOptionView(CustomOptionEntity cacheOption) {
        mCustomOption = cacheOption;
        mContext = SimiManager.getIntance().getCurrentContext();
        mListValueView = new ArrayList<ValueView>();
    }

    public void setDelegate(ManageCustomOptionDelegate delegate) {
        mDelegate = delegate;
    }

    public View initOptionsView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        mView = inflater.inflate(
                Rconfig.getInstance().layout("core_cache_option_layout"), null,
                false);
        rlt_header = (RelativeLayout) mView.findViewById(Rconfig.getInstance()
                .id("core_cache_option_layout_rlt_header"));
        imv_arr = (ImageView) mView.findViewById(Rconfig.getInstance().id(
                "imv_arr"));
        ll_body = (LinearLayout) mView.findViewById(Rconfig.getInstance().id(
                "core_cache_option_layout_ll_body"));
        initHeaderView();

        ll_body.setVisibility(View.GONE);
        ll_body.setBackgroundResource(Rconfig.getInstance().drawable(
                "background_option"));
        createHeader();
        createCacheOption();
        return mView;
    }

    protected void createHeader() {
        rlt_header.setBackgroundResource(Rconfig.getInstance().drawable(
                "bottom_line_border"));
        rlt_header.setBackgroundColor(Color.parseColor("#E8E8E8"));

        // name cache option
        tv_name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv_name.setMaxEms(50);
        tv_name.setTypeface(tv_name.getTypeface(), Typeface.BOLD);
        tv_name.setTextColor(Color.parseColor("#131313"));
        tv_name.setText(mCustomOption.getTitle());
        // initial required text
        if (mCustomOption.isRequired()) {
            tv_required.setText(Config.getInstance().getText("*"));
            tv_required.setTextColor(Color.RED);
        }

        if (isShowWhenStart) {
            ll_body.setVisibility(View.VISIBLE);
            imv_arr.setRotation(90);
        } else {
            ll_body.setVisibility(View.GONE);
            imv_arr.setRotation(0);

        }
        rlt_header.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                animateOption();
            }
        });
        initHeaderView();
    }

    protected void initHeaderView() {
        try {
            tv_name = (TextView) mView.findViewById(Rconfig.getInstance().id(
                    "core_cache_option_layout_tv_name"));
            tv_required = (TextView) mView.findViewById(Rconfig.getInstance()
                    .id("core_cache_option_layout_tv_required"));
            if (mCustomOption.isRequired()) {
                tv_required.setText(Config.getInstance().getText("*"));
                tv_required.setTextColor(Color.RED);
            }
        } catch (Exception e) {
        }
    }

    protected void animateOption() {
        if (ll_body.getVisibility() == View.VISIBLE) {
            Utils.collapse(ll_body);
            imv_arr.setRotation(0);
        } else {
            Utils.expand(ll_body);
            imv_arr.setRotation(90);
        }
    }

    protected void createCacheOption() {
    }

    protected void updatePriceHeader(String price) {
        if (tv_required.getVisibility() == View.GONE) {
            tv_required.setVisibility(View.VISIBLE);
        }

        if (price.equals("") && mCustomOption.isRequired()) {
            tv_required.setText(Config.getInstance().getText("*"));
            tv_required.setTextColor(Color.parseColor(Config.getInstance()
                    .getPrice_color()));
//            if (price.equals("") && mCustomOption.isCompleteRequired()) {
//                tv_required.setText("");
//            }
        } else {
            tv_required.setTextColor(Color.parseColor(Config.getInstance()
                    .getPrice_color()));
            tv_required.setText(Html.fromHtml(price));
        }
    }

    protected void updatePriceForParent(ValueCustomOptionEntity option, boolean isAdd) {
        if (null != mDelegate) {
            mDelegate.updatePrice(option, isAdd);
        }
    }

    protected String getPrice(ValueCustomOptionEntity option) {
        float price = option.getPrice();

        String qty = option.getQty();
        if (Utils.validateString(qty)) {

            float f_qty = Float.parseFloat(qty);

            if (f_qty > 1) {
                price = f_qty * price;
            }
        }

        return String.valueOf(price);
    }

    public CustomOptionEntity getCacheOption() {
        return mCustomOption;
    }


    public JSONObject getDataForCheckout() {
        try {
            boolean isTextType = false;
            String type = mCustomOption.getType();
            if (type.equals("area") && type.equals("field")) {
                isTextType = true;
            }

            JSONObject json = new JSONObject();

            // put id of custom option
            String id = mCustomOption.getID();
            json.put("customs_id", id);

            // put id of value
            JSONArray array = new JSONArray();
            for (int i = 0; i < mListValueView.size(); i++) {
                ValueView valueView = mListValueView.get(i);
                if (valueView.isSelected()) {
                    ValueCustomOptionEntity valueEntity = valueView.getValue();
                    String id_value = valueEntity.getID();
                    array.put(id_value);
                    if (isTextType) {
                        String text = valueEntity.getContentText();
                        json.put("description", text);
                    }
                }
            }
            json.put("value_id", array);
            return json;
        } catch (JSONException e) {
            return null;
        }

    }

    public boolean isComplete() {
        return false;
    }

}
