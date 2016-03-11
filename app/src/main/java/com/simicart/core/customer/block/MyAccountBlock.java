package com.simicart.core.customer.block;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.core.material.LayoutRipple;

public class MyAccountBlock extends SimiBlock {
    protected LayoutRipple rlt_profile;
    protected TextView lb_profile;
    protected LayoutRipple rlt_changePass;
    protected TextView lb_changePass;
    protected LayoutRipple rlt_addressBook;
    protected TextView lb_addressBook;
    protected LayoutRipple rlt_orderHistory;
    protected TextView lb_orderHistory;
    protected LayoutRipple rlt_signOut;
    protected TextView lb_logout;
    protected Drawable icon;

    public void setProfileClick(OnClickListener click) {
        rlt_profile.setOnClickListener(click);
    }

    public void setChangePassClick(OnClickListener click) {
        rlt_changePass.setOnClickListener(click);
    }

    public void setAddressBookClick(OnClickListener click) {
        rlt_addressBook.setOnClickListener(click);
    }

    public void setOrderHistory(OnClickListener click) {
        rlt_orderHistory.setOnClickListener(click);
    }

    public void setSignOutClick(OnClickListener click) {
        rlt_signOut.setOnClickListener(click);
    }

    public MyAccountBlock(View view, Context context) {
        super(view, context);
    }

    @Override
    public void initView() {
        initCommon();
        initProfile();
        initChangePassword();
        initAddressBook();
        initOrderHistory();
        initSignOut();
        configForRTL();
    }

    private void initCommon() {
        icon = mContext.getResources().getDrawable(
                Rconfig.getInstance().drawable("ic_extend"));
        icon.setColorFilter(Config.getInstance().getContent_color(),
                PorterDuff.Mode.SRC_ATOP);

        LinearLayout ll_space = (LinearLayout) mView.findViewById(Rconfig
                .getInstance().id("ll_space"));
        ll_space.setBackgroundColor(Config.getInstance().getApp_backrground());
    }

    private void initProfile() {
        View v_profile = mView.findViewById(Rconfig.getInstance().id(
                "v_profile"));
        v_profile.setBackgroundColor(Config.getInstance().getLine_color());

        rlt_profile = (LayoutRipple) mView.findViewById(Rconfig.getInstance()
                .id("rl_profile"));

        lb_profile = (TextView) mView.findViewById(Rconfig.getInstance().id(
                "lb_profile"));
        lb_profile.setText(Config.getInstance().getText("Profile"));
        lb_profile.setTextColor(Config.getInstance().getContent_color());

        ImageView im_extend_profile = (ImageView) mView.findViewById(Rconfig
                .getInstance().id("im_extend_profile"));
        im_extend_profile.setImageDrawable(icon);

        ImageView im_profile = (ImageView) mView.findViewById(Rconfig
                .getInstance().id("im_profile"));
        Drawable ic_acc_profile = mContext.getResources().getDrawable(
                Rconfig.getInstance().drawable("ic_acc_profile"));
        ic_acc_profile.setColorFilter(Config.getInstance().getContent_color(),
                PorterDuff.Mode.SRC_ATOP);
        im_profile.setImageDrawable(ic_acc_profile);
    }

    private void initChangePassword() {
        View v_changePass = mView.findViewById(Rconfig.getInstance().id("v_changePass"));
        v_changePass.setBackgroundColor(Config.getInstance().getLine_color());

        rlt_changePass = (LayoutRipple) mView.findViewById(Rconfig.getInstance()
                .id("rl_changePass"));

        lb_changePass = (TextView) mView.findViewById(Rconfig.getInstance().id(
                "lb_changePass"));
        lb_changePass.setText(Config.getInstance().getText("Change Password"));
        lb_changePass.setTextColor(Config.getInstance().getContent_color());

        ImageView im_extend_changePass = (ImageView) mView.findViewById(Rconfig
                .getInstance().id("im_extend_changePass"));
        im_extend_changePass.setImageDrawable(icon);

        ImageView im_changePass = (ImageView) mView.findViewById(Rconfig
                .getInstance().id("im_changePass"));
        Drawable ic_changePass = mContext.getResources().getDrawable(
                Rconfig.getInstance().drawable("ic_change_pass"));
        ic_changePass.setColorFilter(Config.getInstance().getContent_color(),
                PorterDuff.Mode.SRC_ATOP);
        im_changePass.setImageDrawable(ic_changePass);
    }

