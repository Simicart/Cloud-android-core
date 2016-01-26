package com.simicart.core.home.model;

import android.util.Log;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.base.network.request.multi.SimiRequest;
import com.simicart.core.catalog.category.entity.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by MSI on 04/12/2015.
 */
public class CategoryWidgetModel extends SimiModel {

    private String category_widgets = "category-widgets";

    @Override
    protected void setUrlAction() {
        addDataExtendURL("category-widgets");
    }

    @Override
    protected void setTypeMethod() {
        mTypeMethod = SimiRequest.Method.GET;
    }

    @Override
    protected void paserData() {
        super.paserData();
        Log.e("CategoryWidgetModel", mJSONResult.toString());
        if (mJSONResult.has(category_widgets)) {
            try {
                JSONArray array = mJSONResult.getJSONArray(category_widgets);
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

    @Override
    public void setShouldCache() {
        mShouldCache = true;
    }
}
