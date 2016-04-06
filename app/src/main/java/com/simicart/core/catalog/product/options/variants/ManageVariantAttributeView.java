package com.simicart.core.catalog.product.options.variants;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.catalog.product.options.ProductVariantDelegate;
import com.simicart.core.catalog.product.entity.productEnity.VariantEntity;
import com.simicart.core.catalog.product.entity.productEnity.VariantsAttributeEntity;

import java.util.ArrayList;

/**
 * Created by MSI on 09/12/2015.
 */
public class ManageVariantAttributeView implements ManageVariantAttributeDelegate {

    protected ArrayList<VariantsAttributeEntity> mListAttribute;
    protected ArrayList<VariantEntity> mListVariant;
    protected Context mContext;
    protected ArrayList<VariantAttributeDelegate> mListAttributeDelegate;
    protected ProductVariantDelegate mDelegate;

    public ManageVariantAttributeView(ArrayList<VariantsAttributeEntity> att, ArrayList<VariantEntity> variants, ProductVariantDelegate delegate) {
        mListAttribute = att;
        mListVariant = variants;
        mContext = SimiManager.getIntance().getCurrentContext();
        mListAttributeDelegate = new ArrayList<VariantAttributeDelegate>();
        mDelegate = delegate;
    }

    public View createView() {
        LinearLayout.LayoutParams param_item = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams param_att = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout ll_att = new LinearLayout(mContext);
        ll_att.setLayoutParams(param_att);
        ll_att.setOrientation(LinearLayout.VERTICAL);
        Log.e("ManageAttView", "ListAttribute Size " + mListAttribute.size());
        for (int i = 0; i < mListAttribute.size(); i++) {
            VariantsAttributeEntity attributeEntity = mListAttribute.get(i);
            VariantAttributeView viewAtt = new VariantAttributeView(attributeEntity, mContext, mListVariant, this);
            View view = viewAtt.createView();
            if (null != view) {
                Log.e("ManageAttView", "View " + i);
                ll_att.addView(view, param_item);
                mListAttributeDelegate.add(viewAtt);
            }
        }
        return ll_att;
    }


    @SuppressLint("LongLogTag")
    @Override
    public void onSend(String valueAttOfItemSend, ArrayList<VariantEntity> listIDVariantAvaiable, String idItemSend) {

        ArrayList<VariantEntity> listVariant = listIDVariantAvaiable;

        for (int i = 0; i < mListAttributeDelegate.size(); i++) {
            VariantAttributeDelegate delegate = mListAttributeDelegate.get(i);
            String id = delegate.getIDAttribute();
            if (!id.equals(idItemSend)) {
                listVariant = delegate.onReceive(listIDVariantAvaiable);
            }
        }

        if (null != listVariant && listVariant.size() == 1) {
            VariantEntity variant = listVariant.get(0);
            mDelegate.onUpdateVariantSelected(variant);
        }


    }
}
