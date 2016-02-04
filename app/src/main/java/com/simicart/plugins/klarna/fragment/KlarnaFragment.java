package com.simicart.plugins.klarna.fragment;

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
import com.simicart.core.base.delegate.SimiDelegate;
import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.config.Config;
import com.simicart.core.config.Rconfig;

public class KlarnaFragment extends SimiFragment {

	public static final String SUCCESS = "klarna_order_id";
	public static final String FAIL = "simipayu/index/failure";

	public static String URL_CHECKOUT_KLARNA = "http://dev-manage.jajahub.com/klarna/index?";

	public static final String MES_SUCCESS = "Complete order Successfully. Thank your for purchase";
	public static final String MES_FAIL = "Failure: Your order has been canceled";

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

		return view;
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
				}
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				webview.removeView(mImageView);
			}
		});

	}

}
