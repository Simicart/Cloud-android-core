package com.simicart.core.customer.controller;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.core.customer.delegate.ProfileDelegate;
import com.simicart.core.customer.entity.ConfigCustomerAddress;
import com.simicart.core.customer.entity.ProfileEntity;
import com.simicart.core.customer.model.ChangePassModel;
import com.simicart.core.customer.model.EditProfileModel;
import com.simicart.core.customer.model.ProfileModel;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileController extends SimiController {

	public static final int CHANGE_PASS = 0;// changepass
	public static final int REQUIRED_CURRENT_PASS = 1;// not type current pass
	public static final int REQUIRED_NEW_PASS = 2;// not type new pass
	public static final int REQUIRED_CONFIRM_PASS = 3;// confirm pass
	public static final int NOT_CHANGE = 4;// Not Change pass
	public static final int WRONG_CURRENT_PASS = 5;// Password is'nt correct

	public static final int TOUCH_CURRENT_PASS = 0;
	public static final int TOUCH_NEW_PASS = 1;
	public static final int TOUCH_CONFIRM_PASS = 2;

	protected ProfileDelegate mDelegate;

	protected OnClickListener mClicker, layout_gender_click;

	protected OnTouchListener mOnTouchCurrentPass;
	protected OnTouchListener mOnTouchNewPass;
	protected OnTouchListener mOnTouchConfirmPass;

	public OnClickListener getSaveClicker() {
		return mClicker;
	}

	public OnClickListener getOnclickGenderImage() {
		return layout_gender_click;
	}

	public OnTouchListener getOnTouchCurrentPass() {
		return mOnTouchCurrentPass;
	}

	public OnTouchListener getOnTouchNewPass() {
		return mOnTouchNewPass;
	}

	public OnTouchListener getOnTouchConfirmPass() {
		return mOnTouchConfirmPass;
	}

	public void setDelegate(ProfileDelegate delegate) {
		mDelegate = delegate;
	}

	@Override
	public void onStart() {
		mDelegate.showLoading();
		mModel = new ProfileModel();
		mModel.setDelegate(new ModelDelegate() {
			@Override
			public void onFail(SimiError error) {
				mDelegate.dismissLoading();
				if(error != null) {
					SimiManager.getIntance().showNotify(null, error.getMessage(), "OK");
				}
			}

			@Override
			public void onSuccess(SimiCollection collection) {
				mDelegate.dismissLoading();
				mDelegate.updateView(collection);
			}
		});
		mModel.addDataExtendURL(DataLocal.getCustomerID());
		mModel.request();

		mClicker = new OnClickListener() {

			@Override
			public void onClick(View v) {
				saveProfile();
				Utils.hideKeyboard(v);
			}
		};

		mOnTouchCurrentPass = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN: {
					mDelegate.onTouchDown(TOUCH_CURRENT_PASS);
					break;
				}
				case MotionEvent.ACTION_UP: {
					mDelegate.onTouchCancel(TOUCH_CURRENT_PASS);
					break;
				}
				case MotionEvent.ACTION_CANCEL: {
					mDelegate.onTouchCancel(TOUCH_CURRENT_PASS);
					break;
				}
				default:
					break;
				}
				return true;
			}
		};

		mOnTouchNewPass = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN: {
					mDelegate.onTouchDown(TOUCH_NEW_PASS);
					break;
				}
				case MotionEvent.ACTION_UP: {
					mDelegate.onTouchCancel(TOUCH_NEW_PASS);
					break;
				}
				case MotionEvent.ACTION_CANCEL: {
					mDelegate.onTouchCancel(TOUCH_NEW_PASS);
					break;
				}
				default:
					break;
				}
				return true;
			}
		};

		mOnTouchConfirmPass = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN: {
					mDelegate.onTouchDown(TOUCH_CONFIRM_PASS);
					break;
				}
				case MotionEvent.ACTION_UP: {
					mDelegate.onTouchCancel(TOUCH_CONFIRM_PASS);
					break;
				}
				case MotionEvent.ACTION_CANCEL: {
					mDelegate.onTouchCancel(TOUCH_CONFIRM_PASS);
					break;
				}
				default:
					break;
				}
				return true;
			}
		};
		layout_gender_click = new OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				// if(mDelegate.getGenderSpinner().performClick() == false){
				// mDelegate.getImageViewGender().setRotation(270);
				mDelegate.getGenderSpinner().performClick();
				// }else{
				// mDelegate.getImageViewGender().setRotation(90);
				// }

			}
		};
	}

	protected void saveProfile() {
		mModel = new ChangePassModel();
		ProfileEntity profile = mDelegate.getProfileEntity();
		if (isCompleteRequired(profile)) {
			switch (checkPass(profile)) {
			case CHANGE_PASS:
				requestChangeProfile(true, profile);
				break;
			case NOT_CHANGE:
				requestChangeProfile(false, profile);
				break;
			case REQUIRED_NEW_PASS:
				SimiManager
						.getIntance()
						.showNotify(
								null,
								"New password or Confirm password field cannot be empty.",
								"OK");
				break;
			case REQUIRED_CURRENT_PASS:
				SimiManager.getIntance().showNotify(null,
						"Current password field cannot be empty.", "OK");
				break;
			case REQUIRED_CONFIRM_PASS:
				SimiManager.getIntance().showNotify(null,
						"New password and Confirm password don't match.", "OK");
				break;
			case WRONG_CURRENT_PASS:
				SimiManager.getIntance().showNotify(null,
						"Current password isn't correct.", "OK");
				break;
			default:
				break;
			}
		} else {
			SimiManager.getIntance().showNotify(null,
					"Please select all (*) fields", "OK");
		}
	}

	private void requestChangePass(final ProfileEntity profile) {
		ChangePassModel model = new ChangePassModel();
		model.setDelegate(new ModelDelegate() {
			@Override
			public void onFail(SimiError error) {
				if(error != null) {
					SimiManager.getIntance().showNotify(null, error.getMessage(), "OK");
				}
			}

			@Override
			public void onSuccess(SimiCollection collection) {
				mDelegate.dismissLoading();

				DataLocal.saveData(profile.getEmail(),
						profile.getNewPass());
				DataLocal.saveEmailPassRemember(profile.getEmail(),
						profile.getNewPass());
			}
		});
		model.addDataExtendURL("change-password");
		JSONObject obj = new JSONObject();
		try {
			obj.put("email", profile.getEmail());
			obj.put("password", profile.getCurrentPass());
			obj.put("newpassword", profile.getNewPass());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		model.setJSONBody(obj);

		model.request();
	}

	private void requestChangeProfile(final boolean _changePass,
			final ProfileEntity profile) {
		mDelegate.showLoading();
		mModel = new EditProfileModel();
		mModel.setDelegate(new ModelDelegate() {
			@Override
			public void onFail(SimiError error) {
				mDelegate.dismissLoading();
				if(error != null) {
					SimiManager.getIntance().showNotify(null, error.getMessage(), "OK");
				}
			}

			@Override
			public void onSuccess(SimiCollection collection) {
				mDelegate.dismissLoading();
				ProfileEntity entity = (ProfileEntity) collection.getCollection().get(0);
				if (_changePass) {
					requestChangePass(profile);
				} else {
					DataLocal.saveData(entity.getFirstName() + " " + entity.getLastName() ,entity.getEmail(),
							DataLocal.getPassword());

					DataLocal.saveCustomer(entity.getFirstName(), entity.getLastName(), entity.getEmail(),
							entity.getFirstName() + " " + entity.getLastName(), DataLocal.getCustomerID());

					DataLocal.saveEmailPassRemember(entity.getEmail(),
							DataLocal.getPassword());

				}
				SimiManager.getIntance().showNotify("SUCCESS", Config.getInstance().getText("The account information has been saved."),
						"OK");
				SimiManager.getIntance().onUpdateItemSignIn();
			}
		});
		mModel.addDataExtendURL(DataLocal.getCustomerID());
		JSONObject obj = new JSONObject();
		try {
			obj.put("first_name", profile.getFirstName());
			obj.put("last_name", profile.getLastName());
			obj.put("email", profile.getEmail());
			obj.put("name", profile.getName());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		mModel.setJSONBody(obj);

		mModel.request();
	}

	private int checkPass(ProfileEntity profile) {
		if ((profile.getConfirmPass() == null || profile.getConfirmPass()
				.equals(""))
				|| (profile.getNewPass() == null || profile.getNewPass()
						.equals(""))
				|| (profile.getCurrentPass() == null || profile
						.getCurrentPass().equals(""))) {
			if ((profile.getConfirmPass() == null || profile.getConfirmPass()
					.equals(""))
					&& (profile.getNewPass() == null || profile.getNewPass()
							.equals(""))
					&& (profile.getCurrentPass() == null || profile
							.getCurrentPass().equals(""))) {
				return NOT_CHANGE;
			} else if ((profile.getCurrentPass() == null || profile
					.getCurrentPass().equals(""))) {
				return REQUIRED_CURRENT_PASS;
			} else {
				return REQUIRED_NEW_PASS;
			}
		} else {
			if (profile.getNewPass().equals(profile.getConfirmPass())) {
				if(!profile.getCurrentPass().equals(DataLocal.getPassword())) {
					return WRONG_CURRENT_PASS;
				} else {
					return CHANGE_PASS;
				}
			} else {
				return REQUIRED_CONFIRM_PASS;
			}
		}
	}

	private boolean isCompleteRequired(ProfileEntity profile) {
		ConfigCustomerAddress _configCustomer = DataLocal.ConfigCustomerProfile;
		// Chua check phone
		if (profile.getName().equals("") || profile.getEmail().equals("")) {
			return true;
		} else {
			if (_configCustomer.getPrefix().toLowerCase().equals("req")
					&& profile.getFirstName().equals("")) {
				return false;
			}
			if (_configCustomer.getSuffix().toLowerCase().equals("req")
					&& profile.getLastName().equals("")) {
				return false;
			}
//			if (_configCustomer.getGender().toLowerCase().equals("req")
//					&& profile.getGender().equals("")) {
//				return false;
//			}
//			if (_configCustomer.getTaxvat().toLowerCase().equals("req")
//					&& profile.getTaxVat().equals("")) {
//				return false;
//			}
//			if (_configCustomer.getDob().toLowerCase().equals("req")
//					&& profile.getDay().equals("")) {
//				return false;
//			}
			return true;
		}
	}

	@Override
	public void onResume() {
		mDelegate.updateView(mModel.getCollection());
	}
}
