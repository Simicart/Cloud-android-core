package com.simicart.core.home.entity;

import android.util.Log;

import com.simicart.core.base.model.entity.SimiEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by MSI on 04/12/2015.
 */
public class SpotProductEntity extends SimiEntity {

    protected String mID;
    protected String mName;
    protected String mType;
    protected String phoneImage;
    protected String tabletImage;
    protected String mLimit;
    protected String mPosition;
    protected boolean mStatus;
    protected ArrayList<String> products;

    private String _id = "_id";
    private String name = "name";
    private String type = "type";
    private String phone_image = "phone_image";
    private String tablet_image = "tablet_iamge";
    private String limit = "limit";
    private String position = "position";
    private String status = "status";
    private String url = "url";

    @Override
    public void parse() {
        // parse id
        if (mJSON.has(_id)) {
            mID = getData(_id);
        }

        // parse type of spot product
        if (mJSON.has(type)) {
            mType = getData(type);
        }

        // parse name
        if (mJSON.has(name)) {
            mName = getData(name);
        }

        // parse phone image
        if (mJSON.has(phone_image)) {
            try {
                JSONObject js = mJSON.getJSONObject(phone_image);
                if (js.has(url)) {
                    phoneImage = js.getString(url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // parse tablet image
        if (mJSON.has(tablet_image)) {
            try {
                JSONObject js = mJSON.getJSONObject(tablet_image);
                if (js.has(url)) {
                    tabletImage = js.getString(tablet_image);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // parse limit
        if (mJSON.has(limit)) {
            limit = getData(limit);
        }

        // parse position
        if (mJSON.has(position)) {
            mPosition = getData(position);
        }

        // parse products
        if (mJSON.has("products")) {
            Log.e("SpotProductEntity", "Parse products");
            products = new ArrayList<String>();
            try {
                JSONArray array = mJSON.getJSONArray("products");
                Log.e("SpotProductEntity ", "Products " + array.toString());
                if (null != array && array.length() > 0) {
                    for (int i = 0; i < array.length(); i++) {
                        String js = array.getString(i);
                        Log.e("SpotProductEntity ", "Product JSONObject " + js.toString());
                        products.add(js.toString());
                    }
                }
            } catch (JSONException e) {
                Log.e("SpotProductEntity", "Parse products Exception " + e.getMessage());
            }
        }

    }


    public String getId() {
        return mID;
    }

    public void setId(String id) {
        this.mID = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        this.mType = type;
    }

    public String getPhoneImage() {
        return phoneImage;
    }

    public void setPhoneImage(String phoneImage) {
        this.phoneImage = phoneImage;
    }

    public String getTabletImage() {
        return tabletImage;
    }

    public void setTabletImage(String tabletImage) {
        this.tabletImage = tabletImage;
    }

    public String getLimit() {
        return mLimit;
    }

    public void setLimit(String limit) {
        this.mLimit = limit;
    }

    public String getPosition() {
        return mPosition;
    }

    public void setPosition(String position) {
        this.mPosition = position;
    }

    public boolean isStatus() {
        return mStatus;
    }

    public void setStatus(boolean status) {
        this.mStatus = status;
    }

    public void setProducts(ArrayList<String> products) {
        this.products = products;
    }

    public ArrayList<String> getProducts() {
        return this.products;
    }
}
