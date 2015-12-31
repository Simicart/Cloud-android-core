package com.simicart.core.catalog.product.entity.productEnity;

import com.simicart.core.base.model.entity.SimiEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by MSI on 07/12/2015.
 */
public class VariantsAttributeEntity extends SimiEntity {
    private String mID;
    private String mName;
    private String mType;
    private ArrayList<String> mValues;

    private String _id = "_id";
    private String name = "name";
    private String type = "type";
    private String values = "values";


    @Override
    public void parse() {
        // parse ID
        if (mJSON.has(_id)) {
            mID = getData(_id);
        }

        // parse name
        if (mJSON.has(name)) {
            mName = getData(name);
        }

        // parse type
        if (mJSON.has(type)) {
            mType = getData(type);
        }

        // parse values
        if (mJSON.has(values)) {
            mValues = getArrayData(values);
        }
    }

    public String getID() {
        return mID;
    }

    public void setID(String mID) {
        this.mID = mID;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getType() {
        return mType;
    }

    public void setType(String mType) {
        this.mType = mType;
    }

    public ArrayList<String> getValues() {
        return mValues;
    }

    public void setValues(ArrayList<String> mValues) {
        this.mValues = mValues;
    }
}
