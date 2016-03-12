package com.simicart.core.customer.entity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.config.Constants;

public class OrderHistory extends SimiEntity {
	private ArrayList<String> mID;
	private String mPageSize;
	private int mTotal;
	private int mFrom;
	private ArrayList<OrderHisDetail> mOrders;

	@Override
	public void parse() {
		if(mJSON.has("all_ids")) {
			mID = getArrayData("all_ids");
		}

		if(mJSON.has("page_size")) {
			try {
				mPageSize = mJSON.getString("page_size");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("total")) {
			try {
				mTotal = mJSON.getInt("total");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("from")) {
			try {
				mFrom = mJSON.getInt("from");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("orders")) {

			if(null == mOrders) {
				mOrders = new ArrayList<>();
			}
			try {
				JSONArray arr = mJSON.getJSONArray("orders");
				for(int i=0;i<arr.length();i++) {
					OrderHisDetail orderHisDetail = new OrderHisDetail();
					orderHisDetail.setJSONObject(arr.getJSONObject(i));
					orderHisDetail.parse();
					mOrders.add(orderHisDetail);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	public int getmFrom() {
		return mFrom;
	}

	public void setmFrom(int mFrom) {
		this.mFrom = mFrom;
	}

	public ArrayList<String> getmID() {
		return mID;
	}

	public void setmID(ArrayList<String> mID) {
		this.mID = mID;
	}

	public ArrayList<OrderHisDetail> getmOrders() {
		return mOrders;
	}

	public void setmOrders(ArrayList<OrderHisDetail> mOrders) {
		this.mOrders = mOrders;
	}

	public String getmPageSize() {
		return mPageSize;
	}

	public void setmPageSize(String mPageSize) {
		this.mPageSize = mPageSize;
	}

	public int getmTotal() {
		return mTotal;
	}

	public void setmTotal(int mTotal) {
		this.mTotal = mTotal;
	}

	public int getNumberOrder()
	{
		if(null == mOrders)
		{
			return 0;
		}


		return mOrders.size();
	}
}
