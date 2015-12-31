package com.simicart.core.catalog.product.entity.productEnity;

import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.common.Utils;

import java.util.HashMap;

/**
 * Created by MSI on 07/12/2015.
 */
public class VariantEntity extends SimiEntity {

    private String mID;
    private String mChildID;
    private String mSKU;
    private String mQty;
    private HashMap<String, String> mValuesID;
    private float mPrice;
    private float mSalePrice;
    private float mPriceTax;
    private float mSalePriceTax;
    private String mProductID;
    private String mClientID;

    private String _id = "_id";
    private String child_id = "child_id";
    private String sku = "sku";
    private String qty = "qty";
    private String price = "price";
    private String product_id = "product_id";
    private String client_id = "client_id";
    private String sale_price = "sale_price";
    private String price_include_tax = "price_include_tax";
    private String sale_price_include_tax = "sale_price_include_tax";


    @Override
    public void parse() {
        if (mJSON.has(_id)) {
            mID = getData(_id);
        }

        if (mJSON.has(child_id)) {
            mChildID = getData(child_id);
        }

        if (mJSON.has(sku)) {
            mSKU = getData(sku);
        }

        if (mJSON.has(qty)) {
            mQty = getData(qty);
        }

        if (mJSON.has(price)) {
            String s_price = getData(price);
            if (Utils.validateString(s_price)) {
                mPrice = Float.parseFloat(s_price);
            }
        }

        if (mJSON.has(product_id)) {
            mProductID = getData(product_id);
        }

        if (mJSON.has(client_id)) {
            mClientID = getData(client_id);
        }

        if (mJSON.has(sale_price)) {
            String s_sale_price = getData(sale_price);
            try {
                mSalePrice = Float.parseFloat(s_sale_price);
            } catch (Exception e) {

            }
        }

        if (mJSON.has(price_include_tax)) {
            String s_price_tax = getData(price_include_tax);
            try {
                mPriceTax = Float.parseFloat(s_price_tax);
            } catch (Exception e) {

            }
        }

        if (mJSON.has(sale_price_include_tax)) {
            String s_sale_price_tax = getData(sale_price_include_tax);
            try {
                mSalePriceTax = Float.parseFloat(s_sale_price_tax);
            } catch (Exception e) {

            }

        }

    }


    public String getID() {
        return mID;
    }

    public void setID(String mID) {
        this.mID = mID;
    }

    public String getChildID() {
        return mChildID;
    }

    public void setChildID(String mChildID) {
        this.mChildID = mChildID;
    }

    public String getSKU() {
        return mSKU;
    }

    public void setSKU(String mSKU) {
        this.mSKU = mSKU;
    }

    public String getQty() {
        return mQty;
    }

    public void setQty(String mQty) {
        this.mQty = mQty;
    }

    public HashMap<String, String> getValuesID() {
        return mValuesID;
    }

    public void setValuesID(HashMap<String, String> mValuesID) {
        this.mValuesID = mValuesID;
    }

    public float getPrice() {
        return mPrice;
    }

    public void setPrice(float mPrice) {
        this.mPrice = mPrice;
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

    public float getPriceTax() {
        return mPriceTax;
    }

    public void setPriceTax(float mPriceTax) {
        this.mPriceTax = mPriceTax;
    }

    public float getSalePriceTax() {
        return mSalePriceTax;
    }

    public void setSalePriceTax(float mSalePriceTax) {
        this.mSalePriceTax = mSalePriceTax;
    }

    public float getSalePrice() {
        return mSalePrice;
    }

    public void setSalePrice(float mSalePrice) {
        this.mSalePrice = mSalePrice;
    }
}
