package com.simicart.core.catalog.product.options.customoption.Custom.datetime;

import android.view.View;

import com.simicart.core.catalog.product.options.customoption.Custom.CustomOptionDelegate;
import com.simicart.core.catalog.product.options.customoption.Custom.CustomOptionView;
import com.simicart.core.catalog.product.options.customoption.Custom.entity.CustomOptionEntity;
import com.simicart.core.catalog.product.options.customoption.value.ValueView;
import com.simicart.core.catalog.product.options.customoption.value.datetime.ValueDateTime;
import com.simicart.core.catalog.product.options.customoption.value.entity.ValueCustomOptionEntity;

import java.util.ArrayList;


public class DateTimeCustomOptionView extends CustomOptionView implements CustomOptionDelegate {

    public DateTimeCustomOptionView(CustomOptionEntity cacheOption) {
        super(cacheOption);
    }

    @Override
    protected void createCacheOption() {
        ArrayList<ValueCustomOptionEntity> values = mCustomOption.getValues();
        if (null != values && values.size() > 0) {
            for (int i = 0; i < values.size(); i++) {
                ValueDateTime valueDateTime = new ValueDateTime(values.get(i), mContext, this);
                View view = valueDateTime.createView();
                if (null != view) {
                    ll_body.addView(view);
                    mListValueView.add(valueDateTime);
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
            ValueView valueView = mListValueView.get(i);
            if (!valueView.getValue().isChecked()) {
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
