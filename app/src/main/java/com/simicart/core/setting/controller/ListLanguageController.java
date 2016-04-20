package com.simicart.core.setting.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.simicart.MainActivity;
import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.delegate.SimiDelegate;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.common.ReadXMLLanguage;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.core.customer.delegate.ChooseCountryDelegate;
import com.simicart.core.customer.entity.MyAddress;
import com.simicart.core.splashscreen.entity.CMSPageEntity;
import com.simicart.core.splashscreen.entity.LocaleConfigEntity;
import com.simicart.core.splashscreen.model.CMSPagesModel;

public class ListLanguageController extends SimiController {
	protected OnItemClickListener mClicker;
	ArrayList<String> list_lag;
	MyAddress addressBookDetail;
	SimiDelegate mDelegate;

	public void setDelegate(SimiDelegate mDelegate) {
		this.mDelegate = mDelegate;
	}

	public OnItemClickListener getClicker() {
		return mClicker;
	}

	@Override
	public void onStart() {
		mClicker = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				selectItem(position);
			}
		};
	}

	protected void selectItem(int position) {
		Collections.sort(list_lag);
		String language = list_lag.get(position).toString();
		String id = DataLocal.getLocale();
		for (LocaleConfigEntity locale : DataLocal.listLocale) {
			if (language.equals(locale.getName())) {
				if (!id.equals(locale.getCode())) {
					String codeLocale = locale.getCode();
					DataLocal.saveLocale(codeLocale);
					Config.getInstance().setLocale_identifier(codeLocale);
					Config.getInstance().clearLanguages();
					createLanguage();
					if(Config.getInstance().isRTLLanguage(locale.getCode())) {
						DataLocal.isLanguageRTL = true;
					} else {
						DataLocal.isLanguageRTL = false;
					}

					getCMSPage();
				}
			}
		}
	}

	@Override
	public void onResume() {
	}

	public void setListLanguage(ArrayList<String> list_lag) {
		this.list_lag = list_lag;
	}

	private void createLanguage() {
		try {
			ReadXMLLanguage readlanguage = new ReadXMLLanguage(MainActivity.context);
			readlanguage.parseXML(Config.getInstance().getLocale_identifier()
					+ "/localize.xml");
			Config.getInstance().setLanguages(readlanguage.getLanguages());
		} catch (Exception e) {
			Map<String, String> languages = new HashMap<String, String>();
			Config.getInstance().setLanguages(languages);
		}
	}

	private void getCMSPage(){
		mDelegate.showDialogLoading();
		CMSPagesModel model = new CMSPagesModel();
		model.setDelegate(new ModelDelegate() {
			@Override
			public void onFail(SimiError error) {
				mDelegate.dismissDialogLoading();
				if (error != null) {
					SimiManager.getIntance().showNotify(null, error.getMessage(), "Ok");
				}
			}

			@Override
			public void onSuccess(SimiCollection collection) {
				mDelegate.dismissDialogLoading();
				if(collection != null && collection.getCollection().size() > 0){
					SimiEntity entity = collection.getCollection().get(0);
					DataLocal.listCms = ((CMSPageEntity) entity).getPage();

					SimiManager.getIntance().notifiChangeAdapterSlideMenu();
					SimiManager.getIntance().onUpdateItemSignIn();
					SimiManager.getIntance().backToHomeFragment();
				}
			}
		});

		model.request();
	}
}
