package com.simicart.core.catalog.product.options.variants;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simicart.core.common.Utils;
import com.simicart.core.config.Rconfig;


/**
 * Created by MSI on 08/12/2015.
 */
public class ItemVariantAttributeView implements ItemVariantAttributeDelegate {

    private interface Status {
        int ON_ENABLE = 0;
        int ON_SELECTED = 1;
        int NORMAL = 2;
    }

    private String mValue;
    private Context mContext;
    private RelativeLayout rlt_item;
    private ImageView img_icon;
    private TextView tv_content;
    private VariantAttributeDelegate mDelegate;
    private int status = Status.NORMAL;

    public ItemVariantAttributeView(String att, VariantAttributeDelegate delegate, Context context) {
        mValue = att;
        mContext = context;
        mDelegate = delegate;
    }

    public View createView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        rlt_item = (RelativeLayout) inflater.inflate(Rconfig.getInstance().layout("core_item_variant_attribute"), null);

        // create icon
        img_icon = (ImageView) rlt_item.findViewById(Rconfig.getInstance().id("img_icon"));
        img_icon.setBackgroundResource(Rconfig.getInstance().drawable(
                "core_icon_option_single"));

        // create content
        tv_content = (TextView) rlt_item.findViewById(Rconfig.getInstance().id("tv_conent"));

        tv_content.setText(mValue);

        // event
        rlt_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDelegate.onSend(mValue);
            }
        });


        return rlt_item;
    }

    protected void updateBackground(String color) {
        int iColor = Color.parseColor(color);
        rlt_item.setBackgroundColor(iColor);
    }

    @Override
    public void clearSelected() {
        if (status != Status.ON_ENABLE) {
            updateBackground("#ffffff");
            img_icon.setBackgroundResource(Rconfig.getInstance().drawable(
                    "core_icon_option_single"));
        }
    }


    @Override
    public void onSelected() {
        status = Status.ON_SELECTED;
        updateBackground("#795548");
        img_icon.setBackgroundResource(Rconfig.getInstance().drawable(
                "core_icon_option_selected"));
    }

    @Override
    public void onEnable() {
        status = Status.ON_ENABLE;
        updateBackground("#795548");
    }

    @Override
    public void clearEnable() {
        status = Status.NORMAL;
        clearSelected();
    }


    @Override
    public String getValue() {
        return mValue;
    }
}
