package com.simicart.theme.materialtheme.home.block;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.simicart.MainActivity;
import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.config.Rconfig;
import com.simicart.theme.materialtheme.home.adapter.MaterialCateogryAdapter;
import com.simicart.theme.materialtheme.home.delegate.MaterialCategoryDelegate;
import com.simicart.theme.materialtheme.home.fragment.MaterialCategoryFragment;

import java.util.ArrayList;

/**
 * Created by Sony on 3/26/2016.
 */
public class MaterialCategoryBlock extends SimiBlock implements MaterialCategoryDelegate{
    private RecyclerView.Adapter mAdapter;
    protected FragmentActivity mActivity;
    protected ObservableRecyclerView mListCategory;
    private View view;

    public void setActivity(FragmentActivity mActivity) {
        this.mActivity = mActivity;
    }

    public MaterialCategoryBlock(View view, Context context) {
        super(view, context);
        this.view = view;
    }

    @Override
    public void initView() {
        mListCategory = (ObservableRecyclerView) view.findViewById(Rconfig.getInstance().id("listCategory"));
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mListCategory.setLayoutManager(layoutManager);
        mListCategory.setHasFixedSize(true);
    }

    @Override
    public void drawView(SimiCollection collection) {
        ArrayList<SimiEntity> entity = collection.getCollection();
        if (null != entity && entity.size() > 0) {
            ArrayList<ProductEntity> listProduct = new ArrayList<ProductEntity>();
            for (SimiEntity simiEntity : entity) {
                ProductEntity productEntity = (ProductEntity) simiEntity;
                listProduct.add(productEntity);
            }
            if(listProduct.size() > 0){
                mAdapter = new RecyclerViewMaterialAdapter(new MaterialCateogryAdapter(listProduct, mActivity));
                mListCategory.setAdapter(mAdapter);
                MaterialViewPagerHelper.registerRecyclerView(mActivity, mListCategory, null);
            }
        }
    }
}
