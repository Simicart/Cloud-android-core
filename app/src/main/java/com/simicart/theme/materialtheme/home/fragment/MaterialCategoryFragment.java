package com.simicart.theme.materialtheme.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.config.Rconfig;

/**
 * Created by Sony on 3/26/2016.
 */
public class MaterialCategoryFragment extends SimiFragment {
    public static MaterialCategoryFragment newInstance(){
        MaterialCategoryFragment fragment = new MaterialCategoryFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                Rconfig.getInstance().layout("material_category_layout"), null);
        return rootView;
    }
}
