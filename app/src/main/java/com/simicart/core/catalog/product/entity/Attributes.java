package com.simicart.core.catalog.product.entity;

import android.util.Log;

import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Attributes extends SimiEntity {
    protected String mTitle;
    protected ArrayList<String> mValue;
    protected String mID;
    protected String mName;
    protected String mCode;
    protected boolean isVisibleOnFront;
    protected String mClientID;
    protected String mType;
    protected int mSeqNo;
    protected String mUpdatedAt;
    protected String mCreatedAt;

    private String _id = "_id";
    private String name = "name";
    private String code = "code";
    private String is_visible_on_front = "is_visible_on_front";
    private String values = "values";
    private String client_id = "client_id";
    private String type = "type";
    private String seq_no = "seq_no";
    private String updated_at = "updated_at";
    private String created_at = "created_at";

    @Override
    public void parse() {
        if(mJSON != null){
            if(mJSON.has(_id)){
                mID = getData(_id);
            }

            if(mJSON.has(name)){
                mName = getData(name);
            }

            if(mJSON.has(code)){
                mCode = getData(code);
            }

            if(mJSON.has(is_visible_on_front)){
                String isVisibleValue = getData(is_visible_on_front);
                if(Utils.validateString(isVisibleValue) && isVisibleValue.equals("1")){
                    isVisibleOnFront = true;
                }
            }

            if(mJSON.has(client_id)){
                mClientID = getData(client_id);
            }

            if(mJSON.has(type)){
                mType = getData(type);
            }

            if(mJSON.has(seq_no)){
                String SeqNoValue = getData(seq_no);
                if(Utils.validateString(SeqNoValue)){
                    mSeqNo = Integer.parseInt(SeqNoValue);
                }
            }

            if(mJSON.has(updated_at)){
                mUpdatedAt = getData(updated_at);
            }

            if(mJSON.has(created_at)){
                mCreatedAt = getData(created_at);
            }

            if(mJSON.has(values)){
                mValue = new ArrayList<String>();
                try {
                    JSONArray valueArr = mJSON.getJSONArray(values);
                    if(valueArr != null && valueArr.length() > 0){
                        for (int i = 0; i < valueArr.length(); i++) {
                            mValue.add(valueArr.getString(i));
                        }
                    }
                } catch (JSONException e) {
                    mValue = null;
                    Log.e("Attibtes", e.getMessage());
                }
            }
        }
    }

    public ArrayList<String> getValue() {
        return mValue;
    }

    public void setValue(ArrayList<String> value) {
        this.mValue = value;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
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

    public void setName(String nName) {
        this.mName = nName;
    }

    public String getCode() {
        return mCode;
    }

    public void setCode(String mCode) {
        this.mCode = mCode;
    }

    public boolean IsVisibleOnFront() {
        return isVisibleOnFront;
    }

    public void setIsVisibleOnFront(boolean isVisibleOnFront) {
        this.isVisibleOnFront = isVisibleOnFront;
    }

    public String getClientID() {
        return mClientID;
    }

    public void setClientID(String mClientID) {
        this.mClientID = mClientID;
    }

    public String getType() {
        return mType;
    }

    public void setType(String mType) {
        this.mType = mType;
    }

    public int getSeqNo() {
        return mSeqNo;
    }

    public void setSeqNo(int mSeqNo) {
        this.mSeqNo = mSeqNo;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String mUpdatedAt) {
        this.mUpdatedAt = mUpdatedAt;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }
}
