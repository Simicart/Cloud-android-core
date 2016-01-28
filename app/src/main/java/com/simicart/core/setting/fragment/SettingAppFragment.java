package com.simicart.core.setting.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.core.material.LayoutRipple;
import com.simicart.core.setting.entity.CurrencyEntity;
import com.simicart.core.splashscreen.entity.LocaleConfigEntity;
import com.simicart.core.store.entity.Stores;

import java.util.ArrayList;

public class SettingAppFragment extends SimiFragment {

	protected TextView tv_language;
	protected LayoutRipple rl_language;
	protected TextView tv_language_selected;
	protected TextView tv_currency;
	protected LayoutRipple rl_currency;
	protected TextView tv_currency_selected;
	protected TextView tv_notification;
	protected RelativeLayout rl_notification;
	protected TextView tv_locator;
	protected LayoutRipple rl_locator;
	protected String currency;
	protected String language = "";
	protected Context mContext;

	public static SettingAppFragment newInstance() {
		SettingAppFragment fragment = new SettingAppFragment();
		return fragment;
	}

	@Override
	public View onCreateView(final LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		View rootView = null;
		// if (DataLocal.isLanguageRTL) {
		// rootView = inflater.inflate(
		// Rconfig.getInstance().layout("rtl_setting_layout"),
		// container, false);
		// } else {
		rootView = inflater.inflate(
				Rconfig.getInstance().layout("core_setting_layout"), container,
				false);
		// }
		mContext = getActivity();
		tv_language = (TextView) rootView.findViewById(Rconfig.getInstance()
				.id("tv_language"));
		tv_language.setText(Config.getInstance().getText("Language"));
		tv_language.setTextColor(Config.getInstance().getContent_color());
		tv_language_selected = (TextView) rootView.findViewById(Rconfig
				.getInstance().id("tv_language_selected"));
		boolean checkLanguage = false;
		ArrayList<LocaleConfigEntity> localeArr = DataLocal.listLocale;
		if(localeArr != null && localeArr.size() > 0) {
			language = localeArr.get(0).getName();
			for (int i = 0; i < localeArr.size(); i++) {
				if (DataLocal.getLocale().equals(localeArr.get(i).getCode())) {
					language = localeArr.get(i).getName();
				}
			}
		}
		tv_language_selected.setText(language);
		rl_language = (LayoutRipple) rootView.findViewById(Rconfig
				.getInstance().id("rl_language"));
		tv_currency = (TextView) rootView.findViewById(Rconfig.getInstance()
				.id("tv_currency"));
		tv_currency.setText(Config.getInstance().getText("Currency"));
		tv_currency.setTextColor(Config.getInstance().getContent_color());
		tv_currency_selected = (TextView) rootView.findViewById(Rconfig
				.getInstance().id("tv_currency_selected"));
		currency = "";
		for (CurrencyEntity entity : DataLocal.listCurrency) {
			if (entity.getValue().equals(DataLocal.getCurrencyID())) {
				currency = entity.getTitle();
			}
		}
		tv_currency_selected.setText(currency);
		rl_currency = (LayoutRipple) rootView.findViewById(Rconfig
				.getInstance().id("rl_currency"));
		rl_currency.setVisibility(View.GONE);
		tv_notification = (TextView) rootView.findViewById(Rconfig
				.getInstance().id("tv_notification"));
		tv_notification.setBackgroundColor(Config.getInstance()
				.getApp_backrground());
		tv_notification.setTextColor(Config.getInstance().getContent_color());
		tv_notification.setText(Config.getInstance().getText(
				"Show notifications"));
		rl_notification = (RelativeLayout) rootView.findViewById(Rconfig
				.getInstance().id("rl_notification"));
		rl_notification.setBackgroundColor(Config.getInstance()
				.getApp_backrground());
		tv_locator = (TextView) rootView.findViewById(Rconfig.getInstance().id(
				"tv_locator"));
		tv_locator.setTextColor(Config.getInstance().getContent_color());
		tv_locator.setText(Config.getInstance().getText("Location Setting"));
		rl_locator = (LayoutRipple) rootView.findViewById(Rconfig.getInstance()
				.id("rl_locator"));

		View v_under = (View) rootView.findViewById(Rconfig.getInstance().id(
				"v_under"));
		v_under.setBackgroundColor(Config.getInstance().getApp_backrground());

		final ToggleButton tb_notification = (ToggleButton) rootView
				.findViewById(Rconfig.getInstance().id("tb_notification"));
		if (DataLocal.enableNotification()) {
			tb_notification.setChecked(true);
		} else {
			tb_notification.setChecked(false);
		}
		rl_notification.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (tb_notification.isChecked()) {
					tb_notification.setChecked(false);
					DataLocal.saveNotificationSet(false);
				} else {
					tb_notification.setChecked(true);
					DataLocal.saveNotificationSet(true);
				}
			}
		});
		// tb_notification
		// .setOnCheckedChangeListener(new OnCheckedChangeListener() {
		//
		// @Override
		// public void onCheckedChanged(CompoundButton buttonView,
		// boolean isChecked) {
		// DataLocal.saveNotificationSet(isChecked);
		// }
		// });

		rl_locator.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent viewIntent = new Intent(
						Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				startActivity(viewIntent);
			}
		});

		rl_currency.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				changeCurrency();
			}
		});

		rl_language.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				changeLanguage();
			}
		});

		setColor(rootView);

		return rootView;
	}

	private void setColor(View mView) {
		ImageView im_language_extend = (ImageView) mView.findViewById(Rconfig
				.getInstance().id("im_language_extend"));
		ImageView im_currency_extend = (ImageView) mView.findViewById(Rconfig
				.getInstance().id("im_currency_extend"));
		ImageView im_locator_extend = (ImageView) mView.findViewById(Rconfig
				.getInstance().id("im_locator_extend"));
		Drawable icon = mContext.getResources().getDrawable(
				Rconfig.getInstance().drawable("ic_extend"));
		icon.setColorFilter(Config.getInstance().getContent_color(),
				PorterDuff.Mode.SRC_ATOP);
		im_language_extend.setImageDrawable(icon);
		im_currency_extend.setImageDrawable(icon);
		im_locator_extend.setImageDrawable(icon);

		ImageView im_language = (ImageView) mView.findViewById(Rconfig
				.getInstance().id("im_language"));
		Drawable ic_lang = mContext.getResources().getDrawable(
				Rconfig.getInstance().drawable("ic_lang"));
		ic_lang.setColorFilter(Config.getInstance().getContent_color(),
				PorterDuff.Mode.SRC_ATOP);
		im_language.setImageDrawable(ic_lang);

		ImageView im_currency = (ImageView) mView.findViewById(Rconfig
				.getInstance().id("im_currency"));
		Drawable ic_currency = mContext.getResources().getDrawable(
				Rconfig.getInstance().drawable("ic_currency"));
		ic_currency.setColorFilter(Config.getInstance().getContent_color(),
				PorterDuff.Mode.SRC_ATOP);
		im_currency.setImageDrawable(ic_currency);

		ImageView im_notification = (ImageView) mView.findViewById(Rconfig
				.getInstance().id("im_notification"));
		Drawable ic_notification = mContext.getResources().getDrawable(
				Rconfig.getInstance().drawable("ic_notification"));
		ic_notification.setColorFilter(Config.getInstance().getContent_color(),
				PorterDuff.Mode.SRC_ATOP);
		im_notification.setImageDrawable(ic_notification);

		ImageView im_locator = (ImageView) mView.findViewById(Rconfig
				.getInstance().id("im_locator"));
		Drawable ic_location_setting = mContext.getResources().getDrawable(
				Rconfig.getInstance().drawable("ic_location_setting"));
		ic_location_setting.setColorFilter(Config.getInstance()
				.getContent_color(), PorterDuff.Mode.SRC_ATOP);
		im_locator.setImageDrawable(ic_location_setting);

		View v_language = (View) mView.findViewById(Rconfig.getInstance().id(
				"v_language"));
		View v_currency = (View) mView.findViewById(Rconfig.getInstance().id(
				"v_currency"));
		View v_notification = (View) mView.findViewById(Rconfig.getInstance()
				.id("v_notification"));
		View v_locator = (View) mView.findViewById(Rconfig.getInstance().id(
				"v_locator"));
		v_language.setBackgroundColor(Config.getInstance().getLine_color());
		v_currency.setBackgroundColor(Config.getInstance().getLine_color());
		v_notification.setBackgroundColor(Config.getInstance().getLine_color());
		v_locator.setBackgroundColor(Config.getInstance().getLine_color());

		mView.setBackgroundColor(Config.getInstance().getApp_backrground());
	}

	protected void changeCurrency() {
		ListCurrencyFragment fragment = ListCurrencyFragment.newInstance();
		fragment.setCurrent_item(currency);
		if (DataLocal.isTablet) {
			SimiManager.getIntance().replacePopupFragment(fragment);
		} else {
			SimiManager.getIntance().replaceFragment(fragment);
		}
	}

	protected void changeLanguage() {
		ListLanguageFragment fragment = ListLanguageFragment.newInstance();
		fragment.setCurrent_item(language);
		if (DataLocal.isTablet) {
			SimiManager.getIntance().replacePopupFragment(fragment);
		} else {
			SimiManager.getIntance().replaceFragment(fragment);
		}
	}
}
