package com.simicart.plugins.payu.fragment;

import android.annotation.SuppressLint;
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
import com.simicart.core.checkout.entity.OrderEntity;
import com.simicart.core.checkout.fragment.ThankyouFragment;
import com.simicart.core.config.Config;
import com.simicart.core.config.Constants;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.core.customer.entity.OrderHisDetail;
import com.simicart.plugins.payu.model.GetUrlModel;
import com.simicart.plugins.payu.model.PayUCancelModel;

public class PayUFragment extends SimiFragment {
    public static final String SUCCESS = "success";
    public static final String FAIL = "failure";

    public static final String MES_SUCCESS = "Complete order Successfully. Thank your for purchase";
    public static final String MES_FAIL = "Your order has been canceled";

    public String url_payu;
    public String orderID;

    public WebView webview;
    public View mImageView;

    public SimiDelegate mDelegate;

    private OrderEntity order;

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

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public static PayUFragment newInstance() {
        PayUFragment fragment = new PayUFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                Rconfig.getInstance().layout("core_webview_layout"), container,
                false);

        SimiManager.getIntance().hidenMenuTop(true);
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


        return rootView;
    }


    public void getUrlRedirect() {
        mDelegate.showLoading();
        final GetUrlModel model = new GetUrlModel();
        model.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                mDelegate.dismissLoading();
                String msg = error.getMessage();
                SimiManager.getIntance().showNotify(msg);
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
                } else if (url.contains("payu/return")) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ThankyouFragment tkFragment = ThankyouFragment.newInstance();
                            tkFragment.setMessage(Config.getInstance().getText("Thank you for your purchase!"));
                            tkFragment.setInvoice_number(String.valueOf(order.getSeqNo()));
                            OrderHisDetail orderHis = new OrderHisDetail();
                            orderHis.setJSONObject(order.getJSONObject());
                            orderHis.parse();
                            tkFragment.setOrderHisDetail(orderHis);
                            if (DataLocal.isTablet) {
                                SimiManager.getIntance().replacePopupFragment(
                                        tkFragment);
                            } else {
                                SimiManager.getIntance().replaceFragment(
                                        tkFragment);
                            }
                        }
                    }, 3000);
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

        webview.setOnKeyListener(new View.OnKeyListener() {

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
    }


    @Override
    public void onDestroy() {
        SimiManager.getIntance().hidenMenuTop(false);
        super.onDestroy();
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

    private void requestCancelOrder() {
        PayUCancelModel payUCancelModel = new PayUCancelModel();
        payUCancelModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if (error != null) {
                    showToastMessage(error.getMessage());
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {

            }
        });
        payUCancelModel.addDataExtendURL(orderID);
        payUCancelModel.addDataExtendURL("cancel");
        payUCancelModel.request();
    }

    public void showToastMessage(String message) {
        Toast toast = Toast.makeText(MainActivity.context, Config.getInstance()
                .getText(message), Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }
}
