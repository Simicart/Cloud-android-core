package com.simicart.core.customer.controller;

import android.view.MotionEvent;
import android.view.View;

import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.error.SimiError;
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
    protected OnClickListener mClicker;
    public static final int TOUCH_CURRENT_PASS = 0;
    public static final int TOUCH_NEW_PASS = 1;
    public static final int TOUCH_CONFIRM_PASS = 2;
    public static final int CHANGE_PASS = 0;// changepass
    public static final int REQUIRED_CURRENT_PASS = 1;// not type current pass
    public static final int REQUIRED_NEW_PASS = 2;// not type new pass
    public static final int REQUIRED_CONFIRM_PASS = 3;// confirm pass
    public static final int NOT_CHANGE = 4;// Not Change pass
    public static final int WRONG_CURRENT_PASS = 5;// Password is'nt correct

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
                changePassWord();
                Utils.hideKeyboard(v);
            }
        };
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
                if (!profile.getCurrentPass().equals(DataLocal.getPassword())) {
                    return WRONG_CURRENT_PASS;
                } else {
                    return CHANGE_PASS;
                }
            } else {
                return REQUIRED_CONFIRM_PASS;
            }
        }
    }

    protected void changePassWord() {
        ProfileEntity profile = mDelegate.getProfileEntity();
        switch (checkPass(profile)) {
            case NOT_CHANGE:
                SimiManager.getIntance().showNotify(null,
                        "Current password field cannot be empty.", "Ok");
                break;
            case CHANGE_PASS:
                requestChangePass(profile);
                break;
            case REQUIRED_NEW_PASS:
                SimiManager
                        .getIntance()
                        .showNotify(
                                null,
                                "New password or Confirm password field cannot be empty.",
                                "Ok");
                break;
            case REQUIRED_CURRENT_PASS:
                SimiManager.getIntance().showNotify(null,
                        "Current password field cannot be empty.", "Ok");
                break;
            case REQUIRED_CONFIRM_PASS:
                SimiManager.getIntance().showNotify(null,
                        "New password and Confirm password don't match.", "Ok");
                break;
            case WRONG_CURRENT_PASS:
                SimiManager.getIntance().showNotify(null,
                        "Current password isn't correct.", "Ok");
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
                if(error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "OK");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.dismissDialogLoading();

                DataLocal.saveData(profile.getEmail(),
                        profile.getNewPass());
                DataLocal.saveEmailPassRemember(profile.getEmail(),
                        profile.getNewPass());

                SimiManager.getIntance().showNotify(null, "Your password has changed successfully", "Ok");
            }
        });
        model.addDataExtendURL("change-password");
        JSONObject obj = new JSONObject();
        try {
            obj.put("email", DataLocal.getEmail());
            obj.put("password", profile.getCurrentPass());
            obj.put("newpassword", profile.getNewPass());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        model.setJSONBody(obj);

        model.request();
    }

    @Override
    public void onResume() {

    }
}
