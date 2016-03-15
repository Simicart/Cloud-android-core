package com.simicart.core.customer.controller;

import android.view.View;
import android.view.View.OnClickListener;

import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.customer.delegate.ForgotPasswordDelegate;
import com.simicart.core.customer.model.ForgotPasswordModel;

public class ForgotPasswordController extends SimiController {

    protected ForgotPasswordDelegate mDelegate;
    protected OnClickListener mClicker;

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
                SimiManager.getIntance().hideKeyboard();
                onSend();
            }
        };

    }

    protected void onSend() {

        String email = mDelegate.getEmail();
        if (!Utils.validateString(email)) {
            SimiManager.getIntance().showNotify(null,
                    Config.getInstance().getText("Please enter an email."),
                    Config.getInstance().getText("OK"));

            return;
        }

        resetToken(email);

    }

    public String resetToken(final String email) {
        mDelegate.showLoading();
        String token = "";
        final ForgotPasswordModel model = new ForgotPasswordModel();
        model.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                mDelegate.dismissLoading();
                if(error != null){
                    mDelegate.showNotify(error.getMessage());
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.dismissLoading();
                if(!model.getMessage().equals("")){
                    mDelegate.showNotify(model.getMessage());
                }
                SimiManager.getIntance().backPreviousFragment();
            }
        });
        model.addDataExtendURL("forget-password");
        model.addDataBody("email", email);
        model.request();
        return token;
    }


    @Override
    public void onResume() {
        // TODO Auto-generated method stub

    }

}
