package com.simicart.core.splashscreen.entity;

import com.simicart.core.base.model.entity.SimiEntity;

/**
 * Created by Sony on 12/10/2015.
 */
public class IconEntity extends SimiEntity{
    protected String mID;
    protected String mUrl;

    private String _id = "_id";
    private String url = "url";

    @Override
    public void parse() {
        if(mJSON != null){
            if(mJSON.has(_id)){
                mID = getData(_id);
            }

            if(mJSON.has(url)){
                mUrl = getData(url);
            }
        }
    }

    public String getID() {
        return mID;
    }

    public void setID(String mID) {
        this.mID = mID;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }
}
