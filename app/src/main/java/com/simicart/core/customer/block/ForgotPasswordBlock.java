package com.simicart.core.customer.block;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.config.Config;
import com.simicart.core.config.Constants;
import com.simicart.core.config.Rconfig;
import com.simicart.core.customer.delegate.ForgotPasswordDelegate;
import com.simicart.core.material.ButtonRectangle;

@SuppressLint("DefaultLocale")
public class ForgotPasswordBlock extends SimiBlock implements
		ForgotPasswordDelegate {

	protected ButtonRectangle btn_Send;
	protected EditText edt_Email, edt_newPass, edt_confirmPass;
	private TextView lable_email, lable_newPass, lable_confirmPass;

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
		lable_email = (TextView) mView.findViewById(Rconfig
				.getInstance().id("lable_email"));
		lable_email.setTextColor(Color.GRAY);
		lable_email.setText(Config.getInstance().getText("ENTER YOUR EMAIL")
				.toUpperCase()
				+ ":");

		lable_newPass = (TextView) mView.findViewById(Rconfig
				.getInstance().id("lable_newPass"));
		lable_newPass.setTextColor(Color.GRAY);
		lable_newPass.setText(Config.getInstance().getText("ENTER YOUR NEW PASSWORD")
				.toUpperCase()
				+ ":");

		lable_confirmPass = (TextView) mView.findViewById(Rconfig
				.getInstance().id("lable_confirmPass"));
		lable_confirmPass.setTextColor(Color.GRAY);
		lable_confirmPass.setText(Config.getInstance().getText("CONFIRM YOUR NEW PASSWORD")
				.toUpperCase()
				+ ":");

		// button sent
		btn_Send = (ButtonRectangle) mView.findViewById(Rconfig.getInstance().id(
				"bt_send"));
		btn_Send.setText(Config.getInstance().getText("Reset my password"));
		btn_Send.setTextColor(Color.WHITE);
		btn_Send.setBackgroundColor(Config.getInstance().getColorMain());
		btn_Send.setTextSize(Constants.SIZE_TEXT_BUTTON);

		// Email Field
		edt_Email = (EditText) mView.findViewById(Rconfig.getInstance().id(
				"et_email"));
		edt_Email.setHint(Config.getInstance().getText("Email"));
		
		lable_email.setTextColor(Config.getInstance().getContent_color());
		edt_Email.setTextColor(Config.getInstance().getContent_color());
		edt_Email.setHintTextColor(Config.getInstance().getHintContent_color());

		// New Pass Field
		edt_newPass = (EditText) mView.findViewById(Rconfig.getInstance().id(
				"et_new_pass"));
		edt_newPass.setHint(Config.getInstance().getText("New Password"));

		lable_newPass.setTextColor(Config.getInstance().getContent_color());
		edt_newPass.setTextColor(Config.getInstance().getContent_color());
		edt_newPass.setHintTextColor(Config.getInstance().getHintContent_color());

		// Confirm Pass Field
		edt_confirmPass = (EditText) mView.findViewById(Rconfig.getInstance().id(
				"et_confirm_pass"));
		edt_confirmPass.setHint(Config.getInstance().getText("Confirm Password"));

		lable_confirmPass.setTextColor(Config.getInstance().getContent_color());
		edt_confirmPass.setTextColor(Config.getInstance().getContent_color());
		edt_confirmPass.setHintTextColor(Config.getInstance().getHintContent_color());
	}

	@Override
	public String getEmail() {
		return edt_Email.getText().toString();
	}

	@Override
	public String getNewPass() {
		return edt_newPass.getText().toString();
	}

	@Override
	public String getConfirmPass() {
		return edt_confirmPass.getText().toString();
	}

	public void showNotify(String message) {
		SimiManager.getIntance().showNotify(message);
	}

}
