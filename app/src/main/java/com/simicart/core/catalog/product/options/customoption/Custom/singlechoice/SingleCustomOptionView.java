package com.simicart.core.catalog.product.options.customoption.Custom.singlechoice;


import android.view.View;
import android.widget.LinearLayout;

import com.simicart.core.catalog.product.options.customoption.Custom.CustomOptionDelegate;
import com.simicart.core.catalog.product.options.customoption.Custom.CustomOptionView;
import com.simicart.core.catalog.product.options.customoption.Custom.entity.CustomOptionEntity;
import com.simicart.core.catalog.product.options.customoption.value.entity.ValueCustomOptionEntity;
import com.simicart.core.catalog.product.options.customoption.value.singlechoice.ValueSingleChoice;
import com.simicart.core.config.Rconfig;

public class SingleCustomOptionView extends CustomOptionView implements
        CustomOptionDelegate {

    // protected RadioGroup mRadioGroup;
    protected int iconRadio;
    protected ValueSingleChoice opSignNone = null;

    public SingleCustomOptionView(CustomOptionEntity cacheOption) {
        super(cacheOption);
        iconRadio = Rconfig.getInstance().drawable("radio_bt");
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
            ValueSingleChoice option_signle = new ValueSingleChoice(option, mContext,
                    this);
            View view = option_signle.createView();
            ll_body.addView(view, param);
            mListValueView.add(option_signle);
        }

        if (!mCustomOption.isRequired()) {
            // add radio button none
            opSignNone = new ValueSingleChoice(null, mContext, this);
            opSignNone.setTypeNone(true);
            View view = opSignNone.createView();
            ll_body.addView(view, param);
        }

    }


    // cache option single delegate
    @Override
    public void updateStateCacheOption(String id, boolean isSeletecd) {
        if (isSeletecd) {
            mCustomOption.setCompleteSelected(true);
        } else {
            boolean aChecked = false;
            for (final ValueCustomOptionEntity option : mCustomOption.getValues()) {
                if (option != null && option.getID() != null
                        && !option.getID().equals(id)
                        && option.isChecked()) {
                    aChecked = true;
                }
            }
            if (aChecked) {
                mCustomOption.setCompleteSelected(true);
            } else {
                mCustomOption.setCompleteSelected(false);
            }
        }
    }

    @Override
    public void updatePriceParent(ValueCustomOptionEntity option, boolean operation) {
        updatePriceForParent(option, operation);
    }

    @Override
    public boolean isComplete() {
        if (!mCustomOption.isRequired()) {
            return true;
        }

        for (int i = 0; i < mListValueView.size(); i++) {
            ValueSingleChoice singleChoice = (ValueSingleChoice) mListValueView.get(i);
            if (singleChoice.isComplete()) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void updatePriceForHeader(String price) {
        updatePriceHeader(price);
    }


    @Override
    public void clearOther(String id) {

        for (int i = 0; i < mListValueView.size(); i++) {
            ValueSingleChoice singleChoice = (ValueSingleChoice) mListValueView.get(i);
            String id_choice = singleChoice.getIDValue();
            if (!id.equals(id_choice) && singleChoice.getValue().isChecked()) {
                singleChoice.selectOption(false);
            }
        }


    }
}
