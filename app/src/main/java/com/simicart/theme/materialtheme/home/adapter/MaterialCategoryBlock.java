package com.simicart.theme.materialtheme.home.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.github.ksoichiro.android.observablescrollview.ObservableWebView;
import com.simicart.R;
import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.config.Rconfig;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sony on 3/26/2016.
 */
public class MaterialCategoryBlock extends SimiBlock {
    private RecyclerView.Adapter mAdapter;
    private static final int ITEM_COUNT = 100;
    private List<Object> mContentItems = new ArrayList<>();
    private ObservableWebView mWebView;
    protected FragmentActivity mActivity;

    public void setActivity(FragmentActivity mActivity) {
        this.mActivity = mActivity;
    }

    public MaterialCategoryBlock(View view, Context context) {
        super(view, context);
    }

    @Override
    public void initView() {
//        mListCategory = (RecyclerView) mView.findViewById(Rconfig.getInstance().id("listCategory"));
//        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        mListCategory.setLayoutManager(layoutManager);
//        mListCategory.setHasFixedSize(true);
//
//        mAdapter = new RecyclerViewMaterialAdapter(new MaterialCateogryAdapter(mContentItems), 1);
//        {
//            for (int i = 0; i < ITEM_COUNT; ++i)
//                mContentItems.add(new Object());
//            mAdapter.notifyDataSetChanged();
//        }
//
//        MaterialViewPagerHelper.registerRecyclerView(mActivity, mListCategory, null);

        mWebView = (ObservableWebView) mView.findViewById(R.id.webView);

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

        MaterialViewPagerHelper.registerWebView(mActivity, mWebView, null);
    }
}
