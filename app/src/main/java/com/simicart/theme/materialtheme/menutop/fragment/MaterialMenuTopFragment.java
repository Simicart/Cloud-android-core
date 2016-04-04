package com.simicart.theme.materialtheme.menutop.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mikepenz.materialdrawer.Drawer;
import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.config.Config;
import com.simicart.core.config.Rconfig;
import com.simicart.core.menutop.fragment.FragmentMenuTop;
import com.simicart.theme.materialtheme.menutop.block.MaterialMenuTopBlock;
import com.simicart.theme.materialtheme.menutop.controller.MaterialMenuTopController;

/**
 * Created by Sony on 3/27/2016.
 */
public class MaterialMenuTopFragment extends FragmentMenuTop {
    protected MaterialMenuTopBlock mBlock;
    protected MaterialMenuTopController mController;
    protected Drawer mDrawer;

    public static MaterialMenuTopFragment newInstance() {
        MaterialMenuTopFragment fragment = new MaterialMenuTopFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(Rconfig.getInstance()
                .layout("material_menutop_layout"), container, false);
        rootView.setBackgroundColor(Config.getInstance().getKey_color());
        Context mContext = getActivity();
        mBlock = new MaterialMenuTopBlock(rootView, mContext);
        mBlock.initView();

        if (null == mController) {
            mController = new MaterialMenuTopController();
            mController.setDelegate(mBlock);
            mController.setDrawer(mDrawer);
            mController.onStart();
        } else {
            mController.setDelegate(mBlock);
            mController.setDrawer(mDrawer);
            mController.onResume();
        }

        mBlock.setOnTouchCart(mController.getTouchCart());
        mBlock.setOnTouchMenu(mController.getTouchMenu());
        mBlock.setOnClickSearch(mController.getOnClickSearch());
        SimiManager.getIntance().setMenuTopController(mController);

        return rootView;
    }

    public void setDrawer(Drawer mDrawer) {
        this.mDrawer = mDrawer;
    }
}
