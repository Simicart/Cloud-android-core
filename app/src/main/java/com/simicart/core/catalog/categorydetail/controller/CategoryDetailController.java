package com.simicart.core.catalog.categorydetail.controller;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.catalog.category.delegate.FilterRequestDelegate;
import com.simicart.core.catalog.category.fragment.SortFragment;
import com.simicart.core.catalog.categorydetail.model.CategoryDetailModel;
import com.simicart.core.catalog.filter.entity.FilterEntity;
import com.simicart.core.catalog.filter.entity.FilterState;
import com.simicart.core.catalog.filter.entity.ValueFilterEntity;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.catalog.product.fragment.ProductDetailParentFragment;
import com.simicart.core.catalog.categorydetail.delegate.CategoryDetailDelegate;
import com.simicart.core.catalog.categorydetail.entity.TagSearch;
import com.simicart.core.catalog.categorydetail.model.ConstantsSearch;
import com.simicart.core.common.Utils;
import com.simicart.core.config.DataLocal;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CategoryDetailController extends SimiController implements
        FilterRequestDelegate {
    protected CategoryDetailDelegate mDelegate;
    protected int limit = 8;
    protected String resultNumber;
    protected String mQuery;
    protected int mCurrentOffset = 0;
    protected String mID = "-1";
    protected String mName;
    protected String mSortType = "None";
    protected JSONObject jsonFilter;
    protected boolean isOnscroll = true;
    protected boolean checkUrlRequest = false;
    protected String tag_search;
    protected OnScrollListener mScrollListviewListener,
            mScrollGridviewListener;
    protected OnItemClickListener mListviewClick;
    protected View.OnClickListener mOnTouchChangeViewData, mOnTouchToFilter,
            mOnTouchToSort;
    protected OnTouchListener mOnTouchGridview;
    protected boolean is_back_filter;
    protected String typeSearch = "0";
    // protected String type_search;
    private Map<String, String> list_param = new HashMap<String, String>();

    // zoom gridview
    float distance_up = 0;
    float distance_down = 0;
    float down_Y = 0;
    float up_Y = 0;
    boolean clickDetected = true;
    private static boolean checkZoom = false;
    private int position = -1;
    //  protected int firstPos = -1;

    public void setList_Param(Map<String, String> list_query) {
        this.list_param = list_query;
    }

    public CategoryDetailController(String name, String id) {
        this.mName = name;
        this.mID = id;
    }

    @Override
    public void onStart() {
        createListener();
        requestProduct();
    }

    private void requestProduct() {
        if (mCurrentOffset == 0) {
            mDelegate.showLoading();
        }
        if (DataLocal.isTablet) {
            limit = 32;
        } else {
            limit = 12;
        }
        if (mModel == null) {
            mModel = new CategoryDetailModel();
            String param_url = getValueListParam(ConstantsSearch.PARAM_URL);
            if (Utils.validateString(param_url)) {
                ((CategoryDetailModel) mModel).setUrlSearch(param_url);

                if (param_url.equals("categories")) {
                    mModel.addDataExtendURL(mID, "products");
                }
            }
        }

        String param_typesport = getValueListParam(ConstantsSearch.PARAM_TYPE_SPORT);
        if (Utils.validateString(param_typesport)) {
            mDelegate.showSort(false);

            if (param_typesport.equals("1")) {
                mModel.addDataParameter("group-type", "best-sellers");
            }
            if (param_typesport.equals("2")) {
                mModel.addOrderDataParameter("updated_at");
                mModel.sortDirDESC();
            }
            if (param_typesport.equals("3")) {
                mModel.addOrderDataParameter("created_at");
                mModel.sortDirDESC();
            }
            if (param_typesport.equals("4")) {
                String param_ids = getValueListParam(ConstantsSearch.PARAM_IDS_FEATURE);
                if (Utils.validateString(param_ids)) {
                    mModel.addDataParameter("ids", param_ids);
                }
            }
        } else {
            mDelegate.showSort(true);
        }

        String param_query = getValueListParam(ConstantsSearch.PARAM_QUERY);
        if (Utils.validateString(param_query)) {
            if (typeSearch.equals(TagSearch.TYPE_SEARCH_ALL)) {
                mModel.addDataExtendURL("search");
                mModel.addFilterDataParameter("keywords", param_query);
            } else {
                mModel.addDataExtendURL("search");
                mModel.addFilterDataParameter("keywords", param_query);
                mModel.addDataParameter("filter[category_ids][all]", mID);
            }
        }
        String param_offset = getValueListParam(ConstantsSearch.PARAM_OFFSET);
        if (Utils.validateString(param_offset)) {
            mModel.addOffsetDataParameter(String.valueOf(param_offset));
        } else {
            mModel.addOffsetDataParameter(String.valueOf(mCurrentOffset));
        }
        String param_limit = getValueListParam(ConstantsSearch.PARAM_LIMIT);
        if (param_limit != null && !param_limit.equals("")) {
            mModel.addLimitDataParameter(String.valueOf(param_limit));
        } else {
            mModel.addLimitDataParameter(String.valueOf(limit));
        }

        String param_sort_option = getValueListParam(ConstantsSearch.PARAM_SORT_OPTION);
        if (Utils.validateString(param_sort_option)) {
            if (param_sort_option.equals("1")) {
                mModel.addOrderDataParameter("sale_price");
                mModel.sortDirASC();
            } else if (param_sort_option.equals("2")) {
                mModel.addOrderDataParameter("sale_price");
                mModel.sortDirDESC();
            } else if (param_sort_option.equals("3")) {
                mModel.addOrderDataParameter("name");
                mModel.sortDirASC();
            } else if (param_sort_option.equals("4")) {
                mModel.addOrderDataParameter("name");
                mModel.sortDirDESC();
            }
        }

        mModel.addDataParameter("stock_info", "1");
        mModel.addFilterDataParameter("status", "1");
        mModel.addDataParameter("order", "created_at");
        mModel.sortDirDESC();

        mModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {

            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.dismissLoading();
                mDelegate.removeFooterView();
                resultNumber = ((CategoryDetailModel) mModel).getTotal() + "";
                mDelegate.setQty(resultNumber);
                mDelegate.updateView(collection);
                isOnscroll = true;
            }
        });
        mModel.request();
    }

    private String getValueListParam(String key) {
        if (list_param.containsKey(key)) {
            return list_param.get(key);
        }
        return "";
    }

    protected void createListener() {
        mListviewClick = new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                selectemItem(position - 1);
            }
        };

        mOnTouchChangeViewData = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tag_search = mDelegate.onChangeTypeViewShow();
            }
        };

        mOnTouchToFilter = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };

        mOnTouchToSort = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSortLayout(mQuery);
            }
        };

        initScrollGrid();

        initScrollList();

        initTouchScroll();
    }

    protected void initScrollList() {
        mScrollListviewListener = new OnScrollListener() {

            int currentFirstVisibleItem;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                try {
                    int threshold = 1;
                    int count = view.getCount();
                    int current_size = ((CategoryDetailModel) mModel).getCurrentSize();
                    if (scrollState == SCROLL_STATE_IDLE) {
                        if ((view.getLastVisiblePosition() >= count - threshold)
                                && Integer.parseInt(resultNumber) > current_size) {
                            if (isOnscroll) {
                                mCurrentOffset += limit;
                                isOnscroll = false;
                                mDelegate.addFooterView();
                                mDelegate.setIsLoadMore(true);
                                requestProduct();
                            }
                        }
                    }

                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItem) {
                try {
                    if (currentFirstVisibleItem > firstVisibleItem) {
                        mDelegate.setVisibilityMenuBotton(true);
                    } else if (currentFirstVisibleItem < firstVisibleItem) {
                        mDelegate.setVisibilityMenuBotton(false);
                    }
                    currentFirstVisibleItem = firstVisibleItem;
                    mDelegate
                            .setCurrentPosition(view.getFirstVisiblePosition());
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        };
    }


    protected void initScrollGrid() {
        mScrollGridviewListener = new OnScrollListener() {

            int currentFirstVisibleItem;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                int threshold = 1;
                int count = view.getCount();
                int current_size = ((CategoryDetailModel) mModel).getCurrentSize();
                if (scrollState == SCROLL_STATE_IDLE) {
                    if ((view.getLastVisiblePosition() >= count - threshold)
                            && Integer.parseInt(resultNumber) > current_size) {
                        if (isOnscroll) {
                            mCurrentOffset += limit;
                            isOnscroll = false;
                            mDelegate.addFooterView();
                            mDelegate.setTagSearch(TagSearch.TAG_GRIDVIEW);
                            mDelegate.setIsLoadMore(true);
                            requestProduct();
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItem) {
                if (currentFirstVisibleItem > firstVisibleItem) {
                    mDelegate.setVisibilityMenuBotton(true);
                } else if (currentFirstVisibleItem < firstVisibleItem) {
                    mDelegate.setVisibilityMenuBotton(false);
                }
                currentFirstVisibleItem = firstVisibleItem;
                mDelegate.setCurrentPosition(view.getFirstVisiblePosition());
            }
        };
    }


    protected void initTouchScroll() {
        mOnTouchGridview = new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int action = event.getAction();
                if (action != MotionEvent.ACTION_UP
                        && action != MotionEvent.ACTION_DOWN) {
                    clickDetected = false;
                }
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        // first finger down only
                        float singer_down = event.getY();
                        down_Y = singer_down;
                        int postion = ((GridView) v).pointToPosition(
                                (int) event.getX(), (int) event.getY());
                        position = postion;
                        break;
                    case MotionEvent.ACTION_UP:
                        // first finger lifter
                        float singer_up = event.getY();
                        up_Y = singer_up;
                        float value = up_Y - down_Y;
                        float distance = Math.abs(value);
                        if (value < 0) {
                            // scroll down
                            if (distance < 50) {
                                if (clickDetected == false) {
                                    selectemItem(position);
                                }
                            } else {
                                if (checkZoom == false) {
                                    // scrollDown();
                                    mDelegate.setVisibilityMenuBotton(false);
                                }
                            }
                        } else if (value == 0.0) {
                            selectemItem(position);
                        } else {
                            // scroll up
                            if (distance < 50) {
                                if (clickDetected == false) {
                                    selectemItem(position);
                                }
                            } else {
                                if (checkZoom == false) {
                                    // scrollUp();
                                    mDelegate.setVisibilityMenuBotton(true);
                                }
                            }
                        }
                        checkZoom = false;
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        // second finger lifted
                        float up = spacing(event);
                        distance_up = up;
                        boolean is_zoom_out;
                        if (distance_up < distance_down) {
                            // zoom out
                            is_zoom_out = true;
                        } else {
                            is_zoom_out = false;
                        }
                        processZoomGridView(is_zoom_out);
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        // second finger down
                        float down = spacing(event);
                        distance_down = down;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    default:
                        break;
                }
                return false;
            }
        };
    }

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    private void processZoomGridView(boolean is_zoom_out) {
        mDelegate.onChangeNumberColumnGrid(is_zoom_out);
    }


    private void selectemItem(int position) {
        if (position != -1) {
            ArrayList<ProductEntity> listProduct = mDelegate.getListProduct();
            String productId = listProduct.get(position).getID();
            if (productId != null) {
                ProductDetailParentFragment fragment = ProductDetailParentFragment
                        .newInstance();
                fragment.setProductID(productId);
                ArrayList<String> listID = ((CategoryDetailModel) mModel).getListProducID();
                if (null != listID && listID.size() > 0) {
                    fragment.setListIDProduct(listID);
                } else {
                    ArrayList<String> list_id = mDelegate.getListProductId();
                    if (null != list_id && list_id.size() > 0) {
                        fragment.setListIDProduct(list_id);
                    }
                }
                SimiManager.getIntance().replaceFragment(fragment);
            }
        }
        SimiManager.getIntance().hideKeyboard();
    }


    private void toSortLayout(String query) {
        SortFragment fragment = SortFragment.newInstance(mName, mID);
        if (!getValueListParam(ConstantsSearch.PARAM_URL).equals("")) {
            fragment.setUrl_search(getValueListParam(ConstantsSearch.PARAM_URL));
        }
        String param_key = getValueListParam(ConstantsSearch.PARAM_KEY);
        if (param_key != null && !param_key.equals("")) {
            fragment.setKey(param_key);
        }
        fragment.setJSONFilter(jsonFilter);
        fragment.setSortType(mSortType);
        fragment.setJSONFilter(jsonFilter);
        fragment.setSort_tag(mDelegate.getTagSearch());
        fragment.setQuery(query);
        SimiManager.getIntance().replacePopupFragment(fragment);
    }

    @Override
    public void onResume() {
        if (is_back_filter) {
            is_back_filter = false;
            mDelegate.setCheckFilter(true);
            mDelegate.setTagSearch(tag_search);
            mModel.getCollection().getCollection().clear();
            checkUrlRequest = true;
            requestProduct();
        } else {
            mDelegate.setTagSearch(tag_search);
            mDelegate.updateView(mModel.getCollection());
            if (mModel.getCollection().getCollection().size() > 0) {
                mDelegate.setQty(resultNumber.trim());
            }
            mDelegate.setVisibilityMenuBotton(true);
        }
    }

    @Override
    public void requestFilter(FilterEntity filterEntity) {
        if (null != filterEntity) {
            if (null == jsonFilter) {
                jsonFilter = new JSONObject();
            }
            String attribute = filterEntity.getmAttribute();
            ArrayList<ValueFilterEntity> valueEntity = filterEntity
                    .getmValueFilters();
            if (null != valueEntity && valueEntity.size() > 0) {
                for (int i = 0; i < valueEntity.size(); i++) {
                    ValueFilterEntity entity = valueEntity.get(i);
                    if (entity.isSelected()) {
                        String value = entity.getmValue();
                        if (Utils.validateString(attribute)
                                && Utils.validateString(value)) {
                            try {
                                jsonFilter.put(attribute, value);
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                }
                // request
                is_back_filter = true;
                // requestNewData();

            }
        }
    }

    @Override
    public void clearFilter(FilterState filter) {
        if (null != filter && null != jsonFilter) {
            String attribute = filter.getAttribute();
            if (jsonFilter.has(attribute)) {
                jsonFilter.remove(attribute);
            }
            is_back_filter = true;
        }
    }

    @Override
    public void clearAllFilter() {
        is_back_filter = true;
        jsonFilter = null;
    }

    public boolean isBackFilter() {
        return is_back_filter;
    }

    public void setmID(String mID) {
        this.mID = mID;
    }

    public void setQuery(String query) {
        this.mQuery = query;
    }

    public void setTag_search(String tag_search) {
        this.tag_search = tag_search;
    }

    public void setJsonFilter(JSONObject json) {
        jsonFilter = json;
    }

    public void setmSortType(String mSortType) {
        this.mSortType = mSortType;
    }

    public void setDelegate(CategoryDetailDelegate mDelegate) {
        this.mDelegate = mDelegate;
    }

    public OnScrollListener getScrollListviewListener() {
        return mScrollListviewListener;
    }

    public View.OnClickListener getmOnTouchChangeViewData() {
        return mOnTouchChangeViewData;
    }

    public OnTouchListener getmOnTouchGridview() {
        return mOnTouchGridview;
    }

    public View.OnClickListener getmOnTouchToFilter() {
        return mOnTouchToFilter;
    }

    public View.OnClickListener getmOnTouchToSort() {
        return mOnTouchToSort;
    }

    public OnItemClickListener getmListviewClick() {
        return mListviewClick;
    }

    public OnScrollListener getmScrollGridviewListener() {
        return mScrollGridviewListener;
    }

    public void setTypeSearch(String typeSearch) {
        this.typeSearch = typeSearch;
    }
}
