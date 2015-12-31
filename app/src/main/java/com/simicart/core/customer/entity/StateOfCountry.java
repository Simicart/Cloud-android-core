package com.simicart.core.customer.entity;

import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.config.Constants;

public class StateOfCountry extends SimiEntity {
	protected String mCode;
	protected String mName;

	private String code = "code";
	private String name = "name";

	@Override
	public void parse() {
		if(mJSON != null){
			if(mJSON.has(code)){
				mCode = getData(code);
			}

			if(mJSON.has(name)){
				mName = getData(name);
			}
		}
	}

	public String getCode() {
		return mCode;
	}

	public void setCode(String mCode) {
		this.mCode = mCode;
	}

	public String getName() {
		return mName;
	}

	public void setName(String mName) {
		this.mName = mName;
	}

}
