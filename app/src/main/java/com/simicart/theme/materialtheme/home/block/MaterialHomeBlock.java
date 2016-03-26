package com.simicart.theme.materialtheme.home.block;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.config.Config;
import com.simicart.core.config.Rconfig;
import com.simicart.core.home.entity.SpotProductEntity;
import com.simicart.theme.materialtheme.home.adapter.MaterialTabAdapter;

import java.util.ArrayList;

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
    }

    @Override
    public void drawView(SimiCollection collection) {
        ArrayList<SimiEntity> entity = collection.getCollection();
        if (null != entity && entity.size() > 0) {
            ArrayList<SpotProductEntity> listSportProduct = new ArrayList<SpotProductEntity>();
            for (SimiEntity simiEntity : entity) {
                SpotProductEntity spotProductEntity = (SpotProductEntity) simiEntity;
                listSportProduct.add(spotProductEntity);
            }

            if (listSportProduct.size() > 0) {
                MaterialTabAdapter mAdapter = new MaterialTabAdapter(mFragmentManager, listSportProduct);
                mViewPager.getViewPager().setAdapter(mAdapter);

                mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
                mViewPager.getPagerTitleStrip().setTextColor(Color.WHITE);
                mViewPager.getPagerTitleStrip().setBackgroundColor(Config.getInstance().getKey_color());
                mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());
            }
        }
    }
}
