package com.simicart.core.catalog.product.options.customoption.entity;

import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.common.Utils;

/**
 * Created by MSI on 07/12/2015.
 */
public class ValueCustomOptionEntity extends SimiEntity {

    private String mTitle;
    private Float mPrice;
    private String mPriceType;
    private String mID;

    private String title = "title";
    private String price = "price";
    private String price_type = "price_type";
    private String _id = "_id";

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

        // parse price type
        if (mJSON.has(price_type)) {
            mPriceType = getData(price_type);
        }

        // parse ID
        if (mJSON.has(_id)) {
            mID = getData(_id);
        }

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
}
