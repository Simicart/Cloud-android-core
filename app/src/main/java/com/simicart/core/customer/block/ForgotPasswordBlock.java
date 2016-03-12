package com.simicart.core.customer.block;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.config.Config;
import com.simicart.core.config.Constants;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.core.customer.delegate.ForgotPasswordDelegate;
import com.simicart.core.material.ButtonRectangle;

@SuppressLint("DefaultLocale")
public class ForgotPasswordBlock extends SimiBlock implements
        ForgotPasswordDelegate {

    protected ButtonRectangle btn_Send;
    protected EditText edt_Email;
    private TextView tv_label;

    public ForgotPasswordBlock(View view, Context context) {
        super(view, context);
        // TODO Auto-generated constructor stub
    }

    public void setOnClicker(OnClickListener clicker) {
        btn_Send.setOnClickListener(clicker);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void initView() {
        // label
        tv_label = (TextView) mView.findViewById(Rconfig
                .getInstance().id("tv_label"));
        tv_label.setTextColor(Color.GRAY);
        if (DataLocal.isLanguageRTL) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.RIGHT;
            tv_label.setLayoutParams(params);
        }
        String msg_label = Config.getInstance().getText("ENTER YOUR EMAIL").toUpperCase()  + ":";
        tv_label.setText(msg_label);


        // button sent
        btn_Send = (ButtonRectangle) mView.findViewById(Rconfig.getInstance().id(
                "bt_send"));
        btn_Send.setText(Config.getInstance().getText("Reset my password"));
        btn_Send.setTextColor(Color.WHITE);
        btn_Send.setBackgroundColor(Config.getInstance().getKey_color());
        btn_Send.setTextSize(Constants.SIZE_TEXT_BUTTON);

        // Email Field
        edt_Email = (EditText) mView.findViewById(Rconfig.getInstance().id(
                "et_email"));
        if (DataLocal.isLanguageRTL) {
            edt_Email.setGravity(Gravity.RIGHT);
        }
        edt_Email.setHint(Config.getInstance().getText("Email"));

        tv_label.setTextColor(Config.getInstance().getContent_color());
        edt_Email.setTextColor(Config.getInstance().getContent_color());
        edt_Email.setHintTextColor(Config.getInstance().getHintContent_color());


    }

    @Override
    public String getEmail() {
        return edt_Email.getText().toString();
    }


    public void showNotify(String message) {
        SimiManager.getIntance().showNotify(null, message, "Ok");
    }

}
