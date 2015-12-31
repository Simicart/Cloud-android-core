package com.simicart.core.base.model.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class SimiEntity {

    protected JSONObject mJSON;
    protected Bundle bundle = new Bundle();

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setJSONObject(JSONObject json) {
        this.mJSON = json;
    }

    public JSONObject getJSONObject() {
        return mJSON;
    }

    public String getData(String key) {
        if (mJSON != null && mJSON.has(key)) {
            try {
                return this.mJSON.getString(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public ArrayList<String> getArrayData(String key) {
        if (mJSON != null && mJSON.has(key)) {
            try {
                ArrayList<String> list = new ArrayList<>();
                JSONArray array = mJSON.getJSONArray(key);
                for (int i = 0; i < array.length(); i++) {
                    list.add(array.getString(i));
                }
                return list;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void parse() {

    }

    public boolean has(String key) {
        return mJSON.has(key);
    }

    public String getValue(String key) {
        try {
            return mJSON.getString(key);
        } catch (JSONException e) {
            return null;
        }
    }

}
