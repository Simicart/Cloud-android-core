package com.simicart.core.customer.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.checkout.controller.ConfigCheckout;
import com.simicart.core.checkout.fragment.AddressBookCheckoutFragment;
import com.simicart.core.checkout.fragment.ReviewOrderFragment;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.core.customer.delegate.ChooseCountryDelegate;
import com.simicart.core.customer.delegate.NewAddressBookDelegate;
import com.simicart.core.customer.entity.ConfigCustomerAddress;
import com.simicart.core.customer.entity.CountryAllowed;
import com.simicart.core.customer.entity.MyAddress;
import com.simicart.core.customer.entity.ProfileEntity;
import com.simicart.core.customer.entity.StateOfCountry;
import com.simicart.core.customer.fragment.AddressBookFragment;
import com.simicart.core.customer.fragment.CountryFragment;
import com.simicart.core.customer.fragment.NewAddressBookFragment;
import com.simicart.core.customer.model.GetCountryModel;
import com.simicart.core.customer.model.NewAddressBookModel;

@SuppressLint("DefaultLocale")
public class NewAddressBookController extends SimiController implements
        ChooseCountryDelegate {

    public static int TYPE_SELECT_STATE = 0;
    public static int TYPE_SELECT_COUNTRY = 1;

    protected ArrayList<CountryAllowed> mListCountryAllowed;
    protected OnClickListener mChooseCountry;
    protected OnClickListener mChooseStates;
    protected NewAddressBookDelegate mDelegate;
    protected OnClickListener mClickSave;
    protected ArrayList<String> mListCountry;
    protected String mCurrentCountry = "";
    protected String mCurrentState = "";
    protected MyAddress mBillingAddress;
    protected MyAddress mShippingAddress;

    public void setBillingAddress(MyAddress mBillingAddress) {
        this.mBillingAddress = mBillingAddress;
    }

    public void setShippingAddress(MyAddress mShippingAddress) {
        this.mShippingAddress = mShippingAddress;
    }

    protected int mAfterController;
    protected int addressFor = -1;

    public void setAddressFor(int addresFor) {
        this.addressFor = addresFor;
    }

    public int getAddressFor() {
        return this.addressFor;
    }

    public void setAfterController(int controll) {
        mAfterController = controll;
    }

    public OnClickListener getClickSave() {
        return mClickSave;
    }

    public OnClickListener getChooseCountry() {
        return mChooseCountry;
    }

    public OnClickListener getChooseStates() {
        return mChooseStates;
    }


    public void setDelegate(NewAddressBookDelegate mDelegate) {
        this.mDelegate = mDelegate;
    }

    @Override
    public void onStart() {
        onRequestCountryAllowed();

        mClickSave = new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Utils.hideKeyboard(arg0);
                onSaveAddress();
            }
        };

        mChooseCountry = new OnClickListener() {

            @Override
            public void onClick(View v) {
                SimiManager.getIntance().hideKeyboard();
                changeFragmentCountry(TYPE_SELECT_COUNTRY, mListCountry);
            }
        };

        mChooseStates = new OnClickListener() {

            @Override
            public void onClick(View v) {
                ArrayList<String> states = getStateFromCountry(mCurrentCountry,
                        mListCountryAllowed);
                if (null != states && states.size() > 0) {
                    changeFragmentCountry(
                            TYPE_SELECT_STATE,
                            getStateFromCountry(mCurrentCountry,
                                    mListCountryAllowed));
                }
            }
        };


        mDelegate.createView(mAfterController);
    }

    private void onSaveAddress() {
        MyAddress address = getAddressForSave();

        if (isCompleteRequired(address)) {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(
                    address.getEmail()).matches()) {
                if (mAfterController == NewAddressBookFragment.NEW_AS_GUEST) {
                    onSaveForGuest(address);
                } else if (mAfterController == NewAddressBookFragment.NEW_CUSTOMER) {
                    onSaveForNewCustomer(address);
                } else {
                    OnRequestChangeAddress(address);
                }
            } else {
                String msg = Config.getInstance().getText("Invalid email address");
                showNotify(msg);
            }
        } else {
            String msg = "Please select all (*) fields";
            showNotify(msg);
        }
    }

    private MyAddress getAddressForSave() {
        MyAddress address = mDelegate.getNewAddressBook();

        String state = address.getStateName();
        if (null != state) {
            mCurrentState = state;
        }
        String country = address.getCountryName();
        if (null != country) {
            mCurrentCountry = country;
        }

        CountryAllowed countryAllow = getCurrentCountry(mCurrentCountry);
        if (null != countryAllow) {
            address.getCountry().setName(countryAllow.getName());
            address.getCountry().setCode(countryAllow.getCode());
            if (!mCurrentState.equals("")) {
                StateOfCountry stateOfCountry = getCurrentState(
                        mCurrentState, countryAllow);
                if (null != stateOfCountry) {
                    address.getStates().setName(stateOfCountry.getName());
                    address.getStates().setCode(stateOfCountry.getCode());
                    address.setStateId(stateOfCountry.getCode());
                }
            }
        }

        return address;
    }

    private void onSaveForGuest(MyAddress address) {
        ReviewOrderFragment fragment = ReviewOrderFragment
                .newInstance();
        fragment.setBilingAddress(address);
        fragment.setShippingAddress(address);
        fragment.setAfterControll(mAfterController);
        SimiManager.getIntance().replacePopupFragment(
                fragment);
    }

    private void onSaveForNewCustomer(MyAddress address) {
        ReviewOrderFragment fragment = ReviewOrderFragment
                .newInstance();
        ProfileEntity profile = mDelegate
                .getProfileEntity();
        if (null != profile) {
            String name = profile.getName();
            String email = profile.getEmail();
            String password = profile.getCurrentPass();
            String firstname = profile.getFirstName();
            String lastname = profile.getLastName();
            DataLocal.saveData(name, email, password);
            DataLocal.saveCustomer(firstname, lastname, email, name, "");
            DataLocal.saveEmailPassRemember(email, password);
            DataLocal.saveEmailCreditCart(email);
            fragment.setBilingAddress(address);
            fragment.setAfterControll(mAfterController);
            fragment.setShippingAddress(address);
            fragment.setAfterControll(mAfterController);

            if (addressFor == AddressBookCheckoutFragment.BILLING_ADDRESS) {
                fragment.setBilingAddress(address);
                fragment.setShippingAddress(mShippingAddress);
            } else if (addressFor == AddressBookCheckoutFragment.SHIPPING_ADDRESS) {
                fragment.setShippingAddress(address);
                fragment.setBilingAddress(mBillingAddress);
            } else {
                fragment.setShippingAddress(address);
                fragment.setBilingAddress(address);
            }
            SimiManager.getIntance().removeDialog();
            SimiManager.getIntance().replaceFragment(
                    fragment);
        }
    }


    private void showNotify(String message) {
        SimiManager.getIntance().showNotify(null,
                message, "OK");
    }

    @Override
    public void onResume() {
        mDelegate.createView(mAfterController);
        mDelegate.updateView(mModel.getCollection());
        mDelegate.updateCountry(mCurrentCountry);
        mDelegate.updateState(mCurrentState);
    }

    protected void onRequestCountryAllowed() {
        mDelegate.showLoading();
        mModel = new GetCountryModel();
        mModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if (error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "OK");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.dismissLoading();
                ArrayList<SimiEntity> entity = collection.getCollection();
                if (null != entity && entity.size() > 0) {
                    mListCountryAllowed = new ArrayList<CountryAllowed>();
                    for (SimiEntity simiEntity : entity) {
                        CountryAllowed country_add = (CountryAllowed) simiEntity;
                        mListCountryAllowed.add(country_add);
                    }
                    mListCountry = new ArrayList<String>();
                    for (int i = 0; i < mListCountryAllowed.size(); i++) {
                        mListCountry.add(mListCountryAllowed.get(i)
                                .getName());
                    }
                    String name = mListCountryAllowed.get(0)
                            .getName();

                    if (null != name) {
                        mDelegate.updateCountry(name);
                        mCurrentCountry = name;
                        ArrayList<String> states = getStateFromCountry(
                                name, mListCountryAllowed);
                        if (null != states && states.size() > 0) {
                            mCurrentState = states.get(0);
                        } else {
                            mCurrentState = "";
                        }


                        mDelegate.updateState(mCurrentState);
                        mDelegate.updateView(mModel.getCollection());
                        selectDefaultCountry();
                        selectDefaultState();
                    }
                }
            }
        });

        mModel.request();
    }


    protected void selectDefaultCountry() {
        String default_country = Config.getInstance().getDefaultCountryName();
        if (Utils.validateString(default_country)) {
            chooseCountry(TYPE_SELECT_COUNTRY, default_country);
        }
    }

    protected void selectDefaultState() {
        String default_state = Config.getInstance().getDefaultStateName();
        if (Utils.validateString(default_state)) {
            chooseCountry(TYPE_SELECT_STATE, default_state);
        }
    }

    protected void OnRequestChangeAddress(final MyAddress address) {
        mDelegate.showLoading();
        mModel = new NewAddressBookModel();
        mModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if (error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "OK");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.dismissLoading();
                if (mAfterController == NewAddressBookFragment.NEW_ADDRESS) {
                    AddressBookFragment fragment = AddressBookFragment
                            .newInstance();
                    SimiManager.getIntance().replacePopupFragment(fragment);
                } else {
                    MyAddress newAddress = getAddressForCheckOut();

                    if (null != newAddress) {
                        newAddress.setEmail(DataLocal.getEmail());
                        if (mAfterController == NewAddressBookFragment.NEW_ADDRESS_CHECKOUT) {
                            onChangeNewAddressForCheckout(newAddress);
                        } else {
                            onChangeAddressForCheckout(newAddress);
                        }
                    }
                }
            }
        });

        CountryAllowed country = getCurrentCountry(mCurrentCountry);
        if (null != country) {
            address.getCountry().setName(country.getName());
            address.getCountry().setCode(country.getCode());
            if (!mCurrentState.equals("")) {
                StateOfCountry state = getCurrentState(mCurrentState, country);
                if (null != state) {
                    address.getStates().setName(state.getName());
                    address.getStates().setCode(state.getCode());
                    address.setStateId(state.getCode());
                }
            }
        }

        mModel.addDataExtendURL(DataLocal.getCustomerID(), "addresses");
        try {
            JSONObject json_body = address.paramJsonRequest();
            json_body.put("default_billing", "1");
            json_body.put("default_shipping", "1");
            mModel.setJSONBody(json_body);
        } catch (Exception e) {

        }

        mModel.request();
    }

    private MyAddress getAddressForCheckOut() {
        MyAddress newAddress = (MyAddress) mModel
                .getCollection().getCollection().get(0);

        CountryAllowed country = getCurrentCountry(mCurrentCountry);
        if (null != country) {
            newAddress.setCountryName(mCurrentCountry);
            newAddress.setCountryCode(country.getCode());
            if (!mCurrentState.equals("")) {
                StateOfCountry state = getCurrentState(
                        mCurrentState, country);
                if (null != state) {
                    newAddress.setStateName(mCurrentState);
                    newAddress.setStateCode(state
                            .getCode());
                    newAddress.setStateId(state.getCode());
                }
            }
        }

        return newAddress;
    }


    private void onChangeNewAddressForCheckout(MyAddress newAddress) {
        ReviewOrderFragment fragment = ReviewOrderFragment
                .newInstance();
        switch (addressFor) {
            case AddressBookCheckoutFragment.ALL_ADDRESS:
                fragment.setBilingAddress(newAddress);
                fragment.setShippingAddress(newAddress);
                break;
            case AddressBookCheckoutFragment.BILLING_ADDRESS:
                fragment.setBilingAddress(newAddress);
                fragment.setShippingAddress(mShippingAddress);
                break;
            case AddressBookCheckoutFragment.SHIPPING_ADDRESS:
                fragment.setBilingAddress(mBillingAddress);
                fragment.setShippingAddress(newAddress);
                break;
            default:
                break;
        }
        SimiManager.getIntance().replaceFragment(
                fragment);
    }

    private void onChangeAddressForCheckout(MyAddress newAddress) {
        ReviewOrderFragment fragment = ReviewOrderFragment
                .newInstance();
        fragment.setBilingAddress(newAddress);
        fragment.setShippingAddress(newAddress);
        SimiManager.getIntance().replaceFragment(
                fragment);
    }


    protected CountryAllowed getCurrentCountry(String name) {
        CountryAllowed country = null;
        if (null != name && null != mListCountryAllowed) {
            for (int i = 0; i < mListCountryAllowed.size(); i++) {
                country = mListCountryAllowed.get(i);
                if (country.getName().equals(name)) {
                    break;
                }
            }
        }

        return country;
    }

    protected StateOfCountry getCurrentState(String name, CountryAllowed country) {
        StateOfCountry state = null;
        if (null != name && null != country) {
            ArrayList<StateOfCountry> states = country.getState();
            if (null != states && states.size() > 0) {
                for (int i = 0; i < states.size(); i++) {
                    state = states.get(i);
                    if (state.getName().equals(name)) {
                        break;
                    }
                }
            }
        }

        return state;
    }

    private boolean isCompleteRequired(MyAddress add_address) {
        String first_name = add_address.getFirstName();
        if (!Utils.validateString(first_name)) {
            return false;
        }

        String last_name = add_address.getLastName();
        if (!Utils.validateString(last_name)) {
            return false;
        }

        String email = add_address.getEmail();
        if (!Utils.validateString(email)) {
            return false;
        }

        String street = add_address.getStreet();
        if (!Utils.validateString(street)) {
            return false;
        }


        String city = add_address.getCity();
        if (!Utils.validateString(city)) {
            return false;
        }


//        String state = add_address.getStateName();
//        if(!Utils.validateString(state))
//        {
//            return false;
//        }

//        String country = add_address.getCountryName();
//        if (!Utils.validateString(country)) {
//            return false;
//        }

        String zipcode = add_address.getZipCode();
        if (!Utils.validateString(zipcode)) {
            return false;
        }


        String phone = add_address.getPhone();
        if (!Utils.validateString(phone)) {
            return false;
        }


        return true;
    }

    protected void changeFragmentCountry(int type,
                                         ArrayList<String> list_country) {
        CountryFragment fragment = CountryFragment.newInstance();
        fragment.setChooseDelegate(this);
        fragment.setList_country(list_country);
        fragment.setType(type);
        SimiManager.getIntance().replacePopupFragment(fragment);
    }

    public ArrayList<String> getStateFromCountry(String country,
                                                 ArrayList<CountryAllowed> listCountry) {
        ArrayList<String> states = new ArrayList<String>();
        for (CountryAllowed countryAllowed : listCountry) {

            String current_name = countryAllowed.getName();
            if (current_name.equals(country)) {
                if (countryAllowed.getState() != null) {
                    for (StateOfCountry state : countryAllowed.getState()) {
                        states.add(state.getName());
                    }
                }
                return states;
            }
        }
        return states;
    }

    @Override
    public void chooseCountry(int type, String country) {
        if (type == TYPE_SELECT_COUNTRY) {
            mCurrentCountry = country;
            mDelegate.updateCountry(country);
            ArrayList<String> states = getStateFromCountry(country,
                    mListCountryAllowed);
            if (null != states && states.size() > 0) {
                mCurrentState = states.get(0);
            } else {
                mCurrentState = "";
            }

            mDelegate.updateState(mCurrentState);

        } else if (type == TYPE_SELECT_STATE) {
            mCurrentState = country;
            mDelegate.updateState(mCurrentState);
        }

    }

    @Override
    public void setCurrentCountry(String country) {
        mCurrentCountry = country;
    }

    @Override
    public void setCurrentState(String state) {
        mCurrentState = state;

    }

}
