package com.simicart.core.catalog.category.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.multi.SimiRequest;
import com.simicart.core.catalog.category.entity.Category;
import com.simicart.core.catalog.category.entity.CategoryEntity;
import com.simicart.core.config.Constants;

public class CategoryModel extends SimiModel {
    private String categories = "categories";

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
        Log.e("CategoryModel", mJSONResult.toString());
        if (mJSONResult.has(categories)) {
            try {
                JSONArray listCategory = mJSONResult.getJSONArray(categories);
                if (listCategory.length() > 0) {
                    for (int i = 0; i < listCategory.length(); i++) {
                        CategoryEntity categoryEntity = new CategoryEntity();
                        categoryEntity.setJSONObject(listCategory.getJSONObject(i));
                        categoryEntity.parse();
                        if(categoryEntity.isStatus()) {
                            collection.addEntity(categoryEntity);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
