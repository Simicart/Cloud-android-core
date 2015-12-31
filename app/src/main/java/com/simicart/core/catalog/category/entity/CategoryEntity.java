package com.simicart.core.catalog.category.entity;

import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.common.Utils;

/**
 * Created by Sony on 12/3/2015.
 */
public class CategoryEntity extends SimiEntity{
    protected String mId;
    protected String mName;
    protected String mSlug;
    protected String mDescription;
    protected boolean isStatus;
    protected boolean isHasChild;
    protected String mClienID;
    protected String mSqeNo;
    protected String mUpdatedAt;
    protected String mCreateAt;

    private String _id = "_id";
    private String name = "name";
    private String slug = "slug";
    private String description = "description";
    private String status = "status";
    private String has_children = "has_children";
    private String client_id = "client_id";
    private String seq_no = "seq_no";
    private String updated_at = "updated_at";
    private String created_at = "created_at";

    @Override
    public void parse() {
        if(mJSON != null){
            if(mJSON.has(_id)){
                mId = getData(_id);
            }

            if(mJSON.has(name)){
                mName = getData(name);
            }

            if(mJSON.has(slug)){
                mSlug = getData(slug);
            }

            if(mJSON.has(description)){
                mDescription = getData(description);
            }

            if(mJSON.has(status)){
                String statusEnable = getData(status);
                if(Utils.validateString(statusEnable) && statusEnable.equals("1")){
                    isStatus = true;
                }
            }

            if(mJSON.has(has_children)){
                String enable = getData(has_children);
                if (Utils.validateString(enable) && enable.equals("true")) {
                    isHasChild = true;
                }
            }

            if(mJSON.has(client_id)){
                mClienID = getData(client_id);
            }

            if(mJSON.has(seq_no)){
                mSqeNo = getData(seq_no);
            }

            if(mJSON.has(updated_at)){
                mUpdatedAt = getData(updated_at);
            }

            if(mJSON.has(created_at)){
                mCreateAt = getData(mCreateAt);
            }
        }
    }

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getSlug() {
        return mSlug;
    }

    public void setSlug(String mSlug) {
        this.mSlug = mSlug;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public boolean isStatus() {
        return isStatus;
    }

    public void setIsStatus(boolean isStatus) {
        this.isStatus = isStatus;
    }

    public boolean isHasChild() {
        return isHasChild;
    }

    public void setIsHasChild(boolean isHasChild) {
        this.isHasChild = isHasChild;
    }

    public String getClienID() {
        return mClienID;
    }

    public void setClienID(String mClienID) {
        this.mClienID = mClienID;
    }

    public String getSqeNo() {
        return mSqeNo;
    }

    public void setSqeNo(String mSqeNo) {
        this.mSqeNo = mSqeNo;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String mUpdatedAt) {
        this.mUpdatedAt = mUpdatedAt;
    }

    public String getCreateAt() {
        return mCreateAt;
    }

    public void setCreateAt(String mCreateAt) {
        this.mCreateAt = mCreateAt;
    }
}
