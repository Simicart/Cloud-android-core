package com.simicart.plugins.braintree;


import com.simicart.MainActivity;
import com.simicart.core.checkout.entity.OrderEntity;
import com.simicart.core.checkout.entity.PaymentMethod;
import com.simicart.core.event.checkout.CheckoutData;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BraintreePayment {
	
	Context context;
	
	public BraintreePayment(String method, CheckoutData checkoutData) {
		this.context = MainActivity.context;
		Log.e("BrainTree", "BrainTree" + method);
		if (method.equals("callBrainTreeServer")) {
			Log.e("AAAAAAAAAA", "AAAAAAAAA");
//			Intent intent = new Intent(this.context, BrainTreeSimicart.class);
//			this.context.startActivity(intent);
			callBrainTreeServer(checkoutData.getPaymentMethod(),
					checkoutData.getOder());
		}
	}
	
	public void callBrainTreeServer(PaymentMethod paymentMethod,
									OrderEntity order) {
		if (paymentMethod.getMethodCode().equals("braintree")) {
			Intent intent = new Intent(this.context, BrainTreeSimicart.class);

//		String token = "eyJ2ZXJzaW9uIjoyLCJhdXRob3JpemF0aW9uRmluZ2VycHJpbnQiOiI4ZTI1NzI2ZGUzOTExMmJjOTMxZTA5NDkxZDJjZTAyYTM4MGJkNTA3NzVjNTY5NzhkYTc1NWFhZjAxYzhiY2QxfGNyZWF0ZWRfYXQ9MjAxNi0wMS0xM1QwMToxMjo1Ny41NDk3NDIyMDArMDAwMFx1MDAyNm1lcmNoYW50X2lkPTM0OHBrOWNnZjNiZ3l3MmJcdTAwMjZwdWJsaWNfa2V5PTJuMjQ3ZHY4OWJxOXZtcHIiLCJjb25maWdVcmwiOiJodHRwczovL2FwaS5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tOjQ0My9tZXJjaGFudHMvMzQ4cGs5Y2dmM2JneXcyYi9jbGllbnRfYXBpL3YxL2NvbmZpZ3VyYXRpb24iLCJjaGFsbGVuZ2VzIjpbXSwiZW52aXJvbm1lbnQiOiJzYW5kYm94IiwiY2xpZW50QXBpVXJsIjoiaHR0cHM6Ly9hcGkuc2FuZGJveC5icmFpbnRyZWVnYXRld2F5LmNvbTo0NDMvbWVyY2hhbnRzLzM0OHBrOWNnZjNiZ3l3MmIvY2xpZW50X2FwaSIsImFzc2V0c1VybCI6Imh0dHBzOi8vYXNzZXRzLmJyYWludHJlZWdhdGV3YXkuY29tIiwiYXV0aFVybCI6Imh0dHBzOi8vYXV0aC52ZW5tby5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tIiwiYW5hbHl0aWNzIjp7InVybCI6Imh0dHBzOi8vY2xpZW50LWFuYWx5dGljcy5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tIn0sInRocmVlRFNlY3VyZUVuYWJsZWQiOnRydWUsInRocmVlRFNlY3VyZSI6eyJsb29rdXBVcmwiOiJodHRwczovL2FwaS5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tOjQ0My9tZXJjaGFudHMvMzQ4cGs5Y2dmM2JneXcyYi90aHJlZV9kX3NlY3VyZS9sb29rdXAifSwicGF5cGFsRW5hYmxlZCI6dHJ1ZSwicGF5cGFsIjp7ImRpc3BsYXlOYW1lIjoiQWNtZSBXaWRnZXRzLCBMdGQuIChTYW5kYm94KSIsImNsaWVudElkIjpudWxsLCJwcml2YWN5VXJsIjoiaHR0cDovL2V4YW1wbGUuY29tL3BwIiwidXNlckFncmVlbWVudFVybCI6Imh0dHA6Ly9leGFtcGxlLmNvbS90b3MiLCJiYXNlVXJsIjoiaHR0cHM6Ly9hc3NldHMuYnJhaW50cmVlZ2F0ZXdheS5jb20iLCJhc3NldHNVcmwiOiJodHRwczovL2NoZWNrb3V0LnBheXBhbC5jb20iLCJkaXJlY3RCYXNlVXJsIjpudWxsLCJhbGxvd0h0dHAiOnRydWUsImVudmlyb25tZW50Tm9OZXR3b3JrIjp0cnVlLCJlbnZpcm9ubWVudCI6Im9mZmxpbmUiLCJ1bnZldHRlZE1lcmNoYW50IjpmYWxzZSwiYnJhaW50cmVlQ2xpZW50SWQiOiJtYXN0ZXJjbGllbnQzIiwiYmlsbGluZ0FncmVlbWVudHNFbmFibGVkIjp0cnVlLCJtZXJjaGFudEFjY291bnRJZCI6ImFjbWV3aWRnZXRzbHRkc2FuZGJveCIsImN1cnJlbmN5SXNvQ29kZSI6IlVTRCJ9LCJjb2luYmFzZUVuYWJsZWQiOmZhbHNlLCJtZXJjaGFudElkIjoiMzQ4cGs5Y2dmM2JneXcyYiIsInZlbm1vIjoib2ZmIn0=";
//		intent.putExtra("EXTRA_TOKEN", token);
//		Log.e("BrainTree TOKEN_ID ", token);
//
//		String client_id = "";
//		client_id = paymentMethod.getData("_id");
//		intent.putExtra("EXTRA_CLIENT_ID", client_id);
//		Log.e("BrainTree CLIENT_ID ", client_id);
//
//		String is_sandbox = "";
//		is_sandbox = paymentMethod.getData("sandbox");
//		intent.putExtra("EXTRA_SANDBOX", is_sandbox);
//		Log.e("BrainTree IS_SANDBOX ", is_sandbox);
//
//		String price = "";
//		price = total_price;
//		intent.putExtra("EXTRA_PRICE", price);
//		Log.e("BrainTree EXTRA_PRICE", price);
//
//		String bnCode = "";
//		bnCode = paymentMethod.getData("bncode");
//		intent.putExtra("EXTRA_BNCODE", bnCode);
//		Log.e("BrainTree BNCODE ", bnCode);
		String totalPrice = "";
			totalPrice = String.valueOf(order.getGrandTotal());
		intent.putExtra("EXTRA_TOTAL_PRICE", totalPrice);

		String orderID = "";
		orderID = order.getID();
		intent.putExtra("EXTRA_INVOICE_NUMBER", orderID);
			this.context.startActivity(intent);
		}
	}

}
