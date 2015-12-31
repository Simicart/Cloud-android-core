package com.simicart.core.catalog.product.options.groupitem;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.catalog.product.options.customoption.Custom.CustomOptionView;
import com.simicart.core.catalog.product.options.customoption.Custom.entity.CustomOptionEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by MSI on 08/12/2015.
 */
public class GroupItemView implements ItemGroupDelegate {

    private ArrayList<ItemGroupEntity> mItems;
    private Context mContext;
    private ArrayList<ItemGroupView> mListItemView;
    private ItemGroupDelegate mDelegate;

    public void setDelegate(ItemGroupDelegate delegate) {
        mDelegate = delegate;
    }

    public GroupItemView(ArrayList<ItemGroupEntity> items) {
        mContext = SimiManager.getIntance().getCurrentContext();
        mItems = items;
        mListItemView = new ArrayList<ItemGroupView>();
    }

    public View createView() {
        LinearLayout ll_items = new LinearLayout(mContext);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        ll_items.setLayoutParams(param);
        ll_items.setOrientation(LinearLayout.VERTICAL);
        for (int i = 0; i < mItems.size(); i++) {
            ItemGroupView itemView = new ItemGroupView(mItems.get(i), mContext);
            itemView.setDelegate(this);
            View view = itemView.createView();
            if (null != view) {
                ll_items.addView(view);
                mListItemView.add(itemView);
            }
        }
        return ll_items;
    }

    public JSONArray getDataForCheckout() {
        JSONArray array = new JSONArray();
        for (int i = 0; i < mListItemView.size(); i++) {
            ItemGroupView itemView = mListItemView.get(i);
            JSONObject json = itemView.getDataForCheckout();
            if (null != json) {
                array.put(json);
            }
        }
        return array;
    }

    public boolean isComplete() {
        for (int i = 0; i < mListItemView.size(); i++) {
            ItemGroupView itemView = mListItemView.get(i);
            if (!itemView.isComple()) {
                return false;
            }
        }
        return true;
    }


    @Override
    public void updatePriceForItemGroup(ItemGroupEntity item, boolean isAdd) {
        mDelegate.updatePriceForItemGroup(item, isAdd);
    }
}
