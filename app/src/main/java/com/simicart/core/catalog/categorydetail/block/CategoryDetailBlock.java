package com.simicart.core.catalog.categorydetail.block;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.simicart.core.adapter.ProductListAdapter;
import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.catalog.filter.FilterEvent;
import com.simicart.core.catalog.filter.common.FilterConstant;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.catalog.categorydetail.adapter.GridViewCategoryDetailApdapter;
import com.simicart.core.catalog.categorydetail.adapter.ListPopupAdapter;
import com.simicart.core.catalog.categorydetail.delegate.CategoryDetailDelegate;
import com.simicart.core.catalog.categorydetail.entity.ItemListPopup;
import com.simicart.core.catalog.categorydetail.entity.TagSearch;
import com.simicart.core.catalog.categorydetail.fragment.CategoryDetailFragment;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.core.searchvoice.SearchVoice;

public class CategoryDetailBlock extends SimiBlock implements CategoryDetailDelegate,
        OnItemClickListener {
    protected RelativeLayout rlt_menu_bottom, ll_list, rlt_change_view,
            rlt_filter, rlt_sort;
    private FilterEvent mFilterEvent;
    private ProductListAdapter mAdapterListView;
    private GridViewCategoryDetailApdapter mAdapterGridView;
    private String cate_name = "";
    private String cate_id = "";
    private View ivView;
    private String mQuery = "";
    private String tag_search;
    private GridView grv_products;
    private ListView lv_products;
    private ImageView img_change_view;
    private ArrayList<ProductEntity> listProduct = new ArrayList<ProductEntity>();
    private int position_curent_product = 0;
    private TextView txt_total_item;
    private EditText edit_search;
    private Animation zoomin;
    private Animation zoomout;
    private ArrayList<String> listProductIds = new ArrayList<String>();

    private RelativeLayout rlt_layout;
    private RelativeLayout relativeLayoutSearch;

    private boolean check_filter;
    private boolean is_loadmore = false;

    protected ProgressBar progressbar;
    private View layout_toast;
    private TextView txt_toast;

    private PopupWindow popupWindow;
    private PopupWindow popUp;
    private String ALL_PRODUCT = "All Product";
    private int totalResult = 0;

    public void setTag_search(String tag_search) {
        this.tag_search = tag_search;
    }


    public void setCate_id(String cate_id) {
        this.cate_id = cate_id;
    }

    public void setCateName(String cate_name) {
        this.cate_name = cate_name;
    }

    public void setmQuery(String mQuery) {
        this.mQuery = mQuery;
    }

    public CategoryDetailBlock(View view, Context context) {
        super(view, context);
    }

    public void setFilterEvent(FilterEvent filterEvent) {
        mFilterEvent = filterEvent;
    }

    @Override
    public void initView() {
        rlt_menu_bottom = (RelativeLayout) mView.findViewById(Rconfig
                .getInstance().id("menu_bottom_search"));
        rlt_menu_bottom.setVisibility(View.GONE);
        rlt_filter = (RelativeLayout) mView.findViewById(Rconfig.getInstance()
                .id("ll_to_filter"));
        rlt_sort = (RelativeLayout) mView.findViewById(Rconfig.getInstance()
                .id("ll_to_sort"));
        rlt_change_view = (RelativeLayout) mView.findViewById(Rconfig
                .getInstance().id("ll_change_view_data"));
        lv_products = (ListView) mView.findViewById(Rconfig.getInstance().id(
                "lv_list_search"));
        ColorDrawable sage = new ColorDrawable(Config.getInstance()
                .getLine_color());
        lv_products.setDivider(sage);
        lv_products.setDividerHeight(1);
        LayoutInflater inflaterHeader = SimiManager.getIntance()
                .getCurrentActivity().getLayoutInflater();
        ViewGroup header = (ViewGroup) inflaterHeader.inflate(Rconfig
                        .getInstance().layout("core_header_listview_search"),
                lv_products, false);
        lv_products.addHeaderView(header);
        if (!DataLocal.isTablet) {
            img_change_view = (ImageView) mView.findViewById(Rconfig
                    .getInstance().id("img_change_view"));
            img_change_view.setBackgroundResource(Rconfig.getInstance()
                    .drawable("ic_to_gridview"));
        } else {
            txt_total_item = (TextView) mView.findViewById(Rconfig
                    .getInstance().id("txt_totalitem"));
        }
        grv_products = (GridView) mView.findViewById(Rconfig.getInstance().id(
                "grv_product"));
        if (tag_search.equals(TagSearch.TAG_LISTVIEW)) {
            lv_products.setVisibility(View.VISIBLE);
            grv_products.setVisibility(View.GONE);
        } else {
            lv_products.setVisibility(View.GONE);
            grv_products.setVisibility(View.VISIBLE);
            grv_products.setPadding(Utils.getValueDp(10), 0,
                    Utils.getValueDp(10), 0);
        }
        zoomin = AnimationUtils.loadAnimation(mContext, Rconfig.getInstance()
                .getId("zoomin", "anim"));
        zoomout = AnimationUtils.loadAnimation(mContext, Rconfig.getInstance()
                .getId("zoomout", "anim"));
        progressbar = (ProgressBar) mView.findViewById(Rconfig.getInstance()
                .id("progressBar_load"));
        progressbar.setVisibility(View.GONE);
        if (!DataLocal.isTablet) {
            initSearch();
        }
        createToast();
    }

    void createToast() {
        Activity activity = SimiManager.getIntance().getCurrentActivity();
        LayoutInflater inflater = activity.getLayoutInflater();
        int id_view_group = Rconfig.getInstance().id("custom_toast_layout");
        ViewGroup viewGroup = (ViewGroup) activity.findViewById(id_view_group);
        int id_custom_view = Rconfig.getInstance().layout("core_custom_toast_productlist");
        layout_toast = inflater.inflate(id_custom_view, viewGroup);
        int id_tv_toast = Rconfig.getInstance().id("txt_custom_toast");
        txt_toast = (TextView) layout_toast.findViewById(id_tv_toast);
    }

    void initSearch() {
        popUp = popupWindowsort();

        LinearLayout ll_search = (LinearLayout) mView.findViewById(Rconfig
                .getInstance().id("ll_search"));
        ll_search.setBackgroundColor(Config.getInstance()
                .getSearch_box_background());

        rlt_layout = (RelativeLayout) mView.findViewById(Rconfig.getInstance()
                .id("rlt_layout"));
        relativeLayoutSearch = (RelativeLayout) mView.findViewById(Rconfig
                .getInstance().id("rlt_layout"));
        ImageView img_ic_search = (ImageView) mView.findViewById(Rconfig
                .getInstance().id("img_ic_search"));
        Drawable drawable = mContext.getResources().getDrawable(
                Rconfig.getInstance().drawable("ic_search"));
        drawable.setColorFilter(Config.getInstance().getSearch_icon_color(),
                PorterDuff.Mode.SRC_ATOP);
        img_ic_search.setImageDrawable(drawable);

        edit_search = (EditText) mView.findViewById(Rconfig.getInstance().id(
                "edittext_search"));
        edit_search.setHint(Config.getInstance().getText("Search product"));
        edit_search.setHintTextColor(Color.parseColor("#32000000"));
        if (!cate_name.equals("") && cate_name != null) {
            edit_search.setHint(cate_name);
            // edit_search.setTypeface(null, Typeface.BOLD);
        }
        if (mQuery != null && !mQuery.equals("")) {
            edit_search.setHint(mQuery);
        }
        edit_search.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        edit_search.setTextColor(Config.getInstance().getSearch_text_color());
        edit_search.setHintTextColor(Config.getInstance()
                .getSearch_text_color());
        edit_search.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == true) {
                    rlt_layout.setVisibility(View.GONE);
                } else {
                    rlt_layout.setVisibility(View.VISIBLE);
                }
            }
        });
        edit_search.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_SEARCH)
                        && (event.getAction() == KeyEvent.ACTION_DOWN)) {
                    showSearchScreen(edit_search.getText().toString(),
                            tag_search);
                    Utils.hideKeyboard(v);
                    return true;
                }
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                    // Do your thing.
                    if (popUp.isShowing()) {
                        hidePopupListView();
                        return true;
                    }
                    return false; // So it is not propagated.
                }
                return false;
            }
        });
        relativeLayoutSearch.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                edit_search.requestFocus();
                InputMethodManager imm = (InputMethodManager) mContext
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(edit_search, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        edit_search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (cate_name != null && !cate_name.equals("")
                        && !cate_name.equals("all categories")) {
                    if (s.length() > 0 && edit_search.hasFocus()) {
                        if (popUp.isShowing()) {
                        } else {
                            popUp.showAsDropDown(edit_search, 0, 0);
                        }
                    } else {
                        hidePopupListView();
                    }
                }
            }
        });

