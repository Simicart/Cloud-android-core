package com.simicart.theme.materialtheme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.mikepenz.materialdrawer.Drawer;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.catalog.categorydetail.fragment.CategoryDetailFragment;
import com.simicart.core.config.Constants;
import com.simicart.core.event.fragment.SimiEventFragmentEntity;
import com.simicart.core.menutop.fragment.FragmentMenuTop;
import com.simicart.theme.materialtheme.categorydetail.fragment.MaterialCategoryDetailFragment;
import com.simicart.theme.materialtheme.home.fragment.MaterialHomeFragment;
import com.simicart.theme.materialtheme.menutop.fragment.MaterialMenuTopFragment;

/**
 * Created by MSI on 26/03/2016.
 */
public class MaterialTheme {
    public MaterialTheme() {
        // register event of home page
        IntentFilter filter = new IntentFilter("com.simicart.core.home.fragment.HomeFragment");
        final BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getBundleExtra(Constants.DATA);
                SimiEventFragmentEntity entity = (SimiEventFragmentEntity) bundle.getSerializable(Constants.ENTITY);
                MaterialHomeFragment fragment = MaterialHomeFragment.newInstance();
                entity.setFragmetn(fragment);
            }
        };
        Context context = SimiManager.getIntance().getCurrentContext();
        LocalBroadcastManager.getInstance(context).registerReceiver(receiver, filter);

        // register event of category detail
        IntentFilter filter_cateDetail = new IntentFilter("com.simicart.core.catalog.categorydetail.fragment.CategoryDetailFragment");
        final BroadcastReceiver receiver_cateDetail = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getBundleExtra(Constants.DATA);
                SimiEventFragmentEntity entity = (SimiEventFragmentEntity) bundle.getSerializable(Constants.ENTITY);

                Log.e("MaterialTheme", "===============> Open MaterialCategoryDetailFragment");

                CategoryDetailFragment cateFragment = (CategoryDetailFragment) entity.getFragment();
                String cateID = cateFragment.getCateID();
                String cateName = cateFragment.getCateName();
                String extend_url = "";
                if (cateID.equals("-1")) {
                    extend_url = "products";
                } else {
                    extend_url = "categories";
                }

                MaterialCategoryDetailFragment fragment = new MaterialCategoryDetailFragment();
                fragment.setCategoryId(cateID);
                fragment.setCategoryName(cateName);
                fragment.setUrlSearch(extend_url);
                entity.setFragmetn(fragment);
            }
        };
        LocalBroadcastManager.getInstance(context).registerReceiver(receiver_cateDetail, filter_cateDetail);

        // register event of menutop
//        IntentFilter filter_menutop = new IntentFilter("com.simicart.core.menutop.fragment.FragmentMenuTop");
//        final BroadcastReceiver receiver_menutop = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                Bundle bundle = intent.getBundleExtra(Constants.DATA);
//                SimiEventFragmentEntity entity = (SimiEventFragmentEntity) bundle.getSerializable(Constants.ENTITY);
//
//                Log.e("MaterialTheme", "===============> Open MaterialMenuTopFragment");
//
//                FragmentMenuTop menuTopFragment = (FragmentMenuTop) entity.getFragment();
//                Drawer mDrawer = menuTopFragment.getDrawer();
//                MaterialMenuTopFragment fragment = MaterialMenuTopFragment.newInstance();
//                fragment.setDrawer(mDrawer);
//                entity.setFragmetn(fragment);
//            }
//        };
//        LocalBroadcastManager.getInstance(context).registerReceiver(receiver_menutop, filter_menutop);

    }
}
