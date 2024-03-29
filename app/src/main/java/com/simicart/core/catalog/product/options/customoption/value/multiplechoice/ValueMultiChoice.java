package com.simicart.core.catalog.product.options.customoption.value.multiplechoice;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simicart.core.catalog.product.options.customoption.Custom.CustomOptionDelegate;
import com.simicart.core.catalog.product.options.customoption.Custom.CustomOptionView;
import com.simicart.core.catalog.product.options.customoption.value.ValueView;
import com.simicart.core.catalog.product.options.customoption.value.entity.ValueCustomOptionEntity;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.config.Rconfig;

public class ValueMultiChoice extends ValueView {

    protected boolean isSave = false;
    protected TextView tv_title;
    protected ImageView img_icon;
    protected LinearLayout ll_option;
    protected boolean isChecked = true;
    protected TextView tv_price;

    public ValueMultiChoice(ValueCustomOptionEntity value, Context context,
                            CustomOptionDelegate delegate) {
        super(value, context, delegate);
        // TODO Auto-generated constructor stub
    }

    @Override
    public View createView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        mView = inflater.inflate(
                Rconfig.getInstance().layout("core_cache_option_item"), null,
                false);
        ll_option = (LinearLayout) mView.findViewById(Rconfig.getInstance().id(
                "ll_option"));

        tv_title = (TextView) mView.findViewById(Rconfig.getInstance().id(
                "tv_title"));
        tv_price = (TextView) mView.findViewById(Rconfig.getInstance().id(
                "tv_price"));
        img_icon = (ImageView) mView.findViewById(Rconfig.getInstance().id(
                "img_icon_option"));
        img_icon.setBackgroundResource(Rconfig.getInstance().drawable(
                "core_icon_option_multi"));

        tv_title.setText(Html.fromHtml(getTitleOption()),
                TextView.BufferType.SPANNABLE);

        String price = getPriceOption();
        if (Utils.validateString(price)) {
            tv_price.setText(Html.fromHtml(price),
                    TextView.BufferType.SPANNABLE);
        } else {
            tv_price.setVisibility(View.INVISIBLE);
        }

        if (!checkSaved()) {
            if (mValue.isDefault()) {
                selectOption(true);
            }
        }

        ll_option.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isChecked) {
                    isChecked = false;
                    // mDelegate.clearCheckAll(mValue.getOptionId());
                    selectOption(true);

                } else {
                    isChecked = true;
                    selectOption(false);
                }

            }
        });

        return mView;
    }

    @Override
    public boolean checkSaved() {
        if (mValue.isChecked()) {
            isSave = true;
            selectOption(true);
            isSave = false;
            return true;
        }
        return false;
    }


    @Override
    public void selectOption(boolean isSelected) {

        float optionPrice = mValue.getPrice();
        int i_qty = 0;
        String optionQty = mValue.getQty();
        if (Utils.validateString(optionQty)) {
            i_qty = (int) Float.parseFloat(optionQty);
        }
        if (i_qty > 1) {
            optionPrice = optionPrice * i_qty;
        }

        if (isSelected) {
            updateView(true);
            mValue.setChecked(true);
            mDelegate.updateStateCacheOption(mValue.getID(), true);

            mDelegate.updatePriceParent(mValue, CustomOptionView.ADD_OPERATOR);

        } else {
            updateView(false);
            mDelegate.updateStateCacheOption(mValue.getID(), false);
            mValue.setChecked(false);
            mDelegate.updatePriceParent(mValue, CustomOptionView.SUB_OPERATOR);
        }

    }

    @Override
    public void setBackgroundColor(int color) {
        ll_option.setBackgroundColor(color);
    }

    @Override
    public void updateView(boolean isSelected) {
        this.isSelected = isSelected;
        if (isSelected) {
            img_icon.setBackgroundResource(Rconfig.getInstance().drawable(
                    "core_icon_option_selected"));
        } else {
            img_icon.setBackgroundResource(Rconfig.getInstance().drawable(
                    "core_icon_option_multi"));
        }
    }

}
