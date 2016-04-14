package com.simicart.core.catalog.product.options.customoption;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.catalog.product.options.ManageOptionDelegate;
import com.simicart.core.catalog.product.options.customoption.Custom.CustomOptionView;
import com.simicart.core.catalog.product.options.customoption.Custom.date.DateCustomOptionView;
import com.simicart.core.catalog.product.options.customoption.Custom.datetime.DateTimeCustomOptionView;
import com.simicart.core.catalog.product.options.customoption.Custom.entity.CustomOptionEntity;
import com.simicart.core.catalog.product.options.customoption.Custom.multichoice.MultiCustomOptionView;
import com.simicart.core.catalog.product.options.customoption.Custom.singlechoice.SingleCustomOptionView;
import com.simicart.core.catalog.product.options.customoption.Custom.text.TextCustomOptionView;
import com.simicart.core.catalog.product.options.customoption.Custom.time.TimeCustomOptionView;
import com.simicart.core.catalog.product.options.customoption.ManageCustomOptionDelegate;
import com.simicart.core.catalog.product.options.customoption.value.entity.ValueCustomOptionEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by MSI on 10/12/2015.
 */
public class ManageCustomOptionView implements ManageCustomOptionDelegate {
    protected ArrayList<CustomOptionEntity> mListCustomOption;
    protected ArrayList<CustomOptionView> mListCustomView;
    protected ManageOptionDelegate mDelegate;
    protected Context mContext;

    private String area = "area";
    private String field = "field";
    private String date_time = "date_time";
    private String checkbox = "checkbox";
    private String drop_down = "drop_down";
    private String radio = "radio";
    private String multiple = "multiple";
    private String date = "date";
    private String time = "time";

    public void setDelegate(ManageOptionDelegate delegate) {
        mDelegate = delegate;
    }

    public ManageCustomOptionView(ArrayList<CustomOptionEntity> listCustom) {
        mListCustomOption = listCustom;
        mContext = SimiManager.getIntance().getCurrentContext();
        mListCustomView = new ArrayList<CustomOptionView>();
    }

    public View createView() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout ll_customOption = new LinearLayout(mContext);
        ll_customOption.setOrientation(LinearLayout.VERTICAL);
        for (int i = 0; i < mListCustomOption.size(); i++) {
            CustomOptionEntity customEntity = mListCustomOption.get(i);
            String type = customEntity.getType();

            // Text Type
            if (type.equals(area) || type.equals(field)) {
                TextCustomOptionView textView = new TextCustomOptionView(customEntity);
                textView.setDelegate(this);
                View view = textView.initOptionsView();
                if (null != view) {
                    ll_customOption.addView(view, params);
                    mListCustomView.add(textView);
                }
            }
            // single choice type
            else if (type.equals(radio)  || type.equals(drop_down)) {
                SingleCustomOptionView singleView = new SingleCustomOptionView(customEntity);
                singleView.setDelegate(this);
                View view = singleView.initOptionsView();
                if (null != view) {
                    ll_customOption.addView(view, params);
                    mListCustomView.add(singleView);
                }

            }

            // multi choice type
            else if (type.equals(multiple) || type.equals(checkbox)) {
                MultiCustomOptionView multiView = new MultiCustomOptionView(customEntity);
                multiView.setDelegate(this);
                View view = multiView.initOptionsView();
                if (null != view) {
                    ll_customOption.addView(view, params);

                    mListCustomView.add(multiView);
                }
            }

            // date type
            else if (type.equals(date)) {
                DateCustomOptionView dateView = new DateCustomOptionView(customEntity);
                dateView.setDelegate(this);
                View view = dateView.initOptionsView();
                if (null != view) {
                    ll_customOption.addView(view, params);
                    mListCustomView.add(dateView);
                }
            }

            // date time type
            else if (type.equals(date_time)) {
                DateTimeCustomOptionView dateTimeView = new DateTimeCustomOptionView(customEntity);
                dateTimeView.setDelegate(this);
                View view = dateTimeView.initOptionsView();
                if (null != view) {
                    ll_customOption.addView(view, params);
                    mListCustomView.add(dateTimeView);
                }
            }

            // time type
            else if (type.equals(time)) {
                TimeCustomOptionView timeView = new TimeCustomOptionView(customEntity);
                timeView.setDelegate(this);
                View view = timeView.initOptionsView();
                if (null != view) {
                    ll_customOption.addView(view, params);
                    mListCustomView.add(timeView);
                }
            }
        }
        return ll_customOption;
    }

    public boolean isComplete() {
        for (int i = 0; i < mListCustomView.size(); i++) {
            CustomOptionView customView = mListCustomView.get(i);
            if (!customView.isComplete()) {
                Log.e("ManageCustomOptionView ", "======> CHECK FALSE");
                return false;
            }
        }
        Log.e("ManageCustomOptionView ", "======> CHECK TRUE");
        return true;
    }


    public JSONArray getDataForCheckout() {
        JSONArray array = new JSONArray();
        for (int i = 0; i < mListCustomView.size(); i++) {
            CustomOptionView customView = mListCustomView.get(i);
            JSONObject json = customView.getDataForCheckout();
            if (null != json) {
                array.put(json);
            }
        }

        return array;
    }

    @Override
    public void updatePrice(ValueCustomOptionEntity option, boolean isAdd) {
        mDelegate.updatePrice(option, isAdd);
    }
}
