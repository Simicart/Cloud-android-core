package com.simicart.core.checkout.entity;

import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.common.Utils;

/**
 * Created by Sony on 12/11/2015.
 */
public class CouponCodeEntity extends SimiEntity{
    protected String mID;
    protected String mCode;
    protected String mType;
    protected String mEmail;
    protected boolean isEnable;
    protected int mSeqNo;
    protected String mCreatAt;
    protected String mUpdateAt;
    protected String mDescription;

    private String _id = "_id";
    private String code = "code";
    private String type = "type";
    private String email = "email";
    private String status = "status";
    private String seq_no = "seq_no";
    private String created_at = "created_at";
    private String updated_at = "updated_at";
    private String description = "description";

    @Override
    public void parse() {
        if(mJSON != null){
            if(mJSON.has(_id)){
                mID = getData(_id);
            }

            if(mJSON.has(code)){
                mCode = getData(code);
            }

            if(mJSON.has(type)){
                mType = getData(type);
            }

            if(mJSON.has(email)){
                mEmail = getData(email);
            }

            if(mJSON.has(status)){
                String isEnableValue = getData(status);
                if(Utils.validateString(isEnableValue) && isEnableValue.equals("enabled")){
                    isEnable = true;
                }
            }

            if(mJSON.has(seq_no)){
                String seqNoValue = getData(seq_no);
                if(Utils.validateString(seqNoValue)){
                    try {
                        mSeqNo = Integer.parseInt(seqNoValue);
                    }catch (Exception e){

                    }
                }
            }

            if(mJSON.has(created_at)){
                mCreatAt = getData(created_at);
            }

            if(mJSON.has(updated_at)){
                mUpdateAt = getData(updated_at);
            }

            if(mJSON.has(description)){
                mDescription = getData(description);
            }
        }
    }

    public String getID() {
        return mID;
    }

    public void setID(String mID) {
        this.mID = mID;
    }

    public String getCode() {
        return mCode;
    }

    public void setCode(String mCode) {
        this.mCode = mCode;
    }

    public String getType() {
        return mType;
    }

    public void setType(String mType) {
        this.mType = mType;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setIsEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }

    public int getSeqNo() {
        return mSeqNo;
    }

    public void setSeqNo(int mSeqNo) {
        this.mSeqNo = mSeqNo;
    }

    public String getCreatAt() {
        return mCreatAt;
    }

    public void setCreatAt(String mCreatAt) {
        this.mCreatAt = mCreatAt;
    }

    public String getUpdateAt() {
        return mUpdateAt;
    }

    public void setUpdateAt(String mUpdateAt) {
        this.mUpdateAt = mUpdateAt;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }
}
