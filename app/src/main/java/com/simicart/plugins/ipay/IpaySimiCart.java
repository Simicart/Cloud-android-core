package com.simicart.plugins.ipay;

import org.json.JSONException;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.ipay.Ipay;
import com.ipay.IpayPayment;
import com.simicart.MainActivity;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.delegate.SimiDelegate;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.config.Config;
import com.simicart.plugins.ipay.delegate.ResultDelegate;
import com.simicart.plugins.ipay.model.IpayCancelModel;
import com.simicart.plugins.ipay.model.IpayModel;

public class IpaySimiCart extends Activity {
	private static final int REQUEST_CODE_PAYMENT = 1;
	View rootView;
	public static IpaySimiCart context;
	public transient ResultDelegate result_delegate;
	SimiDelegate mDelegate;
	private String invoice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		rootView = new LinearLayout(getApplicationContext());
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			boolean is_sandbox = extras.getBoolean("EXTRA_SANDBOX");
			String amount = "";
			String currentcy = "";
			String country = "";
			String product_des = "";
			String name = "";
			String email = "";
			String contact = "";
			String url = "";
			String merchant_key = "";
			String merchant_code = "";
			if (is_sandbox) {
				amount = "1.00";
				currentcy = "MYR";
				country = "MY";
				product_des = "TEST";
				name = "John";
				email = "test@simicart.com";
				contact = "60123456789";
				url = "https://www.mobile88.com/epayment/report/testsignature_response.asp";
				merchant_key = "HQgUUZLVzg";
				merchant_code = "M01227";
			} else {
				amount = extras.getString("EXTRA_AMOUNT");
				currentcy = extras.getString("EXTRA_CUREENTCY");
				country = extras.getString("EXTRA_COUNTRY");
				product_des = extras.getString("EXTRA_PRODUCTDES");
				name = extras.getString("EXTRA_NAME");
				email = extras.getString("EXTRA_EMAIL");
				contact = extras.getString("EXTRA_CONTACT");
				url = extras.getString("EXTRA_URL");
				merchant_key = extras.getString("EXTRA_MECHANT_KEY");
				merchant_code = extras.getString("EXTRA_MECHANT_CODE");
			}
			invoice = extras.getString("EXTRA_INVOICE");

			IpayPayment payment = new IpayPayment();
			payment.setMerchantKey(merchant_key);
			payment.setMerchantCode(merchant_code);
			payment.setPaymentId("");
			payment.setCurrency(currentcy);
			payment.setRefNo(invoice);
			payment.setAmount(amount);
			payment.setProdDesc(product_des);
			payment.setUserName(name);
			payment.setUserEmail(email);
			payment.setUserContact(contact);
			payment.setRemark("Success");
			payment.setLang("ISO-8859-1");
			payment.setCountry(country);
			payment.setBackendPostURL(url);
			result_delegate = new ResultDelegate();
			result_delegate.setOrder_id(invoice);
			context = this;
			Intent checkoutIntent = Ipay.getInstance().checkout(payment,
					IpaySimiCart.this, result_delegate);
			startActivityForResult(checkoutIntent, REQUEST_CODE_PAYMENT);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_PAYMENT) {
			if (resultCode == Activity.RESULT_OK) {
				if (data != null) {
					Log.e("Data", data.toString());
				}
			} else if (resultCode == Activity.RESULT_CANCELED) {
				Toast toast = Toast.makeText(MainActivity.context, Config
						.getInstance().getText("Your order has been canceled!"),
						Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.setDuration(10000);
				toast.show();
				Intent i = new Intent(IpaySimiCart.this, MainActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);
				finish();
			}
		}
	}

	public void requestUpdateIpay(String transcation_id, String auth_code,
			String ref_no, String status, String order_id) throws JSONException {
		Log.e("Ipay88", "Transaction: " + transcation_id + ",AuthCode: " + auth_code + ",RefNo: " + ref_no + ",Status: " + status + ",OrderID: " + order_id);
		IpayModel model = new IpayModel();
		ModelDelegate delegate = new ModelDelegate() {
			@Override
			public void onFail(SimiError error) {
			}

			@Override
			public void onSuccess(SimiCollection collection) {
			}
		};
		model.setDelegate(delegate);
		model.addDataBody("payment_id", transcation_id + "");
		model.addDataBody("auth_code", auth_code + "");
		model.addDataBody("ref_no", ref_no + "");
		model.addDataBody("status", status + "");
		model.addDataBody("order_id", order_id + "");
		model.request();

		if(status.equals("1")){
			changeView("Thank you for your purchase!");
		}else{
			changeView("Your order has been canceled!");
		}
	}

	public void showError(String message){
		Toast toast = Toast.makeText(MainActivity.context, Config.getInstance()
				.getText(message), Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(10000);
		toast.show();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		showDialog();
	}

	private void showDialog() {
		new AlertDialog.Builder(SimiManager.getIntance().getCurrentActivity())
				.setMessage(
						Config.getInstance()
								.getText(
										"Are you sure that you want to cancel the order?"))
				.setPositiveButton(Config.getInstance().getText("Yes"),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
												int which) {
								requestCancelOrder();
								changeView("Your order has been canceled!");
								SimiManager.getIntance().backToHomeFragment();
							}
						})
				.setNegativeButton(Config.getInstance().getText("No"),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
												int which) {
								// do nothing
							}
						}).show();

	}

	private void requestCancelOrder(){
		IpayCancelModel ipayCancelModel = new IpayCancelModel();
		ipayCancelModel.setDelegate(new ModelDelegate() {
			@Override
			public void onFail(SimiError error) {
				if(error != null){
					changeView(error.getMessage());
				}
			}

			@Override
			public void onSuccess(SimiCollection collection) {

			}
		});
		ipayCancelModel.addDataExtendURL(invoice);
		ipayCancelModel.addDataExtendURL("cancel");
		ipayCancelModel.request();
	}

	public void changeView(String message) {
		Toast toast = Toast.makeText(MainActivity.context, Config.getInstance()
				.getText(message), Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(10000);
		toast.show();
		Intent i = new Intent(IpaySimiCart.this, MainActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
		finish();
	}
}
