package com.simicart.plugins.ccavanue.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import org.apache.http.util.EncodingUtils;
import java.net.URLEncoder;
import java.util.Timer;
import java.util.TimerTask;
import com.simicart.MainActivity;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.config.Config;
import com.simicart.core.config.Rconfig;
import com.simicart.plugins.ccavanue.common.Communicator;
import com.simicart.plugins.ccavanue.dialog.ActionDialog;
import com.simicart.plugins.ccavanue.fragment.ApproveOTPFragment;
import com.simicart.plugins.ccavanue.fragment.CityBankFragment;
import com.simicart.plugins.ccavanue.fragment.OtpFragment;
import com.simicart.plugins.ccavanue.model.GetRSAModel;
import com.simicart.plugins.ccavanue.model.UpdatePaymentModel;
import com.simicart.plugins.ccavanue.utility.AvenuesParams;
import com.simicart.plugins.ccavanue.utility.Constants;
import com.simicart.plugins.ccavanue.utility.RSAUtility;
import com.simicart.plugins.ccavanue.utility.ServiceHandler;
import com.simicart.plugins.ccavanue.utility.ServiceUtility;


public class WebViewActivity extends Activity implements Communicator {

    WebView myBrowser;
    WebSettings webSettings;
    private BroadcastReceiver mIntentReceiver;
    String bankUrl = "";
    FragmentManager manager;
    ActionDialog actionDialog = new ActionDialog();
    Timer timer = new Timer();
    TimerTask timerTask;
    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler();
    public int loadCounter = 0;

