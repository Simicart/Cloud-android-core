package com.simicart.core.catalog.product.options.customoption.Custom.entity;

import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.catalog.product.options.customoption.value.entity.ValueCustomOptionEntity;
import com.simicart.core.common.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by MSI on 07/12/2015.
 */
public class CustomOptionEntity extends SimiEntity {

    private String mType;
    private String mTitle;
    private Float mPrice;
    private String mPriceType;
    private boolean isRequired;
    private boolean uncheckRequired;
    private String mProductID;
    private String mClientID;
    private ArrayList<ValueCustomOptionEntity> mValues;
    private String mID;
    private boolean isCompleteSelected;

    /* data for multi choice option
     */
    private float mMultiPrice;

    private String _id = "_id";
    private String title = "title";
    private String type = "type";
    private String un_checkRequired = "uncheckRequired";
    private String is_Required = "required";
    private String product_id = "product_id";
    private String client_id = "client_id";
    private String values = "values";

    @Override
    public void parse() {
        // parse client id
        if (mJSON.has(client_id)) {
            mClientID = getData(client_id);
        }

        // parse product id
        if (mJSON.has(product_id)) {
            mProductID = getData(product_id);
        }

        // parse title
        if (mJSON.has(title)) {
            mTitle = getData(title);
        }

        // parse type
        if (mJSON.has(type)) {
            mType = getData(type);
        }

        // parse uncheck required
        if (mJSON.has(un_checkRequired)) {
            String s_uncheck_required = getData(un_checkRequired);
            if (Utils.validateString(s_uncheck_required) && s_uncheck_required.equals("1")) {
                uncheckRequired = true;
            }
        }

        // parse isRequired
        if (mJSON.has(is_Required)) {
            String s_is_required = getData(is_Required);
            if (Utils.validateString(s_is_required) && s_is_required.equals("1")) {
                isRequired = true;
            }
        }

        // parse ID
        if (mJSON.has(_id)) {
            mID = getData(_id);
        }

        // pare values
        if (mJSON.has(values)) {
            mValues = new ArrayList<ValueCustomOptionEntity>();
            try {
                JSONArray array = mJSON.getJSONArray(values);
                if (null != array && array.length() > 0) {
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject json = array.getJSONObject(i);
                        ValueCustomOptionEntity value = new ValueCustomOptionEntity();
                        value.setJSONObject(json);
                        value.parse();
                        mValues.add(value);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    public ArrayList<ValueCustomOptionEntity> getValues() {
        return mValues;
    }

    public void setValues(ArrayList<ValueCustomOptionEntity> mValues) {
        this.mValues = mValues;
    }

    public String getID() {
        return mID;
    }

    public void setID(String mID) {
        this.mID = mID;
    }

    public String getType() {
        return mType;
    }

    public void setType(String mType) {
        this.mType = mType;
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

    public boolean isRequired() {
        return isRequired;
    }

    public void setIsRequired(boolean isRequired) {
        this.isRequired = isRequired;
    }

    public boolean isUncheckRequired() {
        return uncheckRequired;
    }

    public void setUncheckRequired(boolean uncheckRequired) {
        this.uncheckRequired = uncheckRequired;
    }

    public String getProductID() {
        return mProductID;
    }

    public void setProductID(String mProductID) {
        this.mProductID = mProductID;
    }

    public String getClientID() {
        return mClientID;
    }

    public void setClientID(String mClientID) {
        this.mClientID = mClientID;
    }

    public boolean isCompleteSelected() {
        return isCompleteSelected;
    }

    public void setCompleteSelected(boolean isCompleteSelected) {
        this.isCompleteSelected = isCompleteSelected;
    }

    public float getMultiPrice() {
        return mMultiPrice;
    }

    public void setMultiPrice(float mMultiPrice) {
        this.mMultiPrice = mMultiPrice;
    }
}
