package com.simicart.core.catalog.categorydetail.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.network.request.multi.SimiRequest;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;

import java.util.ArrayList;

public class CategoryDetailModel extends SimiModel {
	private String url;
	private int mTotalProduct;
	private ArrayList<String> mListProducID;
	private int mCurrentSize;

	public int getCurrentSize()
	{
		return mCurrentSize;
	}

	@Override
	protected void setUrlAction() {
		addDataExtendURL(url);
	}

	@Override
	protected void setTypeMethod() {
		mTypeMethod = SimiRequest.Method.GET;
	}

	@Override
	protected void paserData() {
		super.paserData();

		mTotalProduct = mTotal;
		if(mJSONResult != null){
			if(mAllIDs != null && mAllIDs.size() > 0) {
				mListProducID = mAllIDs;
			}
			try {
				JSONArray productArr = mJSONResult.getJSONArray("products");
				if(productArr.length() > 0){
					for (int i = 0; i < productArr.length(); i++){
						mCurrentSize++;
						ProductEntity productEntity = new ProductEntity();
						productEntity.setJSONObject(productArr.getJSONObject(i));
						productEntity.parse();
						if(productEntity.isStatus() && productEntity.isVisibility()){
							collection.addEntity(productEntity);
						}
					}
				}
			}catch (JSONException e){

			}
		}
	}
	public void setUrlSearch (String url) {
		this.url = url;
	}

	public ArrayList<String> getListProducID() {
		return mListProducID;
	}

	public int getTotal() {
		return mTotalProduct;
	}
}
