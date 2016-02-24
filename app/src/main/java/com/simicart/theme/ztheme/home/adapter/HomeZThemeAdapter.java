package com.simicart.theme.ztheme.home.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.simicart.core.catalog.category.entity.Category;
import com.simicart.core.common.DrawableManager;
import com.simicart.core.common.Utils;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.theme.ztheme.home.entity.CategoryZTheme;
import com.simicart.theme.ztheme.home.entity.SpotProductZTheme;

public class HomeZThemeAdapter extends BaseExpandableListAdapter {
    private final Context mContext;
    private ArrayList<CategoryZTheme> mCategories;

    public HomeZThemeAdapter(Context context, ArrayList<CategoryZTheme> list) {
        mContext = context;
        this.mCategories = list;
    }

    public void addNewEntity(CategoryZTheme category) {
        mCategories.add(category);
    }

    @Override
    public int getGroupCount() {
        return mCategories.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mCategories.get(groupPosition).getmCategories().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mCategories.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mCategories.get(groupPosition).getmCategories()
                .get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        // TODO Auto-generated method stub
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(
                    Rconfig.getInstance().getId("ztheme_item_home_layout",
                            "layout"), null);
            holder = new ViewHolder();
            holder.img_category = (ImageView) convertView.findViewById(Rconfig
                    .getInstance().getId("img_category", "id"));
            holder.tv_catename = (TextView) convertView.findViewById(Rconfig
                    .getInstance().id("tv_title"));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CategoryZTheme categoryZTheme = mCategories.get(groupPosition);
        SpotProductZTheme spotProductZTheme = categoryZTheme.getSpotProductZTheme();

        // name
        if (holder.tv_catename != null) {
            holder.tv_catename.setVisibility(View.VISIBLE);
            if (categoryZTheme.getType() == CategoryZTheme.TYPE_CAT) {
                String cateName = categoryZTheme.getCategoryName();
                if (Utils.validateString(cateName)) {
                    holder.tv_catename.setText(cateName);
                } else {
                    holder.tv_catename.setVisibility(View.GONE);
                }
            } else {
                String productName = spotProductZTheme.getName();
                if (Utils.validateString(productName)) {
                    holder.tv_catename.setText(productName);
                } else {
                    holder.tv_catename.setVisibility(View.GONE);
                }
            }
        }

        // image
        String url = "";
        if (categoryZTheme.getType() == CategoryZTheme.TYPE_CAT) {
            if (DataLocal.isTablet) {
                url = categoryZTheme.getTabImage();
            } else {
                url = categoryZTheme.getPhoneImage();
            }
        } else {
            url = spotProductZTheme.getImage();
        }
        if (Utils.validateString(url)) {
            DrawableManager.fetchDrawableOnThread(url,
                    holder.img_category);
        } else {
            holder.img_category.setImageResource(Rconfig.getInstance().drawable("default_logo"));
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater
                    .inflate(
                            Rconfig.getInstance().getId(
                                    "ztheme_item_list_category_layout",
                                    "layout"), null);
            TextView txt_category = (TextView) convertView.findViewById(Rconfig
                    .getInstance().getId("tv_catename", "id"));
            txt_category.setText(mCategories.get(groupPosition)
                    .getmCategories().get(childPosition).getCategoryName());
        }
        return convertView;
    }

    static class ViewHolder {
        TextView tv_catename;
        ImageView img_category;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
