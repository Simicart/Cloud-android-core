package com.simicart.core.setting.block;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.core.material.LayoutRipple;
import com.simicart.core.setting.delegate.SettingAppDelegate;
import android.view.View.OnClickListener;

/**
 * Created by Sony on 3/10/2016..
 */
public class SettingAppBlock extends SimiBlock implements SettingAppDelegate{
    protected TextView tv_language;
    protected LayoutRipple rl_language;
    protected TextView tv_language_selected;
    protected TextView tv_currency;
    protected LayoutRipple rl_currency;
    protected TextView tv_currency_selected;
    protected TextView tv_notification;
    protected RelativeLayout rl_notification;
    protected TextView tv_locator;
    protected LayoutRipple rl_locator;
    protected ToggleButton tb_notification;
    protected View v_under;

    public SettingAppBlock(View view, Context context) {
        super(view, context);
    }

    public void setOnClickLanguage(OnClickListener listener){
        rl_language.setOnClickListener(listener);
    }

    public void setOnClickCurrency(OnClickListener listener){
        rl_currency.setOnClickListener(listener);
    }

    public void setOnClickNotification(OnClickListener listener){
        rl_notification.setOnClickListener(listener);
    }

    public void setOnClickLocator(OnClickListener listener){
        rl_locator.setOnClickListener(listener);
    }

    @Override
    public void initView() {
        // Language
        rl_language = (LayoutRipple) mView.findViewById(Rconfig
                .getInstance().id("rl_language"));
        tv_language = (TextView) mView.findViewById(Rconfig.getInstance()
                .id("tv_language"));
        tv_language.setText(Config.getInstance().getText("Language"));
        tv_language.setTextColor(Config.getInstance().getContent_color());
        tv_language_selected = (TextView) mView.findViewById(Rconfig
                .getInstance().id("tv_language_selected"));

        // Currency
        rl_currency = (LayoutRipple) mView.findViewById(Rconfig
                .getInstance().id("rl_currency"));
        rl_currency.setVisibility(View.GONE);
        tv_currency = (TextView) mView.findViewById(Rconfig.getInstance()
                .id("tv_currency"));
        tv_currency.setText(Config.getInstance().getText("Currency"));
        tv_currency.setTextColor(Config.getInstance().getContent_color());
        tv_currency_selected = (TextView) mView.findViewById(Rconfig
                .getInstance().id("tv_currency_selected"));

        // Notification
        rl_notification = (RelativeLayout) mView.findViewById(Rconfig
                .getInstance().id("rl_notification"));
        rl_notification.setBackgroundColor(Config.getInstance()
                .getApp_backrground());
        tv_notification = (TextView) mView.findViewById(Rconfig
                .getInstance().id("tv_notification"));
        tv_notification.setBackgroundColor(Config.getInstance()
                .getApp_backrground());
        tv_notification.setTextColor(Config.getInstance().getContent_color());
        tv_notification.setText(Config.getInstance().getText(
                "Show notifications"));

        tb_notification = (ToggleButton) mView
                .findViewById(Rconfig.getInstance().id("tb_notification"));
        if (DataLocal.enableNotification()) {
            tb_notification.setChecked(true);
        } else {
            tb_notification.setChecked(false);
        }

        // Locator
        rl_locator = (LayoutRipple) mView.findViewById(Rconfig.getInstance()
                .id("rl_locator"));
        rl_locator.setVisibility(View.GONE);
        tv_locator = (TextView) mView.findViewById(Rconfig.getInstance().id(
                "tv_locator"));
        tv_locator.setTextColor(Config.getInstance().getContent_color());
        tv_locator.setText(Config.getInstance().getText("Location Setting"));
        v_under = (View) mView.findViewById(Rconfig.getInstance().id(
                "v_under"));
        v_under.setBackgroundColor(Config.getInstance().getApp_backrground());

        setColor(mView);
    }

