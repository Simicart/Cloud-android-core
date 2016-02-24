package com.simicart.core.customer.entity;

import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.config.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class ShippingAddress extends SimiEntity {
	private String mFirstName;
	private String mLastName;
	private String mPhone;
	private String mStreet;
	private String mCity;
	private String mStateName;
	private String mStateCode;
	private String mPostCode;
	private String mCountryName;
	private String mCountryCode;

	@Override
	public void parse() {
		if(mJSON.has("first_name")) {
			try {
				mFirstName = mJSON.getString("first_name");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("last_name")) {
			try {
				mLastName = mJSON.getString("last_name");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("phone")) {
			try {
				mPhone = mJSON.getString("phone");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("country")) {
			try {
				JSONObject obj = mJSON.getJSONObject("country");
				if(obj.has("code")) {
					mCountryCode = obj.getString("code");
				}
				if(obj.has("name")) {
					mCountryName = obj.getString("name");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("zip")) {
			try {
				mPostCode = mJSON.getString("zip");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("state")) {
			try {
				JSONObject obj = mJSON.getJSONObject("state");
				if(obj.has("code")) {
					mStateCode = obj.getString("code");
				}
				if(obj.has("name")) {
					mStateName = obj.getString("name");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("street")) {
			try {
				mStreet = mJSON.getString("street");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if(mJSON.has("city")) {
			try {
				mCity = mJSON.getString("city");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	public String getmCity() {
		return mCity;
	}

	public void setmCity(String mCity) {
		this.mCity = mCity;
	}

	public String getmCountryCode() {
		return mCountryCode;
	}

	public void setmCountryCode(String mCountryCode) {
		this.mCountryCode = mCountryCode;
	}

	public String getmCountryName() {
		return mCountryName;
	}

	public void setmCountryName(String mCountryName) {
		this.mCountryName = mCountryName;
	}

	public String getmFirstName() {
		return mFirstName;
	}

	public void setmFirstName(String mFirstName) {
		this.mFirstName = mFirstName;
	}

	public String getmLastName() {
		return mLastName;
	}

	public void setmLastName(String mLastName) {
		this.mLastName = mLastName;
	}

	public String getmPhone() {
		return mPhone;
	}

	public void setmPhone(String mPhone) {
		this.mPhone = mPhone;
	}

	public String getmPostCode() {
		return mPostCode;
	}

	public void setmPostCode(String mPostCode) {
		this.mPostCode = mPostCode;
	}

	public String getmStateCode() {
		return mStateCode;
	}

	public void setmStateCode(String mStateCode) {
		this.mStateCode = mStateCode;
	}

	public String getmStateName() {
		return mStateName;
	}

	public void setmStateName(String mStateName) {
		this.mStateName = mStateName;
	}

	public String getmStreet() {
		return mStreet;
	}

	public void setmStreet(String mStreet) {
		this.mStreet = mStreet;
	}
}
