package com.simicart.core.customer.controller;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.config.Config;
import com.simicart.core.customer.delegate.ForgotPasswordDelegate;
import com.simicart.core.customer.model.ForgotPasswordModel;
import com.simicart.core.customer.model.ResetPasswordModel;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgotPasswordController extends SimiController {

    protected ForgotPasswordDelegate mDelegate;
    protected OnClickListener mClicker;
    protected String resetToken = "";

    public void setDelegate(ForgotPasswordDelegate delegate) {
        mDelegate = delegate;
    }

    public OnClickListener getClicker() {
        return mClicker;
    }

    @Override
    public void onStart() {
        mClicker = new OnClickListener() {

            @Override
            public void onClick(View v) {

                onSend();
            }
        };

    }

    protected void onSend() {

        String email = mDelegate.getEmail();
        if (null == email || email.equals("")) {
            SimiManager.getIntance().showNotify(null,
                    Config.getInstance().getText("Please enter an email."),
                    Config.getInstance().getText("OK"));

            return;
        }
        String pass = mDelegate.getNewPass();
        if (null == pass || pass.equals("")) {
            SimiManager.getIntance().showNotify(null,
                    Config.getInstance().getText("Please enter your new password."),
                    Config.getInstance().getText("OK"));

            return;
        }
        String confirmPass = mDelegate.getConfirmPass();
        if (null == confirmPass || confirmPass.equals("")) {
            SimiManager.getIntance().showNotify(null,
                    Config.getInstance().getText("Please confirm your password"),
                    Config.getInstance().getText("OK"));

            return;
        }
        if (!confirmPass.equals(pass)) {
            SimiManager.getIntance().showNotify(null,
                    Config.getInstance().getText("Please confirm password not match with password"),
                    Config.getInstance().getText("OK"));

            return;
        }

        mDelegate.showLoading();
        resetToken(email, pass);

        mDelegate.dismissLoading();
    }

    public String resetToken(final String email, final String pass) {
        String token = "";
        final ForgotPasswordModel model = new ForgotPasswordModel();
        model.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if(error != null){
                    mDelegate.showNotify(error.getMessage());
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                resetToken = model.getResetToken();
                resetPassword(email, pass);
            }
        });
        model.addDataExtendURL("forget-password");
        model.addDataBody("email", email);
        model.request();
        return token;
    }

    public void resetPassword(String email, String pass) {
        ResetPasswordModel model = new ResetPasswordModel();
        model.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if(error != null) {
                    mDelegate.showNotify(error.getMessage());
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                SimiManager.getIntance().showToast(Config.getInstance().getText("Success"));
                SimiManager.getIntance().backPreviousFragment();
            }
        });
        model.addDataExtendURL("reset-password");
        JSONObject obj = new JSONObject();
        try {
            obj.put("email", email);
            obj.put("password", pass);
            obj.put("reset_token", resetToken);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        model.setJSONBody(obj);

        model.request();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub

    }

}
