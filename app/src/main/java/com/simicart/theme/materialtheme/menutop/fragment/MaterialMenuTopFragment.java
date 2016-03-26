package com.simicart.theme.materialtheme.menutop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikepenz.materialdrawer.Drawer;
import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.config.Config;
import com.simicart.core.config.Rconfig;

/**
 * Created by Sony on 3/27/2016.
 */
public class MaterialMenuTopFragment extends SimiFragment {
    protected Drawer mDrawer;
    protected View rootView;

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
        rootView = inflater.inflate(Rconfig.getInstance()
                .layout("material_menutop_layout"), container, false);
        rootView.setBackgroundColor(Config.getInstance().getKey_color());
        return rootView;
    }

    public void setDrawer(Drawer mDrawer) {
        this.mDrawer = mDrawer;
    }
}
