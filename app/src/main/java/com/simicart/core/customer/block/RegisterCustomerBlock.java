package com.simicart.core.customer.block;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.config.Config;
import com.simicart.core.config.Constants;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.core.customer.delegate.RegisterCustomerDelegate;
import com.simicart.core.customer.entity.ConfigCustomerAddress;
import com.simicart.core.customer.entity.RegisterCustomer;
import com.simicart.core.material.ButtonRectangle;

@SuppressLint("DefaultLocale")
public class RegisterCustomerBlock extends SimiBlock implements
		RegisterCustomerDelegate {
	protected EditText edt_first_name;
	protected EditText edt_last_name;
	protected EditText edt_email;
	protected EditText edt_pass;
	protected EditText edt_confirmPass;
	protected ButtonRectangle btn_register;
	protected ConfigCustomerAddress mCustomer;


	public RegisterCustomerBlock(View view, Context context) {
		super(view, context);
		mCustomer = DataLocal.ConfigCustomerAddress;
	}



	public void setRegisterClick(OnClickListener clicker) {
		btn_register.setOnClickListener(clicker);
	}

	@SuppressLint("DefaultLocale")
	@SuppressWarnings("deprecation")
	@Override
	public void initView() {
		edt_first_name = (EditText) mView.findViewById(Rconfig.getInstance().id(
				"edt_first_name"));
		edt_last_name = (EditText) mView.findViewById(Rconfig.getInstance().id(
				"edt_last_name"));
		edt_last_name.setHint(Config.getInstance().getText("Last Name") + "(*)");
		edt_email = (EditText) mView.findViewById(Rconfig.getInstance().id(
				"edt_email"));
		edt_email.setHint(Config.getInstance().getText("Email") + "(*)");
		edt_pass = (EditText) mView.findViewById(Rconfig.getInstance().id(
				"edt_pass"));
		edt_pass.setHint(Config.getInstance().getText("Password") + "(*)");
		edt_confirmPass = (EditText) mView.findViewById(Rconfig.getInstance()
				.id("edt_confirm_pass"));
		edt_confirmPass.setHint(Config.getInstance()
				.getText("Confirm Password") + "(*)");
		// set text color
		edt_first_name.setTextColor(Config.getInstance().getContent_color());
		edt_last_name.setTextColor(Config.getInstance().getContent_color());
		edt_email.setTextColor(Config.getInstance().getContent_color());
		edt_pass.setTextColor(Config.getInstance().getContent_color());
		edt_confirmPass.setTextColor(Config.getInstance().getContent_color());

		edt_first_name
				.setHintTextColor(Config.getInstance().getHintContent_color());
		edt_last_name.setHintTextColor(Config.getInstance().getHintContent_color());
		edt_email.setHintTextColor(Config.getInstance().getHintContent_color());
		edt_pass.setHintTextColor(Config.getInstance().getHintContent_color());
		edt_confirmPass.setHintTextColor(Config.getInstance()
				.getHintContent_color());
		btn_register = (ButtonRectangle) mView.findViewById(Rconfig
				.getInstance().id("bt_register"));
		btn_register.setText(Config.getInstance().getText("Register"));
		btn_register.setTextColor(Color.WHITE);
		btn_register.setBackgroundColor(Config.getInstance().getKey_color());
		btn_register.setTextSize(Constants.SIZE_TEXT_BUTTON);


		Drawable icon = mContext.getResources().getDrawable(
				Rconfig.getInstance().drawable("ic_extend"));
		icon.setColorFilter(Config.getInstance().getContent_color(),
				PorterDuff.Mode.SRC_ATOP);
		createPrefix();
		createSuffix();
	}

	private void createPrefix() {
		edt_first_name.setHint(Config.getInstance().getText("First Name") + " (*)");
	}

	private void createSuffix() {
		edt_last_name.setHint(Config.getInstance().getText("Last Name") + " (*)");
	}





	@Override
	public RegisterCustomer getRegisterCustomer() {
		RegisterCustomer register = new RegisterCustomer();
		register.setFirstName(edt_first_name.getText().toString());
		register.setLastName(edt_last_name.getText().toString());
		register.setEmail(edt_email.getText().toString());
		register.setPass(edt_pass.getText().toString());
		register.setConfirmPass(edt_confirmPass.getText().toString());
		return register;
	}


}
