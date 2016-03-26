package com.simicart.theme.materialtheme.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.simicart.core.home.entity.SpotProductEntity;
import com.simicart.theme.materialtheme.home.fragment.MaterialCategoryFragment;

import java.util.ArrayList;

/**
 * Created by Sony on 3/26/2016.
 */
public class MaterialTabAdapter extends FragmentStatePagerAdapter {
    protected  ArrayList<SpotProductEntity> listSportProduct;
    public MaterialTabAdapter(FragmentManager fm, ArrayList<SpotProductEntity> listSportProduct) {
        super(fm);
        this.listSportProduct = listSportProduct;
    }

    @Override
    public Fragment getItem(int position) {
        MaterialCategoryFragment fragment = MaterialCategoryFragment.newInstance();
        fragment.setSportProduct(listSportProduct.get(position));
        return fragment;
    }

    @Override
    public int getCount() {
        return listSportProduct.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return listSportProduct.get(position).getName();
    }
}
