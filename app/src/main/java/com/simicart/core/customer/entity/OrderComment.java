package com.simicart.core.customer.entity;

import com.simicart.core.base.model.entity.SimiEntity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by James Crabby on 12/8/2015.
 */
public class OrderComment extends SimiEntity {
    private String mComment;
    private int mIsCustomerNotify;
    private String mStatus;
    private String mCreateAtSec;
    private String mCreateAtUsec;
    private String mUpdateAtSec;
    private String mUpdateAtUsec;
    private String mID;

    @Override
    public void parse() {
        if(mJSON.has("comment")) {
            try {
                mComment = mJSON.getString("comment");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(mJSON.has("is_customer_notified")) {
            try {
                mIsCustomerNotify = mJSON.getInt("is_customer_notified");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(mJSON.has("status")) {
            try {
                mStatus = mJSON.getString("status");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(mJSON.has("created_at")) {
            try {
                JSONObject obj = mJSON.getJSONObject("created_at");
                if(obj.has("sec")) {
                    mCreateAtSec = obj.getString("sec");
                }
                if(obj.has("usec")) {
                    mCreateAtUsec = obj.getString("usec");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(mJSON.has("updated_at")) {
            try {
                JSONObject obj = mJSON.getJSONObject("updated_at");
                if(obj.has("sec")) {
                    mUpdateAtSec = obj.getString("sec");
                }
                if(obj.has("usec")) {
                    mUpdateAtUsec = obj.getString("usec");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(mJSON.has("_id")) {
            try {
                mID = mJSON.getString("_id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public String getmComment() {
        return mComment;
    }

    public void setmComment(String mComment) {
        this.mComment = mComment;
    }

    public String getmCreateAtSec() {
        return mCreateAtSec;
    }

    public void setmCreateAtSec(String mCreateAtSec) {
        this.mCreateAtSec = mCreateAtSec;
    }

    public String getmCreateAtUsec() {
        return mCreateAtUsec;
    }

    public void setmCreateAtUsec(String mCreateAtUsec) {
        this.mCreateAtUsec = mCreateAtUsec;
    }

    public String getmID() {
        return mID;
    }

    public void setmID(String mID) {
        this.mID = mID;
    }

    public int getmIsCustomerNotify() {
        return mIsCustomerNotify;
    }

    public void setmIsCustomerNotify(int mIsCustomerNotify) {
        this.mIsCustomerNotify = mIsCustomerNotify;
    }

    public String getmStatus() {
        return mStatus;
    }

    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public String getmUpdateAtSec() {
        return mUpdateAtSec;
    }

    public void setmUpdateAtSec(String mUpdateAtSec) {
        this.mUpdateAtSec = mUpdateAtSec;
    }

    public String getmUpdateAtUsec() {
        return mUpdateAtUsec;
    }

    public void setmUpdateAtUsec(String mUpdateAtUsec) {
        this.mUpdateAtUsec = mUpdateAtUsec;
    }
}
