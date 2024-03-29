package com.simicart.core.customer.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.checkout.entity.QuoteEntity;
import com.simicart.core.checkout.fragment.AddressBookCheckoutFragment;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.config.Constants;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.core.customer.delegate.SignInDelegate;
import com.simicart.core.customer.entity.ProfileEntity;
import com.simicart.core.customer.fragment.ForgotPasswordFragment;
import com.simicart.core.customer.fragment.RegisterCustomerFragment;
import com.simicart.core.customer.model.GetAllQuoteModel;
import com.simicart.core.customer.model.LogInModel;
import com.simicart.core.customer.model.MergeQuoteModel;
import com.simicart.core.customer.model.UpdateCustomerToQuoteModel;
import com.simicart.core.event.fragment.SimiEventFragmentEntity;
import com.simicart.core.home.fragment.HomeFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SignInController extends SimiController {

    protected SignInDelegate mDelegate;
    protected OnClickListener mSignInClicker;
    protected OnClickListener mForgotPassClicker;
    protected OnTouchListener mCreateAccClicker;
    protected OnClickListener mOutSideClicker;
    private TextWatcher mPassWatcher;
    private TextWatcher mEmailWatcher;
    protected OnCheckedChangeListener mOnCheckBox;

    protected boolean isCheckout = false;// sign in into checkout

    public boolean getIsCheckout() {
        return isCheckout;
    }

    public SignInDelegate getDelegate() {
        return mDelegate;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onStart() {

        mDelegate.updateView(null);

        mPassWatcher = new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                String email = mDelegate.getEmail();
                String password = mDelegate.getPassword();
                if (email.length() != 0 && password.length() >= 6) {
                    changeColorSignIn(Config.getInstance().getKey_color());
                } else {
                    changeColorSignIn(Color.GRAY);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        };

        mEmailWatcher = new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                String email = mDelegate.getEmail();
                String password = mDelegate.getPassword();
                if (email.length() != 0 && password.length() >= 6) {
                    changeColorSignIn(Config.getInstance().getKey_color());
                } else {
                    changeColorSignIn(Color.GRAY);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {

            }
        };

        mSignInClicker = new OnClickListener() {

            @Override
            public void onClick(View v) {
                Utils.hideKeyboard(v);
                String email = mDelegate.getEmail();
                String password = mDelegate.getPassword();
                if (email.length() != 0 && password.length() >= 6) {
                    onSignIn();
                } else {
                    mDelegate.getSignIn().setBackgroundColor(Color.GRAY);
                }
            }
        };

        mCreateAccClicker = new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Utils.hideKeyboard(v);
                        v.setBackgroundColor(0xCCCACACA);
                        break;
                    case MotionEvent.ACTION_UP:
                        v.setBackgroundColor(0xCCFFFFFF);
                        onCreateAccount();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        v.setBackgroundColor(0xCCFFFFFF);
                        break;

                    default:
                        break;
                }
                return true;
            }
        };

        mForgotPassClicker = new OnClickListener() {

            @Override
            public void onClick(View v) {
                Utils.hideKeyboard(v);
                onForgotPasswrod();

            }
        };
        mOutSideClicker = new OnClickListener() {

            @Override
            public void onClick(View v) {
                Utils.hideKeyboard(v);
            }
        };

        mOnCheckBox = new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked == false) {
                    DataLocal.saveCheckRemember(false);
                } else {
                    DataLocal.saveCheckRemember(true);
                }
            }
        };
    }

    @SuppressWarnings("deprecation")
    protected void changeColorSignIn(int color) {
        // GradientDrawable gdDefault = new GradientDrawable();
        // gdDefault.setColor(color);
        // gdDefault.setCornerRadius(3);
        mDelegate.getSignIn().setBackgroundColor(color);
    }

    protected void onSignIn() {
        final String email = mDelegate.getEmail();
        final String password = mDelegate.getPassword();
        onSingIn(email, password);
    }

    protected void onSingIn(final String email, final String password) {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mDelegate.getEmail())
                .matches()) {
            mDelegate.showNotify(Config.getInstance().getText(
                    "Invalid email address"));
            return;
        }
        if (null == email || email.equals("")) {
            mDelegate.showNotify(Config.getInstance().getText(
                    "Email is empty.Please input an email."));
            return;
        }
        if (null == password || password.equals("")) {
            mDelegate.showNotify(Config.getInstance().getText(
                    "Password is empty.Please input a password."));
            return;
        }

        mDelegate.showLoading();
        DataLocal.saveData(email, password);

        mModel = new LogInModel();
        ModelDelegate delegate = new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                mDelegate.dismissLoading();
                if (error != null)
                    SimiManager.getIntance().showNotify(null, Config.getInstance().getText("Invalid email or password"), "Ok");
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.dismissLoading();
                ProfileEntity entity = (ProfileEntity) collection.getCollection().get(0);
                saveDataUser(entity, password);
                processSignInSuccess();
            }
        };

        mModel.setDelegate(delegate);
        JSONObject obj = null;
        try {
            obj = new JSONObject();
            obj.put(Constants.USER_EMAIL, email);
            obj.put(Constants.USER_PASSWORD, password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mModel.setJSONBody(obj);
        mModel.addDataExtendURL("login");
        mModel.request();
    }

    private void saveDataUser(ProfileEntity entity, String password) {
        DataLocal.mCustomer = entity;
        DataLocal.isNewSignIn = true;
        DataLocal.saveTypeSignIn(Constants.NORMAL_SIGN_IN);
        String email = "";
        String firstname = "";
        String lastname = "";
        String name = "";
        String customerID = "";

        if (entity.getEmail() != null)
            email = entity.getEmail();
        if (entity.getID() != null)
            customerID = entity.getID();
        if (entity.getFirstName() != null)
            firstname = entity.getFirstName();
        if (entity.getLastName() != null)
            lastname = entity.getLastName();

        name = entity.getFirstName() + " " + entity.getLastName();

        DataLocal.saveData(name, email, password);
        DataLocal.saveCustomerID(customerID);
        DataLocal.saveCustomer(firstname, lastname, email, name, customerID);
        DataLocal.saveEmailPassRemember(email, password);
        DataLocal.saveEmailCreditCart(email);

        DataLocal.saveSignInState(true);
    }

    private void processSignInSuccess() {
        showToastSignIn();
        if (!isCheckout && DataLocal.isTablet) {
            SimiManager.getIntance().clearAllChidFragment();
            SimiManager.getIntance().removeDialog();
        } else {
            SimiManager.getIntance().backPreviousFragment();
            SimiManager.getIntance().removeDialog();
        }

        requestGetAllQuote();
        // dispatch event
        Intent intent = new Intent("com.simicart.core.customer.controller.SignInController");
        intent.putExtra(Constants.DATA, mModel.getJSON().toString());
        Context context = SimiManager.getIntance().getCurrentContext();
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

        SimiFragment fragment = null;
        if (isCheckout) {
            fragment = AddressBookCheckoutFragment.newInstance();
        } else {
             fragment = HomeFragment.newInstance();
        }
        processAfterSignIn(fragment);
    }



    private void processAfterSignIn(SimiFragment fragment) {
        Context context = SimiManager.getIntance().getCurrentContext();
        Intent intent = new Intent("com.simicart.core.base.fragment.SimiFragment.setName");
        SimiEventFragmentEntity entity = new SimiEventFragmentEntity();
        entity.setFragmetn(fragment);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.ENTITY, entity);
        intent.putExtra(Constants.DATA, bundle);
        LocalBroadcastManager.getInstance(context).sendBroadcastSync(intent);
        fragment = entity.getFragment();
        SimiManager.getIntance().replaceFragment(fragment);

    }


    private void requestGetAllQuote() {
        GetAllQuoteModel quoteModel = new GetAllQuoteModel();
        quoteModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if (error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "OK");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                if (collection != null) {
                    ArrayList<SimiEntity> entity = collection.getCollection();
                    if (entity != null && entity.size() > 0) {
                        ArrayList<QuoteEntity> listQuote = new ArrayList<QuoteEntity>();
                        for (SimiEntity simiEntity : entity) {
                            QuoteEntity quoteEntity = (QuoteEntity) simiEntity;
                            listQuote.add(quoteEntity);
                        }

                        if (listQuote.size() > 0) {
                            Config.getInstance().setQuoteCustomerSignIn(listQuote.get(0).getID());
                            SimiManager.getIntance().onUpdateCartQty(String.valueOf(listQuote.get(0).getQty()));
                        }

                        if (Config.getInstance().getQuoteCustomerSignIn().equals("")) {
                            if (!DataLocal.getQuoteCustomerNotSigin().equals("")) {
                                requestUpdateCustomerToQuote();
                            }
                        } else {
                            if (!DataLocal.getQuoteCustomerNotSigin().equals("")) {
                                requestMergeQuote();
                            }
                        }
                    } else {
                        if (!DataLocal.getQuoteCustomerNotSigin().equals("")) {
                            requestUpdateCustomerToQuote();
                        }
                    }
                }
            }
        });

        quoteModel.addDataParameter("filter[customer|customer_id]", DataLocal.getCustomerID());
        quoteModel.addDataParameter("filter[orig_order_id]", "0");
        quoteModel.request();
    }

    private void requestMergeQuote() {
        MergeQuoteModel mergeQuoteModel = new MergeQuoteModel();
        mergeQuoteModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if (error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "OK");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                if (collection != null) {
                    if (collection.getCollection().size() > 0) {
                        QuoteEntity quoteEntity = (QuoteEntity) collection.getCollection().get(0);
                        SimiManager.getIntance().onUpdateCartQty(String.valueOf(quoteEntity.getQty()));
                        Config.getInstance().setQuoteCustomerSignIn(quoteEntity.getID());
                        DataLocal.saveQuoteCustomerNotSignIn("");
                    }
                }
            }
        });

        mergeQuoteModel.addDataExtendURL("merge");
        mergeQuoteModel.addDataBody("source_quoteId", DataLocal.getQuoteCustomerNotSigin());
        mergeQuoteModel.addDataBody("des_quoteId", Config.getInstance().getQuoteCustomerSignIn());
        mergeQuoteModel.request();
    }

    private void requestUpdateCustomerToQuote() {
        UpdateCustomerToQuoteModel updateCustomerToQuoteModel = new UpdateCustomerToQuoteModel();
        updateCustomerToQuoteModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if (error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "OK");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                if (collection != null) {
                    if (collection.getCollection().size() > 0) {
                        QuoteEntity quoteEntity = (QuoteEntity) collection.getCollection().get(0);
                        Config.getInstance().setQuoteCustomerSignIn(quoteEntity.getID());
                        DataLocal.saveQuoteCustomerNotSignIn("");
                    }
                }
            }
        });

        if (!Config.getInstance().getQuoteCustomerSignIn().equals("")) {
            updateCustomerToQuoteModel.addDataExtendURL(Config.getInstance().getQuoteCustomerSignIn());
        }

        if (!DataLocal.getQuoteCustomerNotSigin().equals("")) {
            updateCustomerToQuoteModel.addDataExtendURL(DataLocal.getQuoteCustomerNotSigin());
        }

        JSONObject cutomer = null;
        try {
            cutomer = new JSONObject();
            cutomer.put("customer_first_name", DataLocal.getCustomer().getFirstName());
            cutomer.put("customer_last_name", DataLocal.getCustomer().getLastName());
            cutomer.put("customer_email", DataLocal.getCustomer().getEmail());
            cutomer.put("customer_name", DataLocal.getCustomer().getName());
            cutomer.put("customer_id", DataLocal.getCustomer().getID());
        } catch (JSONException e) {
            cutomer = null;
        }

        if (cutomer != null) {
            updateCustomerToQuoteModel.addDataBody("customer", cutomer);
        }
        updateCustomerToQuoteModel.request();
    }

    private void showToastSignIn() {
        LayoutInflater inflater = SimiManager.getIntance().getCurrentActivity()
                .getLayoutInflater();
        View layout_toast = inflater
                .inflate(
                        Rconfig.getInstance().layout(
                                "core_custom_toast_productlist"),
                        (ViewGroup) SimiManager
                                .getIntance()
                                .getCurrentActivity()
                                .findViewById(
                                        Rconfig.getInstance().id(
                                                "custom_toast_layout")));
        TextView txt_toast = (TextView) layout_toast.findViewById(Rconfig
                .getInstance().id("txt_custom_toast"));
        Toast toast = new Toast(SimiManager.getIntance().getCurrentContext());
        txt_toast.setText(String.format(Config.getInstance().getText("Welcome %s! Start shopping now"), DataLocal.getUsername()));
        toast.setView(layout_toast);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 400);
        toast.show();
    }

    protected void onCreateAccount() {
        RegisterCustomerFragment fragment = RegisterCustomerFragment
                .newInstance();
        fragment.setIsCheckout(isCheckout);
        SimiManager.getIntance().replacePopupFragment(fragment);
    }

    protected void onForgotPasswrod() {
        ForgotPasswordFragment fragment = ForgotPasswordFragment.newInstance();
        SimiManager.getIntance().replacePopupFragment(fragment);

    }

    @Override
    public void onResume() {
        mDelegate.updateView(null);
    }

    public void setDelegate(SignInDelegate delegate) {
        mDelegate = delegate;
    }

    public OnClickListener getSignInClicker() {
        return mSignInClicker;
    }

    public OnClickListener getForgotPassClicker() {
        return mForgotPassClicker;
    }

    public OnTouchListener getCreateAccClicker() {
        return mCreateAccClicker;
    }

    public void setCheckout(boolean isCheckout) {
        this.isCheckout = isCheckout;
    }

    public OnClickListener getOutSideClicker() {
        return mOutSideClicker;
    }

    public TextWatcher getPassWatcher() {
        return mPassWatcher;
    }

    public TextWatcher getEmailWatcher() {
        return mEmailWatcher;
    }

    public OnCheckedChangeListener getOnCheckBox() {
        return mOnCheckBox;
    }
}
