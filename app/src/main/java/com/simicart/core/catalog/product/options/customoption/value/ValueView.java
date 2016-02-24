package com.simicart.core.catalog.product.options.customoption.value;

import android.content.Context;
import android.view.View;

import com.simicart.core.catalog.product.options.customoption.Custom.CustomOptionDelegate;
import com.simicart.core.catalog.product.options.customoption.Custom.CustomOptionView;
import com.simicart.core.catalog.product.options.customoption.value.entity.ValueCustomOptionEntity;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;

public class ValueView {
    protected ValueCustomOptionEntity mValue;
    protected View mView;
    protected Context mContext;
    protected CustomOptionDelegate mDelegate;
    protected boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public ValueCustomOptionEntity getValue() {
        return mValue;
    }

    public void setOptions(ValueCustomOptionEntity mOptions) {
        this.mValue = mOptions;
    }

    public boolean isCheckedOption() {
        return this.mValue.isChecked();
    }

    public ValueView(ValueCustomOptionEntity value, Context context,
                     CustomOptionDelegate delegate) {
        mDelegate = delegate;
        mContext = context;
        mValue = value;
    }

    public View createView() {
        return null;
    }

    public boolean checkSaved() {
        if (mValue.isChecked()) {
            selectOption(true);
            return true;
        }
        return false;
    }


    public void selectOption(boolean isSelected) {
        if (isSelected) {
            updateView(true);
            mDelegate.updateStateCacheOption(mValue.getID(), true);
            if (!Config.getInstance().isShow_zero_price()
                    && mValue.getPrice() == 0) {
                mDelegate.updatePriceForHeader("");
            } else {

                mDelegate.updatePriceForHeader(Config.getInstance().getPrice(
                        getPriceFloat()));
            }
            mValue.setChecked(true);
            mDelegate.updatePriceParent(mValue, CustomOptionView.ADD_OPERATOR);
            updateView(true);
        } else {
            mValue.setChecked(false);
            mDelegate.updatePriceParent(mValue,
                    CustomOptionView.SUB_OPERATOR);
            mDelegate.updateStateCacheOption(mValue.getID(), false);
            mDelegate.updatePriceForHeader("");
            updateView(false);
        }
    }

    public void setBackgroundColor(int color) {

    }

    public void updateView(boolean isSelected) {
        this.isSelected = isSelected;

    }

    public String getIDValue() {
        return mValue.getID();
    }

    public float getPriceFloat() {
        float price = mValue.getPrice();
        float salePrice = mValue.getSalePrice();
        if (salePrice > 0) {
            price = salePrice;
        }

        String qty = mValue.getQty();
        if (Utils.validateString(qty)) {
            try {
                float f_qty = Float.parseFloat(qty);

                if (f_qty > 1) {
                    price = f_qty * price;
                }
            } catch (Exception e) {

            }
        }

        return price;
    }

    public String getPrice() {
        float price = mValue.getPrice();

        String qty = mValue.getQty();
        if (Utils.validateString(qty)) {

            float f_qty = Float.parseFloat(qty);

            if (f_qty > 1) {
                price = f_qty * price;
            }
        }

        return String.valueOf(price);
    }

    public String getTitleOption() {
        int i_qty = 0;

        String name = mValue.getTitle();
        if (!Utils.validateString(name)) {
            name = mValue.getName();
        }

        String title = "<font color='#7F7F7F'>" + name
                + "</font>";

        String qty = mValue.getQty();

        if (Utils.validateString(qty)) {
            i_qty = (int) Float.parseFloat(qty);
            if (i_qty > 1) {
                title = "<font color='#7F7F7F'>" + i_qty + " x "
                        + name
                        + "</font>";
            }
        }

        return title;
    }

    public String getPriceOption() {
        if (mValue.getPrice() == 0
                && !Config.getInstance().isShow_zero_price()) {
            return "";
        }
        float f_price = mValue.getPrice();
        float salePrice = mValue.getSalePrice();
        if (salePrice > 0) {
            f_price = salePrice;
        }
        String price = "<font color='#7F7F7F'> +"
                + Config.getInstance().getPrice(f_price) + "</font>";

        return price;
    }

    public String showValueOption() {
        String content;
        int i_qty = 0;
        String title = "<font color='grey'>" + mValue.getTitle()
                + "</font>";

        String qty = mValue.getQty();

        if ((null != qty) && (!qty.equals("") && (!qty.equals("null")))) {
            i_qty = (int) Float.parseFloat(qty);
            if (i_qty > 1) {
                title = "<font color='grey'>" + i_qty + " x "
                        + mValue.getTitle()
                        + "</font>";
            }
        }

//		if (mValue.getPriceType().equals("grouped")) {
//			title = mValue.getOptionValue();
//		}

        float f_price = mValue.getPrice();

        String price = "<font color='" + Config.getInstance().getPrice_color()
                + "'> +" + Config.getInstance().getPrice(f_price)
                + "</font>";

        content = title + price;
        if (mValue.getPrice() == 0
                && !Config.getInstance().isShow_zero_price()) {
            content = title;
        }

        return content;
    }

    public boolean isComplete() {
        return false;
    }

}
