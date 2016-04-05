package com.simicart.theme.materialtheme.home.block;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.simicart.R;
import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.config.Rconfig;
import com.simicart.theme.materialtheme.home.adapter.MaterialCateogryAdapter;
import com.simicart.theme.materialtheme.home.delegate.MaterialCategoryDelegate;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Sony on 3/26/2016.
 */
public class MaterialCategoryBlock extends SimiBlock implements MaterialCategoryDelegate{
    private RecyclerView.Adapter mAdapter;
    protected Activity mActivity;
    protected RecyclerView mListCategory;

    public void setActivity(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public MaterialCategoryBlock(View view, Context context) {
        super(view, context);
    }

    @Override
    public void initView() {
        mView.setBackgroundColor(Color.TRANSPARENT);
        mListCategory = (RecyclerView) mView.findViewById(Rconfig.getInstance().id("listCategory"));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity);
        mListCategory.setLayoutManager(layoutManager);
        mListCategory.setHasFixedSize(true);
    }

    @Override
    public void drawView(SimiCollection collection) {
        JSONObject jsResult = collection.getJSON();
        if (jsResult.has("products")) {
            try {
                JSONArray array = jsResult.getJSONArray("products");
                if (null != array && array.length() > 0) {
                    ArrayList<ProductEntity> listProduct = new ArrayList<ProductEntity>();
                    int length = array.length();
                    for (int i = 0; i < length; i++) {
                        JSONObject js = array.getJSONObject(i);
                        ProductEntity product = new ProductEntity();
                        product.setJSONObject(js);
                        product.parse();
                        listProduct.add(product);
                    }

                    if(listProduct.size() > 0){
                        mAdapter = new RecyclerViewMaterialAdapter(new MaterialCateogryAdapter(listProduct, mActivity));
                        mListCategory.setAdapter(mAdapter);
                        MaterialViewPagerHelper.registerRecyclerView(mActivity, mListCategory, null);

                        Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.slide_bottom_to_top);
                        anim.setDuration(1000);
                        mListCategory.startAnimation(anim);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
