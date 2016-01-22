package com.simicart.core.splashscreen.entity;

import android.util.Log;

import com.simicart.core.base.model.entity.SimiEntity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by MSI on 02/12/2015.
 */
public class GeneralConfigEntity extends SimiEntity {
    protected String storeName;
    protected String websiteUrl;
    protected String mCountry;
    protected String timeZone;
    protected String mEmail;
    protected String mPhone;
    protected String mLocale;
    protected ArrayList<LocaleConfigEntity> mLocaleApp;

    private String store_name = "store_name";
    private String website_url = "website_url";
    private String country = "country";
    private String timezone = "timezone";
    private String email = "email";
    private String phone = "phone";
    private String locale = "locale";
    private String locale_app = "locale_app";

    @Override
    public void parse() {
        if (mJSON != null) {
            if (mJSON.has(store_name)) {
                storeName = getData(store_name);
            }

            if (mJSON.has(website_url)) {
                websiteUrl = getData(website_url);
            }

            if (mJSON.has(country)) {
                mCountry = getData(country);
            }

            if (mJSON.has(timezone)) {
                timeZone = getData(timezone);
            }

            if (mJSON.has(email)) {
                mEmail = getData(email);
            }

            if (mJSON.has(phone)) {
                mPhone = getData(phone);
            }

            if (mJSON.has(locale)) {
                mLocale = getData(locale);
            }

            if(mJSON.has(locale_app)){
                try {
                    JSONArray locateAr = mJSON.getJSONArray("locale_app");
                    if (null != locateAr && locateAr.length() > 0) {
                        mLocaleApp = new ArrayList<LocaleConfigEntity>();
                        for (int i = 0; i < locateAr.length(); i++){
                            LocaleConfigEntity localeConfigEntity = new LocaleConfigEntity();
                            localeConfigEntity.setJSONObject(locateAr.getJSONObject(i));
                            localeConfigEntity.parse();
                            mLocaleApp.add(localeConfigEntity);
                        }
                    }
                } catch (JSONException e) {
                    mLocaleApp = null;
                    Log.e("GeneralConfigEntity", "LocaleApp: " + e.getMessage());
                }
            }
        }
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        this.mCountry = country;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        this.mPhone = phone;
    }

    public String getLocale() {
        return mLocale;
    }

    public void setLocale(String locale) {
        this.mLocale = locale;
    }

    public ArrayList<LocaleConfigEntity> getLocaleApp() {
        return mLocaleApp;
    }

    public void setLocaleApp(ArrayList<LocaleConfigEntity> mLocaleApp) {
        this.mLocaleApp = mLocaleApp;
    }
}
