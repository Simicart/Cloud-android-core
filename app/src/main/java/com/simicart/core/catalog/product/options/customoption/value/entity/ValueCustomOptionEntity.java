package com.simicart.core.catalog.product.options.customoption.value.entity;

import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.common.Utils;

/**
 * Created by MSI on 07/12/2015.
 */
public class ValueCustomOptionEntity extends SimiEntity {

    private String mTitle;
    private float mPrice;
    private String mPriceType;
    private String mID;
    private String mQty;
    private boolean is_checked;
    private String mName;
    /* data for text */
    private String mContentText;
    /* data for date, date time, time */
    private int mDay;
    private int mMonth;
    private int mYear;
    private int mMinute;
    private int mHour;
    private float mTaxPrice;
    private float mSalePrice;
    private float mSaleTaxPrice;


    private String title = "title";
    private String price = "price";
    private String price_type = "price_type";
    private String _id = "_id";
    private String price_include_tax = "price_include_tax";
    private String sale_price = "sale_price";
    private String sale_price_tax = "sale_price_include_tax";
    private String name = "name";

    @Override
    public void parse() {
        // parse title
        if (mJSON.has(title)) {
            mTitle = getData(title);
        }

        // parse price
        if (mJSON.has(price)) {
            String s_price = getData(price);
            if (Utils.validateString(s_price)) {
                try {
                    mPrice = Float.parseFloat(s_price);
                } catch (Exception e) {

                }
            }
        }

        // parse sale price
        if (mJSON.has(sale_price)) {
            String s_sale_price = getData(sale_price);
            if (Utils.validateString(s_sale_price)) {
                try {
                    mSalePrice = Float.parseFloat(s_sale_price);
                } catch (Exception e) {

                }
            }
        }

        // parse sale price tax
        if (mJSON.has(sale_price_tax)) {
            String s_sale_price_tax = getData(sale_price_tax);
            if (Utils.validateString(s_sale_price_tax)) {
                try {
                    mSaleTaxPrice = Float.parseFloat(s_sale_price_tax);
                } catch (Exception e) {

                }
            }
        }

        // parse price type
        if (mJSON.has(price_type)) {
            mPriceType = getData(price_type);
        }

        // parse ID
        if (mJSON.has(_id)) {
            mID = getData(_id);
        }

        // parse name
        if (mJSON.has(name)) {
            mName = getData(name);
        }

        if (mJSON.has(price_include_tax)) {
            String s_tax_price = getData(price_include_tax);
            if (Utils.validateString(s_tax_price)) {
                try {
                    mTaxPrice = Float.parseFloat(s_tax_price);
                } catch (Exception e) {

                }
            }
        }

    }

    public String getContentText() {
        return mContentText;
    }

    public void setContentText(String mContentText) {
        this.mContentText = mContentText;
    }

    public boolean isChecked() {
        return is_checked;
    }

    public void setChecked(boolean is_checked) {
        this.is_checked = is_checked;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public Float getPrice() {
        return mPrice;
    }

    public void setPrice(Float mPrice) {
        this.mPrice = mPrice;
    }

    public String getPriceType() {
        return mPriceType;
    }

    public void setPriceType(String mPriceType) {
        this.mPriceType = mPriceType;
    }

    public String getID() {
        return mID;
    }

    public void setID(String mID) {
        this.mID = mID;
    }

    public String getQty() {
        return mQty;
    }

    public void setQty(String mQty) {
        this.mQty = mQty;
    }

    public int getDay() {
        return mDay;
    }

    public void setDay(int mDay) {
        this.mDay = mDay;
    }

    public int getMonth() {
        return mMonth;
    }

    public void setMonth(int mDate) {
        this.mMonth = mDate;
    }

    public int getYear() {
        return mYear;
    }

    public void setYear(int mYear) {
        this.mYear = mYear;
    }

    public int getMinute() {
        return mMinute;
    }

    public void setMinute(int mMinute) {
        this.mMinute = mMinute;
    }

    public int getHour() {
        return mHour;
    }

    public void setHour(int mHour) {
        this.mHour = mHour;
    }


    public float getTaxPrice() {
        return mTaxPrice;
    }

    public void setTaxPrice(float taxPrice) {
        mTaxPrice = taxPrice;
    }


    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public float getSalePrice() {
        return mSalePrice;
    }

    public void setSalePrice(float mSalePrice) {
        this.mSalePrice = mSalePrice;
    }

    public float getSaleTaxPrice() {
        return mSaleTaxPrice;
    }

    public void setSaleTaxPrice(float mSaleTaxPrice) {
        this.mSaleTaxPrice = mSaleTaxPrice;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }
}
