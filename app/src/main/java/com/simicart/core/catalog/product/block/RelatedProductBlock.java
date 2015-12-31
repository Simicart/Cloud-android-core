package com.simicart.core.catalog.product.block;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simicart.core.adapter.ProductListAdapter;
import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;

public class RelatedProductBlock extends SimiBlock {

    protected ListView lv_relatedProduct;
    protected ProductListAdapter mAdapter;

    public RelatedProductBlock(View view, Context context) {
        super(view, context);
    }

    @Override
    public void initView() {
        lv_relatedProduct = (ListView) mView.findViewById(Rconfig.getInstance()
                .id("lv_relatedProduct"));
        ColorDrawable sage = new ColorDrawable(Config.getInstance()
                .getLine_color());
        lv_relatedProduct.setDivider(sage);
        lv_relatedProduct.setDividerHeight(1);

    }

    @Override
    public void drawView(SimiCollection collection) {
    }


    public void visiableView() {
        ((ViewGroup) mView).removeAllViewsInLayout();
        TextView tv_notify = new TextView(mContext);
        tv_notify.setText(Config.getInstance().getText(
                "Related product is empty"));
        tv_notify.setTextColor(Config.getInstance().getContent_color());
        tv_notify.setTypeface(null, Typeface.BOLD);
        if (DataLocal.isTablet) {
            tv_notify.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        } else {
            tv_notify.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        }
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        tv_notify.setGravity(Gravity.CENTER);
        tv_notify.setLayoutParams(params);
        ((ViewGroup) mView).addView(tv_notify);
    }

}
