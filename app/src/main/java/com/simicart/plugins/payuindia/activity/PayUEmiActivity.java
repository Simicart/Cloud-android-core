package com.simicart.plugins.payuindia.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.payu.india.Model.Emi;
import com.payu.india.Model.PaymentParams;
import com.payu.india.Model.PayuConfig;
import com.payu.india.Model.PayuHashes;
import com.payu.india.Model.PostData;
import com.payu.india.Payu.PayuConstants;
import com.payu.india.Payu.PayuErrors;
import com.payu.india.PostParams.PaymentPostParams;
import com.simicart.core.config.Rconfig;

import java.util.ArrayList;


public class PayUEmiActivity extends Activity implements View.OnClickListener{

    private Spinner bankNameSpinner;
    private Spinner emiDurationSpinner;
    private SpinnerAdapter emiNameAdapter;
    private SpinnerAdapter emiDurationAdapter;
    private Bundle bundle;
    private Button emiPayNowButton;
    private Emi selectedEmi;

    private EditText cardNumberEditText;
    private EditText nameOnCardEditText;
    private EditText cvvEditText;
    private EditText expiryMonthEditText;
    private EditText expiryYearEditText;

    private ArrayList<Emi> emiArrayList;

    private PaymentParams mPaymentParams;
    private PayuHashes mPayuHashes;
    private Toolbar toolbar;
    private TextView amountTextView;
    private TextView transactionIdTextView;

