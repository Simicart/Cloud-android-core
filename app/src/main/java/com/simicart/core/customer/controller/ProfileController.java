package com.simicart.core.customer.controller;


import android.view.View;
import android.view.View.OnClickListener;

import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.core.customer.delegate.ProfileDelegate;
import com.simicart.core.customer.entity.ProfileEntity;
import com.simicart.core.customer.model.ChangePassModel;
import com.simicart.core.customer.model.EditProfileModel;
import com.simicart.core.customer.model.ProfileModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProfileController extends SimiController {


    protected ProfileDelegate mDelegate;

    protected OnClickListener mClicker;


    public OnClickListener getSaveClicker() {
        return mClicker;
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
                if (error != null) {
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


    }

    protected void saveProfile() {
        mModel = new ChangePassModel();
        ProfileEntity profile = mDelegate.getProfileEntity();
        if (isCompleteRequired(profile)) {
            requestChangeProfile(profile);
        } else {
            SimiManager.getIntance().showNotify(null,
                    "Please select all (*) fields", "OK");
        }
    }


    private void requestChangeProfile(ProfileEntity profile) {
        mDelegate.showLoading();
        mModel = new EditProfileModel();
        mModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                mDelegate.dismissLoading();
                if (error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "OK");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.dismissLoading();
                if (null != collection) {
                    ArrayList<SimiEntity> entities = collection.getCollection();
                    if (null != entities && entities.size() > 0) {
                        ProfileEntity entity = (ProfileEntity) entities.get(0);
                        saveDataSignIn(entity);
                        String msg = Config.getInstance().getText("The account information has been saved.");
                        SimiManager.getIntance().showNotify("SUCCESS", msg, "OK");
                    }
                }

            }
        });
        mModel.addDataExtendURL(DataLocal.getCustomerID());

        JSONObject data_body = getParam(profile);

        mModel.setJSONBody(data_body);

        mModel.request();
    }


    private boolean isCompleteRequired(ProfileEntity profile) {

        String last_name = profile.getLastName();
        if (!Utils.validateString(last_name)) {
            return false;
        }

        String first_name = profile.getFirstName();
        if (!Utils.validateString(first_name)) {
            return false;
        }

        String email = profile.getEmail();
        if (!Utils.validateString(email)) {
            return false;
        }

        return true;
    }

    private void saveDataSignIn(ProfileEntity entity) {

        DataLocal.saveData(entity.getFirstName() + " " + entity.getLastName(), entity.getEmail(),
                DataLocal.getPassword());

        DataLocal.saveCustomer(entity.getFirstName(), entity.getLastName(), entity.getEmail(),
                entity.getFirstName() + " " + entity.getLastName(), DataLocal.getCustomerID());

        DataLocal.saveEmailPassRemember(entity.getEmail(),
                DataLocal.getPassword());
        SimiManager.getIntance().onUpdateItemSignIn();
    }

    private JSONObject getParam(ProfileEntity profile) {
        JSONObject json = new JSONObject();
        try {
            String first_name = profile.getFirstName();
            String last_name = profile.getLastName();
            String name = first_name + last_name;
            String email = profile.getEmail();

            json.put("first_name", first_name);
            json.put("last_name", last_name);
            json.put("email", email);
            json.put("name", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }


    @Override
    public void onResume() {
        mDelegate.updateView(mModel.getCollection());
    }
}
