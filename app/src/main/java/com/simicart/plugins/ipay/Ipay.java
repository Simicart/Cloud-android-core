package com.simicart.plugins.ipay;

import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.widget.Toast;

import com.simicart.MainActivity;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.core.event.checkout.CheckoutData;
import com.simicart.plugins.ipay.entity.IpayEntity;
import com.simicart.plugins.ipay.model.GetIpayConfigModel;

public class Ipay {
	Context context;
	
	public Ipay(String method, CheckoutData checkoutData){
		this.context = MainActivity.context;
		if (method.equals("callIpayServer")) {
			this.callIpayServer(checkoutData);
		}
	}
	
	public void callIpayServer(final CheckoutData checkoutData){
		if(checkoutData.getPaymentMethod().getMethodCode().equals("ipay")) {
			GetIpayConfigModel model = new GetIpayConfigModel();
			model.setDelegate(new ModelDelegate() {
				@Override
				public void onFail(SimiError error) {
					if (error != null) {
						changeView(error.getMessage());
						SimiManager.getIntance().backToHomeFragment();
					}
				}

				@Override
				public void onSuccess(SimiCollection collection) {
					if (collection != null && collection.getCollection().size() > 0) {
						IpayEntity entity = (IpayEntity) collection.getCollection().get(0);
						Intent ipay = new Intent(context, IpaySimiCart.class);
						if (Utils.validateString(entity.getMerchantKey())) {
							ipay.putExtra("EXTRA_MECHANT_KEY", entity.getMerchantKey());
						}
						if (Utils.validateString(entity.getMerchantCode())) {
							ipay.putExtra("EXTRA_MECHANT_CODE", entity.getMerchantCode());
						}
						ipay.putExtra("EXTRA_CUREENTCY", Config.getInstance().getCurrency_code());
						if (entity.isSandBox()) {
							ipay.putExtra("EXTRA_PRODUCTDES", "Test");
						} else {
							ipay.putExtra("EXTRA_PRODUCTDES", "Product");
						}
						ipay.putExtra("EXTRA_NAME", DataLocal.getUsername());
						ipay.putExtra("EXTRA_EMAIL", DataLocal.getEmail());
						ipay.putExtra("EXTRA_CONTACT", "60123456789");
						if (Utils.validateString(entity.getCountry())) {
							ipay.putExtra("EXTRA_COUNTRY", entity.getCountry());
						}
						ipay.putExtra("EXTRA_INVOICE", checkoutData.getOder().getID());
						ipay.putExtra("EXTRA_AMOUNT", checkoutData.getOder().getGrandTotal());
						ipay.putExtra("EXTRA_SANDBOX", entity.isSandBox());
						if (Utils.validateString(entity.getUrl())) {
							ipay.putExtra("EXTRA_URL", entity.getUrl());
						}
						context.startActivity(ipay);
					}
				}
			});
			model.addDataExtendURL("config");
			model.request();
		}
	}

	public void changeView(String message) {
		Toast toast = Toast.makeText(MainActivity.context, Config.getInstance()
				.getText(message), Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(10000);
		toast.show();
	}
}
