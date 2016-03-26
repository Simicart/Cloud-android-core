package com.simicart.theme.materialtheme.home.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.config.Rconfig;

/**
 * Created by Sony on 3/26/2016.
 */
public class MaterialHomeFragment extends SimiFragment {
    public static MaterialHomeFragment newInstance(){
        MaterialHomeFragment fragment = new MaterialHomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                Rconfig.getInstance().layout("material_home_layout"), null);
        return rootView;
    }
}
