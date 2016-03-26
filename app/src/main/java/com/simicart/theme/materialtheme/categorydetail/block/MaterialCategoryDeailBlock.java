package com.simicart.theme.materialtheme.categorydetail.block;

import android.content.Context;
import android.view.View;

import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.catalog.categorydetail.delegate.CategoryDetailDelegate;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;

import java.util.ArrayList;

/**
 * Created by MSI on 26/03/2016.
 */
public class MaterialCategoryDeailBlock extends SimiBlock implements CategoryDetailDelegate {


    public MaterialCategoryDeailBlock(View view, Context context) {
        super(view, context);
    }

    @Override
    public void drawView(SimiCollection collection) {



    }

    @Override
    public void setQty(String qty) {

    }

    @Override
    public void removeFooterView() {

    }

    @Override
    public void addFooterView() {

    }

    @Override
    public void setVisibilityMenuBotton(boolean temp) {

    }

    @Override
    public String getTagSearch() {
        return null;
    }

    @Override
    public void setTagSearch(String tag_search) {

    }

    @Override
    public void onChangeNumberColumnGrid(boolean is_zoom_out) {

    }

    @Override
    public ArrayList<ProductEntity> getListProduct() {
        return null;
    }

    @Override
    public ArrayList<String> getListProductId() {
        return null;
    }

    @Override
    public void setCurrentPosition(int position) {

    }

    @Override
    public void setCheckFilter(boolean filter) {

    }

    @Override
    public void setIsLoadMore(boolean loadmore) {

    }

    @Override
    public void showSort(boolean isCheck) {

    }

    @Override
    public String onChangeTypeViewShow() {
        return null;
    }
}
