package com.simicart.core.checkout.entity;

import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Constants;

public class PaymentMethod extends SimiEntity {
	protected int mshowType = -1;
	protected String mTitle;
	protected String mContent;
	protected String mPaymentMethod;
	protected String mID;
	protected boolean isEnable;
	protected String mName;
	protected String mClientId;
	protected int mPosition;
	protected String mUpdatedAt;
	protected String mCreatedAt;
	protected String mMethodCode;
	protected String mParams;
	private String mSandBox;
	private String mBnCode;
	private String mPaypalAction;
	protected boolean isCheck = false;

	private String _id = "_id";
	private String enable = "enable";
	private String title = "title";
	private String name = "name";
	private String position = "position";
	private String description = "description";
	private String updated_at = "updated_at";
	private String created_at = "created_at";
	private String method_code = "method_code";
	private String params = "params";
	private String type = "type";
	private String client_id = "client_id";
	private String sandbox = "sandbox";
	private String bncode = "bncode";
	private String payment_action = "payment_action";

	// Data place order
	private String mCurrentMethod = "";
	private String mPlacePaymentMethod = "";
	private String mPlaceCCType = "";
	private String mPlaceCCNumber = "";
	private String mPlaceCCExpMonth = "";
	private String mPlaceCCExpYear = "";
	private String mPlaceCCId = "";

	private static PaymentMethod instance;

	public static PaymentMethod getInstance() {
		if (null == instance) {
			instance = new PaymentMethod();
		}
		return instance;
	}

	@Override
	public void parse() {
		if(mJSON != null){
			if(mJSON.has(_id)){
				mID = getData(_id);
			}

			if(mJSON.has(enable)){
				String enableValue = getData(enable);
				if(Utils.validateString(enableValue) && enableValue.equals("1")){
					isEnable = true;
				}
			}

			if(mJSON.has(title)){
				mTitle = getData(title);
			}

			if(mJSON.has(name)){
				mName = getData(name);
			}

			if(mJSON.has(position)){
				String positionValue = getData(position);
				if(Utils.validateString(positionValue)){
					try {
						mPosition = Integer.parseInt(positionValue);
					}catch (Exception e){

					}
				}
			}

			if(mJSON.has(description)){
				mContent = getData(description);
			}

			if(mJSON.has(updated_at)){
				mUpdatedAt = getData(updated_at);
			}

			if(mJSON.has(created_at)){
				mCreatedAt = getData(created_at);
			}

			if(mJSON.has(method_code)){
				mMethodCode = getData(method_code);
			}

			if(mJSON.has(params)){
				mParams = getData(params);
			}

			if(mJSON.has(type)){
				String typeValue = getData(type);
				if(Utils.validateString(typeValue)){
					try {
						mshowType = Integer.parseInt(typeValue);
					}catch (Exception e){

					}
				}
			}

			if(mJSON.has(sandbox)){
				mSandBox = getData(sandbox);
			}

			if(mJSON.has(client_id)){
				mClientId = getData(client_id);
			}

			if(mJSON.has(bncode)){
				mBnCode = getData(bncode);
			}

			if(mJSON.has(payment_action)){
				mPaypalAction = getData(payment_action);
			}
		}
	}

	public String getPaypalAction() {
		return mPaypalAction;
	}

	public void setPaypalAction(String mPaypalAction) {
		this.mPaypalAction = mPaypalAction;
	}

	public void setBnCode(String mBnCode) {
		this.mBnCode = mBnCode;
	}

	public String getBnCode() {
		return mBnCode;
	}

	public void setSandBox(String mSandBox) {
		this.mSandBox = mSandBox;
	}

	public String getSandBox() {
		return mSandBox;
	}

	public int getShow_type() {
		return mshowType;
	}

	public void setShow_type(int show_type) {
		this.mshowType = show_type;
	}

	public String getTitle() {
		if (null == mTitle) {
			mTitle = getData(Constants.TITLE);
		}

		return mTitle;
	}

	public void setTitle(String title) {
		this.mTitle = title;
	}

	public String getContent() {
		return mContent;
	}

	public void setContent(String content) {
		this.mContent = content;
	}

	public String getID() {
		return mID;
	}

	public void setID(String mID) {
		this.mID = mID;
	}

	public boolean isEnable() {
		return isEnable;
	}

	public void setIsEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	public String getName() {
		return mName;
	}

	public void setName(String mName) {
		this.mName = mName;
	}

	public String getClientId() {
		return mClientId;
	}

	public void setClientId(String mClientId) {
		this.mClientId = mClientId;
	}

	public int getPosition() {
		return mPosition;
	}

	public void setPosition(int mPosition) {
		this.mPosition = mPosition;
	}

	public String getUpdatedAt() {
		return mUpdatedAt;
	}

	public void setUpdatedAt(String mUpdatedAt) {
		this.mUpdatedAt = mUpdatedAt;
	}

	public String getCreatedAt() {
		return mCreatedAt;
	}

	public void setCreatedAt(String mCreatedAt) {
		this.mCreatedAt = mCreatedAt;
	}

	public String getMethodCode() {
		return mMethodCode;
	}

	public void setMethodCode(String mMethodCode) {
		this.mMethodCode = mMethodCode;
	}

	public String getParams() {
		return mParams;
	}

	public void setParams(String mParams) {
		this.mParams = mParams;
	}

	public String getPayment_method() {
		return mPaymentMethod;
	}

	public String getCurrentMethod() {
		return mCurrentMethod;
	}

	public void setCurrentMethod(String currentMethod) {
		this.mCurrentMethod = currentMethod;
	}

	public void setPayment_method(String payment_method) {
		this.mPaymentMethod = payment_method;
	}

	public String getPlace_payment_method() {
		return mPlacePaymentMethod;
	}

	public void setPlace_payment_method(String place_payment_method) {
		this.mPlacePaymentMethod = place_payment_method;
	}

	public String getPlace_cc_type() {
		return mPlaceCCType;
	}

	public void setPlace_cc_type(String place_cc_type) {
		this.mPlaceCCType = place_cc_type;
	}

	public String getPlace_cc_number() {
		return mPlaceCCNumber;
	}

	public void setPlace_cc_number(String place_cc_number) {
		this.mPlaceCCNumber = place_cc_number;
	}

	public String getPlace_cc_exp_month() {
		return mPlaceCCExpMonth;
	}

	public void setPlace_cc_exp_month(String place_cc_exp_month) {
		this.mPlaceCCExpMonth = place_cc_exp_month;
	}

	public String getPlace_cc_exp_year() {
		return mPlaceCCExpYear;
	}

	public void setPlace_cc_exp_year(String place_cc_exp_year) {
		this.mPlaceCCExpYear = place_cc_exp_year;
	}

	public void setPlacePaymentMethod(String method) {
		mPlacePaymentMethod = method;
	}

	public String getPlacePaymentMethod() {
		return mPlacePaymentMethod;
	}

	public void setPlacecc_id(String cc_id) {
		mPlaceCCId = cc_id;
	}

	public String getPlacecc_id() {
		return mPlaceCCId;
	}

	public boolean equal(PaymentMethod other)
	{
		String code = other.getMethodCode();
		if(mMethodCode.equals(code))
		{
			return true;
		}

		return false;
	}

	public boolean isCheck() {
		return isCheck;
	}

	public void setIsCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}
}
