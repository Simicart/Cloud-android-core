package com.simicart.theme.materialtheme.categorydetail.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simicart.core.catalog.categorydetail.fragment.CategoryDetailFragment;
import com.simicart.core.config.Rconfig;
import com.simicart.theme.materialtheme.categorydetail.block.MaterialCategoryDeailBlock;

/**
 * Created by MSI on 26/03/2016.
 */
public class MaterialCategoryDetailFragment extends CategoryDetailFragment {

    protected MaterialCategoryDeailBlock mBlock;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(
                Rconfig.getInstance().layout("core_list_search_layout"),
                container, false);
        Context context = getActivity();


        return rootView;
    }



}
