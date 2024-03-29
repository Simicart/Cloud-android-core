package com.simicart.theme.ztheme.home.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

import com.simicart.core.catalog.category.entity.Category;
import com.simicart.core.common.DrawableManager;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Rconfig;
import com.simicart.theme.ztheme.home.entity.CategoryZTheme;
import com.simicart.theme.ztheme.home.entity.SpotProductZTheme;

public class HomeZThemeAdapterTablet extends BaseAdapter {
	private final Context mContext;
	private ArrayList<CategoryZTheme> mCategories;

	public HomeZThemeAdapterTablet(Context context,
			ArrayList<CategoryZTheme> list) {
		mContext = context;
		this.mCategories = list;
	}

	public void addNewEntity(CategoryZTheme category)
	{
		mCategories.add(category);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		View rootView = null;
		if (rootView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rootView = inflater.inflate(
					Rconfig.getInstance().getId("ztheme_item_home_layout",
							"layout"), null);
			holder = new ViewHolder();
			holder.im_cate = (ImageView) rootView.findViewById(Rconfig
					.getInstance().getId("img_category", "id"));
			holder.tv_title = (TextView) rootView.findViewById(Rconfig
					.getInstance().id("tv_title"));
			rootView.setTag(holder);
		} else {
			rootView = (View) convertView;
			holder = (ViewHolder) convertView.getTag();
		}

		CategoryZTheme category = mCategories.get(position);
		String id_category = category.getCategoryID();

		if (Utils.validateString(id_category) && id_category.equals("fake")) {
			holder.im_cate.setImageResource(Rconfig.getInstance().drawable(
					"ztheme_fake_cate_tablet"));
			holder.im_cate.setScaleType(ScaleType.FIT_XY);
			ImageView img_default = (ImageView) convertView
					.findViewById(Rconfig.getInstance().getId("img_default",
							"id"));
			img_default.setVisibility(View.GONE);
		} else {
			String url = "";
			if (category.getType() == CategoryZTheme.TYPE_CAT) {
				final Category object = mCategories.get(position);
				url = object.getCategoryImage();
			} else {
				SpotProductZTheme object = category.getSpotProductZTheme();
				url = object.getImage();
			}
			if (Utils.validateString(url)) {
				DrawableManager.fetchDrawableOnThread(url, holder.im_cate);
			}

			if (holder.tv_title != null) {
				if (mCategories.get(position).getCategoryName() == null
						|| mCategories.get(position).getCategoryName().equals("")) {
					holder.tv_title.setVisibility(View.GONE);
				} else {
					holder.tv_title.setText(mCategories.get(position)
							.getCategoryName());
				}
			}
		}

		return rootView;
	}

	static class ViewHolder {
		ImageView im_cate;
		TextView tv_title;
	}

	@Override
	public int getCount() {
		return mCategories.size();
	}

	@Override
	public Object getItem(int position) {
		return mCategories.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
}
