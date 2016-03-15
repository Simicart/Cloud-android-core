package com.simicart.core.customer.entity;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Constants;

public class MyAddress extends SimiEntity {
    private String mID;
    private String mAddressID = "-1";
    private String mStateID;
    private String mPrefix;
    private String mName;
    private String mSuffix;
    private String mStreet;
    private String mCity;
    private String mStateName;
    private String mStateCode;
    private String mZipCode;
    private String mCountryName;
    private String mCountryCode;
    private String mPhone;
    private String mEmail;
    private CountryAllowed mCountry;
    private StateOfCountry mStates;

    private String mFirstName;
    private String mLastName;


    private String _id = "_id";
    private String first_name = "first_name";
    private String last_name = "last_name";
    private String phone = "phone";
    private String street = "street";
    private String zip = "zip";
    private String city = "city";
    private String country = "country";
    private String state = "state";

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

            if (mJSON.has(phone)) {
                mPhone = getData(phone);
            }

            if (mJSON.has(street)) {
                mStreet = getData(street);
            }

            if (mJSON.has(zip)) {
                mZipCode = getData(zip);
            }

            if (mJSON.has(city)) {
                mCity = getData(city);
            }

            if (mJSON.has(country)) {
                String countryValue = getData(country);
                if (Utils.validateString(countryValue)) {
                    mCountry = new CountryAllowed();
                    try {
                        mCountry.setJSONObject(new JSONObject(countryValue));
                        mCountry.parse();
                    } catch (JSONException e) {
                        mCountry = null;
                        Log.e("MyAddress", e.getMessage());
                    }
                }
            }

