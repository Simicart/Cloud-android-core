package com.simicart.plugins.braintree;

import org.json.JSONException;

import com.google.android.gms.wallet.Cart;
import com.google.android.gms.wallet.LineItem;
import com.simicart.MainActivity;
import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.delegate.SimiDelegate;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.config.Config;
import com.simicart.plugins.braintree.entity.TokenEntity;
import com.simicart.plugins.braintree.model.BrainTreeModel;
import com.simicart.plugins.braintree.model.BraintreeGetTokenModel;
import com.trueplus.simicart.braintreelibrary.BraintreeFragment;
import com.trueplus.simicart.braintreelibrary.BraintreePaymentActivity;
import com.trueplus.simicart.braintreelibrary.PaymentRequest;
import com.trueplus.simicart.braintreelibrary.exceptions.InvalidArgumentException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.Toast;

public class BrainTreeSimicart extends Activity {
	
	View rootView;
	public String total = "0.0";
	public String orderID = "";
	public String token = "";
	private BraintreeFragment mBraintreeFragment;
	public String demoToken = "eyJ2ZXJzaW9uIjoyLCJhdXRob3JpemF0aW9uRmluZ2VycHJpbnQiOiI0ZjQ2NDczMGYzMzk4NDdjNGFhNWY5ZDYxMGQwYmNkNTc0ODNiY2M2YmNmNTJmYzJiZThmMTFmNjM5ZGEyMjk4fGNyZWF0ZWRfYXQ9MjAxNi0wMS0xOVQwNzo1NDoxNS4zOTc2NTk0NTUrMDAwMFx1MDAyNm1lcmNoYW50X2lkPWRjcHNweTJicndkanIzcW5cdTAwMjZwdWJsaWNfa2V5PTl3d3J6cWszdnIzdDRuYzgiLCJjb25maWdVcmwiOiJodHRwczovL2FwaS5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tOjQ0My9tZXJjaGFudHMvZGNwc3B5MmJyd2RqcjNxbi9jbGllbnRfYXBpL3YxL2NvbmZpZ3VyYXRpb24iLCJjaGFsbGVuZ2VzIjpbXSwiZW52aXJvbm1lbnQiOiJzYW5kYm94IiwiY2xpZW50QXBpVXJsIjoiaHR0cHM6Ly9hcGkuc2FuZGJveC5icmFpbnRyZWVnYXRld2F5LmNvbTo0NDMvbWVyY2hhbnRzL2RjcHNweTJicndkanIzcW4vY2xpZW50X2FwaSIsImFzc2V0c1VybCI6Imh0dHBzOi8vYXNzZXRzLmJyYWludHJlZWdhdGV3YXkuY29tIiwiYXV0aFVybCI6Imh0dHBzOi8vYXV0aC52ZW5tby5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tIiwiYW5hbHl0aWNzIjp7InVybCI6Imh0dHBzOi8vY2xpZW50LWFuYWx5dGljcy5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tIn0sInRocmVlRFNlY3VyZUVuYWJsZWQiOnRydWUsInRocmVlRFNlY3VyZSI6eyJsb29rdXBVcmwiOiJodHRwczovL2FwaS5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tOjQ0My9tZXJjaGFudHMvZGNwc3B5MmJyd2RqcjNxbi90aHJlZV9kX3NlY3VyZS9sb29rdXAifSwicGF5cGFsRW5hYmxlZCI6dHJ1ZSwicGF5cGFsIjp7ImRpc3BsYXlOYW1lIjoiQWNtZSBXaWRnZXRzLCBMdGQuIChTYW5kYm94KSIsImNsaWVudElkIjpudWxsLCJwcml2YWN5VXJsIjoiaHR0cDovL2V4YW1wbGUuY29tL3BwIiwidXNlckFncmVlbWVudFVybCI6Imh0dHA6Ly9leGFtcGxlLmNvbS90b3MiLCJiYXNlVXJsIjoiaHR0cHM6Ly9hc3NldHMuYnJhaW50cmVlZ2F0ZXdheS5jb20iLCJhc3NldHNVcmwiOiJodHRwczovL2NoZWNrb3V0LnBheXBhbC5jb20iLCJkaXJlY3RCYXNlVXJsIjpudWxsLCJhbGxvd0h0dHAiOnRydWUsImVudmlyb25tZW50Tm9OZXR3b3JrIjp0cnVlLCJlbnZpcm9ubWVudCI6Im9mZmxpbmUiLCJ1bnZldHRlZE1lcmNoYW50IjpmYWxzZSwiYnJhaW50cmVlQ2xpZW50SWQiOiJtYXN0ZXJjbGllbnQzIiwiYmlsbGluZ0FncmVlbWVudHNFbmFibGVkIjp0cnVlLCJtZXJjaGFudEFjY291bnRJZCI6InN0Y2gybmZkZndzenl0dzUiLCJjdXJyZW5jeUlzb0NvZGUiOiJVU0QifSwiY29pbmJhc2VFbmFibGVkIjp0cnVlLCJjb2luYmFzZSI6eyJjbGllbnRJZCI6IjdlYTc5N2EyYmY2ZjM2YmY5NjFmYTc5ZTk0YTQwNjBlODM2ZTc1NmEyYzM1ZGU4MjlmYzM0NzI3YTJhYmYxODEiLCJtZXJjaGFudEFjY291bnQiOiJjb2luYmFzZS1zYW5kYm94LXNoYXJlZC1tZXJjaGFudEBnZXRicmFpbnRyZWUuY29tIiwic2NvcGVzIjoiYXV0aG9yaXphdGlvbnM6YnJhaW50cmVlIHVzZXIiLCJyZWRpcmVjdFVybCI6Imh0dHBzOi8vYXNzZXRzLmJyYWludHJlZWdhdGV3YXkuY29tL2NvaW5iYXNlL29hdXRoL3JlZGlyZWN0LWxhbmRpbmcuaHRtbCIsImVudmlyb25tZW50Ijoic2hhcmVkX3NhbmRib3gifSwibWVyY2hhbnRJZCI6ImRjcHNweTJicndkanIzcW4iLCJ2ZW5tbyI6Im9mZmxpbmUiLCJhcHBsZVBheSI6eyJzdGF0dXMiOiJtb2NrIiwiY291bnRyeUNvZGUiOiJVUyIsImN1cnJlbmN5Q29kZSI6IlVTRCIsIm1lcmNoYW50SWRlbnRpZmllciI6Im1lcmNoYW50LmNvbS5icmFpbnRyZWVwYXltZW50cy5zYW5kYm94LkJyYWludHJlZS1EZW1vIiwic3VwcG9ydGVkTmV0d29ya3MiOlsidmlzYSIsIm1hc3RlcmNhcmQiLCJhbWV4Il19fQ==";

