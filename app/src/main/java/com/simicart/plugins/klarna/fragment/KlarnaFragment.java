package com.simicart.plugins.klarna.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.simicart.MainActivity;
import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.delegate.SimiDelegate;
import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.config.Config;
import com.simicart.core.config.Rconfig;
import com.simicart.plugins.klarna.model.KlarnaCancelModel;

public class KlarnaFragment extends SimiFragment {

	public static final String SUCCESS = "klarna_order_id";
	public static final String FAIL = "simipayu/index/failure";

	public static String URL_CHECKOUT_KLARNA = "http://dev-manage.jajahub.com/klarna/index?";

	public static final String MES_SUCCESS = "Complete order Successfully. Thank your for purchase";
	public static final String MES_FAIL = "Your order has been canceled";

	public WebView webview;
	public View mImageView;

	public String orderID;

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public static KlarnaFragment newInstance() {
		KlarnaFragment fragment = new KlarnaFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(
				Rconfig.getInstance().layout("core_webview_layout"), null,
				false);
		webview = (WebView) view.findViewById(Rconfig
				.getInstance().id("webview_Ad"));
		mImageView = inflater.inflate(
				Rconfig.getInstance().layout("core_base_loading"), null, false);

		String url = URL_CHECKOUT_KLARNA + "order_id=" + orderID
				+ "&token=" + Config.getInstance().getSecretKey();
		Log.e("Klarna Fragment", "++" + url);
		openWebview(url);

		rootView.setFocusableInTouchMode(true);
		rootView.requestFocus();
		rootView.setOnKeyListener(new View.OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					if (keyCode == KeyEvent.KEYCODE_BACK) {
						showDialog();
						return true;
					}
				}
				return false;
			}
		});

		return view;
	}

	public void openWebview(String url) {
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		lp.addRule(RelativeLayout.CENTER_IN_PARENT);
		mImageView.setLayoutParams(lp);
		webview.addView(mImageView);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setBuiltInZoomControls(true);
		webview.getSettings().setLoadWithOverviewMode(true);
		webview.getSettings().setUseWideViewPort(true);
		webview.getSettings().setLayoutAlgorithm(
				WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
		webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

		String url_site = url;
		webview.loadUrl(url_site);

		webview.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				if (url.contains(SUCCESS)) {
					Handler handler = new Handler();
					handler.postDelayed(new Runnable() {
						@Override
						public void run() {
							SimiManager.getIntance().backToHomeFragment();
						}
					}, 3000);
				} else if (url.contains(FAIL)) {
					SimiManager.getIntance().backToHomeFragment();
					showToastMessage(MES_FAIL);
				} else {
					Log.e(getClass().getName(), "RUNNING:" + url);
				}
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				webview.removeView(mImageView);
			}
		});

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
								showToastMessage(MES_FAIL);
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
		KlarnaCancelModel klarnaCancelModel = new KlarnaCancelModel();
		klarnaCancelModel.setDelegate(new ModelDelegate() {
			@Override
			public void onFail(SimiError error) {
				if(error != null){
					showToastMessage(error.getMessage());
				}
			}

			@Override
			public void onSuccess(SimiCollection collection) {

			}
		});
		klarnaCancelModel.addDataExtendURL(orderID);
		klarnaCancelModel.addDataExtendURL("cancel");
		klarnaCancelModel.request();
	}


	public void showToastMessage(String message) {
		Toast toast = Toast.makeText(MainActivity.context, Config.getInstance()
				.getText(message), Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.show();
	}

}
