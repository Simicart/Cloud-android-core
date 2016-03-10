package com.simicart.core.checkout.block;

import java.security.Key;
import java.util.Calendar;
import java.util.HashMap;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.paypal.android.sdk.e;
import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.checkout.adapter.CreditCardAdapter;
import com.simicart.core.checkout.adapter.DateArrayAdapter;
import com.simicart.core.checkout.adapter.DateNumericAdapter;
import com.simicart.core.checkout.delegate.CreditCardDelegate;
import com.simicart.core.checkout.entity.CreditcardEntity;
import com.simicart.core.checkout.entity.PaymentMethod;
import com.simicart.core.checkout.model.CreditCardModel;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;

public class CreditCardBlock extends SimiBlock implements CreditCardDelegate {

    protected boolean isCheckedMethod;
    protected PaymentMethod mPaymentMethod;
    protected CreditCardAdapter cAdapter;

    protected EditText et_type;
    protected EditText et_cvv;
    protected WheelView wv_card;
    protected WheelView wv_month;
    protected WheelView wv_year;
    protected Button bt_save;
    protected EditText et_expired;
    protected EditText et_card_number;
    protected EditText edt_card_name;
    protected String email;
    protected int key = 0;

    protected int click = 0;
    protected int click_date = 0;
    protected boolean scrolling = false;

