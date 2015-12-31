package com.simicart.core.catalog.product.options.customoption.Custom.multichoice;


import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.simicart.core.catalog.product.options.customoption.Custom.CustomOptionDelegate;
import com.simicart.core.catalog.product.options.customoption.Custom.CustomOptionView;
import com.simicart.core.catalog.product.options.customoption.Custom.entity.CustomOptionEntity;
import com.simicart.core.catalog.product.options.customoption.value.entity.ValueCustomOptionEntity;
import com.simicart.core.catalog.product.options.customoption.value.multiplechoice.ValueMultiChoice;
import com.simicart.core.config.Rconfig;

public class MultiCustomOptionView extends CustomOptionView implements
        CustomOptionDelegate {

    protected RadioGroup mRadioGroup;

    protected int iconRadio;

    public MultiCustomOptionView(CustomOptionEntity cacheOption) {
        super(cacheOption);
        iconRadio = Rconfig.getInstance().drawable("check_box");
    }

    public void setIconRadio(int idDrawable) {
        iconRadio = idDrawable;
    }

    @Override
    protected void createCacheOption() {
        createOptionView();
    }

    protected void createOptionView() {
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        for (final ValueCustomOptionEntity option : mCustomOption.getValues()) {
            ValueMultiChoice option_multi = new ValueMultiChoice(option, mContext, this);
            View view = option_multi.createView();
            ll_body.addView(view, param);
            mListValueView.add(option_multi);
        }
    }

    public boolean isCheckedAll() {
        for (ValueCustomOptionEntity option : mCustomOption.getValues()) {
            if (!option.isChecked()) {
                return false;
            }
        }
        return true;
    }

    public boolean isComplete() {
        if (!mCustomOption.isRequired()) {
            return true;
        }
        for (ValueCustomOptionEntity option : mCustomOption.getValues()) {
            if (option.isChecked()) {
                return true;
            }
        }
        return true;

    }


    @Override
    public void updateStateCacheOption(String id, boolean isSeletecd) {
        if (isSeletecd) {
            if (!mCustomOption.isCompleteSelected()) {
                mCustomOption.setCompleteSelected(true);
            }
        } else {
            if (!isCheckedAll()) {
                mCustomOption.setCompleteSelected(false);
            }
        }
    }

    @Override
    public void updatePriceForHeader(String price) {
        updatePriceHeader(price);
    }


    @Override
    public void updatePriceParent(ValueCustomOptionEntity option, boolean operation) {
        updatePriceForParent(option, operation);
    }

    @Override
    public void clearOther(String id) {

    }

    public void updatePriceMulti(float price, boolean isAdd) {
        if (isAdd) {
            mCustomOption.setMultiPrice(mCustomOption.getMultiPrice() + price);
        } else {
            mCustomOption.setMultiPrice(mCustomOption.getMultiPrice() - price);
        }
    }


    public float getPriceMulti() {
        return mCustomOption.getMultiPrice();
    }


}
