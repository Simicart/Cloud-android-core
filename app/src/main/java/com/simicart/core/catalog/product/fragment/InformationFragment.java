package com.simicart.core.catalog.product.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simicart.core.adapter.TabAdapterFragment;
import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.catalog.product.block.ProductMorePluginBlock;
import com.simicart.core.catalog.product.controller.ProductMorePluginController;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.common.price.ProductDetailPriceView;
import com.simicart.core.config.Config;
import com.simicart.core.config.Rconfig;
import com.simicart.core.style.PagerSlidingTabStrip;
import com.simicart.core.style.material.floatingactionbutton.FloatingActionButton;
import com.simicart.core.style.material.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;

public class InformationFragment extends SimiFragment {

    protected ProductEntity mProduct;
    protected View mRootView;
    protected ProductMorePluginBlock mPluginBlock;
    protected ProductMorePluginController mPluginController;
    protected ProductDetailPriceView priceViewBasic;

    public void setPriceViewBasic(ProductDetailPriceView priceView) {
        priceViewBasic = priceView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static InformationFragment newInstance() {
        InformationFragment fragment = new InformationFragment();
        return fragment;
    }

    public void setProduct(ProductEntity product) {
        mProduct = product;
    }

    public ProductEntity getProduct() {
        return mProduct;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(
                Rconfig.getInstance().layout("core_information_layout"),
                container, false);
        Context context = getActivity();
        if (null != mProduct) {
            initView();
            ArrayList<FloatingActionButton> mListButton = new ArrayList<>();
            FloatingActionButton more_share = new FloatingActionButton(context);
            more_share.setColorNormal(Color.parseColor("#FFFFFF"));
            more_share.setColorPressed(Color.parseColor("#f4f4f4"));
            more_share.setIcon(Rconfig.getInstance().drawable("ic_share"));
            more_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sharingIntent = new Intent(
                            Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(Intent.EXTRA_TEXT,
                            "https://www.simicart.com/");
                    SimiManager
                            .getIntance()
                            .getCurrentActivity()
                            .startActivity(
                                    Intent.createChooser(sharingIntent, "Share via"));
                }
            });
            mListButton.add(more_share);

            // add button facebook
            FloatingActionButton bt_facebook = new FloatingActionButton(context);
            bt_facebook.setStrokeVisible(false);
            bt_facebook.setColorNormal(Color.WHITE);
            bt_facebook.setColorNormal(Color.parseColor("#FFFFFF"));
            bt_facebook.setColorPressed(Color.parseColor("#f4f4f4"));
            bt_facebook.setIcon(Rconfig.getInstance().drawable("ic_facebook"));
            bt_facebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FacebookConnectFragment fragment = new FacebookConnectFragment();
                    fragment.setUrlProduct("https://www.simicart.com/");
                    SimiManager.getIntance().addFragment(fragment);
                }
            });
            mListButton.add(bt_facebook);

            FloatingActionsMenu mMultipleActions = (FloatingActionsMenu) mRootView.findViewById(Rconfig
                    .getInstance().id("more_plugins_action"));
            mMultipleActions.createButton(context, Config.getInstance()
                    .getButton_background(), Config.getInstance()
                    .getButton_background(), Config.getInstance()
                    .getButton_text_color());
            for (int i = 0; i < mListButton.size(); i++) {
                mMultipleActions.addButton(mListButton.get(i));
            }

        }
        return mRootView;
    }

    public void initView() {
        final TabAdapterFragment adapter = new TabAdapterFragment(
                getChildFragmentManager(), mProduct, priceViewBasic);
        final ViewPager mPager = (ViewPager) mRootView.findViewById(Rconfig
                .getInstance().id("pager"));
        mPager.setAdapter(adapter);

        PagerSlidingTabStrip title_tab = (PagerSlidingTabStrip) mRootView
                .findViewById(Rconfig.getInstance().id("pager_title_strip"));
        title_tab.setTextColor(Config.getInstance().getSection_text_color());
        title_tab.setBackgroundColor(Color.parseColor(Config.getInstance()
                .getSection_color()));
        title_tab.setDividerColor(Config.getInstance().getSection_text_color());
        title_tab.setIndicatorColor(Config.getInstance().getButton_background());
        title_tab.setIndicatorHeight(5);
        title_tab.setAllCaps(false);
        title_tab.setViewPager(mPager);
    }
}
