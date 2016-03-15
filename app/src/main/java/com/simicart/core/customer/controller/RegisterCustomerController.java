package com.simicart.core.customer.controller;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.View.OnClickListener;

import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.config.Constants;
import com.simicart.core.config.DataLocal;
import com.simicart.core.customer.delegate.RegisterCustomerDelegate;
import com.simicart.core.customer.entity.ConfigCustomerAddress;
import com.simicart.core.customer.entity.RegisterCustomer;
import com.simicart.core.customer.fragment.SignInFragment;
import com.simicart.core.customer.model.CreateNewAccountModel;
import com.simicart.core.customer.model.RegisterCustomerModel;

import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint("DefaultLocale")
public class RegisterCustomerController extends SimiController {
    protected RegisterCustomerDelegate mDelegate;
    protected OnClickListener mRegister, onClickRelative;
    protected boolean isCheckout = false;

    public OnClickListener getOnclickRegister() {
        return mRegister;
    }

    public OnClickListener getOnClickRelative() {
        return onClickRelative;
    }

    public void setDelegate(RegisterCustomerDelegate mDelegate) {
        this.mDelegate = mDelegate;
    }

    public void setIsCheckout(boolean isCheckout) {
        this.isCheckout = isCheckout;
    }

    @Override
    public void onStart() {
        mRegister = new OnClickListener() {

            @Override
            public void onClick(View v) {
                Utils.hideKeyboard(v);
                RegisterCustomer register = mDelegate.getRegisterCustomer();
                if (isCompleteRequired(register)) {
                    if (android.util.Patterns.EMAIL_ADDRESS.matcher(
                            register.getEmail()).matches()) {
                        if (register.getPass()
                                .equals(register.getConfirmPass())) {
                            onRegisterCustomer();
                        } else {
                            SimiManager
                                    .getIntance()
                                    .showNotify(
                                            null,
                                            Config.getInstance()
                                                    .getText(
                                                            "Password and Confirm password dont't match."),
                                            Config.getInstance().getText("OK"));
                        }
                    } else {
                        SimiManager.getIntance().showNotify(
                                null,
                                Config.getInstance().getText(
                                        "Invalid email address"),
                                Config.getInstance().getText("OK"));
                    }
                } else {
                    SimiManager.getIntance().showNotify(
                            null,
                            Config.getInstance().getText(
                                    "Please select all (*) fields"),
                            Config.getInstance().getText("OK"));
                }
            }
        };
        onClickRelative = new OnClickListener() {

            @Override
            public void onClick(View v) {
            }
        };

    }

    private boolean isCompleteRequired(RegisterCustomer register) {


        String first_name = register.getFirstName();
        if (!Utils.validateString(first_name)) {
            return false;
        }

        String last_name = register.getLastName();
        if (!Utils.validateString(last_name)) {
            return false;
        }


        String email = register.getEmail();
        if (!Utils.validateString(email)) {
            return false;
        }

        String pass = register.getPass();
        if (!Utils.validateString(pass)) {
            return false;
        }

        String confirm_pass = register.getConfirmPass();
        if (!Utils.validateString(confirm_pass)) {
            return false;
        }

        return true;


    }

    protected void onRegisterCustomer() {
        mDelegate.showLoading();
        mModel = new CreateNewAccountModel();
        mModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                mDelegate.dismissLoading();
                SimiManager.getIntance().showNotify(
                        Config.getInstance().getText("FAIL"), error.getMessage(),
                        Config.getInstance().getText("OK"));
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.dismissLoading();
                SimiManager.getIntance().showToast(Config.getInstance().getText("Register Success"));
                replaceFragment();
            }
        });

        RegisterCustomer register = mDelegate.getRegisterCustomer();
        mModel.addDataExtendURL("register");
        JSONObject obj = new JSONObject();
        try {
            obj.put("first_name", register.getFirstName());
            obj.put("last_name", register.getLastName());
            obj.put("email", register.getEmail());
            obj.put("password", register.getPass());
            obj.put("password_confirmation", register.getConfirmPass());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mModel.setJSONBody(obj);
        mModel.request();
    }

    @Override
    public void onResume() {
        mDelegate.updateView(mModel.getCollection());
    }

    private void replaceFragment() {
        RegisterCustomer register = mDelegate.getRegisterCustomer();

        SignInFragment fragment = SignInFragment.newInstance();
        fragment.setCheckout(isCheckout);
        String email = register.getEmail();
        fragment.setEmail(email);
        String pass = register.getPass();
        fragment.setPassword(pass);
        DataLocal.saveData(email, pass);
        DataLocal.saveEmailPassRemember(email, pass);
        SimiManager.getIntance().backPreviousFragment();
        SimiManager.getIntance().replacePopupFragment(fragment);
    }
}
