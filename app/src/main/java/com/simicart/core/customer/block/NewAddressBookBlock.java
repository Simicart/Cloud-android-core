package com.simicart.core.customer.block;

import android.annotation.SuppressLint;
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
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.config.Constants;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.core.customer.delegate.ChooseCountryDelegate;
import com.simicart.core.customer.delegate.NewAddressBookDelegate;
import com.simicart.core.customer.entity.MyAddress;
import com.simicart.core.customer.entity.ProfileEntity;
import com.simicart.core.customer.fragment.NewAddressBookFragment;
import com.simicart.core.material.ButtonRectangle;

@SuppressLint("DefaultLocale")
public class NewAddressBookBlock extends SimiBlock implements
        NewAddressBookDelegate {
    protected EditText edt_first_name;
    protected EditText edt_last_name;
    protected EditText edt_street;
    protected EditText edt_city;
    protected EditText edt_state;
    protected TextView tv_state;
    protected EditText edt_zipcode;
    protected TextView tv_country;
    protected EditText edt_phone;
    protected RelativeLayout rl_state;
    protected RelativeLayout rl_country;
    protected EditText edt_email;
    protected EditText edt_pass;
    protected EditText edt_confirmPass;
    protected ButtonRectangle btn_save;
    protected int mAfterController;
    ChooseCountryDelegate mController;
    private ImageView img_state;
    private ImageView img_country;

    public void setAfterController(int afterController) {
        mAfterController = afterController;
    }

    public NewAddressBookBlock(View view, Context context) {
        super(view, context);
    }

    public void setSaveAddress(OnClickListener click) {
        btn_save.setOnClickListener(click);
    }

    public void setChooseCountry(OnClickListener click) {
        tv_country.setOnClickListener(click);
    }

    public void setChooseStates(OnClickListener click) {
        tv_state.setOnClickListener(click);
    }


    @Override
    public void initView() {

        edt_first_name = (EditText) mView.findViewById(Rconfig.getInstance().id(
                "edt_first_name"));
        edt_last_name = (EditText) mView.findViewById(Rconfig.getInstance().id(
                "edt_last_name"));
        edt_street = (EditText) mView.findViewById(Rconfig.getInstance().id(
                "et_street"));
        edt_city = (EditText) mView.findViewById(Rconfig.getInstance().id(
                "et_city"));
        edt_state = (EditText) mView.findViewById(Rconfig.getInstance().id(
                "et_state"));
        tv_state = (TextView) mView.findViewById(Rconfig.getInstance().id(
                "tv_state"));
        edt_zipcode = (EditText) mView.findViewById(Rconfig.getInstance().id(
                "et_zipcode"));
        tv_country = (TextView) mView.findViewById(Rconfig.getInstance().id(
                "tv_country"));
        edt_phone = (EditText) mView.findViewById(Rconfig.getInstance().id(
                "et_phone"));
        rl_country = (RelativeLayout) mView.findViewById(Rconfig.getInstance()
                .id("rl_country"));
        rl_state = (RelativeLayout) mView.findViewById(Rconfig.getInstance()
                .id("rl_state"));
        edt_email = (EditText) mView.findViewById(Rconfig.getInstance().id(
                "et_email"));
        edt_pass = (EditText) mView.findViewById(Rconfig.getInstance().id(
                "et_pass"));
        edt_confirmPass = (EditText) mView.findViewById(Rconfig.getInstance()
                .id("et_confirm_pass"));
        btn_save = (ButtonRectangle) mView.findViewById(Rconfig.getInstance()
                .id("bt_save"));
        img_state = (ImageView) mView.findViewById(Rconfig.getInstance().id(
                "imv_extend_st"));
        img_country = (ImageView) mView.findViewById(Rconfig.getInstance().id(
                "imv_extend_ct"));
    }

    @Override
    public void createView(int control) {
        createFirstName(control);
        createLastName();
        createEmail(control);
        createStreet();
        createCity();
        createState(control);
        createZipCode();
        createPhone();
        createPassAndPassConfirm(control);
        createButtonSave();
        changeColorComponent();
    }

    private void changeColorComponent() {
        Utils.changeColorTextView(tv_state);
        Utils.changeColorTextView(tv_country);
        Utils.changeColorEditText(edt_first_name);
        Utils.changeColorEditText(edt_last_name);
        Utils.changeColorEditText(edt_street);
        Utils.changeColorEditText(edt_city);
        Utils.changeColorEditText(edt_state);
        Utils.changeColorEditText(edt_zipcode);
        Utils.changeColorEditText(edt_phone);
        Utils.changeColorEditText(edt_email);
        Utils.changeColorEditText(edt_pass);
        Utils.changeColorEditText(edt_confirmPass);
        Utils.changeColorImageview(mContext, img_state, "ic_extend");
        Utils.changeColorImageview(mContext, img_country, "ic_extend");
    }


    protected void createFirstName(int control) {
        edt_first_name.setHint(Config.getInstance().getText("First Name") + " (*)");
    }

    protected void createLastName() {
        edt_last_name.setHint(Config.getInstance().getText("Last Name") + " (*)");
    }


    protected void createStreet() {

        edt_street.setHint(Config.getInstance().getText("Street") + " (*)");
    }

    protected void createCity() {
        edt_city.setHint(Config.getInstance().getText("City") + " (*)");
    }

    protected void createZipCode() {
        edt_zipcode.setHint(Config.getInstance().getText("Post/Zip Code") + " (*)");
    }

    protected void createPhone() {
        edt_phone.setHint(Config.getInstance().getText("Phone") + " (*)");
    }

    protected void createEmail(int control) {
        if (control != NewAddressBookFragment.NEW_ADDRESS
                && control != NewAddressBookFragment.NEW_ADDRESS_CHECKOUT) {
            String email = DataLocal.getEmail();
            if (Utils.validateString(email)) {
                edt_email.setText(email);
                if (mAfterController != NewAddressBookFragment.NEW_CUSTOMER && mAfterController != NewAddressBookFragment.NEW_AS_GUEST) {
                    edt_email.setKeyListener(null);
                }
            } else {
                edt_email.setHint(Config.getInstance().getText("Email")
                        + " (*)");
            }
        } else {
            if (control == NewAddressBookFragment.NEW_ADDRESS_CHECKOUT
                    && !DataLocal.isSignInComplete()) {
                edt_email.setHint(Config.getInstance().getText("Email")
                        + " (*)");
            } else {
                String email = DataLocal.getEmail();
                edt_email.setText(email);
                edt_email.setVisibility(View.GONE);
            }
        }
    }


    protected void createState(int control) {
        edt_state.setHint(Config.getInstance().getText("State"));

    }

    protected void createButtonSave() {
        btn_save.setTextColor(Color.WHITE);
        btn_save.setText(Config.getInstance().getText("Save"));
        btn_save.setTextSize(Constants.SIZE_TEXT_BUTTON);
        btn_save.setBackgroundColor(Config.getInstance().getKey_color());
    }


    protected void createPassAndPassConfirm(int control) {
        if (control == NewAddressBookFragment.NEW_CUSTOMER) {
            edt_pass.setHint(Config.getInstance().getText("Password") + " (*)");
            edt_confirmPass.setHint(Config.getInstance().getText(
                    "Confirm Password")
                    + " (*)");
        } else {
            edt_pass.setVisibility(View.GONE);
            edt_confirmPass.setVisibility(View.GONE);
        }
    }

    @Override
    public MyAddress getNewAddressBook() {
        MyAddress address = new MyAddress();
        // prefix
        String first_name = edt_first_name.getText().toString();
        if (Utils.validateString(first_name)) {
            address.setFirstName(first_name);
        }
        // full name
        String last_name = edt_last_name.getText().toString();
        if (Utils.validateString(last_name)) {
            address.setLastName(last_name);
        }
        // street
        String street = edt_street.getText().toString();
        if (Utils.validateString(street)) {
            address.setStreet(street);
        }
        // city
        String city = edt_city.getText().toString();
        if (Utils.validateString(city)) {
            address.setCity(city);
        }
        // post ZIP code
        String zipcode = edt_zipcode.getText().toString();
        if (Utils.validateString(zipcode)) {
            address.setZipCode(zipcode);
        }
        // phone
        String phone = edt_phone.getText().toString();
        if (Utils.validateString(phone)) {
            address.setPhone(phone);
        }

        // email
        String email = edt_email.getText().toString();
        if (Utils.validateString(email)) {
            address.setEmail(email);
        }

//        // state
//        String state = tv_state.getText().toString();
//        if (Utils.validateString(state)) {
//            address.setStateName(state);
//        }
//        // country
//        String country = tv_country.getText().toString();
//        if (Utils.validateString(country)) {
//            address.setCountryName(country);
//        }

        return address;
    }

    @Override
    public ProfileEntity getProfileEntity() {
        ProfileEntity profile = new ProfileEntity();

        // email
        String email = edt_email.getText().toString();
        if (null != email) {
            profile.setEmail(email);
        } else {
            return null;
        }

        // password
        String password = edt_pass.getText().toString();
        if (null != password) {
            if (password.length() < 6) {
                SimiManager.getIntance().showNotify(
                        Config.getInstance().getText(
                                "Please enter 6 or more characters."));
                return null;
            }
            profile.setCurrentPass(password);
        } else {
            return null;
        }

        // confirm password
        String confirmpassword = edt_confirmPass.getText().toString();
        if (null != confirmpassword) {
            profile.setConfirmPass(confirmpassword);
        } else {
            return null;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            SimiManager.getIntance().showNotify(null,
                    Config.getInstance().getText("Invalid email address"),
                    Config.getInstance().getText("OK"));
            return null;

        }
        if (!password.equals(confirmpassword)) {
            SimiManager.getIntance().showNotify(
                    null,
                    Config.getInstance().getText(
                            "Password and Confirm password dont't match."),
                    Config.getInstance().getText("OK"));
            return null;

        }

        return profile;
    }

    @Override
    public void updateCountry(String country) {
        tv_country.setVisibility(View.VISIBLE);
        if (rl_country.getVisibility() == View.VISIBLE) {
            tv_country.setText(country);
        }
        if (mController != null) {
            mController.setCurrentCountry(country);
        }
    }

    @Override
    public void updateState(String state) {
        if (Utils.validateString(state)) {
            edt_state.setVisibility(View.GONE);
            if (rl_state.getVisibility() == View.VISIBLE) {
                tv_state.setText(state);
            }
        } else {
            edt_state.setVisibility(View.GONE);
            edt_state.setHint(Config.getInstance().getText("State"));
            rl_state.setVisibility(View.GONE);
        }
        if (mController != null) {
            mController.setCurrentState(state);
        }
    }

    public void setControllerDelegate(ChooseCountryDelegate mController) {
        this.mController = mController;
    }


}
