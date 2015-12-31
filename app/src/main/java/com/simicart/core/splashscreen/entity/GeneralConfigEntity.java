package com.simicart.core.splashscreen.entity;

import com.simicart.core.base.model.entity.SimiEntity;

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

    private String store_name = "store_name";
    private String website_url = "website_url";
    private String country = "country";
    private String timezone = "timezone";
    private String email = "email";
    private String phone = "phone";
    private String locale = "locale";

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
}
