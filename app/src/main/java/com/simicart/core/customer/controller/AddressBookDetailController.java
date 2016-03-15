package com.simicart.core.customer.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
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
import com.simicart.core.common.Utils;
import com.simicart.core.config.DataLocal;
import com.simicart.core.customer.delegate.AddressBookDetailDelegate;
import com.simicart.core.customer.delegate.ChooseCountryDelegate;
import com.simicart.core.customer.entity.ConfigCustomerAddress;
import com.simicart.core.customer.entity.CountryAllowed;
import com.simicart.core.customer.entity.MyAddress;
import com.simicart.core.customer.entity.StateOfCountry;
import com.simicart.core.customer.fragment.AddressBookFragment;
import com.simicart.core.customer.fragment.CountryFragment;
import com.simicart.core.customer.model.AddressBookDetailModel;
import com.simicart.core.customer.model.GetCountryModel;

@SuppressLint("DefaultLocale")
public class AddressBookDetailController extends SimiController implements
        ChooseCountryDelegate {
    protected OnClickListener mClickSave;
    protected AddressBookDetailDelegate mDelegate;
    protected ArrayList<CountryAllowed> country;
    protected OnClickListener mChooseCountry;
    protected OnClickListener mChooseStates;
    protected String mCountry;
    protected ArrayList<String> list_country_adapter;
    protected MyAddress mAddressDetail;

    public void setAddressDetail(MyAddress address) {
        mAddressDetail = address;
    }


    public OnClickListener getClickSave() {
        return mClickSave;
    }

    public OnClickListener getChooseCountry() {
        return mChooseCountry;
    }

    public void setDelegate(AddressBookDetailDelegate mDelegate) {
        this.mDelegate = mDelegate;
    }

    public OnClickListener getChooseStates() {
        return mChooseStates;
    }

    @Override
    public void onStart() {
        onRequestCountryAllowed();

        mClickSave = new OnClickListener() {

            @Override
            public void onClick(View v) {
                Utils.hideKeyboard(v);
                MyAddress addressBookDetail = mDelegate.getAddressBookDetail();
                if (isCompleteRequired(addressBookDetail)) {
                    OnRequestChangeAddress(addressBookDetail);
                } else {
                    SimiManager.getIntance().showNotify(null,
                            "Please select all (*) fields", "OK");
                }
            }
        };

        mChooseCountry = new OnClickListener() {

            @Override
            public void onClick(View v) {
                changeFragmentCountry(0, list_country_adapter);
            }
        };

        mChooseStates = new OnClickListener() {

            @Override
            public void onClick(View v) {

                String countryname = mAddressDetail.getCountry().getName();
                changeFragmentCountry(
                        1,
                        getStateFromCountry(countryname,
                                country));
            }
        };

        mDelegate.showAddressDetail(mAddressDetail);
    }

    @Override
    public void onResume() {
        mDelegate.updateView(mModel.getCollection());
        mDelegate.showAddressDetail(mAddressDetail);
        mDelegate.setListCountry(country);
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
                    country = new ArrayList<CountryAllowed>();
                    for (SimiEntity simiEntity : entity) {
                        CountryAllowed country_add = (CountryAllowed) simiEntity;
                        country.add(country_add);
                    }
                    list_country_adapter = new ArrayList<String>();
                    for (int i = 0; i < country.size(); i++) {
                        list_country_adapter.add(country.get(i)
                                .getName());
                    }
                    mDelegate.setListCountry(country);
                    mDelegate.updateView(collection);
                }
            }
        });

        mModel.request();
    }

    protected void OnRequestChangeAddress(MyAddress address) {
        mDelegate.showLoading();
        mModel = new AddressBookDetailModel();
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
                AddressBookFragment fragment = AddressBookFragment
                        .newInstance();
                SimiManager.getIntance().replacePopupFragment(fragment);
            }
        });

        mModel.addDataExtendURL(DataLocal.getCustomerID(), "addresses");
        mModel.addDataExtendURL(address.getID());

        // prefix
        String first_name = address.getPrefix();
        if (Utils.validateString(first_name)) {
            mModel.addDataBody("first_name", first_name);
        }
        // suffix
        String last_name = address.getSuffix();
        if (Utils.validateString(last_name)) {
            mModel.addDataBody("last_name", last_name);
        }

        String statename = address.getStateName();
        JSONObject param_state = new JSONObject();
        if (Utils.validateString(statename)) {
            try {
                param_state.put("name", statename);
                param_state.put("code", address.getStates().getCode());
            } catch (JSONException e) {
                param_state = null;
            }
            if (param_state != null) {
                if (Utils.validateString(param_state.toString())) {
                    mModel.addDataBody("state", param_state);
                }
            } else {
                try {
                    param_state.put("name", "");
                    param_state.put("code", "");
                } catch (JSONException e) {

                }
                mModel.addDataBody("state", param_state);
            }
        } else {
            try {
                param_state.put("name", "");
                param_state.put("code", "");
            } catch (JSONException e) {

            }
            mModel.addDataBody("state", param_state);
        }

        // country name
        String countryname = address.getCountryName();
        JSONObject param_country = new JSONObject();
        if (Utils.validateString(countryname)) {
            try {
                param_country.put("name", countryname);
                param_country.put("code", address.getCountry().getCode());
            } catch (JSONException e) {
                param_country = null;
            }
            if (param_country != null) {
                if (Utils.validateString(param_country.toString())) {
                    mModel.addDataBody("country", param_country);
                }
            } else {
                try {
                    param_country.put("name", "");
                    param_country.put("code", "");
                } catch (JSONException e) {

                }
                mModel.addDataBody("country", param_country);
            }
        } else {
            try {
                param_country.put("name", "");
                param_country.put("code", "");
            } catch (JSONException e) {

            }
            mModel.addDataBody("country", param_country);
        }

        // street
        String street = address.getStreet();
        if (Utils.validateString(street)) {
            mModel.addDataBody("street", street);
        }

        // phone
        String phone = address.getPhone();
        if (Utils.validateString(phone)) {
            mModel.addDataBody("phone", phone);
        }

        // zip code
        String zipcode = address.getZipCode();
        if (Utils.validateString(zipcode)) {
            mModel.addDataBody("zip", zipcode);
        }

        // city
        String city = address.getCity();
        if (Utils.validateString(city)) {
            mModel.addDataBody("city", city);
        }

        mModel.request();
    }

    protected boolean isCompleteRequired(MyAddress addressBookDetail) {

        String first_name = addressBookDetail.getFirstName();
        if (!Utils.validateString(first_name)) {
            return false;
        }

        String last_name = addressBookDetail.getLastName();
        if (!Utils.validateString(last_name)) {
            return false;
        }

        String street = addressBookDetail.getStreet();
        if (!Utils.validateString(street)) {
            return false;
        }

        String city = addressBookDetail.getCity();
        if (!Utils.validateString(city)) {
            return false;
        }

        String state = addressBookDetail.getStateName();
        if (!Utils.validateString(state)) {
            return false;
        }

        String country = addressBookDetail.getCountryName();
        if (!Utils.validateString(country)) {
            return false;
        }

        String zip_code = addressBookDetail.getZipCode();
        if (!Utils.validateString(zip_code)) {
            return false;
        }

        String phone_number = addressBookDetail.getPhone();
        if (!Utils.validateString(phone_number)) {
            return false;
        }

        return true;
    }

    protected void changeFragmentCountry(int type,
                                         ArrayList<String> list_country) {
        CountryFragment fragment_country = CountryFragment.newInstance();
        fragment_country.setChooseDelegate(this);
        fragment_country.setList_country(list_country);
        fragment_country.setType(type);
        SimiManager.getIntance().replacePopupFragment(fragment_country);
    }

    public ArrayList<String> getStateFromCountry(String country,
                                                 ArrayList<CountryAllowed> listCountry) {
        ArrayList<String> states = new ArrayList<String>();

        Log.e("AddressBookDetailController ", "getStateFromCountry ==> COUNTRY " + country);

        for (CountryAllowed countryAllowed : listCountry) {
            String name_country = countryAllowed.getName();

            if (name_country.equals(country)) {
                Log.e("AddressBookDetailController ", "getStateFromCountry ==> NAME_COUNTRY " + name_country);

                ArrayList<StateOfCountry> stateOfCountries = countryAllowed.getState();
                if (null != stateOfCountries && stateOfCountries.size() > 0) {
                    for (StateOfCountry state : stateOfCountries) {
                        Log.e("AddressBookDetailController ", "getStateFromCountry ==> STATE " + state);

                        states.add(state.getName());
                    }
                }
                return states;
            }
        }
        return states;
    }

    @Override
    public void chooseCountry(int type, String mCountry) {

        ArrayList<String> states = getStateFromCountry(mCountry, this.country);

        if (type == 0) {
            mAddressDetail.setCountryName(mCountry);
            mAddressDetail.setStateName("");
            if (states.size() <= 0) {
                mAddressDetail.setStateName("");
            } else {
                mAddressDetail.setStateName(states.get(0).toString());
            }
            mAddressDetail.setCountryCode(getCountryCode(mCountry,
                    this.country));
            mAddressDetail.getCountry().setName(mCountry);
            mAddressDetail.getCountry().setCode(getCountryCode(mCountry,
                    this.country));
        }
        if (type == 1) {
            mAddressDetail.setStateName(mCountry);
            mAddressDetail.setStateCode(getStateCode(
                    mCountry,
                    getListStateFromCountry(
                            mAddressDetail.getCountryName(), this.country)));
            mAddressDetail.setStateId(getStateId(
                    mCountry,
                    getListStateFromCountry(
                            mAddressDetail.getCountryName(), this.country)));
        }

    }


    public String getCountryCode(String country,
                                 ArrayList<CountryAllowed> countryAlloweds) {
        String country_code = "";
        for (CountryAllowed countryAllowed : countryAlloweds) {
            if (countryAllowed.getName().equals(country)) {
                country_code = countryAllowed.getCode();
                return country_code;
            }
        }
        return country_code;
    }

    public String getStateCode(String statename,
                               ArrayList<StateOfCountry> stateOfCountries) {
        String state_code = "";
        if (null != stateOfCountries && stateOfCountries.size() > 0) {
            for (StateOfCountry stateOfCountry : stateOfCountries) {
                if (stateOfCountry.getName().equals(statename)) {
                    state_code = stateOfCountry.getName();
                    return state_code;
                }
            }
        }
        return state_code;
    }

    public ArrayList<StateOfCountry> getListStateFromCountry(String country,
                                                             ArrayList<CountryAllowed> countryAlloweds) {
        for (CountryAllowed countryAllowed : countryAlloweds) {
            if (countryAllowed.getName().equals(country)) {
                return countryAllowed.getState();
            }
        }
        return null;
    }

    public String getStateId(String statename,
                             ArrayList<StateOfCountry> stateOfCountries) {
        String state_Id = "";
        if (null != stateOfCountries && stateOfCountries.size() > 0) {
            for (StateOfCountry stateOfCountry : stateOfCountries) {
                if (stateOfCountry.getName().equals(statename)) {
                    state_Id = stateOfCountry.getCode();
                    return state_Id;
                }
            }
        }
        return state_Id;
    }


    @Override
    public void setCurrentCountry(String country) {
        // TODO Auto-generated method stub
    }

    @Override
    public void setCurrentState(String state) {
        // TODO Auto-generated method stub
    }

}