    private void initAddressBook() {
        View v_address = mView.findViewById(Rconfig.getInstance().id(
                "v_address"));
        v_address.setBackgroundColor(Config.getInstance().getLine_color());

        rlt_addressBook = (LayoutRipple) mView.findViewById(Rconfig
                .getInstance().id("rl_addressBook"));

        lb_addressBook = (TextView) mView.findViewById(Rconfig.getInstance()
                .id("lb_addressBook"));
        lb_addressBook.setTextColor(Config.getInstance().getContent_color());
        lb_addressBook.setText(Config.getInstance().getText("Address Book"));

        ImageView im_extend_address = (ImageView) mView.findViewById(Rconfig
                .getInstance().id("im_extend_address"));
        im_extend_address.setImageDrawable(icon);

        ImageView im_addressbook = (ImageView) mView.findViewById(Rconfig
                .getInstance().id("im_addressbook"));
        Drawable ic_acc_address = mContext.getResources().getDrawable(
                Rconfig.getInstance().drawable("ic_acc_address"));
        ic_acc_address.setColorFilter(Config.getInstance().getContent_color(),
                PorterDuff.Mode.SRC_ATOP);
        im_addressbook.setImageDrawable(ic_acc_address);


    }

    private void initOrderHistory() {
        View v_orderhis = mView.findViewById(Rconfig.getInstance().id(
                "v_orderhis"));
        v_orderhis.setBackgroundColor(Config.getInstance().getLine_color());

        rlt_orderHistory = (LayoutRipple) mView.findViewById(Rconfig
                .getInstance().id("rl_orderHistory"));

        lb_orderHistory = (TextView) mView.findViewById(Rconfig.getInstance()
                .id("lb_orderHistory"));
        lb_orderHistory.setText(Config.getInstance().getText("Order History"));
        lb_orderHistory.setTextColor(Config.getInstance().getContent_color());

        ImageView im_extend_orderhis = (ImageView) mView.findViewById(Rconfig
                .getInstance().id("im_extend_orderhis"));
        im_extend_orderhis.setImageDrawable(icon);

        ImageView im_order_his = (ImageView) mView.findViewById(Rconfig
                .getInstance().id("im_order_his"));
        Drawable ic_acc_history = mContext.getResources().getDrawable(
                Rconfig.getInstance().drawable("ic_acc_history"));
        ic_acc_history.setColorFilter(Config.getInstance().getContent_color(),
                PorterDuff.Mode.SRC_ATOP);
        im_order_his.setImageDrawable(ic_acc_history);
    }

    private void initSignOut() {
        View v_signout = (View) mView.findViewById(Rconfig.getInstance().id(
                "v_signout"));
        v_signout.setBackgroundColor(Config.getInstance().getLine_color());

        rlt_signOut = (LayoutRipple) mView.findViewById(Rconfig.getInstance()
                .id("rl_logout"));

        lb_logout = (TextView) mView.findViewById(Rconfig.getInstance().id(
                "lb_logout"));
        lb_logout.setText(Config.getInstance().getText("Sign Out"));
        lb_logout.setTextColor(Config.getInstance().getContent_color());

        ImageView im_sign_out = (ImageView) mView.findViewById(Rconfig
                .getInstance().id("im_sign_out"));
        Drawable ic_acc_logout = mContext.getResources().getDrawable(
                Rconfig.getInstance().drawable("ic_acc_logout"));
        ic_acc_logout.setColorFilter(Config.getInstance().getContent_color(),
                PorterDuff.Mode.SRC_ATOP);
        im_sign_out.setImageDrawable(ic_acc_logout);
    }

    private void configForRTL() {
        if (DataLocal.isLanguageRTL) {
            lb_profile.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            lb_changePass.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            lb_addressBook.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            lb_orderHistory.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            lb_logout.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        }
    }


}
