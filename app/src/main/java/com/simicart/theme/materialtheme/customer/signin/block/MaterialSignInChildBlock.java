package com.simicart.theme.materialtheme.customer.signin.block;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.config.Config;
import com.simicart.core.config.Constants;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.core.material.ButtonRectangle;

/**
 * Created by Martial on 4/11/2016.
 */
public class MaterialSignInChildBlock extends SimiBlock {

    private ButtonRectangle btn_SignIn;
    private EditText edt_Email;
    private EditText edt_Password;
    private TextView tv_ForgotPassword;
    private CheckBox cb_remember_password;
    private TextView txt_label_create_account;

    private String mEmail = "";
    private String mPassword = "";

    private ImageView img_email;
    private ImageView img_password;

    public MaterialSignInChildBlock(View view, Context context) {
        super(view, context);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void initView() {
        //super.initView();

        cb_remember_password = (CheckBox) mView.findViewById(Rconfig
                .getInstance().id("cb_re_password"));
        cb_remember_password.setText(Config.getInstance().getText(
                "Remember password"));
        cb_remember_password.setTextColor(Config.getInstance()
                .getContent_color());

        txt_label_create_account = (TextView) mView.findViewById(Rconfig
                .getInstance().id("tv_create_account"));
        txt_label_create_account.setText(Config.getInstance().getText(
                "Don't have an account?"));

        btn_SignIn = (ButtonRectangle) mView.findViewById(Rconfig.getInstance()
                .id("bt_signIn"));
        btn_SignIn.setText(Config.getInstance().getText("Sign In"));
        btn_SignIn.setTextColor(Config.getInstance().getButton_text_color());
        btn_SignIn.setBackgroundColor(Config.getInstance()
                .getButton_background());
        btn_SignIn.setTextSize(Constants.SIZE_TEXT_BUTTON);

        // initial Email Field
        edt_Email = (EditText) mView.findViewById(Rconfig.getInstance().id(
                "et_email"));
        edt_Email.setHint(Config.getInstance().getText("Email"));

        edt_Email.setTextColor(Config.getInstance().getContent_color());
        edt_Email.setHintTextColor(Config.getInstance().getHintContent_color());

        // initial Password Field
        edt_Password = (EditText) mView.findViewById(Rconfig.getInstance().id(
                "et_pass"));
        edt_Password.setHint(Config.getInstance().getText("Password"));
        edt_Password.setTextColor(Config.getInstance().getContent_color());
        edt_Password.setHintTextColor(Config.getInstance()
                .getHintContent_color());
        // initial Forgot Password
        tv_ForgotPassword = (TextView) mView.findViewById(Rconfig
                .getInstance().id("iv_forgot_pass"));

        if (DataLocal.getCheckRemember()) {
            cb_remember_password.setChecked(true);
            edt_Email.setText(DataLocal.getEmailRemember());
            edt_Password.setText(DataLocal.getPasswordRemember());
            btn_SignIn.setTextColor(Color.WHITE);
            btn_SignIn.setBackgroundColor(Config.getInstance().getKey_color());
        } else {
            if (mEmail != null && !mEmail.equals("")) {
                edt_Email.setText(mEmail);
            } else {
                edt_Email.setText("");
            }
            if (mPassword != null && !mPassword.equals("")) {
                edt_Password.setText(mPassword);
            } else {
                edt_Password.setText("");
            }
        }
        img_email = (ImageView) mView.findViewById(Rconfig.getInstance().id(
                "img_email"));
        img_password = (ImageView) mView.findViewById(Rconfig.getInstance().id(
                "iv_pass"));
        changeColorImageView(img_email, "ic_your_acc");
        changeColorImageView(img_password, "ic_your_pas");
    }

    private void changeColorImageView(ImageView imageView, String src) {
        Drawable icon = mContext.getResources().getDrawable(
                Rconfig.getInstance().drawable(src));
        icon.setColorFilter(Config.getInstance().getContent_color(),
                PorterDuff.Mode.SRC_ATOP);
        imageView.setImageDrawable(icon);
    }

    @Override
    public void drawView(SimiCollection collection) {
        super.drawView(collection);
    }

    @Override
    public void updateView(SimiCollection collection) {
        super.updateView(collection);
    }
}
