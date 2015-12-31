package com.simicart.core.catalog.product.options.customoption.Custom.text;

import android.view.View;

import com.simicart.core.catalog.product.options.customoption.Custom.CustomOptionDelegate;
import com.simicart.core.catalog.product.options.customoption.Custom.CustomOptionView;
import com.simicart.core.catalog.product.options.customoption.Custom.entity.CustomOptionEntity;
import com.simicart.core.catalog.product.options.customoption.value.entity.ValueCustomOptionEntity;
import com.simicart.core.catalog.product.options.customoption.value.text.ValueText;

import java.util.ArrayList;

public class TextCustomOptionView extends CustomOptionView implements CustomOptionDelegate {

    private boolean ischeckedOptionText = false;

    public TextCustomOptionView(CustomOptionEntity cacheOption) {
        super(cacheOption);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void createCacheOption() {
        ArrayList<ValueCustomOptionEntity> values = mCustomOption.getValues();
        if (null != values && values.size() > 0) {
            for (int i = 0; i < values.size(); i++) {
                ValueText valueText = new ValueText(values.get(i), mContext, this);
                View view = valueText.createView();
                if (null != view) {
                    ll_body.addView(view);
                    mListValueView.add(valueText);
                }
            }
        }
    }

    @Override
    public boolean isComplete() {

        if (!mCustomOption.isRequired()) {
            return true;
        }

        for (int i = 0; i < mListValueView.size(); i++) {
            ValueText valueText = (ValueText) mListValueView.get(i);
            if (!valueText.isComplete()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void updateStateCacheOption(String id, boolean isSeletecd) {

    }

    @Override
    public void updatePriceForHeader(String price) {

    }

    @Override
    public void updatePriceParent(ValueCustomOptionEntity value, boolean operation) {

    }

    @Override
    public void clearOther(String id) {

    }
}