	SimiDelegate mDelegate;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		rootView = new LinearLayout(getApplicationContext());
		setContentView(rootView, new LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));
		Log.e("BrainTreeSimicart", "onCreate");

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			total = extras.getString("EXTRA_TOTAL_PRICE");
			orderID = extras.getString("EXTRA_INVOICE_NUMBER");
		}
		mDelegate = new SimiBlock(rootView, MainActivity.context);
		getAuthorization();
	}
	
	public void onBuyPressed() {

			PaymentRequest paymentRequest = new PaymentRequest()
				.clientToken(token)
				.androidPayCart(getAndroidPayCart())
				.primaryDescription("Cart")
				.secondaryDescription(Config.getInstance().getText("Order ID: ") + orderID)
				.amount(total + " " + Config.getInstance().getCurrency_code());

			startActivityForResult(paymentRequest.getIntent(this), 123);
	}

	private Cart getAndroidPayCart() {
		return Cart.newBuilder()
				.setCurrencyCode(Config.getInstance().getCurrency_code())
				.setTotalPrice(total)
				.build();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 123) {
			Log.e("onActivityResult", "++" + resultCode);
			switch (resultCode) {
			case RESULT_OK:
				String paymentMethodNonce = data.getStringExtra(BraintreePaymentActivity.EXTRA_PAYMENT_METHOD_NONCE);
				Log.e("PaymentMethod", "++" + paymentMethodNonce);
				if(paymentMethodNonce != null) {
					try {
						requestUpdateBrainTree(paymentMethodNonce, total, orderID);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;
			default:
				changeView(Config.getInstance().getText("FAIL"));
				break;
			}
		}
	}

	private void setup() {
		try {
			String authorization;
			if (Settings.useTokenizationKey(this)) {
				authorization = Settings.getEnvironmentTokenizationKey(this);
			} else {
				authorization = token;
			}
			mBraintreeFragment = BraintreeFragment.newInstance(this, authorization);
		} catch (InvalidArgumentException e) {
			//showDialog(e.getMessage());
		}
	}

	public void getAuthorization() {
		BraintreeGetTokenModel model = new BraintreeGetTokenModel();
		mDelegate.showLoading();
		model.setDelegate(new ModelDelegate() {
			@Override
			public void onFail(SimiError error) {
				if (error != null) {
					mDelegate.dismissLoading();
					SimiManager.getIntance().showNotify(null, error.getMessage(), "Ok");
				}
			}

			@Override
			public void onSuccess(SimiCollection collection) {
				mDelegate.dismissLoading();
				SimiEntity entity = collection.getCollection().get(0);
				token = ((TokenEntity) entity).getToken();
				Log.e("BrainTreeSimiCart", "++" + token);
				setup();
				onBuyPressed();
			}
		});
		model.request();
	}
	
	@SuppressLint("NewApi")
	public void requestUpdateBrainTree(String nonce,
			String amount, String orderID) throws JSONException {
		mDelegate.showLoading();
		BrainTreeModel mModel = new BrainTreeModel();
		ModelDelegate delegate = new ModelDelegate() {

			@Override
			public void onFail(SimiError error) {
				mDelegate.dismissLoading();
				if(error != null){
					SimiManager.getIntance().showNotify(null, error.getMessage(), "Ok");
				}
			}

			@Override
			public void onSuccess(SimiCollection collection) {
				mDelegate.dismissLoading();
				changeView(Config.getInstance().getText("SUCCESS"));
			}
		};
		mModel.setDelegate(delegate);

		mModel.addDataBody("nonce", nonce);
		mModel.addDataBody("order_id", orderID);
		mModel.request();
	}
	
	public void changeView(String message) {
		Toast toast = Toast.makeText(MainActivity.context, Config.getInstance()
				.getText(message), Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.show();
		Intent i = new Intent(BrainTreeSimicart.this, MainActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
		finish();
	}

}
