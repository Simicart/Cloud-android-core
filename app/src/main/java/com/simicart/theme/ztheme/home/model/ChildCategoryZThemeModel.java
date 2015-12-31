package com.simicart.theme.ztheme.home.model;

import android.util.Log;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.base.network.request.multi.SimiRequest;
import com.simicart.core.catalog.category.entity.Category;
import com.simicart.core.customer.entity.OrderHistory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by James Crabby on 12/15/2015.
 */
public class ChildCategoryZThemeModel extends SimiModel {
    @Override
    protected void setUrlAction() {
        addDataExtendURL("categories");
    }

    @Override
    protected void setTypeMethod() {
        mTypeMethod = SimiRequest.Method.GET;
    }

    @Override
    protected void paserData() {
        super.paserData();

        Log.e("ChildCategoryModel", "" + mJSONResult.toString());

        if (mJSONResult.has("categories")) {
            try {
                JSONArray array = mJSONResult.getJSONArray("categories");
                if (null != array && array.length() > 0) {
                    ArrayList<SimiEntity> categories = new ArrayList<SimiEntity>();
                    int length = array.length();
                    for (int i = 0; i < length; i++) {
                        Category category = new Category();
                        JSONObject js_category = array.getJSONObject(i);
                        category.setJSONObject(js_category);
                        category.parse();
                        if (category.isEnable()) {
                            categories.add(category);
                        }
                    }
                    collection.setCollection(categories);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
