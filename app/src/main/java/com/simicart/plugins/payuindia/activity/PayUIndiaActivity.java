package com.simicart.plugins.payuindia.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.payu.india.Extras.PayUChecksum;
import com.payu.india.Extras.PayUSdkDetails;
import com.payu.india.Model.PaymentParams;
import com.payu.india.Model.PayuConfig;
import com.payu.india.Model.PayuHashes;
import com.payu.india.Model.PostData;
import com.payu.india.Payu.PayuConstants;
import com.payu.india.Payu.PayuErrors;
import com.simicart.MainActivity;
import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.delegate.SimiDelegate;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.plugins.payuindia.entity.DataEntity;
import com.simicart.plugins.payuindia.entity.HashEntity;
import com.simicart.plugins.payuindia.entity.PayUEntity;
import com.simicart.plugins.payuindia.model.GetHashModel;
import com.simicart.plugins.payuindia.model.UpdatePaymentModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Sony on 1/27/2016.
 */
public class PayUIndiaActivity extends Activity {
    int merchantIndex = 0;
    int env = PayuConstants.PRODUCTION_ENV;
    //    String merchantTestKeys[] = {"gtKFFx", "gtKFFx"};
    String merchantTestKeys[] = {"Sw0m1f", "Sw0m1f"};
    String merchantTestSalts[] = {"eCwWELxi", "eCwWELxi"};

    String merchantProductionKeys[] = {"0MQaQP", "smsplus"};
    String merchantProductionSalts[] = {"13p0PXZk", "1b1b0",};

