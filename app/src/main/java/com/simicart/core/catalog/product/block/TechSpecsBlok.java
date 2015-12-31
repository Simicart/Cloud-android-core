package com.simicart.core.catalog.product.block;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.catalog.product.entity.Attributes;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Sony on 12/9/2015.
 */
public class TechSpecsBlok extends SimiBlock {
    public TechSpecsBlok(View view, Context context) {
        super(view, context);
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void drawView(SimiCollection collection) {
        if(collection != null) {
            ArrayList<SimiEntity> entity = collection.getCollection();
            if (null != entity && entity.size() > 0) {
                ArrayList<Attributes> attributesArr = new ArrayList<Attributes>();
                for (SimiEntity simiEntity : entity) {
                    Attributes attributes = (Attributes) simiEntity;
                    attributesArr.add(attributes);
                }

                if(attributesArr.size() > 0){
                    LinearLayout ll_techSpecs = (LinearLayout) mView
                            .findViewById(Rconfig.getInstance().id("l_scrollView"));

                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
                    lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

                    for (Attributes attributeProduct : attributesArr) {
                        String title = attributeProduct.getName();
                        ArrayList<String> value = attributeProduct.getValue();
                        if (Utils.validateString(title)) {
                            TextView tv_title = new TextView(mContext);
                            tv_title.setLayoutParams(lp);
                            tv_title.setText(title);
                            tv_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                            tv_title.setTypeface(null, Typeface.BOLD);
                            tv_title.setTextColor(Config.getInstance().getContent_color());
                            ll_techSpecs.addView(tv_title);
                        }

                        if (value != null) {
                            if(value .size() > 0){
                                for (int i = 0; i < value.size(); i++){
                                    TextView tv_value = new TextView(mContext);
                                    tv_value.setLayoutParams(lp);
                                    tv_value.setText(Html.fromHtml(("<font color='"
                                            + Config.getInstance().getContent_color_string() + "'>"
                                            + value.get(i) + "</font>")));
                                    ll_techSpecs.addView(tv_value);
                                }
                            }
                        }
                    }
                }
            }else {
                visiableView();
                return;
            }
        }
    }

    public void visiableView() {
        ((ViewGroup) mView).removeAllViewsInLayout();
        TextView tv_notify = new TextView(mContext);
        tv_notify.setText(Config.getInstance().getText(
                "Tech Specs is empty"));
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
