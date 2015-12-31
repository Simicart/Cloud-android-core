package com.simicart.core.catalog.product.block;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;

import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.catalog.product.adapter.ProductDetailChildeAdapter;
import com.simicart.core.catalog.product.controller.ProductDetailParentController;
import com.simicart.core.catalog.product.delegate.ProductDetailChildDelegate;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.config.Rconfig;
import com.simicart.core.style.VerticalViewPager2;

public class ProductDetailChildBlock extends SimiBlock implements ProductDetailChildDelegate {

    protected VerticalViewPager2 mPagerChild;
    protected ProductEntity mProduct;
    protected FragmentManager mFragmentChild;
    protected ProductDetailParentController mParentController;
    protected ProductDetailChildeAdapter mAdapter;

    public void setDelegate(ProductDetailParentController delegate) {
        mParentController = delegate;
    }

    public ProductDetailChildBlock(View view, Context context,
                                   FragmentManager fragmentChild) {
        super(view, context);
        this.mFragmentChild = fragmentChild;
    }

    @Override
    public void initView() {
        mPagerChild = (VerticalViewPager2) mView.findViewById(Rconfig
                .getInstance().id("pager_child"));
        mPagerChild.setOffscreenPageLimit(3);
    }

    @Override
    public void drawView(SimiCollection collection) {
        if (null != collection) {
            mProduct = getProductFromCollection(collection);
            if (null != mProduct) {
                showImage();
            }

        }
    }

    protected ProductEntity getProductFromCollection(SimiCollection collection) {
        ProductEntity product = null;
        ArrayList<SimiEntity> entity = collection.getCollection();
        if (null != entity && entity.size() > 0) {
            product = (ProductEntity) entity.get(0);
        }
        return product;
    }

    protected void showImage() {
        ArrayList<String> Images = mProduct.getImages();
        if (null != Images && Images.size() > 0) {
            mAdapter = new ProductDetailChildeAdapter(
                    mFragmentChild, Images);
            mAdapter.setDelegate(mParentController);
            mPagerChild.setAdapter(mAdapter);
        }
    }

    @Override
    public void updateIndicator() {
        Log.e("ProductDetailChildBlock", "updateIndicator");
        mParentController.updateViewPager(mPagerChild);
    }



}
