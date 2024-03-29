package com.simicart.core.home.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.simicart.core.banner.block.BannerBlock;
import com.simicart.core.banner.controller.BannerController;
import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.catalog.categorydetail.entity.TagSearch;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.core.home.block.CategoryHomeBlock;
import com.simicart.core.home.block.SpotProductBlock;
import com.simicart.core.home.block.SearchHomeBlock;
import com.simicart.core.home.controller.CategoryHomeController;
import com.simicart.core.home.controller.SpotProductController;

public class HomeFragment extends SimiFragment {

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    protected View rootView;
    protected SearchHomeBlock mSearchHomeBlock;
    protected BannerBlock mBannerBlock;
    protected BannerController mBannerController;

    protected CategoryHomeBlock mCategoryHomeBlock;
    protected CategoryHomeController mCategoryHomeController;

    protected SpotProductBlock mProductListBlock;
    protected SpotProductController mSpotProductController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setScreenName("Home Screen");
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(
                Rconfig.getInstance().layout("core_home_layout"), container,
                false);
        Context context = getActivity();
        // init search
        View searchView = rootView.findViewById(Rconfig.getInstance().id(
                "rlt_search"));
        if (DataLocal.isTablet) {
            searchView.setVisibility(View.GONE);
        } else {
            mSearchHomeBlock = new SearchHomeBlock(searchView, context);
            mSearchHomeBlock.setTag(TagSearch.TAG_LISTVIEW);
            mSearchHomeBlock.initView();
        }
        // init banner
        RelativeLayout rlt_banner = (RelativeLayout) rootView
                .findViewById(Rconfig.getInstance().id("rlt_banner_home"));
        mBannerBlock = new BannerBlock(rlt_banner, context);
        mBannerBlock.initView();
        if (null == mBannerController) {
            mBannerController = new BannerController(mBannerBlock);
            mBannerController.onStart();
        } else {
            mBannerController.setDelegate(mBannerBlock);
            mBannerController.onResume();
        }
        // init category
        mCategoryHomeBlock = new CategoryHomeBlock(rootView, context);
        mCategoryHomeBlock.initView();
        if (mCategoryHomeController == null) {
            mCategoryHomeController = new CategoryHomeController();
            mCategoryHomeController.setDelegate(mCategoryHomeBlock);
            mCategoryHomeController.onStart();
        } else {
            mCategoryHomeController.setDelegate(mCategoryHomeBlock);
            mCategoryHomeController.onResume();
        }

        // init spotproduct
        mProductListBlock = new SpotProductBlock(rootView, context);
        if (null == mSpotProductController) {
            mSpotProductController = new SpotProductController();
            mSpotProductController.setDelegate(mProductListBlock);
            mSpotProductController.onStart();
        } else {
            mSpotProductController.setDelegate(mProductListBlock);
            mSpotProductController.onResume();
        }

        return rootView;
    }
}
