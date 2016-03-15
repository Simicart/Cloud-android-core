package com.simicart.core.customer.block;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.config.Constants;
import com.simicart.core.config.Rconfig;
import com.simicart.core.customer.delegate.AddressBookDetailDelegate;
import com.simicart.core.customer.entity.CountryAllowed;
import com.simicart.core.customer.entity.MyAddress;
import com.simicart.core.customer.entity.StateOfCountry;
import com.simicart.core.material.ButtonRectangle;

import org.json.JSONObject;

public class AddressBookDetailBlock extends SimiBlock implements
        AddressBookDetailDelegate {
    protected MyAddress mAddressDetail;
    protected EditText edt_first_name;
    protected EditText edt_last_name;
    protected EditText et_street;
    protected EditText et_city;
    protected EditText et_state;
    protected TextView tv_state;
    protected EditText et_zipcode;
    protected TextView tv_country;
    protected EditText et_phone;
    protected RelativeLayout rl_state;
    protected RelativeLayout rl_country;
    protected ButtonRectangle bt_save;
    private ImageView img_state;
    private ImageView img_country;

    public AddressBookDetailBlock(View view, Context context) {
        super(view, context);
    }

    public void setSaveClicker(OnClickListener clicker) {
        bt_save.setOnClickListener(clicker);
    }

    public void setChooseCountry(OnClickListener clicker) {
        tv_country.setOnClickListener(clicker);
    }

    public void setChooseStates(OnClickListener clicker) {
        tv_state.setOnClickListener(clicker);
    }

    @Override
    public void initView() {
        edt_first_name = (EditText) mView.findViewById(Rconfig.getInstance().id(
                "edt_first_name"));
        edt_last_name = (EditText) mView.findViewById(Rconfig.getInstance().id(
                "edt_last_name"));
        et_street = (EditText) mView.findViewById(Rconfig.getInstance().id(
                "et_street"));
        et_city = (EditText) mView.findViewById(Rconfig.getInstance().id(
                "et_city"));
        et_state = (EditText) mView.findViewById(Rconfig.getInstance().id(
                "et_state"));
        tv_state = (TextView) mView.findViewById(Rconfig.getInstance().id(
                "tv_state"));
        et_zipcode = (EditText) mView.findViewById(Rconfig.getInstance().id(
                "et_zipcode"));
        tv_country = (TextView) mView.findViewById(Rconfig.getInstance().id(
                "tv_country"));
        et_phone = (EditText) mView.findViewById(Rconfig.getInstance().id(
                "et_phone"));
        rl_country = (RelativeLayout) mView.findViewById(Rconfig.getInstance()
                .id("rl_country"));
        rl_state = (RelativeLayout) mView.findViewById(Rconfig.getInstance()
                .id("rl_state"));
        bt_save = (ButtonRectangle) mView.findViewById(Rconfig.getInstance()
                .id("bt_save"));
        img_state = (ImageView) mView.findViewById(Rconfig.getInstance().id(
                "imv_extend_st"));
        img_country = (ImageView) mView.findViewById(Rconfig.getInstance().id(
                "imv_extend_ct"));

        createButtonSave();
        changeColor();
    }


    @Override
    public void showAddressDetail(MyAddress address) {
        mAddressDetail = address;
        createFirstName();
        createLastName();
        createStreet();
        createCity();
        createZipCode();
        createCountry();
        createPhone();

        StateOfCountry stateOfCountry = mAddressDetail.getStates();
        if (null != stateOfCountry) {
            String state = stateOfCountry.getName();
            tv_state.setText(state);
        }
    }

    private void changeColor() {
        Utils.changeColorEditText(edt_first_name);
        Utils.changeColorEditText(edt_last_name);
        Utils.changeColorEditText(et_street);
        Utils.changeColorEditText(et_city);
        Utils.changeColorEditText(et_state);
        Utils.changeColorEditText(et_zipcode);
        Utils.changeColorEditText(et_phone);
        Utils.changeColorTextView(tv_state);
        Utils.changeColorTextView(tv_country);
        Utils.changeColorImageview(mContext, img_state, "ic_extend");
        Utils.changeColorImageview(mContext, img_country, "ic_extend");
    }

    private void createFirstName() {
        edt_first_name.setHint(Config.getInstance().getText("First Name") + " (*)");

        String first_name = mAddressDetail.getFirstName();
        if (Utils.validateString(first_name)) {
            edt_first_name.setText(first_name);
        }

    }

    private void createLastName() {
        String last_name = mAddressDetail.getLastName();
        edt_last_name.setHint(Config.getInstance().getText("Last Name") + " (*)");
        if (Utils.validateString(last_name)) {
            edt_last_name.setText(last_name);
        }

    }


    private void createStreet() {
        et_street.setHint(Config.getInstance().getText("Street") + " (*)");
        String street = mAddressDetail.getStreet();
        if (Utils.validateString(street)) {
            et_street.setText(street);
        }
    }

    private void createCity() {
        et_city.setHint(Config.getInstance().getText("City") + " (*)");
        String city = mAddressDetail.getCity();
        if (Utils.validateString(city)) {
            et_city.setText(city);
        }

    }

    private void createZipCode() {

        et_zipcode.setHint(Config.getInstance().getText("Post/Zip Code")
                + " (*)");
        String zipcode = mAddressDetail.getZipCode();

        if (Utils.validateString(zipcode)) {
            et_zipcode.setText(zipcode);
        }
    }

    private void createPhone() {
        et_phone.setHint(Config.getInstance().getText("Phone") + " (*)");
        String phone = mAddressDetail.getPhone();
        if (Utils.validateString(phone)) {
            et_phone.setText(mAddressDetail.getPhone());
        }

    }


    private void createCountry() {
        String countryname = mAddressDetail.getCountry().getName();
        if (Utils.validateString(countryname)) {
            tv_country.setText(countryname);
        }
    }

    private void createState(ArrayList<CountryAllowed> listCountry) {
        ArrayList<String> states = getStateFromCountry(
                mAddressDetail.getCountry().getName(), listCountry);

        et_state.setVisibility(View.VISIBLE);
        rl_state.setVisibility(View.VISIBLE);


        if (states.size() <= 0) {
            et_state.setVisibility(View.GONE);
            rl_state.setVisibility(View.GONE);
            et_state.setHint(Config.getInstance().getText("State"));
        } else {
            rl_state.setVisibility(View.VISIBLE);
            et_state.setVisibility(View.GONE);
        }

        String state = mAddressDetail.getStateName();
        if (Utils.validateString(state)) {
            tv_state.setText(state);
        }

    }

    @SuppressWarnings("deprecation")
    private void createButtonSave() {
        bt_save.setTextColor(Color.WHITE);
        bt_save.setText(Config.getInstance().getText("Save"));
        bt_save.setBackgroundColor(Config.getInstance().getKey_color());
        bt_save.setTextSize(Constants.SIZE_TEXT_BUTTON);
    }


    @Override
    public MyAddress getAddressBookDetail() {
        MyAddress addressBookDetail = new MyAddress();

        String first_name = edt_first_name.getText().toString();
        if (null != first_name) {
            addressBookDetail.setFirstName(first_name);
        }
        String last_name = edt_last_name.getText().toString();
        if (null != last_name) {
            addressBookDetail.setLastName(last_name);
        }

        String city = et_city.getText().toString();
        if (null != city) {
            addressBookDetail.setCity(city);
        }
        String zipcode = et_zipcode.getText().toString();
        if (null != zipcode) {
            addressBookDetail.setZipCode(zipcode);
        }
        String phone = et_phone.getText().toString();
        if (null != phone) {
            addressBookDetail.setPhone(phone);
        }
        String street = et_street.getText().toString();
        if (null != street) {
            addressBookDetail.setStreet(street);
        }
        String id = mAddressDetail.getAddressId();
        if (null != id) {
            addressBookDetail.setAddressId(id);
        }
        String statename = mAddressDetail.getStateName();
        if (null != statename) {
            addressBookDetail.setStateName(statename);
            String statecode = mAddressDetail.getStateCode();
            if (null != statecode) {
                addressBookDetail.setStateCode(statecode);
            }
            String stateid = mAddressDetail.getStateId();
            if (null != stateid) {
                addressBookDetail.setStateId(stateid);
            }
        }


        String countryname = tv_country.getText().toString();
        if (null != countryname) {
            addressBookDetail.setCountryName(countryname);
            String countrycode = mAddressDetail.getCountryCode();
            if (null != countrycode) {
                addressBookDetail.setCountryCode(countrycode);
            }
        }
        if (null != mAddressDetail.getID()) {
            addressBookDetail.setID(mAddressDetail.getID());
        }
        return addressBookDetail;
    }


    public ArrayList<String> getStateFromCountry(String country,
                                                 ArrayList<CountryAllowed> listCountry) {

        ArrayList<String> states = new ArrayList<String>();
        for (CountryAllowed countryAllowed : listCountry) {
            String name = countryAllowed.getName();
            ArrayList<StateOfCountry> stateCountry = countryAllowed.getState();
            if (name.equals(country)
                    && stateCountry != null) {
                for (StateOfCountry state : stateCountry) {
                    states.add(state.getName());
                }
                return states;
            }
        }
        return states;
    }

    @Override
    public void setListCountry(ArrayList<CountryAllowed> listCountry) {
        createState(listCountry);
    }


}
