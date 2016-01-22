package com.simicart.plugins.checkout.com;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.simicart.MainActivity;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.checkout.entity.OrderEntity;
import com.simicart.core.customer.entity.OrderHisDetail;
import com.simicart.core.event.checkout.CheckoutData;
import com.simicart.plugins.checkout.com.fragment.CheckoutComFragment;

public class CheckoutCom {

	String LIVE_URL = "https://secure.checkout.com/hpayment-tokenretry/pay.aspx?";

	Context context;
	String orderID = "";
	OrderEntity entity;
	OrderHisDetail orderHisDetail;

	public CheckoutCom(String method, CheckoutData checkoutData) {
		Log.e(getClass().getName(), "Method: " + method);
		this.context = MainActivity.context;
		entity = checkoutData.getOder();
		orderHisDetail = checkoutData.getmOderHisDetail();
		orderID = entity.getID();
		Log.e(getClass().getName(), "++" + orderID);
		Log.e(getClass().getName(), "++" + checkoutData.getPaymentMethod().getMethodCode());

		if (method.equals("onCheckOut")) {
			if (checkoutData.getPaymentMethod().getMethodCode().equals("checkout")) {
				Log.e(getClass().getName(), "onCheckOut");
				onCheckOut();
			}
		}
	}

	private void onCheckOut() {
//		Intent intent = new Intent(context, GetCardToken.class);
//		intent.putExtra("orderID", orderID);
//		context.startActivity(intent);
		CheckoutComFragment fragment = new CheckoutComFragment();
		fragment.setOrderID(orderID);
		fragment.setmOrderEntity(entity);
		fragment.setmOrderHistoryDetail(orderHisDetail);
		SimiManager.getIntance().replaceFragment(fragment);
	}
}
