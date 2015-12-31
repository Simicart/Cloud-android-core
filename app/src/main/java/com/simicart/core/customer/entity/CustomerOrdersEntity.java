package com.simicart.core.customer.entity;

import com.simicart.core.base.model.entity.SimiEntity;

import org.json.JSONException;

/**
 * Created by James Crabby on 12/8/2015.
 */
public class CustomerOrdersEntity extends SimiEntity {
    private String mCustomerID;
    private String mCustomerGroupID;
    private String mCustomerEmail;
    private String mCustomerFirstName;
    private String mCustomerLastName;

    @Override
    public void parse() {
        if(mJSON.has("customer_id")) {
            try {
                mCustomerID = mJSON.getString("customer_id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(mJSON.has("customer_group_id")) {
            try {
                mCustomerGroupID = mJSON.getString("customer_group_id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(mJSON.has("customer_email")) {
            try {
                mCustomerEmail = mJSON.getString("customer_email");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(mJSON.has("customer_first_name")) {
            try {
                mCustomerFirstName = mJSON.getString("customer_first_name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(mJSON.has("customer_last_name")) {
            try {
                mCustomerLastName = mJSON.getString("customer_last_name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public String getmCustomerEmail() {
        return mCustomerEmail;
    }

    public void setmCustomerEmail(String mCustomerEmail) {
        this.mCustomerEmail = mCustomerEmail;
    }

    public String getmCustomerFirstName() {
        return mCustomerFirstName;
    }

    public void setmCustomerFirstName(String mCustomerFirstName) {
        this.mCustomerFirstName = mCustomerFirstName;
    }

    public String getmCustomerGroupID() {
        return mCustomerGroupID;
    }

    public void setmCustomerGroupID(String mCustomerGroupID) {
        this.mCustomerGroupID = mCustomerGroupID;
    }

    public String getmCustomerID() {
        return mCustomerID;
    }

    public void setmCustomerID(String mCustomerID) {
        this.mCustomerID = mCustomerID;
    }

    public String getmCustomerLastName() {
        return mCustomerLastName;
    }

    public void setmCustomerLastName(String mCustomerLastName) {
        this.mCustomerLastName = mCustomerLastName;
    }
}
