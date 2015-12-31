package com.simicart.core.customer.entity;

import android.util.Log;

import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class ProfileEntity extends SimiEntity {
    protected String mID;
    protected String mFirstName;
    protected String mLastName;
    protected String mEmail;
    protected String mName;
    protected boolean isStatus;
    protected int mSeqNo;
    protected String mCreateAt;
    protected String mUpdateAt;
    private String mCurrentPass;
    private String mNewPass;
    private String mConfirmPass;
    private ArrayList<MyAddress> mAddress;
    private String mGroupID;

    private String _id = "_id";
    private String first_name = "first_name";
    private String last_name = "last_name";
    private String email = "email";
    private String name = "name";
    private String status = "status";
    private String seq_no = "seq_no";
    private String created_at = "created_at";
    private String updated_at = "updated_at";
    private String addresses = "addresses";
    private String customer_group_id = "customer_group_id";


    public JSONObject toParam() {
        try {
            JSONObject json = new JSONObject();

            JSONObject json_data = new JSONObject();
            if (Utils.validateString(mFirstName)) {
                json_data.put("customer_first_name", mFirstName);
            }

            if (Utils.validateString(mEmail)) {
                json_data.put("customer_email", mEmail);
            }

            if (Utils.validateString(mGroupID)) {
                json_data.put("customer_group_id", mGroupID);
            }

            if (Utils.validateString(mName)) {
                json_data.put("customer_name", mName);
            }

            if (Utils.validateString(mID)) {
                json_data.put("customer_id", mID);
            }

            if (json_data.length() > 0) {
                json.put("customer", json_data);
                return json;
            }


            return null;
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    public void parse() {
        if (mJSON != null) {
            if (mJSON.has(_id)) {
                mID = getData(_id);
            }

            if (mJSON.has(first_name)) {
                mFirstName = getData(first_name);
            }

            if (mJSON.has(last_name)) {
                mLastName = getData(last_name);
            }

            if (mJSON.has(email)) {
                mEmail = getData(email);
            }

            if (mJSON.has(name)) {
                mName = getData(name);
            }

            if (mJSON.has(status)) {
                String statusValue = getData(status);
                if (Utils.validateString(statusValue) && statusValue.equals("1")) {
                    isStatus = true;
                }
            }

            if (mJSON.has(seq_no)) {
                String seqNoValue = getData(seq_no);
                if (Utils.validateString(seqNoValue)) {
                    try {
                        mSeqNo = Integer.parseInt(seqNoValue);
                    } catch (Exception e) {
                    }
                }
            }

            if (mJSON.has(created_at)) {
                mCreateAt = getData(created_at);
            }

            if (mJSON.has(updated_at)) {
                mUpdateAt = getData(updated_at);
            }

            if (mJSON.has(addresses)) {
                try {
                    JSONArray addressArr = mJSON.getJSONArray(addresses);
                    if (addressArr.length() > 0) {
                        mAddress = new ArrayList<MyAddress>();
                        for (int i = 0; i < addressArr.length(); i++) {
                            MyAddress myAddress = new MyAddress();
                            myAddress.setJSONObject(addressArr.getJSONObject(i));
                            myAddress.parse();
                            mAddress.add(myAddress);
                        }
                        if(mAddress.size() > 0) {
                            Collections.reverse(mAddress);
                        }
                    }
                } catch (JSONException e) {
                    mAddress = null;
                    Log.e("ProfileEntity", e.getMessage());
                }
            }

            if (mJSON.has(customer_group_id)) {
                mGroupID = getData(customer_group_id);
            }

        }
    }

    public String getID() {
        return mID;
    }

    public void setID(String mID) {
        this.mID = mID;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public boolean isStatus() {
        return isStatus;
    }

    public void setIsStatus(boolean isStatus) {
        this.isStatus = isStatus;
    }

    public int getSeqNo() {
        return mSeqNo;
    }

    public void setSeqNo(int mSeqNo) {
        this.mSeqNo = mSeqNo;
    }

    public String getCreateAt() {
        return mCreateAt;
    }

    public void setCreateAt(String mCreateAt) {
        this.mCreateAt = mCreateAt;
    }

    public String getUpdateAt() {
        return mUpdateAt;
    }

    public void setUpdateAt(String mUpdateAt) {
        this.mUpdateAt = mUpdateAt;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getName() {
        return mName;
    }

    public String getCurrentPass() {
        return mCurrentPass;
    }

    public void setCurrentPass(String _currentPass) {
        this.mCurrentPass = _currentPass;
    }

    public String getNewPass() {
        return mNewPass;
    }

    public void setNewPass(String _newPass) {
        this.mNewPass = _newPass;
    }

    public String getConfirmPass() {
        return mConfirmPass;
    }

    public void setConfirmPass(String _confirmPass) {
        this.mConfirmPass = _confirmPass;
    }

    public ArrayList<MyAddress> getAddress() {
        return mAddress;
    }

    public void setAddress(ArrayList<MyAddress> mAddress) {
        this.mAddress = mAddress;
    }

    public String getGroupID() {
        return mGroupID;
    }

    public void setGroupID(String mGroupID) {
        this.mGroupID = mGroupID;
    }
}
