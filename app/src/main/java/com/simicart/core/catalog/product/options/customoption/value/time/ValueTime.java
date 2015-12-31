package com.simicart.core.catalog.product.options.customoption.value.time;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.catalog.product.options.customoption.Custom.CustomOptionDelegate;
import com.simicart.core.catalog.product.options.customoption.value.ValueView;
import com.simicart.core.catalog.product.options.customoption.value.entity.ValueCustomOptionEntity;
import com.simicart.core.config.Config;

/**
 * Created by MSI on 08/12/2015.
 */
public class ValueTime extends ValueView {

    private boolean isselectedOptionTime;

    public ValueTime(ValueCustomOptionEntity value, Context context, CustomOptionDelegate delegate) {
        super(value, context, delegate);
    }

    @Override
    public View createView() {
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout ll_time = new LinearLayout(mContext);
        ll_time.setOrientation(LinearLayout.VERTICAL);
        ll_time.setLayoutParams(param);

        final TextView tv_time = new TextView(mContext);
        tv_time.setLayoutParams(param);
        tv_time.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv_time.setGravity(Gravity.CENTER);
        tv_time.setTextColor(Color.parseColor("#000000"));
        ll_time.addView(tv_time);

        final TimePicker timePicker = new TimePicker(SimiManager.getIntance()
                .getCurrentActivity());
        timePicker.setSaveFromParentEnabled(false);
        timePicker.setSaveEnabled(true);

        if ((mValue.getHour() > 0) && (mValue.getMinute() > 0)) {
            isselectedOptionTime = true;
            String hour = "" + mValue.getHour();
            if (mValue.getHour() < 10) {
                hour = "0" + mValue.getHour();
            }
            String minuteS = "" + mValue.getMinute();
            if (mValue.getMinute() < 10) {
                minuteS = "0" + mValue.getMinute();
            }
            mDelegate.updatePriceForHeader(Config.getInstance().getPrice(
                   mValue.getPrice()));
            tv_time.setText(hour + ":" + minuteS);
            timePicker.setCurrentHour(mValue.getHour());
            timePicker.setCurrentMinute(mValue.getMinute());
            mDelegate.updatePriceParent(mValue, true);
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
                if (isAddPrice && !isselectedOptionTime) {
                    mValue.setChecked(true);
                    mDelegate.updatePriceForHeader(Config.getInstance().getPrice(
                            mValue.getPrice()));
                    mDelegate.updatePriceParent(mValue, true);
                    isAddPrice = false;
                }
            }
        });

//        timePicker.setLayoutParams(param);
        ll_time.addView(timePicker,param);

        return ll_time;
    }
}