    private ProgressDialog dialog;
    Intent mainIntent;
    String html, encVal;
    int MyDeviceAPI;
    private String vResponse;
    private String mechantID;
    private String accessCode;
    private String urlRedirect;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Rconfig.getInstance().layout("plugins_ccavanue_activity_main"));
        mainIntent = getIntent();
        manager = getFragmentManager();

        myBrowser = (WebView) findViewById(Rconfig.getInstance().id("webView"));
        webSettings = myBrowser.getSettings();
        webSettings.setJavaScriptEnabled(true);

        MyDeviceAPI = Build.VERSION.SDK_INT;
        requestGetRSA();
    }

    private void requestGetRSA() {
        dialog = new ProgressDialog(WebViewActivity.this);
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();
        final GetRSAModel model = new GetRSAModel();
        model.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if(error != null){
                    changeView(error.getMessage());
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                try {
                    vResponse = model.getRsaKey();
                    mechantID = model.getMerchanID();
                    accessCode = model.getAccessCode();
                    urlRedirect = model.getUrlRedirect();
                    if (!ServiceUtility.chkNull(vResponse).equals("")
                            && ServiceUtility.chkNull(vResponse).toString().indexOf("ERROR") == -1) {
                        StringBuffer vEncVal = new StringBuffer("");
                        vEncVal.append(ServiceUtility.addToPostParams(AvenuesParams.AMOUNT, mainIntent.getStringExtra(AvenuesParams.AMOUNT)));
                        vEncVal.append(ServiceUtility.addToPostParams(AvenuesParams.CURRENCY, mainIntent.getStringExtra(AvenuesParams.CURRENCY)));
                        encVal = RSAUtility.encrypt(vEncVal.substring(0, vEncVal.length() - 1), vResponse);
                    }
                } catch (Exception e) {
                }

                if (dialog.isShowing())
                    dialog.dismiss();

                class MyJavaScriptInterface {
                    @JavascriptInterface
                    public void processHTML(String html) {
                        try {
                            // process the html as needed by the app
                            Log.v("Logs", "-------------- Process HTML : " + html);
                            String status = null;
                            if (html.indexOf("Failure") != -1) {
                                status = "Transaction Declined!";
                                updatePayment("2", status);
                            } else if (html.indexOf("Success") != -1) {
                                status = "Thank you for your purchase!";
                                updatePayment("1", status);
                            } else if (html.indexOf("Aborted") != -1) {
                                status = "Your order has been canceled!";
                                updatePayment("2", status);
                            } else if (html.indexOf("Error") != -1){
                                status = "Transaction Error!";
                                updatePayment("0", status);
                            } else{
                                status = "Transaction Error!";
                                updatePayment("0", status);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.v("Logs", "-------------- Error : " + e);
                        }
                    }
                }

                myBrowser.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");
                myBrowser.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                        Log.e("URLLOADINGCCAVANUE", url);
                        bankUrl = url;
                        return false;
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(myBrowser, url);
                        Log.e("URLFINSHCCAVANUE", url);
                        if (url.indexOf("success") != -1) {
                            myBrowser.loadUrl("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
                        }

                        // calling load Waiting for otp fragment
                        if (loadCounter < 1) {
                            if (MyDeviceAPI >= 19) {
                                loadCitiBankAuthenticateOption(url);
                                loadWaitingFragment(url);
                            }
                        }
                        bankUrl = url;
                    }

                    @Override
                    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                        Toast.makeText(getApplicationContext(), "Oh no! " + description, Toast.LENGTH_SHORT).show();
                    }
                });

            /* An instance of this class will be registered as a JavaScript interface */
                StringBuffer params = new StringBuffer();
                params.append(ServiceUtility.addToPostParams(AvenuesParams.ACCESS_CODE, accessCode));
                params.append(ServiceUtility.addToPostParams(AvenuesParams.MERCHANT_ID, mechantID));
                params.append(ServiceUtility.addToPostParams(AvenuesParams.ORDER_ID, mainIntent.getStringExtra(AvenuesParams.ORDER_ID)));
                params.append(ServiceUtility.addToPostParams(AvenuesParams.REDIRECT_URL, urlRedirect));
                params.append(ServiceUtility.addToPostParams(AvenuesParams.CANCEL_URL, urlRedirect));
                params.append(ServiceUtility.addToPostParams(AvenuesParams.ENC_VAL, URLEncoder.encode(encVal)));

                String vPostParams = params.substring(0, params.length() - 1);
                try {
                    myBrowser.postUrl(Constants.TRANS_URL, EncodingUtils.getBytes(vPostParams, "UTF-8"));
                } catch (Exception e) {

                }
            }
        });
        model.addDataExtendURL("rsa");
        model.addDataBody("order_id", mainIntent.getStringExtra(AvenuesParams.ORDER_ID));
        model.request();
    }



    private void updatePayment(final String paymentStatus, final String status){
        UpdatePaymentModel updatePaymentModel = new UpdatePaymentModel();
        updatePaymentModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if(error != null){
                    changeView(error.getMessage());
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                changeView(status);
            }
        });

        updatePaymentModel.addDataExtendURL("update-ccavenue-payment");
        updatePaymentModel.addDataBody("order_id", mainIntent.getStringExtra(AvenuesParams.ORDER_ID));
        updatePaymentModel.addDataBody("status", paymentStatus);
        updatePaymentModel.request();
    }

    public void changeView(String message) {
        Toast toast = Toast.makeText(MainActivity.context, Config.getInstance()
                .getText(message), Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
        Intent i = new Intent(WebViewActivity.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }

    // Method to start Timer for 30 sec. delay
    public void startTimer() {
        try {
            //set a new Timer
            if (timer == null) {
                timer = new Timer();
            }
            //initialize the TimerTask's job
            initializeTimerTask();

            //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
            timer.schedule(timerTask, 30000, 30000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to Initialize Task
    public void initializeTimerTask() {
        try {
            timerTask = new TimerTask() {
                public void run() {

                    //use a handler to run a toast that shows the current timestamp
                    handler.post(new Runnable() {
                        public void run() {
                        /*int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(getApplicationContext(), "I M Called ..", duration);
                        toast.show();*/
                            loadActionDialog();
                        }
                    });
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to stop timer
    public void stopTimerTask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void loadCitiBankAuthenticateOption(String url) {
        if (url.contains("https://www.citibank.co.in/acspage/cap_nsapi.so")) {
            CityBankFragment citiFrag = new CityBankFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(Rconfig.getInstance().id("otp_frame"), citiFrag, "CitiBankAuthFrag");
            transaction.commit();
            loadCounter++;
        }
    }

    public void removeCitiBankAuthOption() {
        CityBankFragment cityFrag = (CityBankFragment) manager.findFragmentByTag("CitiBankAuthFrag");
        FragmentTransaction transaction = manager.beginTransaction();
        if (cityFrag != null) {
            transaction.remove(cityFrag);
            transaction.commit();
        }
    }

    // Method to load Waiting for OTP fragment
    public void loadWaitingFragment(String url) {

        // SBI Debit Card
        if (url.contains("https://acs.onlinesbi.com/sbi/")) {
            OtpFragment waitingFragment = new OtpFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(Rconfig.getInstance().id("otp_frame"), waitingFragment, "OTPWaitingFrag");
            transaction.commit();
            startTimer();
        }

        // Kotak Bank Visa Debit card
        else if (url.contains("https://cardsecurity.enstage.com/ACSWeb/")) {
            OtpFragment waitingFragment = new OtpFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(Rconfig.getInstance().id("otp_frame"), waitingFragment, "OTPWaitingFrag");
            transaction.commit();
            startTimer();
        }
        // For SBI and All its Asscocites Net Banking
        else if (url.contains("https://merchant.onlinesbi.com/merchant/smsenablehighsecurity.htm") || url.contains("https://merchant.onlinesbi.com/merchant/resendsmsotp.htm") || url.contains("https://m.onlinesbi.com/mmerchant/smsenablehighsecurity.htm")
                || url.contains("https://merchant.onlinesbh.com/merchant/smsenablehighsecurity.htm") || url.contains("https://merchant.onlinesbh.com/merchant/resendsmsotp.htm")
                || url.contains("https://merchant.sbbjonline.com/merchant/smsenablehighsecurity.htm") || url.contains("https://merchant.sbbjonline.com/merchant/resendsmsotp.htm")
                || url.contains("https://merchant.onlinesbm.com/merchant/smsenablehighsecurity.htm") || url.contains("https://merchant.onlinesbm.com/merchant/resendsmsotp.htm")
                || url.contains("https://merchant.onlinesbp.com/merchant/smsenablehighsecurity.htm") || url.contains("https://merchant.onlinesbp.com/merchant/resendsmsotp.htm")
                || url.contains("https://merchant.sbtonline.in/merchant/smsenablehighsecurity.htm") || url.contains("https://merchant.sbtonline.in/merchant/resendsmsotp.htm")) {
            OtpFragment waitingFragment = new OtpFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(Rconfig.getInstance().id("otp_frame"), waitingFragment, "OTPWaitingFrag");
            transaction.commit();
            startTimer();
        }

        // For ICICI Credit Card
        else if (url.contains("https://www.3dsecure.icicibank.com/ACSWeb/EnrollWeb/ICICIBank/server/OtpServer")) {
            OtpFragment waitingFragment = new OtpFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(Rconfig.getInstance().id("otp_frame"), waitingFragment, "OTPWaitingFrag");
            transaction.commit();
            startTimer();
        }
        // City bank Debit card
        else if (url.equals("cityBankAuthPage")) {
            removeCitiBankAuthOption();
            OtpFragment waitingFragment = new OtpFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(Rconfig.getInstance().id("otp_frame"), waitingFragment, "OTPWaitingFrag");
            transaction.commit();
            startTimer();
        }
        // HDFC Debit Card and Credit Card
        else if (url.contains("https://netsafe.hdfcbank.com/ACSWeb/jsp/dynamicAuth.jsp?transType=payerAuth")) {
            //removeCitiBankAuthOption();
            OtpFragment waitingFragment = new OtpFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(Rconfig.getInstance().id("otp_frame"), waitingFragment, "OTPWaitingFrag");
            transaction.commit();
            startTimer();
        }
        // For SBI  Visa credit Card
        else if (url.contains("https://secure4.arcot.com/acspage/cap")) {
            OtpFragment waitingFragment = new OtpFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(Rconfig.getInstance().id("otp_frame"), waitingFragment, "OTPWaitingFrag");
            transaction.commit();
            startTimer();
        }

        // For Kotak Bank Visa Credit Card
        else if (url.contains("https://cardsecurity.enstage.com/ACSWeb/EnrollWeb/KotakBank/server/OtpServer")) {
            OtpFragment waitingFragment = new OtpFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(Rconfig.getInstance().id("otp_frame"), waitingFragment, "OTPWaitingFrag");
            transaction.commit();
            startTimer();
        } else {
            removeWaitingFragment();
            removeApprovalFragment();
            stopTimerTask();
        }
    }

    // Method to remove Waiting fragment
    public void removeWaitingFragment() {
        OtpFragment waitingFragment = (OtpFragment) manager.findFragmentByTag("OTPWaitingFrag");
        if (waitingFragment != null) {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.remove(waitingFragment);
            transaction.commit();
        } else {
            // DO nothing
            //Toast.makeText(this," --test-- ",Toast.LENGTH_SHORT).show();
        }
    }

    // Method to load Approve Otp Fragment
    public void loadApproveOTP(String otpText, String senderNo) {
        try {
            Integer vTemp = Integer.parseInt(otpText);

            if (bankUrl.contains("https://acs.onlinesbi.com/sbi/") && senderNo.contains("SBI") && (otpText.length() == 6 || otpText.length() == 8)) {
                removeWaitingFragment();
                stopTimerTask();
                ApproveOTPFragment approveFragment = new ApproveOTPFragment();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(Rconfig.getInstance().id("otp_frame"), approveFragment, "OTPApproveFrag");
                transaction.commit();
                approveFragment.setOtpText(otpText);
            }
            // For Kotak bank Debit Card
            else if (bankUrl.contains("https://cardsecurity.enstage.com/ACSWeb/") && senderNo.contains("KOTAK") && (otpText.length() == 6 || otpText.length() == 8)) {
                removeWaitingFragment();
                stopTimerTask();
                ApproveOTPFragment approveFragment = new ApproveOTPFragment();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(Rconfig.getInstance().id("otp_frame"), approveFragment, "OTPApproveFrag");
                transaction.commit();
                approveFragment.setOtpText(otpText);
            }
            // for SBI Net Banking
            else if ((((bankUrl.contains("https://merchant.onlinesbi.com/merchant/smsenablehighsecurity.htm") || bankUrl.contains("https://merchant.onlinesbi.com/merchant/resendsmsotp.htm") || bankUrl.contains("https://m.onlinesbi.com/mmerchant/smsenablehighsecurity.htm")) && senderNo.contains("SBI"))
                    || ((bankUrl.contains("https://merchant.onlinesbh.com/merchant/smsenablehighsecurity.htm") || bankUrl.contains("https://merchant.onlinesbh.com/merchant/resendsmsotp.htm")) && senderNo.contains("SBH"))
                    || ((bankUrl.contains("https://merchant.sbbjonline.com/merchant/smsenablehighsecurity.htm") || bankUrl.contains("https://merchant.sbbjonline.com/merchant/resendsmsotp.htm")) && senderNo.contains("SBBJ"))
                    || ((bankUrl.contains("https://merchant.onlinesbm.com/merchant/smsenablehighsecurity.htm") || bankUrl.contains("https://merchant.onlinesbm.com/merchant/resendsmsotp.htm")) && senderNo.contains("SBM"))
                    || ((bankUrl.contains("https://merchant.onlinesbp.com/merchant/smsenablehighsecurity.htm") || bankUrl.contains("https://merchant.onlinesbp.com/merchant/resendsmsotp.htm")) && senderNo.contains("SBP"))
                    || ((bankUrl.contains("https://merchant.sbtonline.in/merchant/smsenablehighsecurity.htm") || bankUrl.contains("https://merchant.sbtonline.in/merchant/resendsmsotp.htm")) && senderNo.contains("SBT"))) && (otpText.length() == 6 || otpText.length() == 8)) {
                removeWaitingFragment();
                stopTimerTask();
                ApproveOTPFragment approveFragment = new ApproveOTPFragment();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(Rconfig.getInstance().id("otp_frame"), approveFragment, "OTPApproveFrag");
                transaction.commit();
                approveFragment.setOtpText(otpText);
            }
            // For ICICI Visa Credit Card
            else if (bankUrl.contains("https://www.3dsecure.icicibank.com/ACSWeb/EnrollWeb/ICICIBank/server/OtpServer") && senderNo.contains("ICICI") && (otpText.length() == 6 || otpText.length() == 8)) {
                removeWaitingFragment();
                stopTimerTask();
                ApproveOTPFragment approveFragment = new ApproveOTPFragment();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(Rconfig.getInstance().id("otp_frame"), approveFragment, "OTPApproveFrag");
                transaction.commit();
                approveFragment.setOtpText(otpText);
            }
            // For ICICI Debit card
            else if (bankUrl.contains("https://acs.icicibank.com/acspage/cap?") && senderNo.contains("ICICI") && (otpText.length() == 6 || otpText.length() == 8)) {
                removeWaitingFragment();
                stopTimerTask();
                ApproveOTPFragment approveFragment = new ApproveOTPFragment();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(Rconfig.getInstance().id("otp_frame"), approveFragment, "OTPApproveFrag");
                transaction.commit();
                approveFragment.setOtpText(otpText);
            }
            // For CITI bank Debit card
            else if (bankUrl.contains("https://www.citibank.co.in/acspage/cap_nsapi.so") && senderNo.contains("CITI") && (otpText.length() == 6 || otpText.length() == 8)) {
                removeWaitingFragment();
                stopTimerTask();
                ApproveOTPFragment approveFragment = new ApproveOTPFragment();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(Rconfig.getInstance().id("otp_frame"), approveFragment, "OTPApproveFrag");
                transaction.commit();
                approveFragment.setOtpText(otpText);
            }
            // For HDFC bank debit card and Credit Card
            else if (bankUrl.contains("https://netsafe.hdfcbank.com/ACSWeb/jsp/dynamicAuth.jsp?transType=payerAuth") && senderNo.contains("HDFC") && (otpText.length() == 6 || otpText.length() == 8)) {
                removeWaitingFragment();
                stopTimerTask();
                ApproveOTPFragment approveFragment = new ApproveOTPFragment();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(Rconfig.getInstance().id("otp_frame"), approveFragment, "OTPApproveFrag");
                transaction.commit();
                approveFragment.setOtpText(otpText);
            }
            // For HDFC Netbanking
            else if (bankUrl.contains("https://netbanking.hdfcbank.com/netbanking/entry") && senderNo.contains("HDFC") && (otpText.length() == 6 || otpText.length() == 8)) {
                removeWaitingFragment();
                stopTimerTask();
                ApproveOTPFragment approveFragment = new ApproveOTPFragment();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(Rconfig.getInstance().id("otp_frame"), approveFragment, "OTPApproveFrag");
                transaction.commit();
                approveFragment.setOtpText(otpText);
            }
            // For SBI Visa credit Card
            else if (bankUrl.contains("https://secure4.arcot.com/acspage/cap") && senderNo.contains("SBI") && (otpText.length() == 6 || otpText.length() == 8)) {
                removeWaitingFragment();
                stopTimerTask();
                ApproveOTPFragment approveFragment = new ApproveOTPFragment();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(Rconfig.getInstance().id("otp_frame"), approveFragment, "OTPApproveFrag");
                transaction.commit();
                approveFragment.setOtpText(otpText);
            } else if (bankUrl.contains("https://cardsecurity.enstage.com/ACSWeb/EnrollWeb/KotakBank/server/OtpServer") && senderNo.contains("KOTAK") && (otpText.length() == 6 || otpText.length() == 8)) {
                removeWaitingFragment();
                stopTimerTask();
                ApproveOTPFragment approveFragment = new ApproveOTPFragment();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(Rconfig.getInstance().id("otp_frame"), approveFragment, "OTPApproveFrag");
                transaction.commit();
                approveFragment.setOtpText(otpText);
            } else {
                removeApprovalFragment();
                stopTimerTask();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeApprovalFragment() {
        ApproveOTPFragment approveOTPFragment = (ApproveOTPFragment) manager.findFragmentByTag("OTPApproveFrag");
        if (approveOTPFragment != null) {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.remove(approveOTPFragment);
            transaction.commit();
        }
    }

    public void loadActionDialog() {

        try {
            actionDialog.show(getFragmentManager(), "ActionDialog");
            stopTimerTask();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter intentFilter = new IntentFilter("SmsMessage.intent.MAIN");
        mIntentReceiver = new BroadcastReceiver() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onReceive(Context context, Intent intent) {

                try {
                    //removeWaitingFragment();
                    removeApprovalFragment();
                    ///////////////////////////////////////
                    String msgText = intent.getStringExtra("get_otp");
                    String otp = msgText.split("\\|")[0];
                    String senderNo = msgText.split("\\|")[1];
                    if (MyDeviceAPI >= 19) {
                        loadApproveOTP(otp, senderNo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Exception :" + e, Toast.LENGTH_SHORT).show();
                }
            }
        };
        this.registerReceiver(mIntentReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(this.mIntentReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }


    // On click of Approve button
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void respond(String otpText) {

        String data = otpText;
        try {
            // For SBI and all the associates
            if (bankUrl.contains("https://acs.onlinesbi.com/sbi/")) {
                myBrowser.evaluateJavascript("javascript:document.getElementById('otp').value = '" + otpText + "'", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {

                    }
                });
            }
            // For Kotak Bank Debit card
            else if (bankUrl.contains("https://cardsecurity.enstage.com/ACSWeb/EnrollWeb/KotakBank")) {
                myBrowser.evaluateJavascript("javascript:document.getElementById('txtOtp').value = '" + otpText + "'", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {

                    }
                });
            }
            // For SBI Visa credit card
            else if (bankUrl.contains("https://secure4.arcot.com/acspage/cap")) {
                myBrowser.evaluateJavascript("javascript:document.getElementsByName('pin1')[0].value = '" + otpText + "'", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {

                    }
                });
            }
            // For SBI and associates banks Net Banking
            else if (bankUrl.contains("https://merchant.onlinesbi.com/merchant/smsenablehighsecurity.htm") || bankUrl.contains("https://merchant.onlinesbi.com/merchant/resendsmsotp.htm") || bankUrl.contains("https://m.onlinesbi.com/mmerchant/smsenablehighsecurity.htm")
                    || bankUrl.contains("https://merchant.onlinesbh.com/merchant/smsenablehighsecurity.htm") || bankUrl.contains("https://merchant.onlinesbh.com/merchant/resendsmsotp.htm")
                    || bankUrl.contains("https://merchant.sbbjonline.com/merchant/smsenablehighsecurity.htm") || bankUrl.contains("https://merchant.sbbjonline.com/merchant/resendsmsotp.htm")
                    || bankUrl.contains("https://merchant.onlinesbm.com/merchant/smsenablehighsecurity.htm") || bankUrl.contains("https://merchant.onlinesbm.com/merchant/resendsmsotp.htm")
                    || bankUrl.contains("https://merchant.onlinesbp.com/merchant/smsenablehighsecurity.htm") || bankUrl.contains("https://merchant.onlinesbp.com/merchant/resendsmsotp.htm")
                    || bankUrl.contains("https://merchant.sbtonline.in/merchant/smsenablehighsecurity.htm") || bankUrl.contains("https://merchant.sbtonline.in/merchant/resendsmsotp.htm")) {
                myBrowser.evaluateJavascript("javascript:document.getElementsByName('securityPassword')[0].value = '" + otpText + "'", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {

                    }
                });
            }
            // For ICICI credit card
            else if (bankUrl.contains("https://www.3dsecure.icicibank.com/ACSWeb/EnrollWeb/ICICIBank/server/OtpServer")) {
                myBrowser.evaluateJavascript("javascript:document.getElementById('txtAutoOtp').value = '" + otpText + "'", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {

                    }
                });
            }
            // For ICICI bank Debit card
            else if (bankUrl.contains("https://acs.icicibank.com/acspage/cap?")) {
                myBrowser.evaluateJavascript("javascript:document.getElementById('txtAutoOtp').value = '" + otpText + "'", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {

                    }
                });
            }
            // For Citi Bank debit card
            else if (bankUrl.contains("https://www.citibank.co.in/acspage/cap_nsapi.so")) {
                myBrowser.evaluateJavascript("javascript:document.getElementsByName('otp')[0].value = '" + otpText + "'", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {

                    }
                });
            }
            // For HDFC Debit card and Credit card
            else if (bankUrl.contains("https://netsafe.hdfcbank.com/ACSWeb/jsp/dynamicAuth.jsp?transType=payerAuth")) {
                myBrowser.evaluateJavascript("javascript:document.getElementById('txtOtpPassword').value = '" + otpText + "'", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {

                    }
                });
            }
            // HDFC Net Banking
            else if (bankUrl.contains("https://netbanking.hdfcbank.com/netbanking/entry")) {
                myBrowser.evaluateJavascript("javascript:document.getElementsByName('fldOtpToken')[0].value = '" + otpText + "'", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {

                    }
                });
            }
            // For Kotak Band visa Credit Card
            else if (bankUrl.contains("https://cardsecurity.enstage.com/ACSWeb/EnrollWeb/KotakBank/server/OtpServer")) {
                myBrowser.evaluateJavascript("javascript:document.getElementById('otpValue').value = '" + otpText + "'", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {

                    }
                });
            }
            // for CITI Bank Authenticate with option selection
            if (data.equals("password")) {
                myBrowser.evaluateJavascript("javascript:document.getElementById('uid_tb_r').click();", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {

                    }
                });
            }
            if (data.equals("smsOtp")) {
                myBrowser.evaluateJavascript("javascript:document.getElementById('otp_tb_r').click();", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {

                    }
                });
                loadWaitingFragment("cityBankAuthPage");
            }
            loadCounter++;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void actionSelected(String data) {
        try {
            if (data.equals("ResendOTP")) {
                stopTimerTask();
                removeWaitingFragment();
                if (bankUrl.contains("https://cardsecurity.enstage.com/ACSWeb/EnrollWeb/KotakBank")) {
                    myBrowser.evaluateJavascript("javascript:reSendOtp();", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {

                        }
                    });
                }
                // For HDFC Credit and Debit Card
                else if (bankUrl.contains("https://netsafe.hdfcbank.com/ACSWeb/jsp/dynamicAuth.jsp?transType=payerAuth")) {
                    myBrowser.evaluateJavascript("javascript:generateOTP();", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {

                        }
                    });
                }
                // SBI Visa Credit Card
                else if (bankUrl.contains("https://secure4.arcot.com/acspage/cap")) {
                    myBrowser.evaluateJavascript("javascript:OnSubmitHandlerResend();", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {

                        }
                    });
                }
                // For Kotak Visa Credit Card
                else if (bankUrl.contains("https://cardsecurity.enstage.com/ACSWeb/EnrollWeb/KotakBank/server/OtpServer")) {
                    myBrowser.evaluateJavascript("javascript:doSendOTP();", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {

                        }
                    });
                }
                // For ICICI Credit Card
                else if (bankUrl.contains("https://www.3dsecure.icicibank.com/ACSWeb/EnrollWeb/ICICIBank/server/OtpServer")) {
                    myBrowser.evaluateJavascript("javascript:resend_otp();", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {
                        }
                    });
                } else {
                    myBrowser.evaluateJavascript("javascript:resendOTP();", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {

                        }
                    });
                }
                //loadCounter=0;
            } else if (data.equals("EnterOTPManually")) {
                stopTimerTask();
                removeWaitingFragment();
            } else if (data.equals("Cancel")) {
                stopTimerTask();
                removeWaitingFragment();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Action not available for this Payment Option !", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setMessage(
                        Config.getInstance()
                                .getText(
                                        "Are you sure that you want to cancel the order?"))
                .setPositiveButton(Config.getInstance().getText("Yes"),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                String status = "Your order has been canceled!";
                                updatePayment("2", status);;
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
}