    private void setColor(View mView) {
        ImageView im_language_extend = (ImageView) mView.findViewById(Rconfig
                .getInstance().id("im_language_extend"));
        ImageView im_currency_extend = (ImageView) mView.findViewById(Rconfig
                .getInstance().id("im_currency_extend"));
        ImageView im_locator_extend = (ImageView) mView.findViewById(Rconfig
                .getInstance().id("im_locator_extend"));
        Drawable icon = mContext.getResources().getDrawable(
                Rconfig.getInstance().drawable("ic_extend"));
        icon.setColorFilter(Config.getInstance().getContent_color(),
                PorterDuff.Mode.SRC_ATOP);
        im_language_extend.setImageDrawable(icon);
        im_currency_extend.setImageDrawable(icon);
        im_locator_extend.setImageDrawable(icon);

        ImageView im_language = (ImageView) mView.findViewById(Rconfig
                .getInstance().id("im_language"));
        Drawable ic_lang = mContext.getResources().getDrawable(
                Rconfig.getInstance().drawable("ic_lang"));
        ic_lang.setColorFilter(Config.getInstance().getContent_color(),
                PorterDuff.Mode.SRC_ATOP);
        im_language.setImageDrawable(ic_lang);

        ImageView im_currency = (ImageView) mView.findViewById(Rconfig
                .getInstance().id("im_currency"));
        Drawable ic_currency = mContext.getResources().getDrawable(
                Rconfig.getInstance().drawable("ic_currency"));
        ic_currency.setColorFilter(Config.getInstance().getContent_color(),
                PorterDuff.Mode.SRC_ATOP);
        im_currency.setImageDrawable(ic_currency);

        ImageView im_notification = (ImageView) mView.findViewById(Rconfig
                .getInstance().id("im_notification"));
        Drawable ic_notification = mContext.getResources().getDrawable(
                Rconfig.getInstance().drawable("ic_notification"));
        ic_notification.setColorFilter(Config.getInstance().getContent_color(),
                PorterDuff.Mode.SRC_ATOP);
        im_notification.setImageDrawable(ic_notification);

        ImageView im_locator = (ImageView) mView.findViewById(Rconfig
                .getInstance().id("im_locator"));
        Drawable ic_location_setting = mContext.getResources().getDrawable(
                Rconfig.getInstance().drawable("ic_location_setting"));
        ic_location_setting.setColorFilter(Config.getInstance()
                .getContent_color(), PorterDuff.Mode.SRC_ATOP);
        im_locator.setImageDrawable(ic_location_setting);

        View v_language = (View) mView.findViewById(Rconfig.getInstance().id(
                "v_language"));
        View v_currency = (View) mView.findViewById(Rconfig.getInstance().id(
                "v_currency"));
        View v_notification = (View) mView.findViewById(Rconfig.getInstance()
                .id("v_notification"));
        View v_locator = (View) mView.findViewById(Rconfig.getInstance().id(
                "v_locator"));
        v_language.setBackgroundColor(Config.getInstance().getLine_color());
        v_currency.setBackgroundColor(Config.getInstance().getLine_color());
        v_notification.setBackgroundColor(Config.getInstance().getLine_color());
        v_locator.setBackgroundColor(Config.getInstance().getLine_color());

        mView.setBackgroundColor(Config.getInstance().getApp_backrground());
    }

    @Override
    public void updateLanguage(String language) {
        tv_language_selected.setText(language);
    }

    @Override
    public void hidenLanguage() {
        rl_language.setVisibility(View.GONE);
    }

    @Override
    public void updateCurrency(String currency) {
        tv_currency_selected.setText(currency);
    }

    @Override
    public void selectNotification() {
        if (tb_notification.isChecked()) {
            tb_notification.setChecked(false);
            DataLocal.saveNotificationSet(false);
        } else {
            tb_notification.setChecked(true);
            DataLocal.saveNotificationSet(true);
        }
    }
}
