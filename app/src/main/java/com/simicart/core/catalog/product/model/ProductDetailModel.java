
package com.simicart.core.catalog.product.model;

import android.util.Log;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.base.network.request.multi.SimiRequest;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by MSI on 02/12/2015.
 */
public class ProductDetailModel extends SimiModel {

    @Override
    protected void setUrlAction() {
        addDataExtendURL("products");
    }

    @Override
    protected void setTypeMethod() {
        mTypeMethod = SimiRequest.Method.GET;
    }

    @Override
    protected void paserData() {
        super.paserData();
        if (mJSONResult.has("product")) {
            try {
                JSONObject json = mJSONResult.getJSONObject("product");
                ProductEntity productEntity = new ProductEntity();
                productEntity.setJSONObject(json);
                productEntity.parse();
                ArrayList<SimiEntity> entities = new ArrayList<SimiEntity>();
                entities.add(productEntity);
                collection.setCollection(entities);
            } catch (JSONException e) {
                Log.e("ProductDetailModel", "parse Exception " + e.getMessage());
            }

        }
    }
}

