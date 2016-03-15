package com.simicart.core.checkout.block;

import java.util.Calendar;
import java.util.HashMap;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.checkout.adapter.CreditCardAdapter;
import com.simicart.core.checkout.adapter.DateArrayAdapter;
import com.simicart.core.checkout.adapter.DateNumericAdapter;
import com.simicart.core.checkout.delegate.CreditCardDelegate;
import com.simicart.core.checkout.entity.CreditcardEntity;
import com.simicart.core.checkout.entity.PaymentMethod;
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
    protected WheelView wv_card_type;
    protected Button bt_save;
    protected EditText et_expired;
    protected EditText et_card_number;
    protected EditText edt_card_name;
    protected String email;
    LinearLayout picker;

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

        wv_card_type = (WheelView) mView
                .findViewById(Rconfig.getInstance().id("select_cart"));

        picker = (LinearLayout) mView.findViewById(Rconfig
                .getInstance().id("card_date"));

        et_card_number = (EditText) mView.findViewById(Rconfig.getInstance()
                .id("card_number"));
        et_card_number.setHint(Config.getInstance().getText("Card Number ")
                + ":");
        et_cvv = (EditText) mView.findViewById(Rconfig.getInstance().id("cvv"));

        initExpiredDate();

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
            initSavedCreditCard();
        }

    }

    public void initSavedCreditCard() {
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

    public void initExpiredDate() {
        et_expired = (EditText) mView.findViewById(Rconfig.getInstance().id(
                "expired"));
        et_expired.setText(Config.getInstance().getText("Expired") + ": MM/YY");
        edt_card_name = (EditText) mView.findViewById(Rconfig.getInstance().id("edt_card_name"));
        edt_card_name.setHint(Config.getInstance().getText("Card Name"));

        et_type = (EditText) mView.findViewById(Rconfig.getInstance().id(
                "card_type"));
        if (cAdapter.getItemText(0) != null) {
            et_type.setText(cAdapter.getItemText(0));
        }

        wv_month = (WheelView) mView.findViewById(Rconfig.getInstance().id(
                "month"));
        wv_year = (WheelView) mView.findViewById(Rconfig.getInstance().id(
                "year"));

        setWheelScroll();
        wv_card.setCurrentItem(0);
    }

    public void setClickCardType(OnClickListener onClick) {
        et_type.setOnClickListener(onClick);
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

    public void setClickExpiredDate(OnClickListener onClick) {
        et_expired.setOnClickListener(onClick);
    }

    @Override
    public String getCardType() {
        return et_type.getText().toString();
    }

    @Override
    public String getCVV() {
        return et_cvv.getText().toString();
    }

    @Override
    public String getExpired() {
        return et_expired.getText().toString();
    }

    @Override
    public String getCardNumber() {
        return et_card_number.getText().toString();
    }

    @Override
    public String getCardName() {
        return edt_card_name.getText().toString();
    }

    @Override
    public PaymentMethod getPaymentMethod() {
        return mPaymentMethod;
    }

    @Override
    public WheelView getWheelViewCardType() {
        return wv_card_type;
    }

    @Override
    public LinearLayout getCardDateLayout() {
        return picker;
    }

}
