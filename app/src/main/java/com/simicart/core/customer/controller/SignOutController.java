package com.simicart.core.customer.controller;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.delegate.SimiDelegate;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.checkout.controller.ConfigCheckout;
import com.simicart.core.checkout.entity.PaymentMethod;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.core.customer.model.SignOutModel;
import com.simicart.core.event.controller.EventController;
import com.simicart.core.home.fragment.HomeFragment;

public class SignOutController extends SimiController {

	protected SimiDelegate mDelegate;

	public void setDelegate(SimiDelegate delegate) {
		mDelegate = delegate;
	}

	@Override
	public void onStart() {
		if (mDelegate != null) {
			mDelegate.showDialogLoading();
		}
		mModel = new SignOutModel();
		mModel.setDelegate(new ModelDelegate() {
			@Override
			public void onFail(SimiError error) {
				if(error != null) {
					SimiManager.getIntance().showNotify(null, error.getMessage(), "OK");
				}
			}

			@Override
			public void onSuccess(SimiCollection collection) {

			}
		});

		mModel.request();

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
