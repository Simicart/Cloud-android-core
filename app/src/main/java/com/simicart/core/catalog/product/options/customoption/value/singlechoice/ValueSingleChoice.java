package com.simicart.core.catalog.product.options.customoption.value.singlechoice;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simicart.core.catalog.product.options.customoption.Custom.CustomOptionDelegate;
import com.simicart.core.catalog.product.options.customoption.value.ValueView;
import com.simicart.core.catalog.product.options.customoption.value.entity.ValueCustomOptionEntity;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.config.Rconfig;

public class ValueSingleChoice extends ValueView {

    protected boolean isNone = false;
    protected TextView tv_title;
    protected LinearLayout ll_option;
    protected TextView tv_price;
    protected ImageView img_icon;
    protected boolean isChecked = true;

    public void setTypeNone(boolean none) {
        isNone = none;
    }

    public boolean getTypeNone() {
        return isNone;
    }

    public ValueSingleChoice(ValueCustomOptionEntity option, Context context,
                             CustomOptionDelegate delegate) {
        super(option, context, delegate);
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
                "core_icon_option_single"));


        if (isNone) {
            String none = "<font color='grey'>"
                    + Config.getInstance().getText("None") + "</font>";
            tv_title.setText(Html.fromHtml(none));
            tv_price.setVisibility(View.INVISIBLE);
        } else {
            tv_title.setText(Html.fromHtml(getTitleOption()),
                    TextView.BufferType.SPANNABLE);

            String price = getPriceOption();
            if (Utils.validateString(price)) {
                tv_price.setText(Html.fromHtml(price),
                        TextView.BufferType.SPANNABLE);
            } else {
                tv_price.setVisibility(View.INVISIBLE);
            }

        }

        if (isNone) {
            ll_option.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (isChecked) {
                        isChecked = false;
                        mDelegate.updatePriceForHeader("");
                        mDelegate.clearOther("none");
                        updateView(true);
                    } else {
                        isChecked = true;
                        updateView(false);
                    }
                }
            });

        } else {
            checkSaved();
            ll_option.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (isChecked) {
                        isChecked = false;
                        mDelegate.updatePriceForHeader("");
                        mDelegate.clearOther(mValue.getID());
                        selectOption(true);
                    } else {
                        isChecked = true;
//                        selectOption(false);
                    }

                }
            });
        }

        return mView;
    }

    @Override
    public void setBackgroundColor(int color) {
        ll_option.setBackgroundColor(color);
    }

    @Override
    public void updateView(boolean isSelected) {
        this.isSelected = isSelected;
        if (isSelected) {
            isChecked = false;
            img_icon.setBackgroundResource(Rconfig.getInstance().drawable(
                    "core_icon_option_selected"));
        } else {
            isChecked = true;
            if (null != mValue) {
                mValue.setChecked(false);
            }
            img_icon.setBackgroundResource(Rconfig.getInstance().drawable(
                    "core_icon_option_single"));
        }
    }

    @Override
    public boolean isComplete() {
        return  mValue.isChecked();
    }
}
