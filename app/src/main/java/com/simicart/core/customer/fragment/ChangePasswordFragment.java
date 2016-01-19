package com.simicart.core.customer.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.core.customer.block.ChangePasswordBlock;
import com.simicart.core.customer.controller.ChangePasswordController;

/**
 * Created by Sony on 1/19/2016.
 */
public class ChangePasswordFragment extends SimiFragment {
    protected ChangePasswordBlock mBlock;
    protected ChangePasswordController mController;
    public static ChangePasswordFragment newInstance() {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(
                Rconfig.getInstance().layout("core_change_password_layout"),
                container, false);

        if (DataLocal.isLanguageRTL) {
            view = inflater.inflate(
                    Rconfig.getInstance().layout("rtl_change_password_layout"),
                    container, false);
        }

        Context context = getActivity();
        mBlock = new ChangePasswordBlock(view, context);
        mBlock.initView();

        if(mController == null){
            mController = new ChangePasswordController();
            mController.setDelegate(mBlock);
            mController.onStart();
        }else{
            mController.setDelegate(mBlock);
            mController.onResume();
        }

        mBlock.setShowCurrentPass(mController.getOnTouchCurrentPass());
        mBlock.setShowNewPass(mController.getOnTouchNewPass());
        mBlock.setShowConfirmPass(mController.getOnTouchConfirmPass());
        mBlock.setSaveClicker(mController.getSaveClicker());
        return view;
    }
}
