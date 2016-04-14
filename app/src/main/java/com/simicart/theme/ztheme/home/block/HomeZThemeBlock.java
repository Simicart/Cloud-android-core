package com.simicart.theme.ztheme.home.block;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ListView;
import android.widget.TextView;

import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.common.Utils;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.theme.ztheme.home.adapter.HomeZThemeAdapter;
import com.simicart.theme.ztheme.home.delegate.HomeZThemeDelegate;
import com.simicart.theme.ztheme.home.entity.CategoryZTheme;

public class HomeZThemeBlock extends SimiBlock implements HomeZThemeDelegate {

    private static int lastExpandedPosition = -1;
    ExpandableListView lv_category;
    HomeZThemeAdapter adapter;
    ArrayList<CategoryZTheme> categories;

    public HomeZThemeBlock(View view, Context context) {
        super(view, context);
        categories = new ArrayList<CategoryZTheme>();
    }

    public void setCategoryListener(OnGroupClickListener listener) {
        lv_category.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    lv_category.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;

                lv_category.setSelectedGroup(groupPosition);
            }
        });

        lv_category.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                // TODO Auto-generated method stub
            }
        });

        lv_category.setOnGroupClickListener(listener);
    }

    public void setChildCategoryListener(OnChildClickListener listener) {
        lv_category.setOnChildClickListener(listener);
    }

    @Override
    public void initView() {
        lv_category = (ExpandableListView) mView.findViewById(Rconfig
                .getInstance().id("lv_category"));
        if (!DataLocal.isTablet) {
            TextView view = new TextView(mContext);
            ListView.LayoutParams tv_lp = new ListView.LayoutParams(
                    ListView.LayoutParams.MATCH_PARENT, Utils.getValueDp(40));
            view.setLayoutParams(tv_lp);
            lv_category.addHeaderView(view);
            adapter = new HomeZThemeAdapter(mContext, categories);
            lv_category.setAdapter(adapter);
        }
    }

    @Override
    public void drawView(SimiCollection collection) {

        if (categories.size() > 0) {
            showCategoriesView(categories);
        }
    }

    protected void showCategoriesView(ArrayList<CategoryZTheme> categories) {
        adapter = new HomeZThemeAdapter(mContext, categories);
        lv_category.setAdapter(adapter);
    }

    @Override
    public void showCatSub(CategoryZTheme category) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setSelection(int position) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setCategoryTree(ArrayList<CategoryZTheme> categoriesTree) {
        // TODO Auto-generated method stub
    }

    @Override
    public void updateData(CategoryZTheme category) {
        if (!DataLocal.isTablet) {
            adapter.addNewEntity(category);
            adapter.notifyDataSetChanged();
        }
    }
}
