package com.simicart.core.catalog.product.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.simicart.core.catalog.product.controller.ProductDetailParentController;
import com.simicart.core.catalog.product.fragment.ProductDetailImageFragment;
import com.simicart.core.common.Utils;

import java.util.ArrayList;

public class ProductDetailChildeAdapter extends FragmentPagerAdapter {
    protected ArrayList<String> images = new ArrayList<String>();

    private int mCount;
    protected ProductDetailParentController mParentController;

    public void setDelegate(ProductDetailParentController delegate) {
        mParentController = delegate;
    }

    public ProductDetailChildeAdapter(FragmentManager fmChild, ArrayList<String> Images) {
        super(fmChild);
        this.images = Images;
        this.mCount = images.size();
    }

    @Override
    public Fragment getItem(int position) {
        String url = images.get(position);
        if (mCount > 0 && Utils.validateString(url)) {
            Log.e("ChildAdapter", url);
            ProductDetailImageFragment fragment = ProductDetailImageFragment.newInstance(url);
            fragment.setDelegate(mParentController);
            return fragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return mCount;
    }

}
