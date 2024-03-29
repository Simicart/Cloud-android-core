package com.simicart.core.checkout.controller;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.View.OnClickListener;

import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.checkout.delegate.CreditCardDelegate;
import com.simicart.core.checkout.entity.CreditcardEntity;
import com.simicart.core.checkout.model.CreditCardModel;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class CreditCardController extends SimiController {

    protected CreditcardEntity entity;
    protected CreditCardDelegate mDelegate;
    protected boolean isCheckedMethod;
    protected int key = 0;

    OnTouchListener onCLickSave;
    OnClickListener onClickCardType;
    OnClickListener onClickExpiredDate;
    OnTouchListener onClickCardName;
    OnTouchListener onCLickCardNumber;
    OnTouchListener onClickCVV;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onStart() {

        onCLickSave = new OnTouchListener() {
            @SuppressWarnings("deprecation")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        GradientDrawable gdDefault = new GradientDrawable();
                        gdDefault.setColor(Color.GRAY);
                        gdDefault.setCornerRadius(15);
                        v.setBackgroundDrawable(gdDefault);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        entity = mDelegate.getCreditCard();
                        onButtonSaveClicked();
                    }

                    case MotionEvent.ACTION_CANCEL: {
                        GradientDrawable gdDefault = new GradientDrawable();
                        gdDefault.setColor(Config.getInstance().getKey_color());
                        gdDefault.setCornerRadius(15);
                        v.setBackgroundDrawable(gdDefault);
                        break;
                    }
                    default:
                        break;
                }
                return true;
            }
        };

        onClickCardType = new OnClickListener() {
            @Override
            public void onClick(View v) {
                SimiManager.getIntance().hideKeyboard();
                if (mDelegate.getWheelViewCardType().getVisibility() == View.GONE) {
                    mDelegate.getCardDateLayout().setVisibility(View.GONE);
                    mDelegate.getWheelViewCardType().setVisibility(View.VISIBLE);
                } else {
                    mDelegate.getWheelViewCardType().setVisibility(View.GONE);
                }
            }
        };

        onClickExpiredDate = new OnClickListener() {
            @Override
            public void onClick(View v) {
                SimiManager.getIntance().hideKeyboard();
                if (mDelegate.getCardDateLayout().getVisibility() == View.GONE) {
                    mDelegate.getWheelViewCardType().setVisibility(View.GONE);
                    mDelegate.getCardDateLayout().setVisibility(View.VISIBLE);
                } else {
                    mDelegate.getCardDateLayout().setVisibility(View.GONE);
                }
            }
        };

        onClickCardName = new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDelegate.getWheelViewCardType().setVisibility(View.GONE);
                mDelegate.getCardDateLayout().setVisibility(View.GONE);
                return false;
            }
        };

        onCLickCardNumber = new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDelegate.getWheelViewCardType().setVisibility(View.GONE);
                mDelegate.getCardDateLayout().setVisibility(View.GONE);
                return false;
            }
        };

        onClickCVV = new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDelegate.getWheelViewCardType().setVisibility(View.GONE);
                mDelegate.getCardDateLayout().setVisibility(View.GONE);
                return false;
            }
        };
    }

    @Override
    public void onResume() {

    }

    private boolean validateInputData(String number) {
        if (!Utils.validateString(number)) {
            String message = "Please enter card number.";
            SimiManager.getIntance().showToast(Config.getInstance().getText(message));
            return false;
        }
        return true;
    }

    public void onSaveCreditcardToOrder() {
        mDelegate.showLoading();
        CreditCardModel model = new CreditCardModel();
        ModelDelegate delegate = new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                mDelegate.dismissLoading();
                if (error != null)
                    SimiManager.getIntance().showToast(error.getMessage().toString());
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.dismissLoading();
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
            cc_types = new JSONArray(mDelegate.getPaymentMethod().getData("card_type"));
            for (int i = 0; i < cc_types.length(); i++) {
                if (cc_types.getJSONObject(i).getString("title").equals(entity.getPaymentType())) {
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
        model.addDataBody("card_number", entity.getPaymentNumber());
        model.addDataBody("card_name", entity.getCardName());
        String ccid = entity.getPaymentCvv();
        if(mDelegate.getPaymentMethod().getData("digit_card").equals("1"))
            model.addDataBody("card_digit", ccid);
        model.addDataBody("expiration_date", entity.getExpired());
        model.request();
    }

    public void onButtonSaveClicked() {
        SimiManager.getIntance().hideKeyboard();
        String my = entity.getExpired();
        String[] split = my.split("/");
        String number = entity.getPaymentNumber();
        String ccid = entity.getPaymentCvv();
        String card_type = entity.getPaymentType();
        String card_name = entity.getCardName();

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
                JSONArray js_card_type = new JSONArray(mDelegate.getPaymentMethod().getData("card_type"));
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
            creditCardHashMap.put(mDelegate.getPaymentMethod().getMethodCode(),
                    creditCard);
            hashMap.put(email, creditCardHashMap);
            DataLocal.saveHashMapCreditCart(hashMap);


            onSaveCreditcardToOrder();
        }
    }

    public void setDelegate(CreditCardDelegate mDelegate) {
        this.mDelegate = mDelegate;
    }

    public void setIsCheckedMethod(boolean isCheckedMethod) {
        this.isCheckedMethod = isCheckedMethod;
    }

    public OnTouchListener getOnClickSave() {
        return onCLickSave;
    }

    public OnClickListener getOnClickCardType() {
        return onClickCardType;
    }

    public OnClickListener getOnClickCardDate() {
        return onClickExpiredDate;
    }

    public OnTouchListener getOnClickCardName() {
        return onClickCardName;
    }

    public OnTouchListener getOnCLickCardNumber() {
        return onCLickCardNumber;
    }

    public OnTouchListener getOnClickCVV() {
        return onClickCVV;
    }

}
