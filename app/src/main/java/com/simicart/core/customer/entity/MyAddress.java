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
	private String mTaxVat;
	private String mGender;
	private String mDay;
	private String mMonth;
	private String mYear;
	private String mPhone;
	private String mEmail;
	private String mFax;
	private String mCompany;
	private String mTaxVatCheckout;
	private CountryAllowed mCountry;
	private StateOfCountry mStates;

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
		if(mJSON != null){
			if(mJSON.has(_id)){
				mID = getData(_id);
			}

			if(mJSON.has(first_name)){
				mPrefix = getData(first_name);
			}

			if(mJSON.has(last_name)){
				mSuffix = getData(last_name);
			}

			if(mJSON.has(phone)){
				mPhone = getData(phone);
			}

			if(mJSON.has(street)){
				mStreet = getData(street);
			}

			if(mJSON.has(zip)){
				mZipCode = getData(zip);
			}

			if(mJSON.has(city)){
				mCity = getData(city);
			}

			if(mJSON.has(country)){
				String countryValue = getData(country);
				if(Utils.validateString(countryValue)){
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

			if(mJSON.has(state)){
				String statesValue = getData(state);
				if(Utils.validateString(statesValue)){
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

	public String getFax() {
		return mFax;
	}

	public void setFax(String fax) {
		this.mFax = fax;
	}

	public String getCompany() {
		return mCompany;
	}

	public void setCompany(String company) {
		this.mCompany = company;
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

	public String getTaxvat() {
		return mTaxVat;
	}

	public void setTaxvat(String taxvat) {
		this.mTaxVat = taxvat;
	}

	public String getGender() {
		return mGender;
	}

	public void setGender(String gender) {
		this.mGender = gender;
	}

	public String getDay() {
		return mDay;
	}

	public void setDay(String day) {
		this.mDay = day;
	}

	public String getMonth() {
		return mMonth;
	}

	public void setMonth(String month) {
		this.mMonth = month;
	}

	public String getYear() {
		return mYear;
	}

	public void setYear(String year) {
		this.mYear = year;
	}

	public void setTaxvatCheckout(String tax) {
		this.mTaxVatCheckout = tax;
	}

	public String getTaxvatCheckout() {
		return this.mTaxVatCheckout;
	}

	public CountryAllowed getCountry() {
		if(mCountry == null)
			mCountry = new CountryAllowed();
		return mCountry;
	}

	public void setCountry(CountryAllowed mCountry) {
		this.mCountry = mCountry;
	}

	public StateOfCountry getStates() {
		if(mStates == null)
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
		// tax vat
		String taxvat = getTaxvat();
		if (Utils.validateString(taxvat)) {
			params.add(new BasicNameValuePair("taxvat", taxvat));
		}
		// tax vat check out
		String taxvatcheckout = getTaxvatCheckout();
		if (Utils.validateString(taxvatcheckout)) {
			params.add(new BasicNameValuePair("vat_id", taxvatcheckout));
		}
		// gender
		String gender = getGender();
		if (Utils.validateString(gender)) {
			params.add(new BasicNameValuePair("gender", Utils
					.getValueGender(gender)));
		}
		String day1 = getDay();
		if (Utils.validateString(day1)) {
			String day = "";
			if (getDay().length() == 1) {
				day = "0" + getDay();
			} else {
				day = getDay();
			}
			params.add(new BasicNameValuePair("day", "" + day + ""));
			String month = "";
			if (getMonth().length() == 1) {
				month = "0" + getMonth();
			} else {
				month = getMonth();
			}
			params.add(new BasicNameValuePair("month", "" + month + ""));
			params.add(new BasicNameValuePair("year", "" + getYear() + ""));
			params.add(new BasicNameValuePair("dob", "" + month + "/" + day
					+ "/" + getYear() + ""));
		}
		// Fax
		String fax = getFax();
		if (Utils.validateString(fax)) {
			params.add(new BasicNameValuePair("fax", fax));
		}
		// company
		String company = getCompany();
		if (Utils.validateString(company)) {
			params.add(new BasicNameValuePair("company", company));
		}

		return params;
	}

	public JSONObject paramJsonRequest() throws JSONException{
		JSONObject params = new JSONObject();

		// prefix
		String first_name = getPrefix();
		if (Utils.validateString(first_name)) {
			params.put("first_name", first_name);
		}
		// suffix
		String last_name = getSuffix();
		if (Utils.validateString(last_name)) {
			params.put("last_name", last_name);
		}

		String statename = "";
		if(getStates() != null) {
			statename = getStates().getName();
		}
		JSONObject param_state = new JSONObject();
		if (Utils.validateString(statename)) {
			try {
				param_state.put("name", statename);
				param_state.put("code", getStates().getCode());
			} catch (JSONException e) {
				param_state = null;
				Log.e("MyAddress", e.getMessage());
			}
			if(param_state != null) {
				if (Utils.validateString(param_state.toString())) {
					params.put("state", param_state);
				}
			}else{
				try {
					param_state.put("name", "");
					param_state.put("code", "");
				}catch (JSONException e) {

				}
				params.put("state", param_state);
			}
		}else{
			try {
				param_state.put("name", "");
				param_state.put("code", "");
			}catch (JSONException e) {

			}
			params.put("state", param_state);
		}

		// country name
		String countryname = "";
		if(getCountry() != null){
			countryname = getCountry().getName();
		}

		JSONObject param_country = new JSONObject();
		if (Utils.validateString(countryname)) {
			try {
				param_country.put("name", countryname);
				param_country.put("code", getCountry().getCode());
			} catch (JSONException e) {
				param_country = null;
				Log.e("MyAddress", e.getMessage());
			}
			if(param_country != null) {
				if (Utils.validateString(param_country.toString())) {
					params.put("country", param_country);
				}
			}else{
				try {
					param_country.put("name", "");
					param_country.put("code", "");
				}catch (JSONException e) {

				}
				params.put("country", param_country);
			}
		}else{
			try {
				param_country.put("name", "");
				param_country.put("code", "");
			}catch (JSONException e) {

			}
			params.put("country", param_country);
		}

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
		Log.e("MyAddress", "ZipCode" + zipcode);
		if (Utils.validateString(zipcode)) {
			params.put("zip", zipcode);
		}

		// city
		String city = getCity();
		if (Utils.validateString(city)) {
			params.put("city", city);
		}

		//dob
		String dob = getDay();
		if(Utils.validateString(dob)){
			String day = "";
			if (getDay().length() == 1) {
				day = "0" + getDay();
			} else {
				day = getDay();
			}

			String month = "";
			if (getMonth().length() == 1) {
				month = "0" + getMonth();
			} else {
				month = getMonth();
			}
			params.put("day", "" + day + "");
			params.put("month", "" + month + "");
			params.put("year", "" + getYear() + "");
			params.put("dob", "" + month + "/" + day
					+ "/" + getYear() + "");
		}

		//gender
		String gender = getGender();
		if (Utils.validateString(gender)){
			params.put("gender", Utils.getValueGender(gender));
		}

		//tax vat
		String tax = getTaxvat();
		if (Utils.validateString(tax)){
			params.put("taxvat", tax);
		}

		return params;
	}
}
