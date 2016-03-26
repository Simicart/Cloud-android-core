package com.simicart.theme.materialtheme.home.block;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.config.Rconfig;
import com.simicart.theme.materialtheme.home.adapter.MaterialTabAdapter;

/**
 * Created by Sony on 3/26/2016.
 */
public class MaterialHomeBlock extends SimiBlock {
    protected MaterialViewPager mViewPager;
    protected FragmentManager mFragmentManager;
    protected Toolbar toolbar;

    public void setFragmentManager(FragmentManager mFragmentManager) {
        this.mFragmentManager = mFragmentManager;
    }

    public MaterialHomeBlock(View view, Context context) {
        super(view, context);
    }

    public void setOnClickTitleViewPager(MaterialViewPager.Listener listener){
        mViewPager.setMaterialViewPagerListener(listener);
    }

    @Override
    public void initView() {
        mViewPager = (MaterialViewPager) mView.findViewById(Rconfig.getInstance().id("materialViewPager"));
        toolbar = mViewPager.getToolbar();
        if(toolbar != null){
            toolbar.setVisibility(View.GONE);
        }
        MaterialTabAdapter mAdapter = new MaterialTabAdapter(mFragmentManager);
        mViewPager.getViewPager().setAdapter(mAdapter);

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setTextColor(Color.WHITE);
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());
    }
}
