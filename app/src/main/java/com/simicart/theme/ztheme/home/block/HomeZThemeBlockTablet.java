package com.simicart.theme.ztheme.home.block;

import java.util.ArrayList;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.catalog.category.fragment.CategoryFragment;
import com.simicart.core.catalog.categorydetail.fragment.CategoryDetailFragment;
import com.simicart.core.config.Rconfig;
import com.simicart.core.slidemenu.fragment.CateSlideMenuFragment;
import com.simicart.theme.ztheme.home.adapter.HomeZThemeAdapterTablet;
import com.simicart.theme.ztheme.home.entity.CategoryZTheme;

public class HomeZThemeBlockTablet extends HomeZThemeBlock implements
		OnTouchListener {

	ListView lv_category;
	FrameLayout fr_layout;
	CategoryZTheme current_Category;
	RelativeLayout rlt_home;
	HomeZThemeAdapterTablet adapter;

	ArrayList<CategoryZTheme> categoriesTree;

	public HomeZThemeBlockTablet(View view, Context context) {
		super(view, context);
	}

	public void setCategoryItemClickListener(OnItemClickListener listener) {
		lv_category.setOnItemClickListener(listener);
	}

	protected void closeCatSub() {
		Animation animation = AnimationUtils.loadAnimation(mContext, Rconfig
				.getInstance().getId("out_to_right", "anim"));
		fr_layout.startAnimation(animation);
		animation.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation arg0) {
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				fr_layout.removeAllViews();
				fr_layout.setVisibility(View.GONE);
			}
		});

	}

	@Override
	public void initView() {
		rlt_home = (RelativeLayout) mView.findViewById(Rconfig.getInstance()
				.id("rlt_home"));
		fr_layout = (FrameLayout) mView.findViewById(Rconfig.getInstance().id(
				"container2"));
		lv_category = (ListView) mView.findViewById(Rconfig.getInstance().id(
				"lv_category"));
		adapter = new HomeZThemeAdapterTablet(mContext, categories);
		lv_category.setAdapter(adapter);
		 //lv_category.setOnTouchListener(this);
	}

	protected void showCategoriesView(ArrayList<CategoryZTheme> categories) {
		adapter = new HomeZThemeAdapterTablet(mContext,
				categories);
		lv_category.setAdapter(adapter);
	}

	@Override
	public void showCatSub(CategoryZTheme category) {
		if (category.hasChild()) {
			CategoryFragment fragment = CategoryFragment.newInstance(
					category.getCategoryName(), category.getCategoryID());
			CateSlideMenuFragment.getIntance().openMenu();
			CateSlideMenuFragment.getIntance().replaceFragmentCategoryMenu(
					fragment);
		} else {
			CategoryDetailFragment fragment = CategoryDetailFragment
					.newInstance();
			fragment.setCategoryId(category.getCategoryID());
			fragment.setCategoryName(category.getCategoryName());
			if (category.getCategoryID().equals("-1")) {
				fragment.setUrlSearch("products");
			} else {
				fragment.setUrlSearch("categories");
			}
			SimiManager.getIntance().replaceFragment(fragment);
			SimiManager.getIntance().hideKeyboard();
		}
	}

	@Override
	public void setCategoryTree(ArrayList<CategoryZTheme> categoriesTree) {
		this.categoriesTree = categoriesTree;
	}

	@Override
	public void setSelection(int position) {
		lv_category.setSelection(position);
	}

	float down_x = 0;
	float down_y = 0;
	float up_x = 0;
	float up_y = 0;

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			down_x = event.getX();
			break;
		case MotionEvent.ACTION_UP:
			up_x = event.getX();
			float distance = Math.abs(up_x - down_x);

			System.out.println(distance);
			break;
		}
		return true;
	}

	@Override
	public void updateData(CategoryZTheme category) {
		adapter.addNewEntity(category);
		adapter.notifyDataSetChanged();
	}
}