//        EventBlock block = new EventBlock();
//        CacheBlock  cacheBlock = new CacheBlock();
//        cacheBlock.setView(mView);
//        SimiEntity entity = new SimiEntity();

//        entity.setJSONObject(object);
//        cacheBlock.setSimiEntity(entity);

        try {
            JSONObject object = new JSONObject();
            object.put("cateId", cate_id);
            object.put("cateName", cate_name);
            object.put("tagSearch", tag_search);

            new SearchVoice("addIconSearchVoice", object, mView);

        } catch (Exception e) {

        }

    }

    private PopupWindow popupWindowsort() {
        // initialize a pop up window type
        popupWindow = new PopupWindow(mContext);
        popupWindow.setFocusable(false);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        ArrayList<ItemListPopup> listItem = new ArrayList<ItemListPopup>();
        ItemListPopup item1 = new ItemListPopup();
        item1.setName(cate_name);
        item1.setCheckSearch(false);
        listItem.add(item1);
        ItemListPopup item2 = new ItemListPopup();
        item2.setName(Config.getInstance().getText(ALL_PRODUCT));
        item2.setCheckSearch(true);
        listItem.add(item2);
        ListPopupAdapter adapter = new ListPopupAdapter(mContext, listItem);
        // the drop down list is a list view
        ListView listViewSort = new ListView(mContext);
        // listViewSort.setBackgroundColor(Color.parseColor("#E6ffffff"));
        listViewSort.setAdapter(adapter);
        listViewSort.setOnItemClickListener(this);
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setContentView(listViewSort);
        return popupWindow;
    }

    private void hidePopupListView() {
        if (popUp != null) {
            if (popUp.isShowing()) {
                popUp.dismiss();
            }
        }
        SimiManager.getIntance().hideKeyboard();
    }

    public void showSearchScreen(String key, String tag) {
        if (key != null && !key.equals("")) {
            hidePopupListView();
            CategoryDetailFragment fragment = CategoryDetailFragment.newInstance();
            fragment.setQuerySearch(key);
            fragment.setTag_search(tag);
            fragment.setCategoryId(cate_id);
            fragment.setTypeSearch(TagSearch.TYPE_SEARCH_ALL);
            fragment.setUrlSearch("products");
            SimiManager.getIntance().addFragment(fragment);
        }
    }

    public void drawListView() {
        lv_products.setVisibility(View.VISIBLE);
        grv_products.setVisibility(View.GONE);
        if (listProduct.size() > 0) {
            if (null == mAdapterListView) {
                mAdapterListView = new ProductListAdapter(mContext, listProduct);
                lv_products.setAdapter(mAdapterListView);
            } else {
                mAdapterListView.setProductList(listProduct);
                mAdapterListView.notifyDataSetChanged();
            }
            if (null != mFilterEvent) {
                Button btn_filter = (Button) mFilterEvent.initView(mContext,
                        cate_name);
                addFilterButton(btn_filter);
            }
        } else {
            lv_products.setVisibility(View.GONE);
        }
    }

    public void drawDataToGridView() {
        grv_products.setVisibility(View.VISIBLE);
        lv_products.setVisibility(View.GONE);
        if (listProduct.size() > 0) {
            if (null == mAdapterGridView) {
                if (DataLocal.isTablet) {
                    mAdapterGridView = new GridViewCategoryDetailApdapter(
                            mContext, listProduct, 4);
                    grv_products.setNumColumns(4);
                } else {
                    mAdapterGridView = new GridViewCategoryDetailApdapter(
                            mContext, listProduct, 2);
                }
                grv_products.setAdapter(mAdapterGridView);
            } else {
                mAdapterGridView.setListProduct(listProduct);
                mAdapterGridView.notifyDataSetChanged();
            }
            if (null != mFilterEvent) {
                Button btn_filter = (Button) mFilterEvent.initView(mContext,
                        cate_name);
                addFilterButton(btn_filter);
            }
        } else {
            grv_products.setVisibility(View.GONE);
        }


    }

    @Override
    public void drawView(SimiCollection collection) {
        addListProductId(collection.getJSON());
        ArrayList<SimiEntity> entityProducts = collection.getCollection();
        if (null != entityProducts && entityProducts.size() > 0) {
            listProduct.clear();
            for (SimiEntity simiEntity : entityProducts) {
                ProductEntity product = (ProductEntity) simiEntity;
                String id = product.getID();
                listProductIds.add(id);
                listProduct.add(product);
            }
            if (check_filter) {
                check_filter = false;
            }
        }
        if (null != mFilterEvent) {
            JSONObject json = collection.getJSON();
            if (null != json && json.has(FilterConstant.LAYEREDNAVIGATION)) {
                mFilterEvent.setJSON(json);
            }
        }
        if (listProduct.size() > 0) {
            rlt_menu_bottom.setVisibility(View.VISIBLE);
            if (tag_search.equals(TagSearch.TAG_GRIDVIEW)) {
                lv_products.setVisibility(View.GONE);
                grv_products.setVisibility(View.VISIBLE);
                grv_products.setSelection(position_curent_product);
                drawDataToGridView();
            } else {
                lv_products.setVisibility(View.VISIBLE);
                grv_products.setVisibility(View.GONE);
                lv_products.setSelection(position_curent_product);
                drawListView();
            }
        }
    }

    public void visiableView() {
        ((ViewGroup) mView).removeAllViewsInLayout();
        TextView tv_notify = new TextView(mContext);
        tv_notify.setText(Config.getInstance().getText(
                "Result products is empty"));
        tv_notify.setTypeface(null, Typeface.BOLD);
        tv_notify.setTextColor(Config.getInstance().getContent_color());
        if (DataLocal.isTablet) {
            tv_notify.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        } else {
            tv_notify.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        }
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        tv_notify.setGravity(Gravity.CENTER);
        tv_notify.setLayoutParams(params);
        ((ViewGroup) mView).addView(tv_notify);
    }

    private boolean addListProductId(JSONObject json) {
        if (json != null && json.has("other")) {
            try {
                JSONArray arrayId = json.getJSONArray("other");
                for (int i = 0; i < arrayId.length(); i++) {
                    JSONObject object_other = (JSONObject) arrayId.get(i);
                    if (object_other != null
                            && object_other.has("product_id_array")) {
                        JSONArray array = object_other
                                .getJSONArray("product_id_array");
                        if (array.length() > 0) {
                            listProductIds.clear();
                            for (int j = 0; j < array.length(); j++) {
                                String id = array.getString(j);
                                listProductIds.add(id);
                            }
                        }
                    }
                }
                return true;
            } catch (Exception e) {
            }
        }
        return false;
    }

    public void setOnTourchChangeView(View.OnClickListener click_listener) {
        if (!DataLocal.isTablet) {
            rlt_change_view.setOnClickListener(click_listener);
        }
    }

    public void setOnTourchToFilter(View.OnClickListener click_listener) {
        rlt_filter.setOnClickListener(click_listener);
    }

    public void setOnTourchToSort(View.OnClickListener click_listener) {
        rlt_sort.setOnClickListener(click_listener);
    }

    public void setScrollListView(OnScrollListener scroller) {
        lv_products.setOnScrollListener(scroller);
    }

    public void setOnTouchListenerGridview(OnTouchListener listener) {
        grv_products.setOnTouchListener(listener);
    }

    public void setScrollGridView(OnScrollListener listener) {
        grv_products.setOnScrollListener(listener);
    }

    public void setOnItemListviewClick(OnItemClickListener clickListener) {
        lv_products.setOnItemClickListener(clickListener);
    }

    @SuppressLint("NewApi")
    protected void addFilterButton(Button btn_filter) {
        if (null != btn_filter) {
            // add filter button right of sort button
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    Utils.getValueDp(25), Utils.getValueDp(25));
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            btn_filter.setLayoutParams(params);
            btn_filter.setBackgroundResource(Rconfig.getInstance().drawable(
                    "ic_filter"));
            rlt_filter.addView(btn_filter);
        }

    }

    @Override
    public void setQty(String qty) {
        if (checkQtyIsInteger(qty)) {
            totalResult = Integer.parseInt(qty);
            if (totalResult > 1) {
                qty = qty + " " + Config.getInstance().getText("items");
            } else {
                qty = qty + " " + Config.getInstance().getText("item");
            }
            if (DataLocal.isTablet) {
                txt_total_item.setText("");
                txt_total_item.setText(qty);
            } else {
                if (is_loadmore == false && mView.getContext() != null) {
                    Toast toast = new Toast(mView.getContext());
                    txt_toast.setText(qty);
                    toast.setView(layout_toast);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL,
                            0, 200);
                    toast.show();
                }
            }
        } else {
            lv_products.setVisibility(View.GONE);
            grv_products.setVisibility(View.GONE);
            rlt_menu_bottom.setVisibility(View.GONE);
            visiableView();
        }
    }

    private boolean checkQtyIsInteger(String qty) {
        if (!Utils.validateString(qty)) {
            return false;
        }
        try {
            int result = Integer.parseInt(qty);
            if (result <= 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void removeFooterView() {
        progressbar.setVisibility(View.GONE);
        lv_products.post(new Runnable() {
            @Override
            public void run() {
                while (lv_products.getFooterViewsCount() > 0) {
                    lv_products.removeFooterView(ivView);
                }
            }
        });
    }

    @Override
    public void addFooterView() {
        if (tag_search.equals(TagSearch.TAG_GRIDVIEW)) {
            progressbar.setVisibility(View.VISIBLE);
        } else {
            progressbar.setVisibility(View.GONE);
            LayoutInflater inflater = (LayoutInflater) lv_products.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ivView = inflater.inflate(
                    Rconfig.getInstance().layout("core_loading_list"), null,
                    false);
            lv_products.post(new Runnable() {
                @Override
                public void run() {
                    lv_products.addFooterView(ivView);
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                        int lastViewedPosition = lv_products
                                .getFirstVisiblePosition();
                        View v = lv_products.getChildAt(0);
                        int topOffset = (v == null) ? 0 : v.getTop();
                        lv_products.setAdapter(mAdapterListView);
                        lv_products.setSelectionFromTop(lastViewedPosition,
                                topOffset);
                    }
                }
            });
        }
    }


    @Override
    public void setVisibilityMenuBotton(boolean temp) {
        if (null != rlt_menu_bottom && !DataLocal.isTablet) {
            if (temp) {
                rlt_menu_bottom.setVisibility(View.VISIBLE);
            } else {
                rlt_menu_bottom.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public ArrayList<ProductEntity> getListProduct() {
        return listProduct;
    }


//    @Override
//    public Context getmContext() {
//        return mContext;
//    }


    @Override
    public void setCurrentPosition(int position) {
        position_curent_product = position;
    }


    @Override
    public String getTagSearch() {
        return tag_search;
    }

    @Override
    public void setTagSearch(String tag_search) {
        setTag_search(tag_search);
    }


    @Override
    public void setCheckFilter(boolean filter) {
        check_filter = true;
    }

    @Override
    public ArrayList<String> getListProductId() {
        return listProductIds;
    }

    @Override
    public void setIsLoadMore(boolean loadmore) {
        this.is_loadmore = loadmore;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        ItemListPopup item = (ItemListPopup) parent.getItemAtPosition(position);
        if (item.getName().equals(ALL_PRODUCT)) {
            showSearchScreen(edit_search.getText().toString(), tag_search);
        } else {
            CategoryDetailFragment searchFragment = CategoryDetailFragment
                    .newInstance();
            searchFragment.setUrlSearch("products");
            searchFragment.setCategoryId(cate_id);
            searchFragment.setTypeSearch(TagSearch.TYPE_SEARCH_CATEGORY);
            searchFragment.setQuerySearch(edit_search.getText().toString());
            SimiManager.getIntance().replaceFragment(searchFragment);
        }
        hidePopupListView();
        SimiManager.getIntance().hideKeyboard();
    }


    @Override
    public void showSort(boolean isCheck) {
        if (isCheck) {
            rlt_sort.setVisibility(View.VISIBLE);
        } else {
            rlt_sort.setVisibility(View.GONE);
        }
    }

    @Override
    public void onChangeNumberColumnGrid(boolean is_zoom_out) {
        Animation animation;
        int column;
        if (is_zoom_out) {
            animation = zoomout;
            column = getNumberColumnForZoomOut();
        } else {
            animation = zoomin;
            column = getNumberColumnForZoomIn();
        }

        if (column > 0) {
            mAdapterGridView = new GridViewCategoryDetailApdapter(mContext, listProduct, column);
            grv_products.setAdapter(mAdapterGridView);
            grv_products.setNumColumns(column);
            grv_products.startAnimation(animation);
            mAdapterGridView.notifyDataSetChanged();
            grv_products.invalidateViews();
        }


    }

    private int getNumberColumnForZoomIn() {
        int column = 0;
        int current_number_column = grv_products.getNumColumns();
        if (current_number_column == 6) {
            column = 4;
        } else if (current_number_column == 4) {
            column = 2;
        }
        return column;
    }

    private int getNumberColumnForZoomOut() {
        int column = 0;
        int current_number_column = grv_products.getNumColumns();
        if (current_number_column == 2) {
            column = 4;
        } else if (current_number_column == 4 && DataLocal.isTablet) {
            column = 6;
        }
        return column;
    }

    @Override
    public String onChangeTypeViewShow() {
        if (null != listProduct && listProduct.size() > 0) {
            if (tag_search.equals(TagSearch.TAG_LISTVIEW)) {
                img_change_view.setBackgroundResource(
                        Rconfig.getInstance().drawable("ic_to_listview"));
                lv_products.setVisibility(View.GONE);
                grv_products.setVisibility(View.VISIBLE);
                if (null == mAdapterGridView) {
                    mAdapterGridView = new GridViewCategoryDetailApdapter(mContext, listProduct, 2);
                } else {
                    mAdapterGridView.setListProduct(listProduct);
                }

                grv_products.setSelection(position_curent_product);
                grv_products.setAdapter(mAdapterGridView);
                tag_search = TagSearch.TAG_GRIDVIEW;
            } else {
                img_change_view.setBackgroundResource(
                        Rconfig.getInstance().drawable("ic_to_gridview"));
                grv_products.setVisibility(View.GONE);
                lv_products.setVisibility(View.VISIBLE);
                if (null == mAdapterListView) {
                    mAdapterListView = new ProductListAdapter(mContext, listProduct);
                } else {
                    mAdapterListView.setProductList(listProduct);
                }
                lv_products.setSelection(position_curent_product);
                lv_products.setAdapter(mAdapterListView);
                tag_search = TagSearch.TAG_LISTVIEW;
            }

        }

        return tag_search;
    }
}
