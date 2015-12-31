package com.simicart.core.catalog.product.options.customoption.Custom.date;

import java.util.ArrayList;
import java.util.Calendar;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.catalog.product.options.customoption.Custom.CustomOptionDelegate;
import com.simicart.core.catalog.product.options.customoption.Custom.CustomOptionView;
import com.simicart.core.catalog.product.options.customoption.Custom.entity.CustomOptionEntity;
import com.simicart.core.catalog.product.options.customoption.value.ValueView;
import com.simicart.core.catalog.product.options.customoption.value.date.ValueDate;
import com.simicart.core.catalog.product.options.customoption.value.entity.ValueCustomOptionEntity;
import com.simicart.core.config.Config;

public class DateCustomOptionView extends CustomOptionView implements CustomOptionDelegate {


    public DateCustomOptionView(CustomOptionEntity cacheOption) {
        super(cacheOption);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void createCacheOption() {

        ArrayList<ValueCustomOptionEntity> values = mCustomOption.getValues();
        if (null != values && values.size() > 0) {
            for (int i = 0; i < values.size(); i++) {
                ValueDate valueDate = new ValueDate(values.get(i), mContext, this);
                View view = valueDate.createView();
                if (null != view) {
                    ll_body.addView(view);
                    mListValueView.add(valueDate);
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
