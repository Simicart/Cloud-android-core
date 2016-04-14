package com.simicart.core.catalog.product.options.bundleitem;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.catalog.product.options.ManageOptionDelegate;
import com.simicart.core.catalog.product.options.customoption.Custom.CustomOptionView;
import com.simicart.core.catalog.product.options.customoption.Custom.entity.CustomOptionEntity;
import com.simicart.core.catalog.product.options.customoption.ManageCustomOptionDelegate;
import com.simicart.core.catalog.product.options.customoption.value.entity.ValueCustomOptionEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by MSI on 11/12/2015.
 */
public class ManageBundleItemView implements ManageCustomOptionDelegate {

    private ArrayList<CustomOptionEntity> mListBundle;
    private ArrayList<CustomOptionView> mListBundleView;
    private ManageOptionDelegate mDelegate;
    private Context mContext;
    private String checkbox = "checkbox";
    private String drop_down = "drop_down";
    private String radio = "radio";
    private String multiple = "multiple";


    public void setDelegate(ManageOptionDelegate delegate) {
        mDelegate = delegate;
    }

    public ManageBundleItemView(ArrayList<CustomOptionEntity> listBundle) {
        mListBundle = listBundle;
        mContext = SimiManager.getIntance().getCurrentContext();
        mListBundleView = new ArrayList<CustomOptionView>();
    }

    public View createView() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout ll_bundle = new LinearLayout(mContext);
        ll_bundle.setOrientation(LinearLayout.VERTICAL);

        for (int i = 0; i < mListBundle.size(); i++) {
            CustomOptionEntity bundleItem = mListBundle.get(i);
            String type = bundleItem.getType();
            if (type.equals(radio) || type.equals(drop_down)) {
                SingleBundleItemView singleView = new SingleBundleItemView(bundleItem);
                singleView.setDelegate(this);
                View view = singleView.initOptionsView();
                if (null != view) {
                    ll_bundle.addView(view, params);
                    mListBundleView.add(singleView);
                }
            } else if (type.equals(multiple) || type.equals(checkbox) ) {
                MultiBundleItemView multiView = new MultiBundleItemView(bundleItem);
                multiView.setDelegate(this);
                View view = multiView.initOptionsView();
                if (null != view) {
                    ll_bundle.addView(view, params);
                    mListBundleView.add(multiView);
                }
            }
        }
        return ll_bundle;
    }

    public JSONArray getDataForCheckout() {
        JSONArray array = new JSONArray();
        for (int i = 0; i < mListBundleView.size(); i++) {
            CustomOptionView bundleView = mListBundleView.get(i);
            JSONObject json = bundleView.getDataForCheckout();
            if (null != json) {
                array.put(json);
            }
        }
        return array;
    }

    public boolean isComplete() {

        for (int i = 0; i < mListBundleView.size(); i++) {
            CustomOptionView bundleView = mListBundleView.get(i);
            if (!bundleView.isComplete()) {
                return false;
            }
        }

        return true;
    }


    @Override
    public void updatePrice(ValueCustomOptionEntity option, boolean isAdd) {
        Log.e("ManageBundleItemView ", "update Price " + option.getName());
        mDelegate.updatePrice(option, isAdd);
    }
}
