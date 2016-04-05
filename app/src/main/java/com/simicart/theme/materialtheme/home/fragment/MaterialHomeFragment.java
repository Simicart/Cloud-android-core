package com.simicart.theme.materialtheme.home.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.config.Rconfig;
import com.simicart.theme.materialtheme.home.block.MaterialHomeBlock;
import com.simicart.theme.materialtheme.home.controller.MaterialHomeController;

/**
 * Created by Sony on 3/26/2016.
 */
public class MaterialHomeFragment extends SimiFragment {
    protected MaterialHomeBlock mBlock;
    protected MaterialHomeController mController;

    public static MaterialHomeFragment newInstance() {
        MaterialHomeFragment fragment = new MaterialHomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                Rconfig.getInstance().layout("material_home_layout"), container, false);
        Context context = getActivity();
        mBlock = new MaterialHomeBlock(rootView, context);
        mBlock.setFragmentManager(getChildFragmentManager());
        mBlock.initView();
        if (mController == null) {
            mController = new MaterialHomeController();
            mController.setDelegate(mBlock);
            mController.onStart();
        } else {
            mController.setDelegate(mBlock);
            mController.onResume();
        }
        mBlock.setOnClickTitleViewPager(mController.getOnClickTitleViewPager());
        return rootView;
    }
}
