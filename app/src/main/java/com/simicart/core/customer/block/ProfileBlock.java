package com.simicart.core.customer.block;

import java.util.ArrayList;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.text.Selection;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.config.Constants;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.core.customer.adapter.GenderAdapter;
import com.simicart.core.customer.controller.ProfileController;
import com.simicart.core.customer.delegate.ProfileDelegate;
import com.simicart.core.customer.entity.ConfigCustomerAddress;
import com.simicart.core.customer.entity.GenderConfig;
import com.simicart.core.customer.entity.ProfileEntity;
import com.simicart.core.material.ButtonRectangle;
import com.simicart.core.material.LayoutRipple;

@SuppressLint("DefaultLocale")
public class ProfileBlock extends SimiBlock implements ProfileDelegate {

    protected EditText edt_first_name;
    protected EditText edt_last_name;
    protected EditText edt_email;
    protected ButtonRectangle btn_save;

    public ProfileBlock(View view, Context context) {
        super(view, context);
    }

    public void setSaveClicker(OnClickListener clicker) {
        btn_save.setOnClickListener(clicker);
    }


    @SuppressWarnings("deprecation")
    @Override
    public void initView() {
        // first name
        edt_first_name = (EditText) mView.findViewById(Rconfig.getInstance().id(
                "edt_first_name"));

        // last name
        edt_last_name = (EditText) mView.findViewById(Rconfig.getInstance().id(
                "edt_last_name"));

        // email
        edt_email = (EditText) mView.findViewById(Rconfig.getInstance().id(
                "edt_email"));

        // save button
        btn_save = (ButtonRectangle) mView.findViewById(Rconfig.getInstance()
                .id("bt_save"));
        btn_save.setTextSize(Constants.SIZE_TEXT_BUTTON);
        btn_save.setText(Config.getInstance().getText("Save"));
        btn_save.setTextColor(Config.getInstance().getButton_text_color());
        btn_save.setBackgroundColor(Config.getInstance().getKey_color());

        setColorComponent();
    }

    @Override
    public void drawView(SimiCollection collection) {
        if (null != collection) {
            ArrayList<SimiEntity> entities = collection.getCollection();
            if (null != entities && entities.size() > 0) {
                ProfileEntity profile = (ProfileEntity) entities.get(0);
                showProfile(profile);
            }
        }

    }

    private void showProfile(ProfileEntity profile) {
        String first_name = profile.getFirstName();
        if (Utils.validateString(first_name)) {
            edt_first_name.setText(first_name);
        }

        String last_name = profile.getLastName();
        if (Utils.validateString(last_name)) {
            edt_last_name.setText(last_name);
        }

        String email = profile.getEmail();
        if (Utils.validateString(email)) {
            edt_email.setText(email);
        }

    }

    private void setColorComponent() {
        setColorEdittext(edt_email);
        setHintColorEdittext(edt_email);

        setColorEdittext(edt_first_name);
        setHintColorEdittext(edt_first_name);

        setColorEdittext(edt_last_name);
        setHintColorEdittext(edt_last_name);

    }


    private void setColorEdittext(EditText editText) {
        if (editText != null) {
            editText.setTextColor(Config.getInstance().getContent_color());
        }
    }

    private void setHintColorEdittext(EditText editText) {
        if (editText != null) {
            editText.setHintTextColor(Config.getInstance()
                    .getHintContent_color());
        }
    }


    @Override
    public ProfileEntity getProfileEntity() {
        ProfileEntity profile = new ProfileEntity();
        //first name
        String first_name = edt_first_name.getText().toString();
        profile.setFirstName(first_name);
        //last name
        String last_name = edt_last_name.getText().toString();
        profile.setLastName(last_name);
        // email
        String email = edt_email.getText().toString();
        profile.setEmail(email);

        return profile;
    }


}
