package com.simicart.plugins.checkout.com.block;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.magestore.simicart.R;
import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.config.Config;
import com.simicart.core.config.Constants;
import com.simicart.core.config.Rconfig;
import com.simicart.core.material.ButtonRectangle;
import com.simicart.plugins.checkout.com.delegate.CheckoutComDelegate;

/**
 * Created by James Crabby on 1/15/2016.
 */
public class CheckoutComBlock extends SimiBlock implements CheckoutComDelegate {

    EditText name;
    EditText numberField;
    EditText cvvField;
    Spinner spinMonth;
    Spinner spinYear;
    Button buttonCheckout;

    public CheckoutComBlock(View view, Context context) {
        super(view, context);
    }

    @Override
    public void initView() {
        TextView tv_name = (TextView) mView.findViewById(Rconfig.getInstance().id("tv_name"));
        TextView tv_card_num = (TextView) mView.findViewById(Rconfig.getInstance().id("tv_card_num"));
        TextView tv_expired_date = (TextView) mView.findViewById(Rconfig.getInstance().id("tv_expired_date"));
        TextView tv_cvv = (TextView) mView.findViewById(Rconfig.getInstance().id("tv_cvv"));
        name = (EditText) mView.findViewById(Rconfig.getInstance().id("name"));
        numberField = (EditText) mView.findViewById(Rconfig.getInstance().id("number"));
        cvvField = (EditText) mView.findViewById(Rconfig.getInstance().id("cvv"));
        spinMonth = (Spinner) mView.findViewById(Rconfig.getInstance().id("spinnerMonth"));
        spinYear = (Spinner) mView.findViewById(Rconfig.getInstance().id("spinnerYear"));
        buttonCheckout = (Button) mView.findViewById(Rconfig.getInstance().id("buttonMain"));

        buttonCheckout.setText(Config.getInstance().getText("Purchase"));
        buttonCheckout.setTextColor(Config.getInstance().getButton_text_color());
        buttonCheckout.setBackgroundColor(Config.getInstance()
                .getButton_background());
        buttonCheckout.setTextSize(Constants.SIZE_TEXT_BUTTON);

        tv_name.setText(Config.getInstance().getText("Name"));
        tv_card_num.setText(Config.getInstance().getText("Card Number"));
        tv_expired_date.setText(Config.getInstance().getText("Expired date"));
        tv_cvv.setText(Config.getInstance().getText("CVV"));

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mContext,
                Rconfig.getInstance().getId(mContext, "months", "array"), android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinMonth.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(mContext,
                Rconfig.getInstance().getId(mContext, "years", "array"), android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinYear.setAdapter(adapter2);
    }

    public void setOnCheckoutButtonClicked(OnClickListener onClickListener) {
        buttonCheckout.setOnClickListener(onClickListener);
    }

    public EditText getCvvField() {
        return cvvField;
    }

    public EditText getName() {
        return name;
    }

    public EditText getNumberField() {
        return numberField;
    }

    public Spinner getSpinMonth() {
        return spinMonth;
    }

    public Spinner getSpinYear() {
        return spinYear;
    }
}
