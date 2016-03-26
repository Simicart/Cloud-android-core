package com.simicart.core.menutop.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikepenz.materialdrawer.Drawer;
import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.config.Config;
import com.simicart.core.config.Constants;
import com.simicart.core.config.Rconfig;
import com.simicart.core.event.fragment.SimiEventFragmentEntity;
import com.simicart.core.menutop.block.MenuTopBlock;
import com.simicart.core.menutop.controller.MenuTopController;
import com.simicart.core.slidemenu.fragment.SlideMenuFragment;

public class FragmentMenuTop extends SimiFragment {

    protected View rootView;
    protected MenuTopBlock mBlock;
    protected MenuTopController mController;
    //	protected SlideMenuFragment mNavigationDrawerFragment;
    protected Drawer mDrawer;

    public static FragmentMenuTop newInstance(
            Drawer drawer) {
        FragmentMenuTop fragment = new FragmentMenuTop();
//		fragment.mNavigationDrawerFragment = mNavigationDrawerFragment;
        fragment.mDrawer = drawer;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(Rconfig.getInstance()
                .layout("core_menutop"), container, false);
        rootView.setBackgroundColor(Config.getInstance().getKey_color());

        Context mContext = getActivity();

        mBlock = new MenuTopBlock(rootView, mContext);
        mBlock.initView();

        if (null == mController) {
            mController = new MenuTopController();
            mController.setDelegate(mBlock);
//			mController.setSlideMenu(mNavigationDrawerFragment);
            mController.setDrawer(mDrawer);
            mController.onStart();
        } else {
            mController.setDelegate(mBlock);
//			mController.setSlideMenu(mNavigationDrawerFragment);
            mController.setDrawer(mDrawer);
            mController.onResume();
        }

        mBlock.setOnTouchCart(mController.getTouchCart());
        mBlock.setOnTouchMenu(mController.getTouchMenu());
        SimiManager.getIntance().setMenuTopController(mController);

        Intent intent = new Intent("com.simicart.core.menutop.fragment.FragmentMenuTop");
        SimiEventFragmentEntity fragmentEntity = new SimiEventFragmentEntity();
        fragmentEntity.setFragmetn(this);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.ENTITY, fragmentEntity);
        intent.putExtra(Constants.DATA, bundle);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);

        return rootView;
    }

    public Drawer getDrawer() {
        return mDrawer;
    }
}
