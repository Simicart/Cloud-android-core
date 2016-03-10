package com.simicart.core.customer.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.delegate.SimiDelegate;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.checkout.controller.ConfigCheckout;
import com.simicart.core.checkout.entity.PaymentMethod;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.core.customer.fragment.AddressBookFragment;
import com.simicart.core.customer.fragment.ChangePasswordFragment;
import com.simicart.core.customer.fragment.OrderHistoryFragment;
import com.simicart.core.customer.fragment.ProfileFragment;
import com.simicart.core.home.fragment.HomeFragment;

public class MyAccountController extends SimiController {
	OnClickListener mClickProfile;
	OnClickListener mClickChangePass;
	OnClickListener mClickAddress;
	OnClickListener mClickOrderHistory;
	OnClickListener mClickSignOut;

	protected SimiDelegate mDelegate;

	public void setDelegate(SimiDelegate delegate) {
		mDelegate = delegate;
	}

	public OnClickListener getClickProfile() {
		return mClickProfile;
	}

	public OnClickListener getClickChangePass() {
		return mClickChangePass;
	}

	public OnClickListener getClickAddress() {
		return mClickAddress;
	}

	public OnClickListener getClickOrderHistory() {
		return mClickOrderHistory;
	}

	public OnClickListener getClickSignOut() {
		return mClickSignOut;
	}

	@Override
	public void onStart() {

		mDelegate.updateView(null);
		mClickProfile = new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (DataLocal.isTablet) {
					ProfileFragment fragment = ProfileFragment.newInstance();
					SimiManager.getIntance().replacePopupFragment(fragment);
				} else {
					ProfileFragment fragment = ProfileFragment.newInstance();
					SimiManager.getIntance().replaceFragment(fragment);
				}
			}
		};

		mClickChangePass = new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(DataLocal.isTablet){
					ChangePasswordFragment fragment = ChangePasswordFragment.newInstance();
					SimiManager.getIntance().replacePopupFragment(fragment);
				}else{
					ChangePasswordFragment fragment = ChangePasswordFragment.newInstance();
					SimiManager.getIntance().replaceFragment(fragment);
				}
			}
		};

		mClickAddress = new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (DataLocal.isTablet) {
					AddressBookFragment fragment = AddressBookFragment
							.newInstance();
					SimiManager.getIntance().replacePopupFragment(fragment);
				} else {
					AddressBookFragment fragment = AddressBookFragment
							.newInstance();
					SimiManager.getIntance().replaceFragment(fragment);
				}
			}
		};

		mClickOrderHistory = new OnClickListener() {

			@Override
			public void onClick(View v) {
				OrderHistoryFragment fragment = OrderHistoryFragment
						.newInstance();
				SimiManager.getIntance().removeDialog();
				SimiManager.getIntance().replaceFragment(fragment);
			}
		};

		mClickSignOut = new OnClickListener() {
			boolean isFirst = true;

			@Override
			public void onClick(View v) {
				if (isFirst) {
					isFirst = false;
					SimiManager.getIntance().removeDialog();
					showToastSignOut();
					DataLocal.isNewSignIn = false;
					// dispatch event
					Intent intent = new Intent("com.simicart.core.customer.controller.SignInController");
					Context context = SimiManager.getIntance().getCurrentContext();
					LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

					DataLocal.saveSignInState(false);
					DataLocal.clearEmailPassowrd();
					ConfigCheckout.getInstance().setmQty("" + 0);
					ConfigCheckout.getInstance().setCheckStatusCart(true);
					SimiManager.getIntance().onUpdateCartQty("");
					if (DataLocal.isTablet) {
						SimiManager.getIntance().clearAllChidFragment();
						SimiManager.getIntance().removeDialog();
					} else {
						SimiManager.getIntance().backPreviousFragment();
					}

					DataLocal.saveQuoteCustomerNotSignIn("");
					Config.getInstance().setQuoteCustomerSignIn("");

//					PaymentMethod.getInstance().setPlacePaymentMethod("");
//					PaymentMethod.getInstance().setPlace_cc_number("");
//					PaymentMethod.getInstance().setPlacecc_id("");
					ConfigCheckout.checkPaymentMethod = false;

					HomeFragment fragment = HomeFragment.newInstance();
					SimiManager.getIntance().replaceFragment(fragment);
				}
			}
		};
	}

	private void showToastSignOut() {
		LayoutInflater inflater = SimiManager.getIntance().getCurrentActivity()
				.getLayoutInflater();
		View layout_toast = inflater
				.inflate(
						Rconfig.getInstance().layout(
								"core_custom_toast_productlist"),
						(ViewGroup) SimiManager
								.getIntance()
								.getCurrentActivity()
								.findViewById(
										Rconfig.getInstance().id(
												"custom_toast_layout")));
		TextView txt_toast = (TextView) layout_toast.findViewById(Rconfig
				.getInstance().id("txt_custom_toast"));
		Toast toast = new Toast(SimiManager.getIntance().getCurrentContext());
		txt_toast.setText(Config.getInstance().getText("Logout Success"));
		toast.setView(layout_toast);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 400);
		toast.show();
	}

	@Override
	public void onResume() {
	}
}