    private PayuConfig payuConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Rconfig.getInstance().layout("plugins_payu_india_activity_emi"));

        // Todo lets set the toolbar
        /*toolbar = (Toolbar) findViewById(Rconfig.getInstance().id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/
        
        bundle = getIntent().getExtras();

        bankNameSpinner = (Spinner) findViewById(Rconfig.getInstance().id("spinner_emi_bank_name"));
        emiDurationSpinner = (Spinner) findViewById(Rconfig.getInstance().id("spinner_emi_duration"));
        cardNumberEditText = (EditText) findViewById(Rconfig.getInstance().id("edit_text_emi_card_number"));
        nameOnCardEditText = (EditText) findViewById(Rconfig.getInstance().id("edit_text_emi_name_on_card"));
        cvvEditText = (EditText) findViewById(Rconfig.getInstance().id("edit_text_emi_cvv"));
        expiryMonthEditText = (EditText) findViewById(Rconfig.getInstance().id("edit_text_emi_expiry_month"));
        expiryYearEditText = (EditText) findViewById(Rconfig.getInstance().id("edit_text_emi_expiry_year"));


        // lets set the paymentdefault params and payu hashes;
        mPaymentParams = bundle.getParcelable(PayuConstants.PAYMENT_PARAMS);
        mPayuHashes = bundle.getParcelable(PayuConstants.PAYU_HASHES);
        payuConfig = bundle.getParcelable(PayuConstants.PAYU_CONFIG);
        payuConfig = null != payuConfig ? payuConfig : new PayuConfig();

        // sethash
        mPaymentParams.setHash(mPayuHashes.getPaymentHash());

        (emiPayNowButton = (Button) findViewById(Rconfig.getInstance().id("button_emi_pay_now"))).setOnClickListener(this);
        (amountTextView = (TextView) findViewById(Rconfig.getInstance().id("text_view_amount"))).setText(PayuConstants.AMOUNT + ": " + mPaymentParams.getAmount());
        (transactionIdTextView = (TextView) findViewById(Rconfig.getInstance().id("text_view_transaction_id"))).setText(PayuConstants.TXNID + ": " + mPaymentParams.getTxnId());
        
        if(bundle.getParcelableArrayList(PayuConstants.EMI) != null){
            // okay we have emi now!
            // lets setup emi name adapter.
            emiArrayList = bundle.getParcelableArrayList(PayuConstants.EMI);
            emiNameAdapter = new PayUEmiNameAdapter(this, emiArrayList);
            bankNameSpinner.setAdapter(emiNameAdapter);


            bankNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    // Item selected, lets setup the emiDuration adapter.
                    emiDurationAdapter = new PayUEmiDurationAdapter(PayUEmiActivity.this, emiArrayList, (Emi) parent.getSelectedItem());
                    emiDurationSpinner.setAdapter(emiDurationAdapter);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            emiDurationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedEmi = (Emi) parent.getSelectedItem();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }else{
            Toast.makeText(this, "Could not find emil list from the privious activity", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PayuConstants.PAYU_REQUEST_CODE) {
            setResult(resultCode, data);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == Rconfig.getInstance().id("button_emi_pay_now")){// paynow button is clicked.

            // card details
            mPaymentParams.setNameOnCard(nameOnCardEditText.getText().toString());
            mPaymentParams.setCardNumber(cardNumberEditText.getText().toString());
            mPaymentParams.setCvv(cvvEditText.getText().toString());
            mPaymentParams.setExpiryYear(expiryYearEditText.getText().toString());
            mPaymentParams.setExpiryMonth(expiryMonthEditText.getText().toString());

            // bank code
            mPaymentParams.setBankCode(selectedEmi.getBankCode());

            PostData postData = new PaymentPostParams(mPaymentParams, PayuConstants.EMI).getPaymentPostParams();

            if(postData.getCode() == PayuErrors.NO_ERROR){
                // launch webview
                payuConfig.setData(postData.getResult());
                Intent intent = new Intent(this, PaymentsActivity.class);
                intent.putExtra(PayuConstants.PAYU_CONFIG, payuConfig);
                startActivityForResult(intent, PayuConstants.PAYU_REQUEST_CODE);
            }else{
                Toast.makeText(this, postData.getResult(), Toast.LENGTH_LONG).show();
            }

        }
    }
}

class PayUEmiNameAdapter extends BaseAdapter{

    Context mContext;
    ArrayList<Emi> mEmiList;
    public PayUEmiNameAdapter(Context context, ArrayList<Emi> emiList){
        mContext = context;
        mEmiList = emiList;
    }

    @Override
    public int getCount() {
        if (null != mEmiList ) return mEmiList.size();
        else return 0;
    }

    @Override
    public Emi getItem(int position) {
        return mEmiList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PayUEmiVH emiViewHolder = null;
        if(convertView == null){
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(Rconfig.getInstance().layout("plugins_payu_india_emi_list_item"), null);
            emiViewHolder = new PayUEmiVH(convertView);
            convertView.setTag(emiViewHolder);
        }else{
            emiViewHolder = (PayUEmiVH) convertView.getTag();
        }

        Emi emi  = getItem(position);

        // set text here
        emiViewHolder.emiNameTextView.setText(emi.getBankName());
        return convertView;
    }
}

class PayUEmiDurationAdapter extends BaseAdapter{

    Context mContext;
    ArrayList<Emi> mEmiList;
    Emi mEmi;
    ArrayList<Emi> mSelectedEmiList;

    public PayUEmiDurationAdapter(Context context, ArrayList<Emi> emiList, Emi emi){
        mContext = context;
        mEmiList = emiList;
        mEmi = emi;
        mSelectedEmiList = null;
        mSelectedEmiList = new ArrayList<>();
        for(int i = 0; i < emiList.size(); i++){
            if(emiList.get(i).getBankName().contentEquals(emi.getBankName())){ // we found the current bank and bank is common in the list
                mSelectedEmiList.add(emiList.get(i));
            }
        }
    }

    @Override
    public int getCount() {
        if(null != mSelectedEmiList) return mSelectedEmiList.size();
        else return 0;
    }

    @Override
    public Emi getItem(int position) {
        return mSelectedEmiList.get(position) ;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PayUEmiVH emiViewHolder = null;
        if(convertView == null){
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(Rconfig.getInstance().layout("plugins_payu_india_emi_list_item"), null);
            emiViewHolder = new PayUEmiVH(convertView);
            convertView.setTag(emiViewHolder);
        }else{
            emiViewHolder = (PayUEmiVH) convertView.getTag();
        }

        Emi emi  = getItem(position);
        // set text here
        emiViewHolder.emiNameTextView.setText(emi.getBankTitle());
        return convertView;
    }
}

class PayUEmiVH {
    TextView emiNameTextView;
    PayUEmiVH(View view) {
        emiNameTextView = (TextView) view.findViewById(Rconfig.getInstance().id("text_view_emi_list"));
    }
}