    public CreditCardBlock(View view, Context context) {
        super(view, context);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void initView() {
        wv_card = (WheelView) mView.findViewById(Rconfig.getInstance().id(
                "select_cart"));
        wv_card.setVisibleItems(3);
        cAdapter = new CreditCardAdapter(mContext, mPaymentMethod);
        wv_card.setViewAdapter(cAdapter);

        et_card_number = (EditText) mView.findViewById(Rconfig.getInstance()
                .id("card_number"));
        et_card_number.setHint(Config.getInstance().getText("Card Number ")
                + ":");
        et_cvv = (EditText) mView.findViewById(Rconfig.getInstance().id("cvv"));

        et_expired = (EditText) mView.findViewById(Rconfig.getInstance().id(
                "expired"));
        et_expired.setText(Config.getInstance().getText("Expired") + ": MM/YY");
        edt_card_name = (EditText) mView.findViewById(Rconfig.getInstance().id("edt_card_name"));
        edt_card_name.setHint(Config.getInstance().getText("Card Name"));

        String code = mPaymentMethod.getPayment_method();


        et_type = (EditText) mView.findViewById(Rconfig.getInstance().id(
                "card_type"));
        if (cAdapter.getItemText(0) != null) {
            et_type.setText(cAdapter.getItemText(0));
        }

        wv_month = (WheelView) mView.findViewById(Rconfig.getInstance().id(
                "month"));
        wv_year = (WheelView) mView.findViewById(Rconfig.getInstance().id(
                "year"));

        setClickCardType();

        setWheelScroll();
        wv_card.setCurrentItem(0);

        bt_save = (Button) mView.findViewById(Rconfig.getInstance().id(
                "card_save"));
        bt_save.setText(Config.getInstance().getText("Save"));
        bt_save.setTextColor(Color.WHITE);
        GradientDrawable gdDefault = new GradientDrawable();
        gdDefault.setColor(Config.getInstance().getKey_color());
        gdDefault.setCornerRadius(15);
        bt_save.setBackgroundDrawable(gdDefault);

        if (mPaymentMethod.getData("digit_card").equals("1")) {
            et_cvv.setVisibility(View.VISIBLE);
        }

        setPickerDate();
        email = DataLocal.getEmailCreditCart();
        if (isSavedCC(mPaymentMethod.getMethodCode())) {
            CreditcardEntity creditcardEntity = DataLocal
                    .getHashMapCreditCart().get(DataLocal.getEmailCreditCart())
                    .get(mPaymentMethod.getMethodCode());

            String type = creditcardEntity.getPaymentType();
            et_type.setText(type);

            String number = creditcardEntity.getPaymentNumber();
            et_card_number.setText(number);

            String expired_day = creditcardEntity.getPaymentMonth() + "/"
                    + creditcardEntity.getPaymentYear();
            et_expired.setText(expired_day);


            if (mPaymentMethod.getData("digit_card").equals("1")) {
                String cvv = creditcardEntity.getPaymentCvv();
                et_cvv.setText(cvv);
            }

            String card_name = creditcardEntity.getCardName();
            if (Utils.validateString(card_name)) {
                edt_card_name.setText(card_name);
            }
        }



    }




    public void setClickCardType() {
        et_type.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                kankan.wheel.widget.WheelView picker = (kankan.wheel.widget.WheelView) mView
                        .findViewById(Rconfig.getInstance().id("select_cart"));
                if (click % 2 == 0) {
                    picker.setVisibility(View.VISIBLE);
                } else {
                    picker.setVisibility(View.GONE);
                }
                click++;
            }
        });
    }

    public void setWheelScroll() {
        wv_card.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if (!scrolling) {
                    et_type.setText(cAdapter.getItemText(wv_card
                            .getCurrentItem()));
                }
            }
        });

        wv_card.addScrollingListener(new OnWheelScrollListener() {
            public void onScrollingStarted(WheelView wheel) {
                scrolling = true;
            }

            public void onScrollingFinished(WheelView wheel) {
                scrolling = false;
                et_type.setText(cAdapter.getItemText(wv_card.getCurrentItem()));
            }
        });
    }

    public void setPickerDate() {
        Calendar calendar = Calendar.getInstance();

        int curMonth = calendar.get(Calendar.MONTH);

        int curYear = calendar.get(Calendar.YEAR);


        if (isSavedCC(mPaymentMethod.getMethodCode())) {
            CreditcardEntity creditcardEntity = DataLocal
                    .getHashMapCreditCart().get(DataLocal.getEmailCreditCart())
                    .get(mPaymentMethod.getMethodCode());
            String month = creditcardEntity.getPaymentMonth();
            String year = creditcardEntity.getPaymentYear();
            setExDate(month, year);
        } else {
            this.setExDate("" + (curMonth + 1), "" + curYear);
        }

        et_expired.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                LinearLayout picker = (LinearLayout) mView.findViewById(Rconfig
                        .getInstance().id("card_date"));
                if (click_date % 2 == 0) {
                    picker.setVisibility(View.VISIBLE);
                } else {
                    picker.setVisibility(View.GONE);
                }
                click_date++;
            }
        });

        OnWheelChangedListener listener = new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updateWheelView();
            }
        };

        String months[] = new String[]{"January - 01", "February - 02",
                "March - 03", "April - 04", "May - 05", "June - 06",
                "July - 07", "August -  08", "September - 09", "October - 10",
                "November - 11", "December - 12"};

        wv_month.setViewAdapter(new DateArrayAdapter(mContext, months, curMonth));
        wv_month.setCurrentItem(curMonth);
        wv_month.addChangingListener(listener);

        wv_year.setViewAdapter(new DateNumericAdapter(mContext, curYear, 2099,
                0));

        wv_year.setCurrentItem(curYear);
        wv_year.addChangingListener(listener);
    }

    private void updateWheelView() {
        String m = "" + (wv_month.getCurrentItem() + 1);
        String show = m;
        if (m.length() == 1) {
            show = "0" + (m);
        }

        Calendar calendar = Calendar.getInstance();

        this.setExDate(show,
                "" + (calendar.get(Calendar.YEAR) + wv_year.getCurrentItem()));
    }

    public void setExDate(String month, String year) {
        String m = month;
        if (month.length() == 1) {
            m = "0" + (month);
        }
        et_expired.setText(m + "/" + year);
    }

    public void setClickSave(OnTouchListener onTouchListener) {
        bt_save.setOnTouchListener(onTouchListener);
    }

    public void setPaymentMethod(PaymentMethod mPaymentMethod) {
        this.mPaymentMethod = mPaymentMethod;
    }

    public void setCheckedMethod(boolean isCheckedMethod) {
        this.isCheckedMethod = isCheckedMethod;
    }


    private boolean isSavedCC(String paymentMethodCode) {
        HashMap<String, HashMap<String, CreditcardEntity>> hashMap = DataLocal
                .getHashMapCreditCart();
        if (hashMap != null && hashMap.size() != 0) {
            String email = DataLocal.getEmailCreditCart();
            if (Utils.validateString(email) && hashMap.containsKey(email)) {
                HashMap<String, CreditcardEntity> creditcard = hashMap
                        .get(email);
                if (creditcard.containsKey(paymentMethodCode)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onCLickSave() {
        SimiManager.getIntance().hideKeyboard();
        String my = "" + et_expired.getText();
        String[] split = my.split("/");
        String number = "" + et_card_number.getText();
        String ccid = "" + et_cvv.getText();
        String card_type = "" + et_type.getText().toString();
        String card_name = "" + edt_card_name.getText().toString();

        if (validateInputData(number)) {
            String email = DataLocal.getEmailCreditCart();
            HashMap<String, HashMap<String, CreditcardEntity>> hashMap = DataLocal
                    .getHashMapCreditCart();
            if (hashMap == null) {
                hashMap = new HashMap<String, HashMap<String, CreditcardEntity>>();
            }

            CreditcardEntity creditCard = new CreditcardEntity(card_type,
                    number, split[0], split[1], ccid, card_name);

            try {
                JSONArray js_card_type = new JSONArray(mPaymentMethod.getData("card_type"));
                for (int i = 0; i < js_card_type.length(); i++) {
                    JSONObject json = js_card_type.getJSONObject(i);
                    String title = json.getString("title");
                    if (Utils.validateString(title) && title.equals(card_type)) {
                        creditCard.setTitleCardType(title);
                        String key = json.getString("key");
                        if (Utils.validateString(key)) {
                            creditCard.setKeyCardType(key);
                        }

                        String code = json.getString("code");
                        if (Utils.validateString(code)) {
                            creditCard.setCodeCardType(code);
                        }
                        break;
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


            HashMap<String, CreditcardEntity> creditCardHashMap = new HashMap<String, CreditcardEntity>();
            if (hashMap.containsKey(email)) {
                creditCardHashMap = hashMap.get(email);
            }
            creditCardHashMap.put(mPaymentMethod.getMethodCode(),
                    creditCard);
            hashMap.put(email, creditCardHashMap);
            DataLocal.saveHashMapCreditCart(hashMap);


            onSaveCreditcardToOrder();
        }
    }

    private boolean validateInputData(String number) {

        if (!Utils.validateString(number)) {
            String message = "Please enter card number.";
            SimiManager.getIntance().showToast(message);
            return false;
        }


        return true;
    }

    public void onSaveCreditcardToOrder() {
        showLoading();
        CreditCardModel model = new CreditCardModel();
        ModelDelegate delegate = new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                dismissLoading();
                if (error != null)
                    SimiManager.getIntance().showToast(error.getMessage().toString());
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                dismissLoading();
                SimiManager.getIntance().showToast(Config.getInstance().getText("Save Credit Card success"));
                SimiManager.getIntance().backPreviousFragment();

            }
        };
        model.setDelegate(delegate);

        String quote_id = "";
        if (DataLocal.isSignInComplete()) {
            quote_id = Config.getInstance().getQuoteCustomerSignIn();
        } else {
            quote_id = DataLocal.getQuoteCustomerNotSigin();
        }
        model.addDataBody("quote_id", quote_id);

        JSONArray cc_types = null;
        JSONObject card_type = new JSONObject();
        try {
            cc_types = new JSONArray(mPaymentMethod.getData("card_type"));
            for (int i = 0; i < cc_types.length(); i++) {
                if (cc_types.getJSONObject(i).getString("title").equals(et_type.getText().toString())) {
                    key = i;
                    break;
                }
            }
            card_type.put("key", cc_types.getJSONObject(key).getString("key"));
            card_type.put("code", cc_types.getJSONObject(key).getString("code"));
            card_type.put("title", cc_types.getJSONObject(key).getString("title"));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        model.addDataBody("card_type", card_type);
        model.addDataBody("card_number", et_card_number.getText().toString());
        model.addDataBody("card_name", et_type.getText().toString());
        String ccid = "" + et_cvv.getText();
        model.addDataBody("card_digit", ccid);
        String card_name = "" + edt_card_name.getText();
        model.addDataBody("card_name", card_name);
        model.request();
    }

}