    String offerKeys[] = {"test123@6622", "offer_test@ffer_t5172", "offerfranklin@6636"};
    String merchantKey = env == PayuConstants.PRODUCTION_ENV ? merchantProductionKeys[merchantIndex] : merchantTestKeys[merchantIndex];
    //    String merchantSalt = env == PayuConstants.PRODUCTION_ENV ? merchantProductionSalts[merchantIndex] : merchantTestSalts[merchantIndex];
    String mandatoryKeys[] = {PayuConstants.KEY, PayuConstants.AMOUNT, PayuConstants.PRODUCT_INFO, PayuConstants.FIRST_NAME, PayuConstants.EMAIL, PayuConstants.TXNID, PayuConstants.SURL, PayuConstants.FURL, PayuConstants.USER_CREDENTIALS, PayuConstants.UDF1, PayuConstants.UDF2, PayuConstants.UDF3, PayuConstants.UDF4, PayuConstants.UDF5, PayuConstants.ENV};
    String mandatoryValues[] = {merchantKey, "10.0", "myproduct", "firstname", "me@itsmeonly.com", "" + System.currentTimeMillis(), "https://payu.herokuapp.com/success", "https://payu.herokuapp.com/failure", merchantKey + ":payutest@payu.in", "udf1", "udf2", "udf3", "udf4", "udf5", "" + env};
    private PayUChecksum checksum;
    private PostData postData;
    private String key;
    private String salt;
    private String var1;
    private Intent intent;
    private PaymentParams mPaymentParams;
    private PayuConfig payuConfig;
    private String cardBin;
    View rootView;
    Intent mainIntent;
    SimiDelegate mDelegate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = new LinearLayout(getApplicationContext());
        setContentView(rootView, new ViewGroup.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        mDelegate = new SimiBlock(rootView, MainActivity.context);
        PayUSdkDetails payUSdkDetails = new PayUSdkDetails();
        Log.e("PayUIndiaActivity", "Build No: " + payUSdkDetails.getSdkBuildNumber() + "\n Build Type: " + payUSdkDetails.getSdkBuildType() + " \n Build Flavor: " + payUSdkDetails.getSdkFlavor() + "\n Application Id: " + payUSdkDetails.getSdkApplicationId() + "\n Version Code: " + payUSdkDetails.getSdkVersionCode() + "\n Version Name: " + payUSdkDetails.getSdkVersionName());
        mainIntent = getIntent();
        if (Utils.validateString(mainIntent.getStringExtra("EXTRA_ORDERID"))) {
            requestGetHash(mainIntent.getStringExtra("EXTRA_ORDERID"));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PayuConstants.PAYU_REQUEST_CODE) {
            if (data != null) {
                Log.e("PayUIndiaActivity", "Result: " + data.getStringExtra("result"));
                HashMap<String, String> hashMapRespone = new HashMap<>();
                String dataRespone = data.getStringExtra("result");
                if (Utils.validateString(dataRespone)) {
                    String[] items = dataRespone.split(",");
                    for (String item : items) {
                        if (item.contains("=")) {
                            String[] dataItem = item.split("=");
                            String key = "";
                            String value = "";
                            if (dataItem != null && dataItem.length >= 1) {
                                key = dataItem[0].trim();
                            }
                            if (dataItem != null && dataItem.length >= 2) {
                                value = dataItem[1].trim();
                            }
                            hashMapRespone.put(key, value);
                        }
                    }
                }
                String status = "";
                String txn_id = "";
                if (!hashMapRespone.isEmpty()) {
                    if (hashMapRespone.get("status") != null) {
                        status = hashMapRespone.get("status");
                    }
                    if (hashMapRespone.get("txnid") != null) {
                        txn_id = hashMapRespone.get("txnid");
                    }
                }
                if (Utils.validateString(status) && Utils.validateString(txn_id)) {
                    if (status.equals("success")) {
                        requestUpdatePayment(mainIntent.getStringExtra("EXTRA_ORDERID"), txn_id, "1", "Thank you for your purchase!");
                    } else if (status.equals("failure")) {
                        requestUpdatePayment(mainIntent.getStringExtra("EXTRA_ORDERID"), txn_id, "0", "Transaction Declined!");
                    } else {
                        requestUpdatePayment(mainIntent.getStringExtra("EXTRA_ORDERID"), txn_id, "0", "Transaction Error!");
                    }
                }else{
                    requestUpdatePayment(mainIntent.getStringExtra("EXTRA_ORDERID"), txn_id, "0", "Transaction Error!");
                }
            } else {
                String txnID = mPaymentParams.getTxnId();
                if(Utils.validateString(txnID)){
                    requestUpdatePayment(mainIntent.getStringExtra("EXTRA_ORDERID"), txnID, "0", "Your order has been canceled");
                }
            }
        }
    }

