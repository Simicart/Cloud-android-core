package com.simicart.plugins.payuindia.entity;

import com.simicart.core.base.model.entity.SimiEntity;

/**
 * Created by Sony on 2/17/2016.
 */
public class HashEntity extends SimiEntity {
    protected String mPaymentHash;
    protected String mGetPaymentIBiBo;
    protected String mVasForMobile;
    protected String mPaymentRelated;
    protected String mVerifyPayment;
    protected String mDeleteUser;
    protected String mGetUser;
    protected String mEditUser;
    protected String mSaveUser;

    private String payment_hash = "payment_hash";
    private String get_merchant_ibibo_codes_hash = "get_merchant_ibibo_codes_hash";
    private String vas_for_mobile_sdk_hash = "vas_for_mobile_sdk_hash";
    private String payment_related_details_for_mobile_sdk_hash = "payment_related_details_for_mobile_sdk_hash";
    private String verify_payment_hash = "verify_payment_hash";
    private String delete_user_card_hash = "delete_user_card_hash";
    private String get_user_cards_hash = "get_user_cards_hash";
    private String edit_user_card_hash = "edit_user_card_hash";
    private String save_user_card_hash = "save_user_card_hash";

    @Override
    public void parse() {
        if (mJSON != null) {
            if (mJSON.has(payment_hash)) {
                mPaymentHash = getData(payment_hash);
            }

            if (mJSON.has(get_merchant_ibibo_codes_hash)) {
                mGetPaymentIBiBo = getData(get_merchant_ibibo_codes_hash);
            }

            if (mJSON.has(vas_for_mobile_sdk_hash)) {
                mVasForMobile = getData(vas_for_mobile_sdk_hash);
            }

            if (mJSON.has(payment_related_details_for_mobile_sdk_hash)) {
                mPaymentRelated = getData(payment_related_details_for_mobile_sdk_hash);
            }

            if (mJSON.has(verify_payment_hash)) {
                mVerifyPayment = getData(verify_payment_hash);
            }

            if (mJSON.has(delete_user_card_hash)) {
                mDeleteUser = getData(delete_user_card_hash);
            }

            if (mJSON.has(get_user_cards_hash)) {
                mGetUser = getData(get_user_cards_hash);
            }

            if (mJSON.has(edit_user_card_hash)) {
                mEditUser = getData(edit_user_card_hash);
            }

            if (mJSON.has(save_user_card_hash)) {
                mSaveUser = getData(save_user_card_hash);
            }
        }
    }

    public String getPaymentHash() {
        return mPaymentHash;
    }

    public void setPaymentHash(String mPaymentHash) {
        this.mPaymentHash = mPaymentHash;
    }

    public String getGetPaymentIBiBo() {
        return mGetPaymentIBiBo;
    }

    public void setGetPaymentIBiBo(String mGetPaymentIBiBo) {
        this.mGetPaymentIBiBo = mGetPaymentIBiBo;
    }

    public String getVasForMobile() {
        return mVasForMobile;
    }

    public void setVasForMobile(String mVasForMobile) {
        this.mVasForMobile = mVasForMobile;
    }

    public String getPaymentRelated() {
        return mPaymentRelated;
    }

    public void setPaymentRelated(String mPaymentRelated) {
        this.mPaymentRelated = mPaymentRelated;
    }

    public String getVerifyPayment() {
        return mVerifyPayment;
    }

    public void setVerifyPayment(String mVerifyPayment) {
        this.mVerifyPayment = mVerifyPayment;
    }

    public String getDeleteUser() {
        return mDeleteUser;
    }

    public void setDeleteUser(String mDeleteUser) {
        this.mDeleteUser = mDeleteUser;
    }

    public String getGetUser() {
        return mGetUser;
    }

    public void setGetUser(String mGetUser) {
        this.mGetUser = mGetUser;
    }

    public String getEditUser() {
        return mEditUser;
    }

    public void setEditUser(String mEditUser) {
        this.mEditUser = mEditUser;
    }

    public String getSaveUser() {
        return mSaveUser;
    }

    public void setSaveUser(String mSaveUser) {
        this.mSaveUser = mSaveUser;
    }
}
