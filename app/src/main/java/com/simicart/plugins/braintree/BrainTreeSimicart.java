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

	SimiDelegate mDelegate;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		rootView = new LinearLayout(getApplicationContext());
		setContentView(rootView, new LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			total = extras.getString("EXTRA_TOTAL_PRICE");
			orderID = extras.getString("EXTRA_INVOICE_NUMBER");
		}
		mDelegate = new SimiBlock(rootView, MainActivity.context);
		getAuthorization();
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
