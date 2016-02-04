package com.simicart.plugins.payuindia.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.payu.india.Model.PaymentParams;
import com.payu.india.Model.PayuConfig;
import com.payu.india.Model.PayuHashes;
import com.payu.india.Model.PostData;
import com.payu.india.Payu.PayuConstants;
import com.payu.india.Payu.PayuErrors;
import com.payu.india.Payu.PayuUtils;
import com.payu.india.PostParams.PaymentPostParams;
import com.simicart.core.config.Rconfig;


public class PayUCreditDebitCardActivity extends Activity implements View.OnClickListener {

    private Button payNowButton;
    private EditText cardNameEditText;
    private EditText cardNumberEditText;
    private EditText cardCvvEditText;
    private EditText cardExpiryMonthEditText;
    private EditText cardExpiryYearEditText;
    private Bundle bundle;
    private CheckBox saveCardCheckBox;

    private String cardName;
    private String cardNumber;
    private String cvv;
    private String expiryMonth;
    private String expiryYear;

    private PayuHashes mPayuHashes;
    private PaymentParams mPaymentParams;
    private PostData postData;
    private Toolbar toolbar;

    private TextView amountTextView;
    private TextView transactionIdTextView;
    private PayuConfig payuConfig;

    private PayuUtils payuUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Rconfig.getInstance().layout("plugins_payu_india_activity_card"));

        // todo lets set the toolbar
            /*toolbar = (Toolbar) findViewById(Rconfig.getInstance().id.app_bar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);*/

        (payNowButton = (Button) findViewById(Rconfig.getInstance().id("button_card_make_payment"))).setOnClickListener(this);

        cardNameEditText = (EditText) findViewById(Rconfig.getInstance().id("edit_text_name_on_card"));
        cardNumberEditText = (EditText) findViewById(Rconfig.getInstance().id("edit_text_card_number"));
        cardCvvEditText = (EditText) findViewById(Rconfig.getInstance().id("edit_text_card_cvv"));
        cardExpiryMonthEditText = (EditText) findViewById(Rconfig.getInstance().id("edit_text_expiry_month"));
        cardExpiryYearEditText = (EditText) findViewById(Rconfig.getInstance().id("edit_text_expiry_year"));
        saveCardCheckBox = (CheckBox) findViewById(Rconfig.getInstance().id("check_box_save_card"));

        bundle = getIntent().getExtras();


        // lets get payment default params and hashes
        mPayuHashes = bundle.getParcelable(PayuConstants.PAYU_HASHES);
        mPaymentParams = bundle.getParcelable(PayuConstants.PAYMENT_PARAMS);
        payuConfig = bundle.getParcelable(PayuConstants.PAYU_CONFIG);
        payuConfig = null != payuConfig ? payuConfig : new PayuConfig();

        (amountTextView = (TextView) findViewById(Rconfig.getInstance().id("text_view_amount"))).setText(PayuConstants.AMOUNT + ": " + mPaymentParams.getAmount());
        (transactionIdTextView = (TextView) findViewById(Rconfig.getInstance().id("text_view_transaction_id"))).setText(PayuConstants.TXNID + ": " + mPaymentParams.getTxnId());

        // lets not show the save card check box if user credentials is not found!
        if (null == mPaymentParams.getUserCredentials())
            saveCardCheckBox.setVisibility(View.GONE);
        else
            saveCardCheckBox.setVisibility(View.VISIBLE);

        payuUtils = new PayuUtils();


        cardNumberEditText.addTextChangedListener(new TextWatcher() {
            String issuer;
            Drawable issuerDrawable;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 5) { // to confirm rupay card we need min 6 digit.
                    if (null == issuer) issuer = payuUtils.getIssuer(charSequence.toString());
                    if (issuer != null && issuer.length() > 1 && issuerDrawable == null) {
                        issuerDrawable = getIssuerDrawable(issuer);
                        if (issuer.contentEquals(PayuConstants.SMAE)) { // hide cvv and expiry
                            cardExpiryMonthEditText.setVisibility(View.GONE);
                            cardExpiryYearEditText.setVisibility(View.GONE);
                            cardCvvEditText.setVisibility(View.GONE);
                        } else { //show cvv and expiry
                            cardExpiryMonthEditText.setVisibility(View.VISIBLE);
                            cardExpiryYearEditText.setVisibility(View.VISIBLE);
                            cardCvvEditText.setVisibility(View.VISIBLE);
                        }
                    }
                } else {
                    issuer = null;
                    issuerDrawable = null;
                }
                cardNumberEditText.setCompoundDrawablesWithIntrinsicBounds(null, null, issuerDrawable, null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        // Oh crap! Resource IDs cannot be used in a switch statement in Android library modules less... (Ctrl+F1)
        // Validates using resource IDs in a switch statement in Android library module
        // we cant not use switch and gotta use simple if else
        if (v.getId() == Rconfig.getInstance().id("button_card_make_payment")) {

            // do i have to store the card
            if (saveCardCheckBox.isChecked()) {
                mPaymentParams.setStoreCard(1);
            } else {
                mPaymentParams.setStoreCard(0);
            }
            // setup the hash
            mPaymentParams.setHash(mPayuHashes.getPaymentHash());

            // lets try to get the post params

            postData = null;
            // lets get the current card number;
            cardNumber = String.valueOf(cardNumberEditText.getText());
            cardName = cardNameEditText.getText().toString();
            expiryMonth = cardExpiryMonthEditText.getText().toString();
            expiryYear = cardExpiryYearEditText.getText().toString();
            cvv = cardCvvEditText.getText().toString();

            // lets not worry about ui validations.
            mPaymentParams.setCardNumber(cardNumber);
            mPaymentParams.setCardName(cardName);
            mPaymentParams.setNameOnCard(cardName);
            mPaymentParams.setExpiryMonth(expiryMonth);
            mPaymentParams.setExpiryYear(expiryYear);
            mPaymentParams.setCvv(cvv);
            postData = new PaymentPostParams(mPaymentParams, PayuConstants.CC).getPaymentPostParams();
            if (postData.getCode() == PayuErrors.NO_ERROR) {
                // okay good to go.. lets make a transaction
                // launch webview
                payuConfig.setData(postData.getResult());
                Intent intent = new Intent(this, PaymentsActivity.class);
                intent.putExtra(PayuConstants.PAYU_CONFIG, payuConfig);
                startActivityForResult(intent, PayuConstants.PAYU_REQUEST_CODE);
            } else {
                Toast.makeText(this, postData.getResult(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, postData.getResult(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PayuConstants.PAYU_REQUEST_CODE) {
            setResult(resultCode, data);
            finish();
        }
    }

    private Drawable getIssuerDrawable(String issuer) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            switch (issuer) {
                case PayuConstants.VISA:
                    return getResources().getDrawable(Rconfig.getInstance().drawable("plugins_payu_india_visa"));
                case PayuConstants.LASER:
                    return getResources().getDrawable(Rconfig.getInstance().drawable("plugins_payu_india_laser"));
                case PayuConstants.DISCOVER:
                    return getResources().getDrawable(Rconfig.getInstance().drawable("plugins_payu_india_discover"));
                case PayuConstants.MAES:
                    return getResources().getDrawable(Rconfig.getInstance().drawable("plugins_payu_india_maestro"));
                case PayuConstants.MAST:
                    return getResources().getDrawable(Rconfig.getInstance().drawable("plugins_payu_india_master"));
                case PayuConstants.AMEX:
                    return getResources().getDrawable(Rconfig.getInstance().drawable("plugins_payu_india_amex"));
                case PayuConstants.DINR:
                    return getResources().getDrawable(Rconfig.getInstance().drawable("plugins_payu_india_diner"));
                case PayuConstants.JCB:
                    return getResources().getDrawable(Rconfig.getInstance().drawable("jcb"));
                case PayuConstants.SMAE:
                    return getResources().getDrawable(Rconfig.getInstance().drawable("plugins_payu_india_maestro"));
                case PayuConstants.RUPAY:
                    return getResources().getDrawable(Rconfig.getInstance().drawable("plugins_payu_india_rupay"));
            }
            return null;
        } else {

            switch (issuer) {
                case PayuConstants.VISA:
                    return getResources().getDrawable(Rconfig.getInstance().drawable("plugins_payu_india_visa"), null);
                case PayuConstants.LASER:
                    return getResources().getDrawable(Rconfig.getInstance().drawable("plugins_payu_india_laser"), null);
                case PayuConstants.DISCOVER:
                    return getResources().getDrawable(Rconfig.getInstance().drawable("plugins_payu_india_discover"), null);
                case PayuConstants.MAES:
                    return getResources().getDrawable(Rconfig.getInstance().drawable("plugins_payu_india_maestro"), null);
                case PayuConstants.MAST:
                    return getResources().getDrawable(Rconfig.getInstance().drawable("plugins_payu_india_master"), null);
                case PayuConstants.AMEX:
                    return getResources().getDrawable(Rconfig.getInstance().drawable("plugins_payu_india_amex"), null);
                case PayuConstants.DINR:
                    return getResources().getDrawable(Rconfig.getInstance().drawable("plugins_payu_india_diner"), null);
                case PayuConstants.JCB:
                    return getResources().getDrawable(Rconfig.getInstance().drawable("jcb"), null);
                case PayuConstants.SMAE:
                    return getResources().getDrawable(Rconfig.getInstance().drawable("plugins_payu_india_maestro"), null);
                case PayuConstants.RUPAY:
                    return getResources().getDrawable(Rconfig.getInstance().drawable("plugins_payu_india_rupay"), null);
            }
            return null;
        }
    }
}
