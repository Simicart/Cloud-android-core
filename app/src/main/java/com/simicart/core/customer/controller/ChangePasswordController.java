package com.simicart.core.customer.controller;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;

import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.core.customer.delegate.ChangePasswordDelegate;
import com.simicart.core.customer.entity.ProfileEntity;
import com.simicart.core.customer.model.ChangePassModel;

import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sony on 1/19/2016.
 */
public class ChangePasswordController extends SimiController {
    protected ChangePasswordDelegate mDelegate;
    protected OnTouchListener mOnTouchCurrentPass;
    protected OnTouchListener mOnTouchNewPass;
    protected OnTouchListener mOnTouchConfirmPass;
    protected TextWatcher mCurrentPassWatcher;
    protected TextWatcher mNewPassWatcher;
    protected TextWatcher mConfirmPassWatcher;
    protected OnClickListener mClicker;
    protected boolean isPassCondition = false;
    public static final int TOUCH_CURRENT_PASS = 0;
    public static final int TOUCH_NEW_PASS = 1;
    public static final int TOUCH_CONFIRM_PASS = 2;

    public static final int CHANGE_PASS = 0;// changepass
    public static final int REQUIRED_CURRENT_PASS = 1;// not type current pass
    public static final int REQUIRED_NEW_PASS = 2;// not type new pass
    public static final int REQUIRED_CONFIRM_PASS = 3;// confirm pass
    public static final int NOT_CHANGE = 4;// Not Change pass
    public static final int WRONG_CURRENT_PASS = 5;// Password is'nt correct

    public TextWatcher getCurrentPassWatcher() {
        return mCurrentPassWatcher;
    }

    public TextWatcher getNewPassWatcher() {
        return mNewPassWatcher;
    }

    public TextWatcher getConfirmPassWatcher() {
        return mConfirmPassWatcher;
    }

    public OnClickListener getSaveClicker() {
        return mClicker;
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

    public void setDelegate(ChangePasswordDelegate mDelegate) {
        this.mDelegate = mDelegate;
    }

    @Override
    public void onStart() {

        mCurrentPassWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String currentPassword = mDelegate.getCurrentPass();
                String newPassword = mDelegate.getNewPass();
                String confirmPassword = mDelegate.getConfirmPass();
                if (currentPassword.length() >= 6 && newPassword.length() >= 6 && confirmPassword.length() >= 6) {
                    isPassCondition = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        mNewPassWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String currentPassword = mDelegate.getCurrentPass();
                String newPassword = mDelegate.getNewPass();
                String confirmPassword = mDelegate.getConfirmPass();
                if (currentPassword.length() >= 6 && newPassword.length() >= 6 && confirmPassword.length() >= 6) {
                    isPassCondition = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        mConfirmPassWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String currentPassword = mDelegate.getCurrentPass();
                String newPassword = mDelegate.getNewPass();
                String confirmPassword = mDelegate.getConfirmPass();
                if (currentPassword.length() >= 6 && newPassword.length() >= 6 && confirmPassword.length() >= 6) {
                    isPassCondition = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

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

        mClicker = new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPassCondition == true) {
                    changePassWord();
                    Utils.hideKeyboard(v);
                }
               else {
                    String msg = "Please enter current password and new password";
                    SimiManager.getIntance().showNotify(null, msg, "Ok");
                }
            }
        };
    }

    private int checkPass(ProfileEntity profile) {
        String current_pass = profile.getCurrentPass();
        String new_pass = profile.getNewPass();
        String confirm_pass = profile.getConfirmPass();

        if (!Utils.validateString(current_pass)) {
            return REQUIRED_CURRENT_PASS;
        }

        if (!current_pass.equals(DataLocal.getPassword())) {
            return WRONG_CURRENT_PASS;
        }

        if (!Utils.validateString(new_pass)) {
            return REQUIRED_NEW_PASS;
        }

        if (!Utils.validateString(confirm_pass)) {
            return REQUIRED_CONFIRM_PASS;
        }

        if (!new_pass.equals(confirm_pass)) {
            return REQUIRED_CONFIRM_PASS;
        }

        return CHANGE_PASS;

    }

    protected void changePassWord() {
        ProfileEntity profile = mDelegate.getProfileEntity();
        String msg = "";
        switch (checkPass(profile)) {

            case REQUIRED_CURRENT_PASS:
                msg = "Current password field cannot be empty.";
                SimiManager.getIntance().showNotify(null, msg, "Ok");
                break;

            case WRONG_CURRENT_PASS:
                msg = "Current password isn't correct.";
                SimiManager.getIntance().showNotify(null, msg, "Ok");
                break;

            case REQUIRED_NEW_PASS:
                msg = "New password or Confirm password field cannot be empty.";
                SimiManager.getIntance().showNotify(null, msg, "Ok");
                break;

            case REQUIRED_CONFIRM_PASS:
                msg = "New password and Confirm password don't match.";
                SimiManager.getIntance().showNotify(null, msg, "Ok");
                break;

            case CHANGE_PASS:
                requestChangePass(profile);
                break;

            default:
                break;
        }


    }

    private void requestChangePass(final ProfileEntity profile) {
        mDelegate.showDialogLoading();
        ChangePassModel model = new ChangePassModel();
        model.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                mDelegate.dismissDialogLoading();
                if (error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "OK");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.dismissDialogLoading();
                saveData(profile);
                String msg = "Your password has changed successfully";
                SimiManager.getIntance().showNotify(null, msg, "Ok");
                SimiManager.getIntance().backToHomeFragment();
            }
        });
        model.addDataExtendURL("change-password");

        JSONObject data_body = getParam(profile);
        model.setJSONBody(data_body);

        model.request();
    }


    private JSONObject getParam(ProfileEntity profile) {
        JSONObject json = new JSONObject();
        try {
            json.put("email", DataLocal.getEmail());
            json.put("password", profile.getCurrentPass());
            json.put("newpassword", profile.getNewPass());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

    private void saveData(ProfileEntity profile) {
        DataLocal.saveData(profile.getEmail(),
                profile.getNewPass());
        DataLocal.saveEmailPassRemember(profile.getEmail(),
                profile.getNewPass());
    }


    @Override
    public void onResume() {

    }
}