    private void requestGetHash(String orderID) {
        mDelegate.showLoading();
        final GetHashModel model = new GetHashModel();
        model.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                mDelegate.dismissLoading();
                if (error != null) {
                    changeView(error.getMessage());
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
//                mDelegate.dismissLoading();
                if (collection != null && collection.getCollection().size() > 0) {
                    PayUEntity payUEntity = (PayUEntity) collection.getCollection().get(0);
                    HashEntity hashEntity = payUEntity.getHash();
                    DataEntity dataEntity = payUEntity.getData();
                    intent = new Intent(PayUIndiaActivity.this, PayUBaseActivity.class);
                    mPaymentParams = new PaymentParams();
                    payuConfig = new PayuConfig();
                    if(payUEntity != null) {
                        if (Utils.validateString(dataEntity.getKey())) {
                            merchantKey = dataEntity.getKey();
                        }
                        Log.e("PayUIndiaActivity", "MerchantKey: " + merchantKey);
                        mPaymentParams.setKey(merchantKey);
                        key = merchantKey;
                        mPaymentParams.setAmount(dataEntity.getAmount());
                        mPaymentParams.setProductInfo(dataEntity.getProductInfo());
                        mPaymentParams.setFirstName(dataEntity.getFirstName());
                        mPaymentParams.setEmail(dataEntity.getEmail());
                        mPaymentParams.setTxnId(dataEntity.getTXNID());
                        mPaymentParams.setSurl(dataEntity.getsURL());
                        mPaymentParams.setFurl(dataEntity.getfURL());
                        if(Utils.validateString(dataEntity.getUdf1())){
                            mPaymentParams.setUdf1(dataEntity.getUdf1());
                        }else{
                            mPaymentParams.setUdf1("");
                        }

                        if(Utils.validateString(dataEntity.getUdf2())){
                            mPaymentParams.setUdf2(dataEntity.getUdf2());
                        }else{
                            mPaymentParams.setUdf2("");
                        }

                        if(Utils.validateString(dataEntity.getUdf3())){
                            mPaymentParams.setUdf3(dataEntity.getUdf3());
                        }else{
                            mPaymentParams.setUdf3("");
                        }

                        if(Utils.validateString(dataEntity.getUdf4())){
                            mPaymentParams.setUdf4(dataEntity.getUdf4());
                        }else{
                            mPaymentParams.setUdf4("");
                        }

                        if(Utils.validateString(dataEntity.getUdf5())){
                            mPaymentParams.setUdf5(dataEntity.getUdf5());
                        }else{
                            mPaymentParams.setUdf5("");
                        }

                        mPaymentParams.setUserCredentials(dataEntity.getUserCredential());
                        var1 = dataEntity.getUserCredential();
                        salt = null;
//                        if (Utils.validateString(model.getSalt())) {
//                            salt = model.getSalt();
//                        }
                        intent.putExtra(PayuConstants.SALT, salt);
                        if(Utils.validateString(dataEntity.getOfferKey())){
                            mPaymentParams.setOfferKey(dataEntity.getOfferKey());
                        }else{
                            mPaymentParams.setOfferKey("");
                        }

                        if(dataEntity.isSanBox()){
                            env = PayuConstants.MOBILE_DEV_ENV;
                        }

                        String environment = "" + env;
                        if(Utils.validateString(dataEntity.getCardBind())){
                            cardBin = dataEntity.getCardBind();
                        }else{
                            cardBin = "";
                        }

                        payuConfig.setEnvironment(environment.contentEquals("" + PayuConstants.PRODUCTION_ENV) ? PayuConstants.PRODUCTION_ENV : PayuConstants.MOBILE_STAGING_ENV);
                    }
                    PayuHashes payuHashes = new PayuHashes();
                    if (hashEntity != null) {
                        if (Utils.validateString(hashEntity.getPaymentHash())) {
                            payuHashes.setPaymentHash(hashEntity.getPaymentHash());
                        }
                        if (Utils.validateString(hashEntity.getGetPaymentIBiBo())) {
                            payuHashes.setMerchantIbiboCodesHash(hashEntity.getGetPaymentIBiBo());
                        }
                        if (Utils.validateString(hashEntity.getVasForMobile())) {
                            payuHashes.setVasForMobileSdkHash(hashEntity.getVasForMobile());
                        }
                        if (Utils.validateString(hashEntity.getPaymentRelated())) {
                            payuHashes.setPaymentRelatedDetailsForMobileSdkHash(hashEntity.getPaymentRelated());
                        }
                        if (Utils.validateString(hashEntity.getDeleteUser())) {
                            payuHashes.setDeleteCardHash(hashEntity.getDeleteUser());
                        }
                        if (Utils.validateString(hashEntity.getGetUser())) {
                            payuHashes.setStoredCardsHash(hashEntity.getGetUser());
                        }
                        if (Utils.validateString(hashEntity.getEditUser())) {
                            payuHashes.setEditCardHash(hashEntity.getEditUser());
                        }
                        if (Utils.validateString(hashEntity.getSaveUser())) {
                            payuHashes.setSaveCardHash(hashEntity.getSaveUser());
                        }

                        launchSdkUI(payuHashes);
                    }
//                    if (null == salt) generateHashFromServer(mPaymentParams);
//                    else generateHashFromSDK(mPaymentParams, intent.getStringExtra(PayuConstants.SALT));
                }
            }
        });
        model.addDataBody("order_id", orderID);
        model.addDataExtendURL("hash");

        model.request();
    }

    private void requestUpdatePayment(String orderID, String txn_id, String status, final String message) {
        UpdatePaymentModel updatePaymentModel = new UpdatePaymentModel();
        updatePaymentModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if (error != null) {
                    changeView(error.getMessage());
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                changeView(message);
            }
        });
        updatePaymentModel.addDataExtendURL("update-payment");
        updatePaymentModel.addDataBody("order_id", orderID);
        updatePaymentModel.addDataBody("txn_id", txn_id);
        updatePaymentModel.addDataBody("status", status);
        updatePaymentModel.request();
    }

    public void generateHashFromServer(PaymentParams mPaymentParams) {
        StringBuffer postParamsBuffer = new StringBuffer();
        postParamsBuffer.append(concatParams(PayuConstants.KEY, mPaymentParams.getKey()));
        postParamsBuffer.append(concatParams(PayuConstants.AMOUNT, mPaymentParams.getAmount()));
        postParamsBuffer.append(concatParams(PayuConstants.TXNID, mPaymentParams.getTxnId()));
        postParamsBuffer.append(concatParams(PayuConstants.EMAIL, null == mPaymentParams.getEmail() ? "" : mPaymentParams.getEmail()));
        postParamsBuffer.append(concatParams(PayuConstants.PRODUCT_INFO, mPaymentParams.getProductInfo()));
        postParamsBuffer.append(concatParams(PayuConstants.FIRST_NAME, null == mPaymentParams.getFirstName() ? "" : mPaymentParams.getFirstName()));
        postParamsBuffer.append(concatParams(PayuConstants.UDF1, mPaymentParams.getUdf1() == null ? "" : mPaymentParams.getUdf1()));
        postParamsBuffer.append(concatParams(PayuConstants.UDF2, mPaymentParams.getUdf2() == null ? "" : mPaymentParams.getUdf2()));
        postParamsBuffer.append(concatParams(PayuConstants.UDF3, mPaymentParams.getUdf3() == null ? "" : mPaymentParams.getUdf3()));
        postParamsBuffer.append(concatParams(PayuConstants.UDF4, mPaymentParams.getUdf4() == null ? "" : mPaymentParams.getUdf4()));
        postParamsBuffer.append(concatParams(PayuConstants.UDF5, mPaymentParams.getUdf5() == null ? "" : mPaymentParams.getUdf5()));
        postParamsBuffer.append(concatParams(PayuConstants.USER_CREDENTIALS, mPaymentParams.getUserCredentials() == null ? PayuConstants.DEFAULT : mPaymentParams.getUserCredentials()));
        // for offer_key
        if (null != mPaymentParams.getOfferKey())
            postParamsBuffer.append(concatParams(PayuConstants.OFFER_KEY, mPaymentParams.getOfferKey()));
        // for check_isDomestic
        if (null != cardBin)
            postParamsBuffer.append(concatParams("card_bin", cardBin));

        String postParams = postParamsBuffer.charAt(postParamsBuffer.length() - 1) == '&' ? postParamsBuffer.substring(0, postParamsBuffer.length() - 1).toString() : postParamsBuffer.toString();
        // make api call
        GetHashesFromServerTask getHashesFromServerTask = new GetHashesFromServerTask();
        getHashesFromServerTask.execute(postParams);
    }

    protected String concatParams(String key, String value) {
        return key + "=" + value + "&";
    }

    class GetHashesFromServerTask extends AsyncTask<String, String, PayuHashes> {

        @Override
        protected PayuHashes doInBackground(String... postParams) {
            PayuHashes payuHashes = new PayuHashes();
            try {
//                URL url = new URL(PayuConstants.MOBILE_TEST_FETCH_DATA_URL);
//                        URL url = new URL("http://10.100.81.49:80/merchant/postservice?form=2");;

                URL url = new URL("https://payu.herokuapp.com/get_hash");

                // get the payuConfig first
                String postParam = postParams[0];

                byte[] postParamsByte = postParam.getBytes("UTF-8");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("Content-Length", String.valueOf(postParamsByte.length));
                conn.setDoOutput(true);
                conn.getOutputStream().write(postParamsByte);

                InputStream responseInputStream = conn.getInputStream();
                StringBuffer responseStringBuffer = new StringBuffer();
                byte[] byteContainer = new byte[1024];
                for (int i; (i = responseInputStream.read(byteContainer)) != -1; ) {
                    responseStringBuffer.append(new String(byteContainer, 0, i));
                }

                Log.e("PayUIndiaActivity", responseStringBuffer.toString());

                JSONObject response = new JSONObject(responseStringBuffer.toString());

                Iterator<String> payuHashIterator = response.keys();
                while (payuHashIterator.hasNext()) {
                    String key = payuHashIterator.next();
                    switch (key) {
                        case "payment_hash":
                            payuHashes.setPaymentHash(response.getString(key));
                            break;
                        case "get_merchant_ibibo_codes_hash": //
                            payuHashes.setMerchantIbiboCodesHash(response.getString(key));
                            break;
                        case "vas_for_mobile_sdk_hash":
                            payuHashes.setVasForMobileSdkHash(response.getString(key));
                            break;
                        case "payment_related_details_for_mobile_sdk_hash":
                            payuHashes.setPaymentRelatedDetailsForMobileSdkHash(response.getString(key));
                            break;
                        case "delete_user_card_hash":
                            payuHashes.setDeleteCardHash(response.getString(key));
                            break;
                        case "get_user_cards_hash":
                            payuHashes.setStoredCardsHash(response.getString(key));
                            break;
                        case "edit_user_card_hash":
                            payuHashes.setEditCardHash(response.getString(key));
                            break;
                        case "save_user_card_hash":
                            payuHashes.setSaveCardHash(response.getString(key));
                            break;
                        case "check_offer_status_hash":
                            payuHashes.setCheckOfferStatusHash(response.getString(key));
                            break;
                        case "check_isDomestic_hash":
                            payuHashes.setCheckIsDomesticHash(response.getString(key));
                            break;
                        default:
                            break;
                    }
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return payuHashes;
        }

        @Override
        protected void onPostExecute(PayuHashes payuHashes) {
            super.onPostExecute(payuHashes);
            launchSdkUI(payuHashes);
        }
    }

    public void generateHashFromSDK(PaymentParams mPaymentParams, String Salt) {
        PayuHashes payuHashes = new PayuHashes();
        postData = new PostData();

        // payment Hash;
        checksum = null;
        checksum = new PayUChecksum();
        checksum.setAmount(mPaymentParams.getAmount());
        checksum.setKey(mPaymentParams.getKey());
        checksum.setTxnid(mPaymentParams.getTxnId());
        checksum.setEmail(mPaymentParams.getEmail());
        checksum.setSalt(salt);
        checksum.setProductinfo(mPaymentParams.getProductInfo());
        checksum.setFirstname(mPaymentParams.getFirstName());
        checksum.setUdf1(mPaymentParams.getUdf1());
        checksum.setUdf2(mPaymentParams.getUdf2());
        checksum.setUdf3(mPaymentParams.getUdf3());
        checksum.setUdf4(mPaymentParams.getUdf4());
        checksum.setUdf5(mPaymentParams.getUdf5());

        postData = checksum.getHash();
        if (postData.getCode() == PayuErrors.NO_ERROR) {
            payuHashes.setPaymentHash(postData.getResult());
        }

        // checksum for payemnt related details
        // var1 should be either user credentials or default
        var1 = var1 == null ? PayuConstants.DEFAULT : var1;

        if ((postData = calculateHash(key, PayuConstants.PAYMENT_RELATED_DETAILS_FOR_MOBILE_SDK, var1, salt)) != null && postData.getCode() == PayuErrors.NO_ERROR) // Assign post data first then check for success
            payuHashes.setPaymentRelatedDetailsForMobileSdkHash(postData.getResult());
        //vas
        if ((postData = calculateHash(key, PayuConstants.VAS_FOR_MOBILE_SDK, PayuConstants.DEFAULT, salt)) != null && postData.getCode() == PayuErrors.NO_ERROR)
            payuHashes.setVasForMobileSdkHash(postData.getResult());

        // getIbibocodes
        if ((postData = calculateHash(key, PayuConstants.GET_MERCHANT_IBIBO_CODES, PayuConstants.DEFAULT, salt)) != null && postData.getCode() == PayuErrors.NO_ERROR)
            payuHashes.setMerchantIbiboCodesHash(postData.getResult());

        if (!var1.contentEquals(PayuConstants.DEFAULT)) {
            // get user card
            if ((postData = calculateHash(key, PayuConstants.GET_USER_CARDS, var1, salt)) != null && postData.getCode() == PayuErrors.NO_ERROR) // todo rename storedc ard
                payuHashes.setStoredCardsHash(postData.getResult());
            // save user card
            if ((postData = calculateHash(key, PayuConstants.SAVE_USER_CARD, var1, salt)) != null && postData.getCode() == PayuErrors.NO_ERROR)
                payuHashes.setSaveCardHash(postData.getResult());
            // delete user card
            if ((postData = calculateHash(key, PayuConstants.DELETE_USER_CARD, var1, salt)) != null && postData.getCode() == PayuErrors.NO_ERROR)
                payuHashes.setDeleteCardHash(postData.getResult());
            // edit user card
            if ((postData = calculateHash(key, PayuConstants.EDIT_USER_CARD, var1, salt)) != null && postData.getCode() == PayuErrors.NO_ERROR)
                payuHashes.setEditCardHash(postData.getResult());
        }

        if (mPaymentParams.getOfferKey() != null) {
            postData = calculateHash(key, PayuConstants.OFFER_KEY, mPaymentParams.getOfferKey(), salt);
            if (postData.getCode() == PayuErrors.NO_ERROR) {
                payuHashes.setCheckOfferStatusHash(postData.getResult());
            }
        }

        if (mPaymentParams.getOfferKey() != null && (postData = calculateHash(key, PayuConstants.CHECK_OFFER_STATUS, mPaymentParams.getOfferKey(), salt)) != null && postData.getCode() == PayuErrors.NO_ERROR) {
            payuHashes.setCheckOfferStatusHash(postData.getResult());
        }

        // we have generated all the hases now lest launch sdk's ui
        launchSdkUI(payuHashes);
    }

    // deprecated, should be used only for testing.
    private PostData calculateHash(String key, String command, String var1, String salt) {
        checksum = null;
        checksum = new PayUChecksum();
        checksum.setKey(key);
        checksum.setCommand(command);
        checksum.setVar1(var1);
        checksum.setSalt(salt);
        return checksum.getHash();
    }

    public void launchSdkUI(PayuHashes payuHashes) {
        intent.putExtra(PayuConstants.PAYU_CONFIG, payuConfig);
//        intent.putExtra(PayuConstants.PAYMENT_DEFAULT_PARAMS, mPaymentParams);
        intent.putExtra(PayuConstants.PAYMENT_PARAMS, mPaymentParams);
        intent.putExtra(PayuConstants.PAYU_HASHES, payuHashes);
        intent.putExtra(PayuConstants.SALT, salt);
        startActivityForResult(intent, PayuConstants.PAYU_REQUEST_CODE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        changeView("Your order has been canceled");
    }

    public void changeView(String message) {
        Toast toast = Toast.makeText(MainActivity.context, Config.getInstance()
                .getText(message), Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(10000);
        toast.show();
        Intent i = new Intent(PayUIndiaActivity.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }
}
