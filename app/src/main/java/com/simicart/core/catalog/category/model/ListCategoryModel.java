package com.simicart.core.catalog.category.model;

import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.multi.SimiRequest;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;

public class ListCategoryModel extends SimiModel {

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
//		try {
//			JSONArray list = this.mJSONResult.getJSONArray("data");
//			if (null == collection) {
//				collection = new SimiCollection();
//				collection.setJSON(mJSONResult);
//			}
//			for (int i = 0; i < list.length(); i++) {
//				Product product = new Product();
//				product.setJSONObject(list.getJSONObject(i));
//				collection.addEntity(product);
//			}
//		} catch (JSONException e) {
//
//		}
		super.paserData();
		if(mJSONResult != null){
			Log.e("ListCategoryModel", mJSONResult.toString());
			try {
				JSONArray productArr = mJSONResult.getJSONArray("products");
				if(productArr.length() > 0){
					for (int i = 0; i < productArr.length(); i++){
						ProductEntity productEntity = new ProductEntity();
						productEntity.setJSONObject(productArr.getJSONObject(i));
						productEntity.parse();
						if(productEntity.isStatus()){
							collection.addEntity(productEntity);
						}
					}
				}
			}catch (JSONException e){

			}
		}
	}

}
