package com.simicart.core.catalog.categorydetail.fragment;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.catalog.filter.FilterEvent;
import com.simicart.core.catalog.categorydetail.block.CategoryDetailBlock;
import com.simicart.core.catalog.categorydetail.controller.CategoryDetailController;
import com.simicart.core.catalog.categorydetail.entity.TagSearch;
import com.simicart.core.catalog.categorydetail.model.ConstantsSearch;
import com.simicart.core.checkout.controller.ConfigCheckout;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.core.home.block.SearchHomeBlock;

public class CategoryDetailFragment extends SimiFragment {
    protected View rootView;
    protected String mQuery;
    protected String tag_search;
    protected CategoryDetailBlock mCategoryDetailBlock;
    protected CategoryDetailController mCategoryDetailController;
    protected String mCatID = "-1";
    protected String mCatName = "";
    protected String mSortID = "None";
    protected JSONObject jsonFilter;
    protected String typeSearch = "0";
    protected FilterEvent filterEvent = null;
    protected int typeSportProduct = 0;
    protected String idsFeature = "";
    protected Map<String, String> list_param = new HashMap<String, String>();


    public String getCateID()
    {
        return mCatID;
    }

    public String getCateName()
    {
        return mCatName;
    }




    public static CategoryDetailFragment newInstance() {
        CategoryDetailFragment fragment = new CategoryDetailFragment();
        fragment.setTargetFragment(fragment, ConfigCheckout.TARGET_LISTPRODUCT);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(
                Rconfig.getInstance().layout("core_list_search_layout"),
                container, false);
        Context context = getActivity();
        if (tag_search == null) {
            if (DataLocal.isTablet) {
                tag_search = TagSearch.TAG_GRIDVIEW;
            } else {
                if (Config.getInstance().getDefaultList().equals("1")) {
                    // neu gia tri 1: Gridview
                    this.tag_search = TagSearch.TAG_GRIDVIEW;
                } else {
                    this.tag_search = TagSearch.TAG_LISTVIEW;
                }
            }
            setScreenName("List Category ID:" + mCatID);
        }
        setScreenName("Search Screen");
        mCategoryDetailBlock = new CategoryDetailBlock(rootView, context);
        mCategoryDetailBlock.setTag_search(tag_search);
        mCategoryDetailBlock.setmQuery(mQuery);
        mCategoryDetailBlock.setCateName(mCatName);
        mCategoryDetailBlock.setCate_id(mCatID);
        mCategoryDetailBlock.initView();
        if (mCategoryDetailController == null) {
            mCategoryDetailBlock.setCateName(mCatName);
            mCategoryDetailBlock.setFilterEvent(filterEvent);
            mCategoryDetailBlock.setmQuery(mQuery);

            mCategoryDetailController = new CategoryDetailController(mCatName, mCatID);
            filterEvent = new FilterEvent(mCategoryDetailController);
            mCategoryDetailController.setTag_search(tag_search);

            mCategoryDetailController.setQuery(mQuery);

            mCategoryDetailController.setTypeSearch(typeSearch);
            mCategoryDetailController.setmSortType(mSortID);
            mCategoryDetailController.setDelegate(mCategoryDetailBlock);
            mCategoryDetailController.setJsonFilter(jsonFilter);
            mCategoryDetailController.setList_Param(list_param);
            mCategoryDetailController.onStart();
        }
        if (!mCatID.equals("-1")) {
            mCategoryDetailBlock.setCateName(mCatName);
        }
        mCategoryDetailBlock.setScrollListView(mCategoryDetailController
                .getScrollListviewListener());
        mCategoryDetailBlock.setScrollGridView(mCategoryDetailController
                .getmScrollGridviewListener());
        mCategoryDetailBlock.setOnTourchChangeView(mCategoryDetailController
                .getmOnTouchChangeViewData());
        mCategoryDetailBlock.setOnTourchToFilter(mCategoryDetailController
                .getmOnTouchToFilter());
        mCategoryDetailBlock.setOnTourchToSort(mCategoryDetailController.getmOnTouchToSort());
        mCategoryDetailBlock.setOnTouchListenerGridview(mCategoryDetailController
                .getmOnTouchGridview());
        mCategoryDetailBlock.setOnItemListviewClick(mCategoryDetailController
                .getmListviewClick());
        // tablet
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mCategoryDetailController != null) {

            Log.e("CategoryDetailFragment","=============> onResume");

            mCategoryDetailController.setDelegate(mCategoryDetailBlock);
            filterEvent = new FilterEvent(mCategoryDetailController);
            mCategoryDetailBlock.setmQuery(mQuery);
            mCategoryDetailBlock.setFilterEvent(filterEvent);
            mCategoryDetailController.onResume();
        }
    }

    public void setTag_search(String tag_search) {
        if (DataLocal.isTablet) {
            this.tag_search = TagSearch.TAG_GRIDVIEW;
        } else {
            if (Config.getInstance().getDefaultList().equals("1")) {
                // neu gia tri 1: Gridview
                this.tag_search = TagSearch.TAG_GRIDVIEW;
            } else {
                this.tag_search = TagSearch.TAG_LISTVIEW;
            }
            // this.tag_search = tag_search;
        }
    }

    public void setmSortID(String mSortID) {
        this.mSortID = mSortID;
    }

    public void setCatName(String mCatName) {
        this.mCatName = mCatName;
    }

    public void setJsonFilter(JSONObject jsonFilter) {
        this.jsonFilter = jsonFilter;
    }

    public void setmCatID(String mCatID) {
        this.mCatID = mCatID;
    }

    public void setQuery(String mQuery) {
        this.mQuery = mQuery;
    }

    public void setTypeSportProduct(int typeSportProduct) {
        this.typeSportProduct = typeSportProduct;
    }

    public void setIdsFeature(String idsFeature) {
        this.idsFeature = idsFeature;
    }

    // set param
    public void setListParam(String key, String value) {
        list_param.put(key, value);
    }

    public void setTypeSport(int type) {
        setTypeSportProduct(type);
        setListParam(ConstantsSearch.PARAM_TYPE_SPORT, String.valueOf(type));
    }

    public void setListIdsFeature(String ids) {
        setIdsFeature(ids);
        setListParam(ConstantsSearch.PARAM_IDS_FEATURE, ids);
    }

    public void setCategoryId(String categoryId) {
        setmCatID(categoryId);
        setListParam(ConstantsSearch.PARAM_CATEGORY_ID, categoryId);
    }

    public void setCategoryName(String categoryName) {
        setCatName(categoryName);
        setListParam(ConstantsSearch.PARAM_CATEGORY_NAME, categoryName);
    }

    public void setUrlSearch(String url) {
        setListParam(ConstantsSearch.PARAM_URL, url);
    }

    public void setQuerySearch(String query) {
        setQuery(query);
        setListParam(ConstantsSearch.PARAM_QUERY, query);
    }

    public void setKey(String key) {
        setListParam(ConstantsSearch.PARAM_KEY, key);
    }

    public void setOffset(String offset) {
        setListParam(ConstantsSearch.PARAM_OFFSET, offset);
    }

    public void setLimit(String limit) {
        setListParam(ConstantsSearch.PARAM_LIMIT, limit);
    }

    public void setSortOption(String sortOption) {
        setmSortID(sortOption);
        setListParam(ConstantsSearch.PARAM_SORT_OPTION, sortOption);
    }

    public void setTypeSearch(String typeSearch) {
        this.typeSearch = typeSearch;
    }
}
