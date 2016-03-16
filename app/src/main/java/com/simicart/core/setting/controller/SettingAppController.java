package com.simicart.core.setting.controller;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;

import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.config.DataLocal;
import com.simicart.core.setting.delegate.SettingAppDelegate;
import com.simicart.core.setting.entity.CurrencyEntity;
import com.simicart.core.setting.fragment.ListCurrencyFragment;
import com.simicart.core.setting.fragment.ListLanguageFragment;
import com.simicart.core.splashscreen.entity.LocaleConfigEntity;

import java.util.ArrayList;

/**
 * Created by Sony on 3/10/2016.
 */
public class SettingAppController extends SimiController {
    protected String language = "";
    protected String currency = "";
    protected SettingAppDelegate mDelegate;
    protected OnClickListener onClickLanguage;
    protected OnClickListener onClickCurrency;
    protected OnClickListener onClickNotification;
    protected OnClickListener onClickLocator;
    protected Context mContext;

    public void setDelegate(SettingAppDelegate mDelegate) {
        this.mDelegate = mDelegate;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public OnClickListener getOnClickLanguage() {
        return onClickLanguage;
    }

    public OnClickListener getOnClickCurrency() {
        return onClickCurrency;
    }

    public OnClickListener getOnClickNotification() {
        return onClickNotification;
    }

    public OnClickListener getOnClickLocator() {
        return onClickLocator;
    }

    @Override
    public void onStart() {
        getLanguage();
        getCurrency();

        onClickLanguage = new OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLanguage();
            }
        };

        onClickCurrency = new OnClickListener() {
            @Override
            public void onClick(View v) {
                changeCurrency();
            }
        };

        onClickNotification = new OnClickListener() {
            @Override
            public void onClick(View v) {
                mDelegate.selectNotification();
            }
        };

        onClickLocator = new OnClickListener() {
            @Override
            public void onClick(View v) {
                enableLocator();
            }
        };
    }

    @Override
    public void onResume() {

    }

    private void getLanguage() {
        ArrayList<LocaleConfigEntity> localeArr = DataLocal.listLocale;
        if (localeArr != null && localeArr.size() > 0) {
            language = localeArr.get(0).getName();
            for (int i = 0; i < localeArr.size(); i++) {
                LocaleConfigEntity localeConfigEntity = localeArr.get(i);
                if (DataLocal.getLocale().equals(localeConfigEntity.getCode())) {
                    language = localeConfigEntity.getName();
                }
            }
            mDelegate.updateLanguage(language);
        } else {
            mDelegate.hidenLanguage();
        }
    }

    private void getCurrency() {
        String currencyID = DataLocal.getCurrencyID();
        for (CurrencyEntity entity : DataLocal.listCurrency) {
            if (entity.getValue().equals(currencyID)) {
                currency = entity.getTitle();
            }
        }
        mDelegate.updateCurrency(currency);
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

    protected void changeCurrency() {
        ListCurrencyFragment fragment = ListCurrencyFragment.newInstance();
        fragment.setCurrent_item(currency);
        if (DataLocal.isTablet) {
            SimiManager.getIntance().replacePopupFragment(fragment);
        } else {
            SimiManager.getIntance().replaceFragment(fragment);
        }
    }

    protected void enableLocator() {
        Intent viewIntent = new Intent(
                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        mContext.startActivity(viewIntent);
    }
}
