package com.simicart.core.catalog.product.options.groupitem;

import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.common.Utils;

/**
 * Created by MSI on 08/12/2015.
 */
public class ItemGroupEntity extends SimiEntity implements Comparable<ItemGroupEntity> {
    private String _id;
    private int mDefaultQty;
    private String mName;
    private float mPrice;
    private String mItemID;
    private String mProductID;
    private String mClientID;
    private float mSalePrice;
    private float mPriceTax;
    private float mSalePriceTax;
    private int mPosition;


    private String id = "_id";
    private String default_qty = "default_qty";
    private String position = "position";
    private String name = "name";
    private String price = "price";
    private String item_id = "item_id";
    private String product_id = "product_id";
    private String client_id = "client_id";
    private String sale_price = "sale_price";
    private String price_include_tax = "price_include_tax";
    private String sale_price_include_tax = "sale_price_include_tax";

    @Override
    public void parse() {
        // parse _id
        if (mJSON.has(id)) {
            _id = getData(id);
        }

        // parse default qty
        if (mJSON.has(default_qty)) {
            String defaultQty = getData(default_qty);
            try {
                mDefaultQty = Integer.parseInt(defaultQty);
            } catch (Exception e) {

            }
        }

        // parse name
        if (mJSON.has(name)) {
            mName = getData(name);
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

        // parse item id
        if (mJSON.has(item_id)) {
            mItemID = getData(item_id);
        }

        // parse product id
        if (mJSON.has(product_id)) {
            mProductID = getData(product_id);
        }

        // parse client id
        if (mJSON.has(client_id)) {
            mClientID = getData(client_id);
        }

        if (mJSON.has(sale_price)) {
            String s_sale_price = getData(sale_price);
            if (Utils.validateString(s_sale_price)) {
                try {
                    mSalePrice = Float.parseFloat(s_sale_price);
                } catch (Exception e) {

                }
            }
        }


        if (mJSON.has(price_include_tax)) {
            String s_price_tax = getData(price_include_tax);
            if (Utils.validateString(s_price_tax)) {
                try {
                    mPriceTax = Float.parseFloat(s_price_tax);
                } catch (Exception e) {

                }
            }
        }

        if (mJSON.has(sale_price_include_tax)) {
            String s_sale_price_tax = getData(sale_price_include_tax);
            if (Utils.validateString(s_sale_price_tax)) {
                try {
                    mSalePriceTax = Float.parseFloat(s_sale_price_tax);
                } catch (Exception e) {

                }
            }
        }

        if (mJSON.has(position)) {
            String s_position = getData(position);
            if (Utils.validateString(s_position)) {
                mPosition = Integer.parseInt(s_position);
            }
        }


    }

    public String getid() {
        return _id;
    }

    public void setid(String _id) {
        this._id = _id;
    }

    public int getDefaultQty() {
        return mDefaultQty;
    }

    public void setDefaultQty(int mDefaultQty) {
        this.mDefaultQty = mDefaultQty;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public float getPrice() {
        return mPrice;
    }

    public void setPrice(float mPrice) {
        this.mPrice = mPrice;
    }

    public String getItemID() {
        return mItemID;
    }

    public void setItemID(String mItemID) {
        this.mItemID = mItemID;
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


    public float getSalePrice() {
        return mSalePrice;
    }

    public void setSalePrice(float mSalePrice) {
        this.mSalePrice = mSalePrice;
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

    public int getPosition() {
        return mPosition;
    }

    @Override
    public int compareTo(ItemGroupEntity another) {
        int position = another.getPosition();
        return this.mPosition - position;
    }
}
