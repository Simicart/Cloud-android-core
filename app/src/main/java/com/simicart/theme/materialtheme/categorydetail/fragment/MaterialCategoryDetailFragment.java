package com.simicart.theme.materialtheme.categorydetail.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.catalog.categorydetail.controller.CategoryDetailController;
import com.simicart.core.catalog.categorydetail.entity.TagSearch;
import com.simicart.core.catalog.categorydetail.model.ConstantsSearch;
import com.simicart.core.config.Rconfig;
import com.simicart.theme.materialtheme.categorydetail.block.MaterialCategoryDeailBlock;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MSI on 26/03/2016.
 */
public class MaterialCategoryDetailFragment extends SimiFragment {

    protected CategoryDetailController mController;

    protected String mCateName;
    protected String mCateID;
    protected String mUrl;
    protected Map<String, String> list_param = new HashMap<String, String>();

    public void setCategoryId(String id) {
        mCateID = id;
    }

    public void setCategoryName(String name) {
        mCateName = name;
    }

    public void setUrlSearch(String url) {
        mUrl = url;
        list_param.put(ConstantsSearch.PARAM_URL, url);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(
                Rconfig.getInstance().layout("material_category_detail_layout"),
                container, false);
        Context context = getActivity();
        String tag_search = TagSearch.TAG_GRIDVIEW;

        MaterialCategoryDeailBlock block = new MaterialCategoryDeailBlock(rootView, context);
        block.initView();

        if(mController == null) {
            mController = new CategoryDetailController(mCateName, mCateID);
            mController.setTag_search(tag_search);
            mController.setList_Param(list_param);
            mController.setDelegate(block);
            mController.onStart();
        } else {
            mController.setDelegate(block);
            mController.setTag_search(tag_search);
            mController.setList_Param(list_param);
            mController.onResume();
        }

        block.setChangeTypeListener(mController.getmOnTouchChangeViewData());

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
