package com.simicart.plugins.facebooklogin;

import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.simicart.MainActivity;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.checkout.entity.QuoteEntity;
import com.simicart.core.checkout.fragment.AddressBookCheckoutFragment;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.config.Constants;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.core.customer.delegate.SignInDelegate;
import com.simicart.core.customer.entity.ProfileEntity;
import com.simicart.core.customer.fragment.SignInFragment;
import com.simicart.core.customer.model.GetAllQuoteModel;
import com.simicart.core.customer.model.MergeQuoteModel;
import com.simicart.core.customer.model.UpdateCustomerToQuoteModel;
import com.simicart.core.event.block.SimiEventBlockEntity;
import com.simicart.core.event.fragment.SimiEventFragmentEntity;
import com.simicart.core.home.fragment.HomeFragment;
import com.simicart.core.material.ButtonRectangle;

public class FacebookLogin {

    private static Context mContext;
    private static View mView;
    private static Activity mActivity;
    private static SignInFragment mSignInFragment;
    SimiModel mModel;
    private static CallbackManager callbackManager;

    public FacebookLogin() {
        Context context = SimiManager.getIntance().getCurrentContext();

        // register event: create a fragment
        IntentFilter fragment_filter = new IntentFilter("com.simicart.core.customer.fragment.SignInFragment");
        BroadcastReceiver fragment_receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getBundleExtra(Constants.DATA);
                SimiEventFragmentEntity entity = (SimiEventFragmentEntity) bundle.getSerializable(Constants.ENTITY);
                String method = bundle.getString(Constants.METHOD);
                if (method.equals("createFragment")) {
                    mSignInFragment = (SignInFragment) entity.getFragment();
                } else if (method.equals("onActivityResult")) {
                    onActivityResult();
                }
            }
        };
        LocalBroadcastManager.getInstance(context).registerReceiver(fragment_receiver, fragment_filter);

        // register event: create block
        IntentFilter blockFilter = new IntentFilter("com.simicart.core.customer.block.SignInBlock");
        BroadcastReceiver blockReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {


                Bundle bundle = intent.getBundleExtra(Constants.DATA);
                String method = bundle.getString(Constants.METHOD);
                SimiEventBlockEntity entity = (SimiEventBlockEntity) bundle.getSerializable(Constants.ENTITY);
                if (method.equals("createBlock")) {
                    mView = entity.getView();
                    mContext = MainActivity.context;
                    mActivity = (Activity) mContext;
                    addButtonFaceBookLogin();
                }

            }
        };
        LocalBroadcastManager.getInstance(context).registerReceiver(blockReceiver, blockFilter);


    }


    private void onActivityResult() {
        if (callbackManager != null) {
            callbackManager.onActivityResult(mSignInFragment.getRequestCode(),
                    mSignInFragment.getResultCode(), mSignInFragment.getData());
        }

    }

    public void addButtonFaceBookLogin() {
        FacebookSdk.sdkInitialize(mContext);
        callbackManager = CallbackManager.Factory.create();

        ButtonRectangle bt_signin = (ButtonRectangle) mView
                .findViewById(Rconfig.getInstance().id("bt_signIn"));
        RelativeLayout otherSignIn = (RelativeLayout) mView
                .findViewById(Rconfig.getInstance().id("rel_other_signin"));

        View v = mActivity.getLayoutInflater().inflate(
                Rconfig.getInstance().layout(
                        "plugin_facebooklogin_layout_login"), null);
        RelativeLayout.LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        int dp = Utils.getValueDp(5);
        params.setMargins(dp, dp, dp, dp);
        params.addRule(RelativeLayout.BELOW, bt_signin.getId());
        v.setLayoutParams(params);
        otherSignIn.addView(v);

        // check session
        LoginManager.getInstance().logOut();

        final LoginButton login_button = (LoginButton) v.findViewById(Rconfig
                .getInstance().id("authButton"));
        login_button.setFragment(mSignInFragment);
        login_button.setReadPermissions(Arrays
                .asList("email,user_photos,user_birthday"));
        login_button.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {

                    @Override
                    public void onSuccess(LoginResult result) {
                        onLoginSuccess(result);
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.e("Login Facebook Error", error.getMessage());
                    }

                    @Override
                    public void onCancel() {
                        Log.e("Login Facebook Cancel:", "Cancel");
                    }
                });

    }

    private void onLoginSuccess(LoginResult result) {
        GraphRequest request = GraphRequest.newMeRequest(
                result.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object,
                                            GraphResponse response) {
                        processResultLogin(object);
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields",
                "id,name,email,gender, birthday");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void processResultLogin(JSONObject json) {
        String name = "";
        String email = "";
        String id = "";
        try {
            if (json.has("id")) {
                id = json.getString("id");
            }
            if (json.has("name")) {
                name = json.getString("name");
            }
            if (json.has("email")) {
                email = json
                        .getString("email");
            } else {
                email = id + "@facebook.com";
            }
            if (email.length() > 0
                    && mSignInFragment != null) {

                boolean isCheckout = mSignInFragment
                        .getController()
                        .getIsCheckout();
                requestFaceBookSignIn(
                        email,
                        name,
                        isCheckout);
            }
        } catch (Exception e) {
            Log.e("FacebookLogin:",
                    "Get Information");
        }
    }


    private void requestFaceBookSignIn(String facebook_email, String facebook_name,
                                       final boolean checkOut) {

        final SignInDelegate mDelegate = mSignInFragment.getController()
                .getDelegate();

        mDelegate.showLoading();
        DataLocal.saveData(facebook_name, facebook_email, "");

        mModel = new FacebookModel();
        ModelDelegate delegate = new ModelDelegate() {

            @Override
            public void onFail(SimiError error) {
                mDelegate.dismissLoading();
                if (error != null) {
                    mDelegate.showNotify(error.getMessage());
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.dismissLoading();
                if (collection != null && collection.getCollection().size() > 0) {
                    ProfileEntity entity = (ProfileEntity) collection.getCollection().get(0);
                    DataLocal.saveTypeSignIn("facebook");
                    DataLocal.saveSignInState(true);

                    String email = "";
                    String firstname = "";
                    String lastname = "";
                    String name = "";
                    String customerID = "";

                    if (entity.getEmail() != null)
                        email = entity.getEmail();
                    if (entity.getFirstName() != null && entity.getLastName() != null)
                        name = entity.getFirstName() + " " + entity.getLastName();
                    if (entity.getID() != null)
                        customerID = entity.getID();
                    if (entity.getFirstName() != null)
                        firstname = entity.getFirstName();
                    if (entity.getLastName() != null)
                        lastname = entity.getLastName();

                    if (null != name) {
                        DataLocal.saveData(name, email);
                        DataLocal.saveCustomerID(customerID);
                        DataLocal.saveCustomer(firstname, lastname, email, name, customerID);
                    }

                    requestGetAllQuote();
                    SimiManager.getIntance().backPreviousFragment();

                    if (checkOut) {
                        AddressBookCheckoutFragment fragment = AddressBookCheckoutFragment
                                .newInstance();
                        SimiManager.getIntance().replacePopupFragment(fragment);

                    } else {
                        SimiFragment fragment = HomeFragment.newInstance();
                        // event for wish list
                        Intent intent = new Intent("com.simicart.event.wishlist.afterSignIn");
                        SimiEventFragmentEntity fragmentEntity = new SimiEventFragmentEntity();
                        fragmentEntity.setFragmetn(fragment);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Constants.ENTITY, fragmentEntity);
                        bundle.putString(Constants.METHOD, "afterSignIn");
                        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);

                        SimiManager.getIntance().removeDialog();
                        SimiManager.getIntance().replaceFragment(fragment);
                    }
                }
            }

        };

        mModel.setDelegate(delegate);
        mModel.addDataExtendURL("facebook-login");
        mModel.addDataBody("email", "" + facebook_email + "");
        mModel.addDataBody("first_name", "" + facebook_name + "");
        mModel.addDataBody("last_name", "");
        mModel.request();
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
                            QuoteEntity firstQuote = listQuote.get(0);
                            Config.getInstance().setQuoteCustomerSignIn(firstQuote.getID());
                            SimiManager.getIntance().onUpdateCartQty(String.valueOf(firstQuote.getQty()));
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
}
