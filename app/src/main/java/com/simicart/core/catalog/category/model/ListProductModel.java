package com.simicart.core.catalog.category.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.network.request.multi.SimiRequest;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;

public class ListProductModel extends SimiModel {

    protected String mID = "-1";

    public void setCategoryID(String id) {
        mID = id;
    }


    @Override
    protected void setUrlAction() {
        if (mID.equals("-1")) {
            addDataExtendURL("products");
        } else {
            addDataExtendURL("categories");
        }
    }

    @Override
    protected void setTypeMethod() {
        mTypeMethod = SimiRequest.Method.GET;
    }

    @Override
    protected void paserData() {
        super.paserData();

        if (mJSONResult != null) {
            try {
                JSONArray productArr = mJSONResult.getJSONArray("products");
                if (productArr.length() > 0) {
                    for (int i = 0; i < productArr.length(); i++) {
                        ProductEntity productEntity = new ProductEntity();
                        productEntity.setJSONObject(productArr.getJSONObject(i));
                        productEntity.parse();
                        if (productEntity.isStatus()) {
                            collection.addEntity(productEntity);
                        }
                    }
                }
            } catch (JSONException e) {

            }
        }
    }

    @Override
    public void setShouldCache() {
        mShouldCache = true;
    }
}
