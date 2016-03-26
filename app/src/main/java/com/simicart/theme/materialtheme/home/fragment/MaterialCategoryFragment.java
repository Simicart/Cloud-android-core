package com.simicart.theme.materialtheme.home.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableWebView;
import com.simicart.MainActivity;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.catalog.product.model.ProductDetailModel;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Rconfig;
import com.simicart.core.home.entity.SpotProductEntity;
import com.simicart.theme.materialtheme.home.adapter.MaterialCateogryAdapter;
import com.simicart.theme.materialtheme.home.block.MaterialCategoryBlock;
import com.simicart.theme.materialtheme.home.controller.MaterialCategoryController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sony on 3/26/2016.
 */
public class MaterialCategoryFragment extends SimiFragment {
    protected  MaterialCategoryBlock mBlock;
    protected MaterialCategoryController mController;
    private RecyclerView.Adapter mAdapter;
    protected ObservableRecyclerView mListCategory;

    protected  SpotProductEntity sportProduct;
    protected View viewCategory;

    public void setSportProduct(SpotProductEntity sportProduct) {
        this.sportProduct = sportProduct;
    }

    public static MaterialCategoryFragment newInstance(){
        MaterialCategoryFragment fragment = new MaterialCategoryFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                Rconfig.getInstance().layout("material_category_layout"), container, false);

        viewCategory = rootView;
        String type = sportProduct.getType();
        String limit = sportProduct.getLimit();
        if (type.equals("1")) {
            requestBestSeller(limit);
        } else if (type.equals("2")) {
            requestNewlyUpdated(limit);
        } else if (type.equals("3")) {
            requestRecentlyAdded(limit);
        } else if (type.equals("4")) {
            String ids = arrayToString(sportProduct.getProducts());
            if (Utils.validateString(ids)) {
                getFeature(ids, limit);
            }
        }
//        Context mContext = getActivity();
//        mBlock = new MaterialCategoryBlock(rootView, mContext);
//        mBlock.setActivity(getActivity());
//        mBlock.initView();
//        if(mController == null){
//            mController = new MaterialCategoryController();
//            mController.setDelegate(mBlock);
//            mController.setSportProduct(sportProduct);
//            mController.onStart();
//        }else{
//            mController.setDelegate(mBlock);
//            mController.setSportProduct(sportProduct);
//            mController.onResume();
//        }
        return rootView;
    }

    private void requestBestSeller(String limit) {
        ProductDetailModel mModel = new ProductDetailModel();
        mModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if (error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "OK");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                showListCategory(collection);
            }
        });

        mModel.addDataParameter("group-type", "best-sellers");
        if(Utils.validateString(limit) && !limit.equals("0")){
            mModel.addLimitDataParameter(limit);
        }else {
            mModel.addLimitDataParameter("15");
        }
        mModel.request();
    }

    private void requestNewlyUpdated(String limit) {
        ProductDetailModel mModel = new ProductDetailModel();
        mModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if (error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "OK");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                showListCategory(collection);
            }
        });
        mModel.addOrderDataParameter("updated_at");
        mModel.sortDirDESC();
        if(Utils.validateString(limit) && !limit.equals("0")){
            mModel.addLimitDataParameter(limit);
        }else{
            mModel.addLimitDataParameter("15");
        }
        mModel.request();
    }

    protected void requestRecentlyAdded(String limit) {
        ProductDetailModel mModel = new ProductDetailModel();
        mModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if (error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "OK");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                showListCategory(collection);
            }
        });
        mModel.addOrderDataParameter("created_at");
        mModel.sortDirDESC();
        if(Utils.validateString(limit) && !limit.equals("0")){
            mModel.addLimitDataParameter(limit);
        }else{
            mModel.addLimitDataParameter("15");
        }
        mModel.request();
    }

    protected String arrayToString(ArrayList<String> array) {
        StringBuilder builder = new StringBuilder();
        boolean isFirst = true;
        if (null != array && array.size() > 0) {
            for (int i = 0; i < array.size(); i++) {
                String id = array.get(i);
                if (isFirst) {
                    builder.append(id);
                    isFirst = false;
                } else {
                    builder.append("," + id);
                }
            }
        }
        Log.e("SpotProductController", "arrToString " + builder.toString());
        return builder.toString();
    }

    protected void getFeature(String ids, String limit) {
        ProductDetailModel mModel = new ProductDetailModel();
        mModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if (error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "OK");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                showListCategory(collection);
            }
        });
        mModel.addDataParameter("ids", ids);
        if(Utils.validateString(limit) && !limit.equals("0")){
            mModel.addLimitDataParameter(limit);
        }else {
            mModel.addLimitDataParameter("15");
        }
        mModel.request();
    }

    private void showListCategory(SimiCollection collection){
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
                        mListCategory = (ObservableRecyclerView) viewCategory.findViewById(Rconfig.getInstance().id("listCategory"));
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        mListCategory.setLayoutManager(layoutManager);
                        mListCategory.setHasFixedSize(true);

                        mAdapter = new RecyclerViewMaterialAdapter(new MaterialCateogryAdapter(listProduct, getActivity()));
                        mListCategory.setAdapter(mAdapter);
                        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mListCategory, null);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
