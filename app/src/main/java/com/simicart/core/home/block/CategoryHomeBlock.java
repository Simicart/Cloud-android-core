package com.simicart.core.home.block;

import java.util.ArrayList;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.devsmart.android.ui.HorizontalListView;
import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.catalog.category.entity.Category;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.core.home.adapter.HomeCategoryAdapter;
import com.simicart.core.home.controller.CategoryHomeListener;
import com.simicart.core.home.delegate.CategoryHomeDelegate;

public class CategoryHomeBlock extends SimiBlock implements
        CategoryHomeDelegate {

    private View mView;
    private CategoryHomeListener categoryHomeListener;
    private RelativeLayout rl_home;
    private LinearLayout ll_category;

    public CategoryHomeBlock(View view, Context context) {
        super(view, context);
        this.mView = view;
    }

    @Override
    public void initView() {
        ll_category = (LinearLayout) mView.findViewById(Rconfig.getInstance().id("ll_category"));
        rl_home = (RelativeLayout) mView.findViewById(Rconfig.getInstance().id("rl_home"));
    }

    @Override
    public void drawView(SimiCollection collection) {
        ArrayList<SimiEntity> entity = collection.getCollection();
        if (null != entity && entity.size() > 0) {
            ArrayList<Category> categories = new ArrayList<Category>();
            for (SimiEntity simiEntity : entity) {
                Category category = (Category) simiEntity;
                categories.add(category);
            }
            if (categories.size() > 0) {
                showCategorys(categories);
            }
        } else {
            String demo_enable = Config.getInstance().getDemoEnable().toUpperCase();
            if (demo_enable.equals("DEMO_ENABLE") || demo_enable.equals("YES")) {
                showFakeCategorys();
            } else {
                mView.setVisibility(View.GONE);
            }
        }
    }

    private void showFakeCategorys() {
        ArrayList<Category> categories = new ArrayList<Category>();
        for (int i = 0; i < 4; i++) {
            Category category1 = new Category();
            category1.setCategoryID("fake");
            category1.setCategoryName("Category " + i);
            category1.setChild(false);
            category1.setCategoryImage("fake_category");
            categories.add(category1);
        }
        showCategorys(categories);
    }


    public void showCategorys(ArrayList<Category> listCategory) {
        // name
        RelativeLayout.LayoutParams title_lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        TextView tv_name = new TextView(mContext);
        tv_name.setLayoutParams(title_lp);
        int height = 0;
        if (DataLocal.isTablet) {
            tv_name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
        } else {
            tv_name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        }
        int padd = Utils.getValueDp(2);
        tv_name.setPadding(Utils.getValueDp(7), Utils.getValueDp(10),
                Utils.getValueDp(7), Utils.getValueDp(10));
        tv_name.setText(Config.getInstance().getText("Category").toUpperCase());
        if (DataLocal.isLanguageRTL) {
            tv_name.setGravity(Gravity.RIGHT);
        }
        tv_name.setTextColor(Config.getInstance().getContent_color());

        LinearLayout ll_cat = new LinearLayout(mContext);
        if (DataLocal.isTablet) {
            height = Utils.getValueDp(250);
            LinearLayout.LayoutParams spot_ll = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, height);
            ll_cat.setPadding(0, padd, 0, 0);
            ll_cat.setLayoutParams(spot_ll);
        } else {
            height = Utils.getValueDp(140);
            LinearLayout.LayoutParams spot_ll = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, height);
            ll_cat.setPadding(0, padd, 0, 0);
            ll_cat.setLayoutParams(spot_ll);
        }
        HorizontalListView listview_category = new HorizontalListView(mContext,
                null);
        RelativeLayout.LayoutParams listh = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        listview_category.setLayoutParams(listh);

        HomeCategoryAdapter adapter = new HomeCategoryAdapter(mContext,
                listCategory);
        listview_category.setAdapter(adapter);
        categoryHomeListener = new CategoryHomeListener();
        categoryHomeListener.setListCategory(listCategory);
        listview_category.setOnItemClickListener(categoryHomeListener
                .createOnTouchCategory());

        ll_cat.addView(listview_category);
        ll_category.addView(tv_name);
        ll_category.addView(ll_cat);

        View view = new View(mContext);
        view.setBackgroundColor(Config.getInstance().getLine_color());
        LinearLayout.LayoutParams lp_view = new LinearLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, 1);
        ll_category.addView(view, lp_view);


    }

    @Override
    public void showLoadingHome() {
        rl_home.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissLoadingHome() {
        rl_home.setVisibility(View.GONE);
    }
}
