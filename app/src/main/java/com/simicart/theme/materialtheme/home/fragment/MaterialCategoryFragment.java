package com.simicart.theme.materialtheme.home.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.github.ksoichiro.android.observablescrollview.ObservableWebView;
import com.simicart.R;
import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.config.Rconfig;
import com.simicart.theme.materialtheme.home.adapter.MaterialCategoryBlock;
import com.simicart.theme.materialtheme.home.adapter.MaterialCateogryAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sony on 3/26/2016.
 */
public class MaterialCategoryFragment extends SimiFragment {
    protected  MaterialCategoryBlock mBlock;
    private RecyclerView.Adapter mAdapter;
    private static final int ITEM_COUNT = 100;
    private List<Object> mContentItems = new ArrayList<>();
    private ObservableWebView mWebView;

    public static MaterialCategoryFragment newInstance(){
        MaterialCategoryFragment fragment = new MaterialCategoryFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                Rconfig.getInstance().layout("material_category_layout"), container, false);
        Context mContext = getActivity();
//        mBlock = new MaterialCategoryBlock(rootView, mContext);
//        mBlock.setActivity(getActivity());
//        mBlock.initView();
        mWebView = (ObservableWebView) rootView.findViewById(R.id.webView);

        //must be called before loadUrl()
        MaterialViewPagerHelper.preLoadInjectHeader(mWebView);

        //have to inject header when WebView page loaded
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                MaterialViewPagerHelper.injectHeader(mWebView, true);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        mWebView.loadUrl("http://mobile.francetvinfo.fr/");

        MaterialViewPagerHelper.registerWebView(getActivity(), mWebView, null);
        return rootView;
    }
}
