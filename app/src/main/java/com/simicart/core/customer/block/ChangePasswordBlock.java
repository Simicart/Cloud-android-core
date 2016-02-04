package com.simicart.core.customer.block;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.config.Config;
import com.simicart.core.config.Constants;
import com.simicart.core.config.Rconfig;
import com.simicart.core.customer.controller.ProfileController;
import com.simicart.core.customer.delegate.ChangePasswordDelegate;
import com.simicart.core.customer.entity.ProfileEntity;
import com.simicart.core.material.ButtonRectangle;

/**
 * Created by Sony on 1/19/2016.
 */
public class ChangePasswordBlock extends SimiBlock implements ChangePasswordDelegate{
    protected EditText edt_currentPass;
    protected EditText edt_newPass;
    protected EditText edt_confirmPass;
    protected ButtonRectangle btn_save;
    protected ImageView im_show_current_pass;
    protected ImageView im_show_new_pass;
    protected ImageView im_show_confirm_pass;

    public ChangePasswordBlock(View view, Context context) {
        super(view, context);
    }

    public void setCurrentPassWatcher(TextWatcher watcher) {
        edt_currentPass.addTextChangedListener(watcher);
    }

    public void setNewPassWatcher(TextWatcher watcher) {
        edt_newPass.addTextChangedListener(watcher);
    }

    public void setConfirmPassWatcher(TextWatcher watcher) {
        edt_confirmPass.addTextChangedListener(watcher);
    }

    public void setSaveClicker(View.OnClickListener clicker) {
        btn_save.setOnClickListener(clicker);
    }

    public void setShowCurrentPass(View.OnTouchListener onTouch) {
        im_show_current_pass.setOnTouchListener(onTouch);
    }

    public void setShowNewPass(View.OnTouchListener onTouch) {
        im_show_new_pass.setOnTouchListener(onTouch);
    }

    public void setShowConfirmPass(View.OnTouchListener onTouch) {
        im_show_confirm_pass.setOnTouchListener(onTouch);
    }

    @Override
    public void initView() {
        btn_save = (ButtonRectangle) mView.findViewById(Rconfig.getInstance()
                .id("bt_save"));
        btn_save.setTextSize(Constants.SIZE_TEXT_BUTTON);
        btn_save.setText(Config.getInstance().getText("Save"));
        btn_save.setTextColor(Config.getInstance().getButton_text_color());
        btn_save.setBackgroundColor(Config.getInstance().getKey_color());

        // current password
        edt_currentPass = (EditText) mView.findViewById(Rconfig.getInstance()
                .id("et_current_pass"));
        edt_currentPass.setHint(Config.getInstance()
                .getText("Current Password"));

        // new password
        edt_newPass = (EditText) mView.findViewById(Rconfig.getInstance().id(
                "et_new_pass"));
        edt_newPass.setHint(Config.getInstance().getText("New Password"));

        // confirm password
        edt_confirmPass = (EditText) mView.findViewById(Rconfig.getInstance()
                .id("et_confirm_pass"));
        edt_confirmPass.setHint(Config.getInstance()
                .getText("Confirm Password"));

        im_show_current_pass = (ImageView) mView.findViewById(Rconfig
                .getInstance().id("im_show_current_pass"));
        im_show_new_pass = (ImageView) mView.findViewById(Rconfig.getInstance()
                .id("im_show_new_pass"));
        im_show_confirm_pass = (ImageView) mView.findViewById(Rconfig
                .getInstance().id("im_show_confirm_pass"));

        setColorEdittext(edt_currentPass);
        setHintColorEdittext(edt_currentPass);

        setColorEdittext(edt_newPass);
        setHintColorEdittext(edt_newPass);

        setColorEdittext(edt_confirmPass);
        setHintColorEdittext(edt_confirmPass);

        changeColorImageView(im_show_current_pass, "ic_show_password");
        changeColorImageView(im_show_new_pass, "ic_show_password");
        changeColorImageView(im_show_confirm_pass, "ic_show_password");
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

    private void changeColorImageView(ImageView img, String src) {
        if (img != null) {
            Drawable icon = mContext.getResources().getDrawable(
                    Rconfig.getInstance().drawable(src));
            icon.setColorFilter(Config.getInstance().getContent_color(),
                    PorterDuff.Mode.SRC_ATOP);
            img.setImageDrawable(icon);
        }
    }

    @Override
    public ProfileEntity getProfileEntity() {
        ProfileEntity profile = new ProfileEntity();
        profile.setCurrentPass(edt_currentPass.getText().toString());
        profile.setNewPass(edt_newPass.getText().toString());
        profile.setConfirmPass(edt_confirmPass.getText().toString());
        return profile;
    }

    @Override
    public void onTouchDown(int type) {
        if (type == ProfileController.TOUCH_CURRENT_PASS) {
            edt_currentPass
                    .setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else if (type == ProfileController.TOUCH_NEW_PASS) {
            edt_newPass
                    .setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else if (type == ProfileController.TOUCH_CONFIRM_PASS) {
            edt_confirmPass
                    .setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
    }

    @Override
    public void onTouchCancel(int type) {
        if (type == ProfileController.TOUCH_CURRENT_PASS) {
            edt_currentPass.setInputType(129);
            int position = edt_currentPass.length();
            Selection.setSelection(edt_currentPass.getText(), position);
        } else if (type == ProfileController.TOUCH_NEW_PASS) {
            edt_newPass.setInputType(129);
            int position = edt_newPass.length();
            Selection.setSelection(edt_newPass.getText(), position);
        } else if (type == ProfileController.TOUCH_CONFIRM_PASS) {
            edt_confirmPass.setInputType(129);
            int position = edt_confirmPass.length();
            Selection.setSelection(edt_confirmPass.getText(), position);
        }
    }

    @Override
    public String getCurrentPass() {
        return edt_currentPass.getText().toString();
    }

    @Override
    public String getNewPass() {
        return edt_newPass.getText().toString();
    }

    @Override
    public String getConfirmPass() {
        return edt_confirmPass.getText().toString();
    }
}
