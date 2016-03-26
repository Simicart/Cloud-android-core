package com.simicart.theme.materialtheme.home.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableWebView;
import com.simicart.MainActivity;
import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.config.Rconfig;
import com.simicart.theme.materialtheme.home.adapter.MaterialCateogryAdapter;
import com.simicart.theme.materialtheme.home.block.MaterialCategoryBlock;

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
    protected ObservableRecyclerView mListCategory;

    public static MaterialCategoryFragment newInstance(){
        MaterialCategoryFragment fragment = new MaterialCategoryFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                Rconfig.getInstance().layout("material_category_layout"), container, false);
//        Context mContext = getActivity();
//        mBlock = new MaterialCategoryBlock(rootView, mContext);
//        mBlock.setActivity(getActivity());
//        mBlock.initView();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListCategory = (ObservableRecyclerView) view.findViewById(Rconfig.getInstance().id("listCategory"));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mListCategory.setLayoutManager(layoutManager);
        mListCategory.setHasFixedSize(true);

        ArrayList<ProductEntity> listProduct = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            ProductEntity productEntity = new ProductEntity();
            productEntity.setName("Product");
            productEntity.setPrice(1.25);
            productEntity.setIsStatus(true);
            productEntity.setMangerStock(true);
            listProduct.add(productEntity);
        }

        mAdapter = new RecyclerViewMaterialAdapter(new MaterialCateogryAdapter(listProduct, getActivity()));
        mListCategory.setAdapter(mAdapter);

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mListCategory, null);
    }
}
