package com.simicart.core.checkout.entity;

import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Constants;

import org.json.JSONException;

public class ShippingMethod extends SimiEntity {
    private float mShippingCost;
    private String mShippingMethodCode;
    private String mShippingTitle = "";
    private String mShippingCurrency;
    private float mShippingTaxPercent;
    private String mServiceName;
    private String mCode;
    private String mParam;
    private Float mPrice;
    private String mDescription;

    private String service_name = "service_name";
    private String price = "price";
    private String code = "code";
    private String method_code = "method_code";
    private String params = "params";
    private String cost = "cost";
    private String title = "title";
    private String currency = "currency";
    private String shiping_tax_percent = "shiping_tax_percent";
    private String description = "description";

    @Override
    public void parse() {
        if (mJSON != null) {
            if (mJSON.has(cost)) {
                try {
                    mShippingCost = Float.parseFloat(mJSON.getString(cost));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (mJSON.has(method_code)) {
                try {
                    mShippingMethodCode = mJSON.getString(method_code);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (mJSON.has(title)) {
                try {
                    mShippingTitle = mJSON.getString(title);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (mJSON.has(currency)) {
                try {
                    mShippingCurrency = mJSON.getString(currency);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (mJSON.has(shiping_tax_percent)) {
                try {
                    mShippingTaxPercent = Float.parseFloat(mJSON.getString(shiping_tax_percent));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if(mJSON.has(service_name)){
                mServiceName = getData(service_name);
            }

            if(mJSON.has(code)){
                mCode = getData(code);
            }

            if(mJSON.has(params)){
                mParam = getData(params);
            }

            if(mJSON.has(price)){
                String priceValue = getData(price);
                if(Utils.validateString(priceValue)){
                    try {
                        mPrice = Float.parseFloat(priceValue);
                    }catch (Exception e){

                    }
                }
            }

            if(mJSON.has(description)){
                mDescription = getData(description);
            }
        }
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getServiceName() {
        return mServiceName;
    }

    public void setServiceName(String mServiceName) {
        this.mServiceName = mServiceName;
    }

    public Float getPrice() {
        return mPrice;
    }

    public void setPrice(Float mPrice) {
        this.mPrice = mPrice;
    }

    public String getParam() {
        return mParam;
    }

    public void setParam(String mParam) {
        this.mParam = mParam;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getmCode() {
        return mCode;
    }

    public void setmCode(String mCode) {
        this.mCode = mCode;
    }

    public String getmFee() {
        return mFee;
    }

    public void setmFee(String mFee) {
        this.mFee = mFee;
    }

    public String getmFeeInclTax() {
        return mFeeInclTax;
    }

    public void setmFeeInclTax(String mFeeInclTax) {
        this.mFeeInclTax = mFeeInclTax;
    }

    public String getmID() {
        return mID;
    }

    public void setmID(String mID) {
        this.mID = mID;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setCode(String _code) {
        mCode = _code;
    }

    public String getCode() {
        return mCode;
    }

    public String getmShippingMethodCode() {
        return mShippingMethodCode;
    }

    public void setmShipingMethodCode(String mShipingMethodCode) {
        this.mShippingMethodCode = mShipingMethodCode;
    }

    public float getmShippingCost() {
        return mShippingCost;
    }

    public void setmShippingCost(float mShippingCost) {
        this.mShippingCost = mShippingCost;
    }

    public String getmShippingCurrency() {
        return mShippingCurrency;
    }

    public void setmShippingCurrency(String mShippingCurrency) {
        this.mShippingCurrency = mShippingCurrency;
    }

    public float getmShippingTaxPercent() {
        return mShippingTaxPercent;
    }

    public void setmShippingTaxPercent(float mShippingTaxPercent) {
        this.mShippingTaxPercent = mShippingTaxPercent;
    }

    public String getmShippingTitle() {
        return mShippingTitle;
    }

    public void setmShippingTitle(String mShippingTitle) {
        this.mShippingTitle = mShippingTitle;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public static String getPlace_shipping_price() {
        return place_shipping_price;
    }

    public static void setPlace_shipping_price(String place_shipping_price) {
        ShippingMethod.place_shipping_price = place_shipping_price;
    }

	/*OLD CODE*/

    private String mID;
    //	private String mCode;
    private String mTitle;
    private String mFee;
    private String mFeeInclTax;
    private String mName;
    private boolean isSelected;
//    static String code = "";
//    static String title = "NONE";
    static String place_shipping_price = "0";

    public boolean isS_method_selected() {
        return isSelected;
    }

    public void setS_method_selected(boolean s_method_selected) {
        this.isSelected = s_method_selected;
    }

    public String getS_method_id() {
        if (mID == null) {
            mID = (getData(Constants.METHOD_ID));
        }
        return mID;
    }

    public void setS_method_id(String s_method_id) {
        this.mID = s_method_id;
    }

    public String getS_method_code() {
        if (null == mCode) {
            mCode = getData(Constants.METHOD_CODE);
        }
        return mCode;
    }

    public void setS_method_code(String s_method_code) {
        this.mCode = s_method_code;
    }

    public String getS_method_title() {
        if (null == mTitle) {
            mTitle = getData(Constants.METHOD_TITLE);
        }
        return mTitle;
    }

    public void setS_method_title(String s_method_title) {
        this.mTitle = s_method_title;
    }

    public String getS_method_fee_incl_tax() {

        if (null == mFeeInclTax) {
            mFeeInclTax = getData(Constants.METHOD_FEE_INCL_TAX);
        }
        return mFeeInclTax;
    }

    public void setS_method_fee_incl_tax(String s_method_fee_incl_tax) {
        this.mFeeInclTax = s_method_fee_incl_tax;
    }

    public String getS_method_fee() {
        if (null == mFee) {
            mFee = getData(Constants.METHOD_FEE);
        }
        return mFee;
    }

    public void setS_method_fee(String s_method_fee) {
        this.mFee = s_method_fee;
    }

    public String getS_method_name() {

        if (null == mName) {
            mName = getData(Constants.METHOD_NAME);
        }
        return mName;
    }

    public void setS_method_name(String s_method_name) {
        this.mName = s_method_name;
    }

//    public static void setTitle(String _title) {
//        title = _title;
//    }
//
//    public static String getTitle() {
//        return title;
//    }

    public static void setPlaceShippingPrice(String price) {
        place_shipping_price = price;
    }

    public static String getPlaceShippingPrice() {
        return place_shipping_price;
    }

    public static void refreshShipping() {
//        code = "";
//        title = "NONE";
        place_shipping_price = "0";
    }

}