            if (mJSON.has(state)) {
                String statesValue = getData(state);
                if (Utils.validateString(statesValue)) {
                    mStates = new StateOfCountry();
                    try {
                        mStates.setJSONObject(new JSONObject(statesValue));
                        mStates.parse();
                    } catch (JSONException e) {
                        mStates = null;
                        Log.e("MyAddress", e.getMessage());
                    }
                }
            }
        }
    }


    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String first_name) {
        this.mFirstName = first_name;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String last_name) {
        this.mLastName = last_name;
    }


    public String getAddressId() {
        if (!Utils.validateString(mAddressID) || mAddressID.equals("-1")) {
            mAddressID = getData(Constants.ADDRESS_ID);
        }
        if (!Utils.validateString(mAddressID)) {
            mAddressID = "0";
        }

        return mAddressID;
    }

    public void setAddressId(String addressId) {
        this.mAddressID = addressId;
    }

    public String getStateId() {
        return mStateID;
    }

    public void setStateId(String stateId) {
        this.mStateID = stateId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getStreet() {
        return mStreet;
    }

    public void setStreet(String street) {
        this.mStreet = street;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        this.mCity = city;
    }

    public String getStateName() {
        return mStateName;
    }

    public void setStateName(String stateName) {
        this.mStateName = stateName;
    }

    public String getStateCode() {
        return mStateCode;
    }

    public void setStateCode(String stateCode) {
        this.mStateCode = stateCode;
    }

    public String getZipCode() {
        return mZipCode;
    }

    public void setZipCode(String zipCode) {
        this.mZipCode = zipCode;
    }

    public String getCountryName() {
        return mCountryName;
    }

    public void setCountryName(String countryName) {
        this.mCountryName = countryName;
    }

    public String getCountryCode() {
        return mCountryCode;
    }

    public void setCountryCode(String countryCode) {
        this.mCountryCode = countryCode;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        this.mPhone = phone;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    public String getPrefix() {
        return mPrefix;
    }

    public void setPrefix(String prefix) {
        this.mPrefix = prefix;
    }

    public String getSuffix() {
        return mSuffix;
    }

    public void setSuffix(String suffix) {
        this.mSuffix = suffix;
    }


    public CountryAllowed getCountry() {
        if (mCountry == null)
            mCountry = new CountryAllowed();
        return mCountry;
    }

    public void setCountry(CountryAllowed mCountry) {
        this.mCountry = mCountry;
    }

    public StateOfCountry getStates() {
        if (mStates == null)
            mStates = new StateOfCountry();
        return mStates;
    }

    public void setStates(StateOfCountry mStates) {
        this.mStates = mStates;
    }

    public String getID() {
        return mID;
    }

    public void setID(String mID) {
        this.mID = mID;
    }

    public List<NameValuePair> toParamsRequest() {
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        String addressID = "";
        if (null == getAddressId() || getAddressId().equals("-1")
                || getAddressId().equals("")) {
            addressID = "0";
        } else {
            addressID = getAddressId();
        }
        params.add(new BasicNameValuePair("address_id", addressID));

        // name
        String name = getName();
        if (Utils.validateString(name)) {
            params.add(new BasicNameValuePair("name", name));
        }

        // street
        String street = getStreet();
        if (Utils.validateString(street)) {
            params.add(new BasicNameValuePair("street", street));
        }
        // city
        String city = getCity();
        if (Utils.validateString(city)) {
            params.add(new BasicNameValuePair("city", city));
        }
        // state name
        String statename = getStateName();
        if (Utils.validateString(statename)) {

            params.add(new BasicNameValuePair("state_code", getStateCode()));
//			params.add(new BasicNameValuePair("state_id", getStateId()));
            params.add(new BasicNameValuePair("state_name", statename));


        }
        // country name
        String countryname = getCountryName();

        if (null != countryname && !countryname.equals("")) {
            params.add(new BasicNameValuePair("country_code", getCountryCode()));
            params.add(new BasicNameValuePair("country_name", countryname));
        }
        // ZIP code
        String zipcode = getZipCode();
        if (Utils.validateString(zipcode)) {
            params.add(new BasicNameValuePair("zip", zipcode));
        }
//		if (null != zipcode && !zipcode.equals("")) {
//			params.add(new BasicNameValuePair("zip_code", zipcode));
//		}
        // phone
        String phone = getPhone();
        if (Utils.validateString(phone)) {
            params.add(new BasicNameValuePair("phone", phone));
        }
        // email
        String email = getEmail();
        if (Utils.validateString(email)) {
            params.add(new BasicNameValuePair("email", email));
        }
        // prefix
        String first_name = getPrefix();
        if (Utils.validateString(first_name)) {
            params.add(new BasicNameValuePair("first_name", first_name));
        }
        // suffix
        String last_name = getSuffix();
        if (Utils.validateString(last_name)) {
            params.add(new BasicNameValuePair("last_name", last_name));
        }

        return params;
    }

    public JSONObject paramJsonRequest() throws JSONException {
        JSONObject params = new JSONObject();

        // prefix
        String first_name = getFirstName();
        if (Utils.validateString(first_name)) {
            params.put("first_name", first_name);
        }
        // suffix
        String last_name = getLastName();
        if (Utils.validateString(last_name)) {
            params.put("last_name", last_name);
        }

        String state_name = "";
        StateOfCountry stateOfCountry = getStates();
        JSONObject param_state = new JSONObject();
        if (null != stateOfCountry) {
            state_name = stateOfCountry.getName();
        }
        String state_code = stateOfCountry.getCode();
        if (!Utils.validateString(state_name)) {
            state_name = "";
        }
        if (!Utils.validateString(state_code)) {
            state_code = "";
        }

        param_state.put("name", state_name);
        param_state.put("code", state_code);
        params.put("state", param_state);

        // country name
        String country_name = "";
        CountryAllowed countryAllowed = getCountry();
        if (null != countryAllowed) {
            country_name = countryAllowed.getName();
        }
        String country_code = countryAllowed.getCode();
        if (!Utils.validateString(country_name)) {
            country_name = "";
        }

        if (!Utils.validateString(country_code)) {
            country_code = "";
        }
        JSONObject param_country = new JSONObject();
        param_country.put("name", country_name);
        param_country.put("code", country_code);
        params.put("country", param_country);


        // street
        String street = getStreet();
        if (Utils.validateString(street)) {
            params.put("street", street);
        }

        // phone
        String phone = getPhone();
        if (Utils.validateString(phone)) {
            params.put("phone", phone);
        }

        // zip code
        String zipcode = getZipCode();
        if (Utils.validateString(zipcode)) {
            params.put("zip", zipcode);
        }

        // city
        String city = getCity();
        if (Utils.validateString(city)) {
            params.put("city", city);
        }


        return params;
    }
}
