package com.simicart.core.checkout.model;

import android.util.Log;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.multi.SimiRequest;
import com.simicart.core.checkout.entity.Condition;
import com.simicart.core.checkout.entity.PaymentMethod;
import com.simicart.core.checkout.entity.QuoteEntity;
import com.simicart.core.checkout.entity.ShippingMethod;
import com.simicart.core.checkout.entity.TotalPrice;
import com.simicart.core.config.Constants;

public class ReviewOrderModel extends SimiModel {

	protected ArrayList<ShippingMethod> shippingMethods = new ArrayList<>();
	protected ArrayList<PaymentMethod> paymentMethods = new ArrayList<>();
	protected ArrayList<Condition> conditions = new ArrayList<>();
	protected TotalPrice totalPrice = new TotalPrice();

	@Override
	protected void setUrlAction() {
		addDataExtendURL("quotes");
	}

	@Override
	protected void setTypeMethod() {
		mTypeMethod = SimiRequest.Method.GET;
	}

	@Override
	protected void paserData() {
		super.paserData();
		if(mJSONResult != null){
			if(mJSONResult.has("quote")){
				try {
					JSONObject quoteArr = mJSONResult.getJSONObject("quote");
					QuoteEntity quoteEntity = new QuoteEntity();
					quoteEntity.setJSONObject(quoteArr);
					quoteEntity.parse();
					collection.addEntity(quoteEntity);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public ArrayList<ShippingMethod> getShippingMethods() {
		return shippingMethods;
	}

	public void setShippingMethods(ArrayList<ShippingMethod> shippingMethod) {
		shippingMethods = shippingMethod;
	}

	public ArrayList<PaymentMethod> getPaymentMethods() {
		return paymentMethods;
	}

	public TotalPrice getTotalPrice() {
		return totalPrice;
	}

	public ArrayList<Condition> getConditions() {
		return conditions;
	}

}
