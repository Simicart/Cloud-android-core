package com.simicart.theme.materialtheme.customer.signin.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.config.Rconfig;
import com.simicart.theme.materialtheme.customer.signin.block.MaterialSignInChildBlock;
import com.simicart.theme.materialtheme.customer.signin.controller.MaterialSignInChildController;

/**
 * Created by Martial on 4/11/2016.
 */
public class MaterialSignInChildFragment extends SimiFragment {

    MaterialSignInChildBlock mBlock;
    MaterialSignInChildController mController;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(Rconfig.getInstance().layout("material_sign_in_child_layout"), container, false);

        mBlock = new MaterialSignInChildBlock(v, getActivity());
        mBlock.initView();

        return v;
    }
}
