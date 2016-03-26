package com.simicart.theme.materialtheme.categorydetail.block;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.catalog.categorydetail.delegate.CategoryDetailDelegate;
import com.simicart.core.catalog.categorydetail.entity.TagSearch;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.config.Rconfig;
import com.simicart.theme.materialtheme.categorydetail.adapter.MaterialCateDetailAdapter;
import com.simicart.theme.materialtheme.categorydetail.adapter.MaterialCateDetailAdapterGrid;

import java.util.ArrayList;

/**
 * Created by MSI on 26/03/2016.
 */
public class MaterialCategoryDeailBlock extends SimiBlock implements CategoryDetailDelegate {

    private RecyclerView rv_cateDetail;
    private MaterialCateDetailAdapter mListAdapter;
    private MaterialCateDetailAdapterGrid mGridAdapter;
    private LinearLayoutManager mLinearManager;
    private GridLayoutManager mGridManager;
    private ImageButton imb_changeType;
    private ArrayList<ProductEntity> mProducts;

    public void setChangeTypeListener(View.OnClickListener listener) {
        imb_changeType.setOnClickListener(listener);
    }

    public MaterialCategoryDeailBlock(View view, Context context) {
        super(view, context);
    }

    @Override
    public void initView() {
        rv_cateDetail = (RecyclerView) mView.findViewById(Rconfig.getInstance().id("rcv_category_detail"));
        mLinearManager = new LinearLayoutManager(mContext);
        mGridManager = new GridLayoutManager(mContext, 2);
        rv_cateDetail.setLayoutManager(mLinearManager);
        imb_changeType = (ImageButton) mView.findViewById(Rconfig.getInstance().id("imb_change_type"));
    }

    @Override
    public void drawView(SimiCollection collection) {
        ArrayList<SimiEntity> entities = collection.getCollection();
        if (null != entities && entities.size() > 0) {
            mProducts = new ArrayList<>();
            for (int i = 0; i < entities.size(); i++) {
                ProductEntity product = (ProductEntity) entities.get(i);
                mProducts.add(product);
            }


            mListAdapter = new MaterialCateDetailAdapter(mProducts);
            mGridAdapter = new MaterialCateDetailAdapterGrid(mProducts);
            rv_cateDetail.setAdapter(mListAdapter);
        }
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
    public String onChangeTypeViewShow(boolean isList) {
        String tag_search;
        if (isList) {
            mListAdapter.setListProduct(mProducts);
            rv_cateDetail.setLayoutManager(mLinearManager);
            rv_cateDetail.setAdapter(mListAdapter);
            tag_search = TagSearch.TAG_LISTVIEW;
            mListAdapter.notifyDataSetChanged();
        } else {
            mGridAdapter.setListProduct(mProducts);
            rv_cateDetail.setLayoutManager(mGridManager);
            rv_cateDetail.setAdapter(mGridAdapter);
            tag_search = TagSearch.TAG_GRIDVIEW;
            mGridAdapter.notifyDataSetChanged();
        }

        return tag_search;
    }
}
