package com.simicart.plugins.payuindia.entity;

import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.common.Utils;

/**
 * Created by Sony on 2/17/2016.
 */
public class DataEntity extends SimiEntity {
    protected String mKey;
    protected String mTXNID;
    protected String mAmount;
    protected String mFirstName;
    protected String mEmail;
    protected String mPhone;
    protected String mProductInfo;
    protected String sURL;
    protected String fURL;
    protected String mSalt;
    protected String mOfferKey;
    protected String mCardBind;
    protected String mUserCredential;
    protected String mUdf1;
    protected String mUdf2;
    protected String mUdf3;
    protected String mUdf4;
    protected String mUdf5;
    protected boolean isSanBox;

    private String key = "key";
    private String txnid = "txnid";
    private String amount = "amount";
    private String firstname = "firstname";
    private String email = "email";
    private String phone = "phone";
    private String productinfo = "productinfo";
    private String surl = "surl";
    private String furl = "furl";
    private String salt = "salt";
    private String offerKey = "offerKey";
    private String cardBin = "cardBin";
    private String user_credential = "user_credential";
    private String udf1 = "udf1";
    private String udf2 = "udf2";
    private String udf3 = "udf3";
    private String udf4 = "udf4";
    private String udf5 = "udf5";
    private String is_sanbox = "is_sanbox";

    @Override
    public void parse() {
        if (mJSON != null) {
            if (mJSON.has(key)) {
                mKey = getData(key);
            }

            if (mJSON.has(txnid)) {
                mTXNID = getData(txnid);
            }

            if (mJSON.has(amount)) {
                mAmount = getData(amount);
            }

            if (mJSON.has(firstname)) {
                mFirstName = getData(firstname);
            }

            if (mJSON.has(email)) {
                mEmail = getData(email);
            }

            if (mJSON.has(phone)) {
                mPhone = getData(phone);
            }

            if (mJSON.has(productinfo)) {
                mProductInfo = getData(productinfo);
            }

            if (mJSON.has(surl)) {
                sURL = getData(surl);
            }

            if (mJSON.has(furl)) {
                fURL = getData(furl);
            }

            if (mJSON.has(salt)) {
                mSalt = getData(salt);
            }

            if (mJSON.has(offerKey)) {
                mOfferKey = getData(offerKey);
            }

            if (mJSON.has(cardBin)) {
                mCardBind = getData(cardBin);
            }

            if (mJSON.has(user_credential)) {
                mUserCredential = getData(user_credential);
            }

            if (mJSON.has(udf1)) {
                mUdf1 = getData(udf1);
            }

            if (mJSON.has(udf2)) {
                mUdf1 = getData(udf2);
            }

            if (mJSON.has(udf3)) {
                mUdf1 = getData(udf3);
            }

            if (mJSON.has(udf4)) {
                mUdf1 = getData(udf4);
            }

            if (mJSON.has(udf5)) {
                mUdf1 = getData(udf5);
            }

            if(mJSON.has(is_sanbox)){
                String sanboxValue = getData(is_sanbox);
                if(Utils.validateString(sanboxValue) && sanboxValue.equals("1")){
                    isSanBox = true;
                }
            }
        }
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String mKey) {
        this.mKey = mKey;
    }

    public String getTXNID() {
        return mTXNID;
    }

    public void setTXNID(String mTXNID) {
        this.mTXNID = mTXNID;
    }

    public String getAmount() {
        return mAmount;
    }

    public void setAmount(String mAmount) {
        this.mAmount = mAmount;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getProductInfo() {
        return mProductInfo;
    }

    public void setProductInfo(String mProductInfo) {
        this.mProductInfo = mProductInfo;
    }

    public String getsURL() {
        return sURL;
    }

    public void setsURL(String sURL) {
        this.sURL = sURL;
    }

    public String getfURL() {
        return fURL;
    }

    public void setfURL(String fURL) {
        this.fURL = fURL;
    }

    public String getSalt() {
        return mSalt;
    }

    public void setSalt(String mSalt) {
        this.mSalt = mSalt;
    }

    public String getOfferKey() {
        return mOfferKey;
    }

    public void setOfferKey(String mOfferKey) {
        this.mOfferKey = mOfferKey;
    }

    public String getCardBind() {
        return mCardBind;
    }

    public void setCardBind(String mCardBind) {
        this.mCardBind = mCardBind;
    }

    public String getUserCredential() {
        return mUserCredential;
    }

    public void setUserCredential(String mUserCredential) {
        this.mUserCredential = mUserCredential;
    }

    public String getUdf1() {
        return mUdf1;
    }

    public void setUdf1(String mUdf1) {
        this.mUdf1 = mUdf1;
    }

    public String getUdf2() {
        return mUdf2;
    }

    public void setUdf2(String mUdf2) {
        this.mUdf2 = mUdf2;
    }

    public String getUdf3() {
        return mUdf3;
    }

    public void setUdf3(String mUdf3) {
        this.mUdf3 = mUdf3;
    }

    public String getUdf4() {
        return mUdf4;
    }

    public void setUdf4(String mUdf4) {
        this.mUdf4 = mUdf4;
    }

    public String getUdf5() {
        return mUdf5;
    }

    public void setUdf5(String mUdf5) {
        this.mUdf5 = mUdf5;
    }

    public boolean isSanBox() {
        return isSanBox;
    }

    public void setIsSanBox(boolean isSanBox) {
        this.isSanBox = isSanBox;
    }
}
