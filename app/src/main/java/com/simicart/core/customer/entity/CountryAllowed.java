package com.simicart.core.customer.entity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.config.Constants;

public class CountryAllowed extends SimiEntity {
	protected String mID;
	protected String mName;
	protected String mCode;
	protected ArrayList<StateOfCountry> mState;

	private String _id = "_id";
	private String name = "name";
	private String code = "code";
	private String states = "states";

	@Override
	public void parse() {
		if(mJSON != null){
			if(mJSON.has(_id)){
				mID = getData(_id);
			}

			if(mJSON.has(name)){
				mName = getData(name);
			}

			if(mJSON.has(code)){
				mCode = getData(code);
			}

			if(mJSON.has(states)){
				try {
					JSONArray statesArray = new JSONArray(getData(states));
					if(statesArray.length() > 0){
						mState = new ArrayList<StateOfCountry>();
						for (int i = 0; i < statesArray.length(); i++) {
							JSONObject statesObject = statesArray.getJSONObject(i);
							StateOfCountry stateEntity = new StateOfCountry();
							stateEntity.setJSONObject(statesObject);
							stateEntity.parse();
							mState.add(stateEntity);
						}
					}
				} catch (JSONException e) {
					mState = null;
				}
			}
		}
	}

	public String getID() {
		return mID;
	}

	public void setID(String mID) {
		this.mID = mID;
	}

	public String getName() {
		return mName;
	}

	public void setName(String mName) {
		this.mName = mName;
	}

	public String getCode() {
		return mCode;
	}

	public void setCode(String mCode) {
		this.mCode = mCode;
	}

	public ArrayList<StateOfCountry> getState() {
		return mState;
	}

	public void setState(ArrayList<StateOfCountry> mState) {
		this.mState = mState;
	}

}
