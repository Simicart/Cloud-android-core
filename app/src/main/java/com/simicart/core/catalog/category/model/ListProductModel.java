package com.simicart.core.catalog.category.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.network.request.multi.SimiRequest;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;

public class ListProductModel extends SimiModel {

    protected String mID = "-1";
//	protected String mQty;

    public void setCategoryID(String id) {
        mID = id;
    }

//	public String getQty() {
//		return mQty;
//	}

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
            Log.e("ListProductModel", mJSONResult.toString());
            try {
                JSONArray productArr = mJSONResult.getJSONArray("products");
                if (productArr.length() > 0) {
                    for (int i = 0; i < productArr.length(); i++) {
                        ProductEntity productEntity = new ProductEntity();
                        productEntity.setJSONObject(productArr.getJSONObject(i));
                        productEntity.parse();
                        Log.e("ListProductModelSt", productEntity.isStatus() + "");
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
