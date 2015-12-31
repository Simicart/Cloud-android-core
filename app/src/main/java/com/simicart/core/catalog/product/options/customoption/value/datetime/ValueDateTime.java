package com.simicart.core.catalog.product.options.customoption.value.datetime;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.catalog.product.options.customoption.Custom.CustomOptionDelegate;
import com.simicart.core.catalog.product.options.customoption.value.ValueView;
import com.simicart.core.catalog.product.options.customoption.value.entity.ValueCustomOptionEntity;
import com.simicart.core.config.Config;

import java.util.Calendar;

/**
 * Created by MSI on 08/12/2015.
 */
public class ValueDateTime extends ValueView {

    private boolean isselectedOptionDateTime;

    public ValueDateTime(ValueCustomOptionEntity value, Context context, CustomOptionDelegate delegate) {
        super(value, context, delegate);
    }

    @Override
    public View createView() {
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout ll_datetime = new LinearLayout(mContext);
        ll_datetime.setOrientation(LinearLayout.VERTICAL);

        final TextView tv_time = new TextView(mContext);
        tv_time.setLayoutParams(param);
        tv_time.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv_time.setGravity(Gravity.CENTER);
        tv_time.setTextColor(Color.parseColor("#000000"));
        ll_datetime.addView(tv_time, param);

        final LinearLayout ll_time = new LinearLayout(mContext);
        ll_time.setLayoutParams(param);
        ll_time.setOrientation(LinearLayout.HORIZONTAL);
        ll_datetime.addView(ll_time, param);

        final TextView tv_date = new TextView(mContext);
        tv_date.setLayoutParams(param);
        tv_date.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv_date.setGravity(Gravity.CENTER);
        tv_date.setTextColor(Color.parseColor("#000000"));
        ll_datetime.addView(tv_date, param);

        final LinearLayout ll_date = new LinearLayout(mContext);
        ll_date.setLayoutParams(param);
        ll_date.setOrientation(LinearLayout.HORIZONTAL);
        ll_datetime.addView(ll_date, param);

        Calendar today = Calendar.getInstance();

        final TimePicker timePicker = new TimePicker(SimiManager.getIntance()
                .getCurrentActivity());
        timePicker.setSaveFromParentEnabled(false);
        timePicker.setSaveEnabled(true);

        final DatePicker datePicker = new DatePicker(SimiManager.getIntance()
                .getCurrentActivity());
        datePicker.setSpinnersShown(true);
        datePicker.setSaveFromParentEnabled(false);
        datePicker.setSaveEnabled(true);

        DatePicker.OnDateChangedListener dateListener = new DatePicker.OnDateChangedListener() {
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
                if (mValue.getHour() == -1) {
                    isAddPrice = false;
                }
                if (isAddPrice && mValue.getHour() != -1
                        && !isselectedOptionDateTime) {
                    isSelected = true;
                    mDelegate.updatePriceForHeader(Config.getInstance().getPrice(
                            mValue.getPrice()));
                    mDelegate.updatePriceParent(mValue, true);
                    mValue.setChecked(true);
                    isAddPrice = false;
                }
            }
        };

        if (mValue.getHour() > 0 && mValue.getDay() > 0) {
            // time
            String hour = "" + mValue.getHour();
            if (mValue.getHour() < 10) {
                hour = "0" + mValue.getHour();
            }
            String minuteS = "" + mValue.getMinute();
            if (mValue.getMinute() < 10) {
                minuteS = "0" + mValue.getMinute();
            }
            timePicker.setCurrentHour(mValue.getHour());
            timePicker.setCurrentMinute(mValue.getMinute());

            tv_time.setText(hour + ":" + minuteS);

            // date
            String day = "" + mValue.getDay();
            if (mValue.getDay() < 10) {
                day = "0" + mValue.getDay();
            }
            String month = "" + mValue.getMonth();
            if (mValue.getMonth() < 10) {
                month = "0" + mValue.getMonth();
            }
            tv_date.setText(day + "-" + month + "-" + mValue.getYear());
            datePicker.init(mValue.getYear(),
                    mValue.getMonth() - 1, mValue.getDay(),
                    dateListener);

            isselectedOptionDateTime = true;
            mDelegate.updatePriceForHeader(Config.getInstance().getPrice(
                    mValue.getPrice()));
            mDelegate.updatePriceParent(mValue, true);
        } else {
            datePicker.init(today.get(Calendar.YEAR),
                    today.get(Calendar.MONTH),
                    today.get(Calendar.DAY_OF_MONTH), dateListener);
        }

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            boolean isAddPrice = true;

            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                mValue.setHour(hourOfDay);
                mValue.setMinute(minute);

                String hour = "" + hourOfDay;
                if (hourOfDay < 10) {
                    hour = "0" + hourOfDay;
                }
                String minuteS = "" + minute;
                if (minute < 10) {
                    minuteS = "0" + minute;
                }
                tv_time.setText(hour + ":" + minuteS);
                if (mValue.getDay() == -1) {
                    isAddPrice = false;
                }
                if (isAddPrice && mValue.getDay() != -1
                        && !isselectedOptionDateTime) {
                    isSelected = true;
                    mValue.setChecked(true);
                    mDelegate.updatePriceForHeader(Config.getInstance().getPrice(
                            mValue.getPrice()));
                    mDelegate.updatePriceParent(mValue, true);
                    isAddPrice = false;
                }
            }
        });

        timePicker.setLayoutParams(param);
        ll_time.addView(timePicker);

//        datePicker.setLayoutParams(param);
        ll_date.addView(datePicker, param);
        datePicker.setCalendarViewShown(false);

        return ll_datetime;
    }
}
