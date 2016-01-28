package com.simicart.plugins.payu.fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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
import com.simicart.core.config.Constants;
import com.simicart.core.config.Rconfig;
import com.simicart.plugins.payu.model.GetUrlModel;

public class PayUFragment extends SimiFragment {
	public static final String SUCCESS = "simipayu/index/success";
	public static final String FAIL = "simipayu/index/failure";

	public static final String MES_SUCCESS = "Complete order Successfully. Thank your for purchase";
	public static final String MES_FAIL = "Failure: Your order has been canceled";

	public  String url_payu;
	public String orderID;

	public WebView webview;
	public View mImageView;

	public SimiDelegate mDelegate;

	public String getUrl_payu() {
		return url_payu;
	}

	public void setUrl_payu(String url_payu) {
		this.url_payu = url_payu;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public static PayUFragment newInstance() {
		PayUFragment fragment = new PayUFragment();
		return fragment;
	}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(
				Rconfig.getInstance().layout("core_webview_layout"), container,
				false);
		webview = (WebView) rootView.findViewById(Rconfig
				.getInstance().id("webview_Ad"));
		mImageView = inflater.inflate(
				Rconfig.getInstance().layout("core_base_loading"), null, false);

		mDelegate = new SimiBlock(rootView, getActivity());

		getUrlRedirect();

		// add loading View
//		if(getArguments() != null){
////		url_payu = (String) getData(Constants.KeyData.URL_PAYU, Constants.KeyData.TYPE_STRING, getArguments());
////		invoice_number = (String) getData(Constants.KeyData.INVOICE_NUMBER, Constants.KeyData.TYPE_STRING, getArguments());
//		}



		return rootView;
	}

	public void showToastMessage(String message) {
		Toast toast = Toast.makeText(MainActivity.context, Config.getInstance()
				.getText(message), Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.show();
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
					SimiManager.getIntance().backToHomeFragment();
					showToastMessage(MES_SUCCESS);
				} else if (url.contains(FAIL)) {
					SimiManager.getIntance().backToHomeFragment();
					showToastMessage(MES_FAIL);
				} else {
					Log.e(getClass().getName(), "RUNNING:" + url);
					// backtoHomeScreen();
					// showToastMessage(MES_SUCCESS);
				}
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				webview.removeView(mImageView);
			}

		});
	}

	public void getUrlRedirect() {
		mDelegate.showLoading();
		final GetUrlModel model = new GetUrlModel();
		model.setDelegate(new ModelDelegate() {
			@Override
			public void onFail(SimiError error) {
				mDelegate.dismissLoading();
			}

			@Override
			public void onSuccess(SimiCollection collection) {
				mDelegate.dismissLoading();
				String url = model.getUrl();
				Log.e("PayUFragment", "++" + url);
				openWebview(url);
			}
		});
		Log.e("PayUFragment", "++" + orderID);
		model.addDataBody("order_id", orderID);
		model.request();
	}

}
