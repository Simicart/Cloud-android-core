package com.simicart.plugins.payuindia.entity;

import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.common.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sony on 2/17/2016.
 */
public class PayUEntity extends SimiEntity{
    protected HashEntity mHash;
    protected DataEntity mData;

    private String hash = "hash";
    private String data = "data";

    @Override
    public void parse() {
        if(mJSON != null){
            if(mJSON.has(hash)){
                String hashValue = getData(hash);
                if(Utils.validateString(hashValue)){
                    mHash = new HashEntity();
                    try {
                        mHash.setJSONObject(new JSONObject(hashValue));
                        mHash.parse();
                    } catch (JSONException e) {
                        mHash = null;
                    }
                }
            }

            if(mJSON.has(data)){
                String dataValue = getData(data);
                if(Utils.validateString(dataValue)){
                    mData = new DataEntity();
                    try {
                        mData.setJSONObject(new JSONObject(dataValue));
                        mData.parse();
                    } catch (JSONException e) {
                        mData = null;
                    }
                }
            }
        }
    }

    public HashEntity getHash() {
        return mHash;
    }

    public void setHash(HashEntity mHash) {
        this.mHash = mHash;
    }

    public DataEntity getData() {
        return mData;
    }

    public void setData(DataEntity mData) {
        this.mData = mData;
    }
}
