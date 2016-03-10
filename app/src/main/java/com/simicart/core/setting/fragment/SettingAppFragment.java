package com.simicart.core.setting.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.core.setting.block.SettingAppBlock;
import com.simicart.core.setting.controller.SettingAppController;

public class SettingAppFragment extends SimiFragment {
    protected Context mContext;
    protected SettingAppBlock mBlock;
    protected SettingAppController mController;
    protected View rootView;

    public static SettingAppFragment newInstance() {
        SettingAppFragment fragment = new SettingAppFragment();
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (DataLocal.isLanguageRTL) {
            rootView = inflater.inflate(
                    Rconfig.getInstance().layout("rtl_setting_layout"),
                    container, false);
        } else {
            rootView = inflater.inflate(
                    Rconfig.getInstance().layout("core_setting_layout"), container,
                    false);
        }

        mContext = getActivity();

        mBlock = new SettingAppBlock(rootView, mContext);
        mBlock.initView();
        if(mController == null){
            mController = new SettingAppController();
            mController.setContext(mContext);
            mController.setDelegate(mBlock);
            mController.onStart();
        }else{
            mController.setContext(mContext);
            mController.setDelegate(mBlock);
            mController.onResume();
        }

        mBlock.setOnClickLanguage(mController.getOnClickLanguage());
        mBlock.setOnClickCurrency(mController.getOnClickCurrency());
        mBlock.setOnClickNotification(mController.getOnClickNotification());
        mBlock.setOnClickLocator(mController.getOnClickLocator());

        return rootView;
    }
}
