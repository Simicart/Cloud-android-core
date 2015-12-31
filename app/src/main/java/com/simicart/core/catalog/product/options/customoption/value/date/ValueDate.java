package com.simicart.core.catalog.product.options.customoption.value.date;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.catalog.product.options.customoption.Custom.CustomOptionDelegate;
import com.simicart.core.catalog.product.options.customoption.value.ValueView;
import com.simicart.core.catalog.product.options.customoption.value.entity.ValueCustomOptionEntity;

import java.util.Calendar;

/**
 * Created by MSI on 08/12/2015.
 */
public class ValueDate extends ValueView {

    private boolean isselectedOptionDate = false;

    public ValueDate(ValueCustomOptionEntity value, Context context, CustomOptionDelegate delegate) {
        super(value, context, delegate);
    }

    @Override
    public View createView() {
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout ll_date = new LinearLayout(mContext);
        ll_date.setOrientation(LinearLayout.VERTICAL);
        ll_date.setLayoutParams(param);

        Calendar today = Calendar.getInstance();

        final TextView tv_date = new TextView(mContext);
        tv_date.setLayoutParams(param);
        tv_date.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv_date.setGravity(Gravity.CENTER);
        tv_date.setTextColor(Color.parseColor("#000000"));
        ll_date.addView(tv_date);

        final DatePicker datePicker = new DatePicker(SimiManager.getIntance()
                .getCurrentActivity());
        datePicker.setSpinnersShown(true);
        datePicker.setSaveFromParentEnabled(false);
        datePicker.setSaveEnabled(true);
        DatePicker.OnDateChangedListener listener = new DatePicker.OnDateChangedListener() {
            boolean isAddPrice = true;

            @Override
            public void onDateChanged(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                mValue.setDay(dayOfMonth);
                mValue.setMonth(monthOfYear + 1);
                mValue.setYear(year);
                String day = "" + dayOfMonth;
                if (dayOfMonth < 10) {
                    day = "0" + dayOfMonth;
                }
                String month = "" + (monthOfYear + 1);
                if ((monthOfYear + 1) < 10) {
                    month = "0" + (monthOfYear + 1);
                }
                tv_date.setText(day + "-" + month + "-" + year);
                if (isAddPrice && !isselectedOptionDate) {
                    isSelected = true;
                    mValue.setChecked(true);
                    mDelegate.updatePriceParent(mValue, true);
                    isAddPrice = false;
                }
            }
        };
        if (mValue.getDay() > 0 && mValue.getMonth() > 0
                && mValue.getYear() > 0) {
            isselectedOptionDate = true;
            String day = "" + mValue.getDay();
            if (mValue.getMonth() < 10) {
                day = "0" + mValue.getMonth();
            }
            String month = "" + mValue.getMonth();
            if (mValue.getMonth() < 10) {
                month = "0" + mValue.getMonth();
            }
            tv_date.setText(day + "-" + month + "-" + mValue.getYear());
            mDelegate.updatePriceParent(mValue, true);
            datePicker.init(mValue.getYear(),
                    mValue.getMonth() - 1, mValue.getDay(),
                    listener);
        } else {
            datePicker.init(today.get(Calendar.YEAR),
                    today.get(Calendar.MONTH),
                    today.get(Calendar.DAY_OF_MONTH), listener);
        }
//        datePicker.setLayoutParams(param);
        ll_date.addView(datePicker, param);
        datePicker.setCalendarViewShown(false);

        return ll_date;
    }
}
