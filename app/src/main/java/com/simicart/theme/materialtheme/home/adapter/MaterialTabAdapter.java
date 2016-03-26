package com.simicart.theme.materialtheme.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.simicart.theme.materialtheme.home.fragment.MaterialCategoryFragment;

/**
 * Created by Sony on 3/26/2016.
 */
public class MaterialTabAdapter extends FragmentStatePagerAdapter {

    public MaterialTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position % 4) {
            default:
                return MaterialCategoryFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position % 4) {
            case 0:
                return "Cateogry1";
            case 1:
                return "Cateogry2";
            case 2:
                return "Cateogry3";
            case 3:
                return "Cateogry4";
        }
        return "";
    }
}
